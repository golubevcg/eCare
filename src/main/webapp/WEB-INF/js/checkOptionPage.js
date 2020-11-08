//edit button action
function makePageEditable(){
    $('[name="SaveChangesButton"]').removeAttr('hidden');
    $('[name="blockConnectedContracts"]').removeAttr('hidden');
    $('[name="deleteOptionButton"]').removeAttr('hidden');
    $('[name="checkbox2"]').removeAttr('hidden');
    $('[name="name"]').attr("disabled", false);
    $('[name="connectionCost"]').attr("disabled", false);
    $('[name="price"]').attr("disabled", false);
    $('[name="shortDescription"]').attr("disabled", false);
    $('[name="selectedObligatoryOptions"]').attr("disabled", false);
    $('[name="selectedIncompatibleOptions"]').attr("disabled", false);
}

$(document).ready(function(){
    let oldName = $('#inputFormName').val();
    $.ajax({
        type: 'GET',
        url: '/checkOption/getIncompatibleAndObligatoryOptions/' + oldName,
        success: function(result){
            let parsedResult = JSON.parse(result);
            let incompatibleOptionNamesSet = parsedResult[0];
            let obligatoryOptionNamesSet = parsedResult[1];

            $('#selectedIncompatibleOptions').val(incompatibleOptionNamesSet).change();
            $('#selectedObligatoryOptions').val(obligatoryOptionNamesSet).change();

            let toDisableInObligatoryList = $('#selectedIncompatibleOptions').find(':selected');
            for (let i = 0; i < toDisableInObligatoryList.length; i++) {
                $("#" + toDisableInObligatoryList[i].id.replaceAll("incomp", "oblig") ).attr('disabled', true);
                $('#selectedObligatoryOptions').select2();
            }

            let toDisableInIncompList = $('#selectedObligatoryOptions').find(':selected');
            for (let i = 0; i < toDisableInIncompList.length; i++) {
                $("#" + toDisableInIncompList[i].id.replaceAll("oblig", "incomp") ).attr('disabled', true);
                $('#selectedIncompatibleOptions').select2();
            }
        }
    });

});

$(document).ready(function(){
    $(".mul-select").select2({
        tags: true,
        tokenSeparators: ['/',',',';'," "]
    });
});

function validateAndSubmitIfTrue(){

    let validation = true;

    let newName = $('#inputFormName').val();

    let newNameField = $('#nameFieldRequired');

    if(newName===""){
        newNameField.text("This field is required.");
        newNameField.removeAttr('hidden');
        validation=false;
    }else{
        if(newName.length<4){
            newNameField.text("Name must be more than 4 characters.");
            newNameField.removeAttr('hidden');
            validation=false;
        }else{
            $.ajax({
                type: 'GET',
                url: '/checkOption/checkNewName/' + newName,
                success: function(result){
                    if(result===true){
                        newNameField.text("Option with this name already exists.");
                        newNameField.removeAttr('hidden');
                        validation=false;
                    }
                }
            });

        }
    }

    if($('#inputFormConnCost').val()===""){
        $('#connectionFieldRequired').removeAttr('hidden');
        validation=false;
    }

    if($('#inputFormPrice').val()===""){
        $('#priceFieldRequired').removeAttr('hidden');
        validation=false;
    }

    let shortDescriptionFieldRequired = $('#shortDescriptionFieldRequired');
    let inputFormShortDisc = $('#inputFormShortDisc').val();
    if(inputFormShortDisc===""){
        shortDescriptionFieldRequired.text("This field is required.");
        shortDescriptionFieldRequired.removeAttr('hidden');
        validation=false;
    }else{
        if(inputFormShortDisc.length<8){
            shortDescriptionFieldRequired.text("Name must be more than 8 characters.");
            shortDescriptionFieldRequired.removeAttr('hidden');
            validation=false;
        }
    }

    if(validation===true) {
        let selectedObligatoryOptions = $('#selectedObligatoryOptions').select2('data');
        let selectedIncompatibleOptions = $('#selectedIncompatibleOptions').select2('data');

        let arrayOfArrays = [];
        arrayOfArrays.push(selectedObligatoryOptions);
        arrayOfArrays.push(selectedIncompatibleOptions);

        $.ajax({
            contentType: "application/json",
            type: 'POST',
            url: '/checkOption/submitArraysValues/',
            data: JSON.stringify(arrayOfArrays),
            success: function (result1) {
                $("#userDTOInputForm").submit();
            }
        })
    }


}

function deleteOption(){
    if (confirm("Are you sure you want to delete this option?")) {
        let optionName = $('#inputFormName').val();
        $.ajax({
            type: 'GET',
            url: '/checkOption/deleteOption/' + optionName,
            success: function(result){
                if(result===true){
                    location.href = '/workerOffice';
                }else{
                    alert("Option with this name was not found.")
                }
            }
        });
    } else {

    }
}

$(document).ready(function() {
    $('#selectedIncompatibleOptions').on('select2:select', function(evt) {
        let lastSelectedOptionId = evt.params.data.element.id.replaceAll('incomp', '');
        $('#oblig'+lastSelectedOptionId).attr('disabled', true);
        $('#selectedObligatoryOptions').select2();
    });

    $('#selectedIncompatibleOptions').on('select2:unselect', function(evt) {
        let lastSelectedOptionId = evt.params.data.element.id.replaceAll('incomp', '');
        $('#oblig'+lastSelectedOptionId).attr('disabled', false);
        $('#selectedObligatoryOptions').select2();
    });


    $('#selectedObligatoryOptions').on('select2:select', function(evt) {
        let lastSelectedOptionId = evt.params.data.element.id.replaceAll('oblig', '');
        $('#incomp'+lastSelectedOptionId).attr('disabled', true);
        $('#selectedIncompatibleOptions').select2();
    });

    $('#selectedObligatoryOptions').on('select2:unselect', function(evt) {
        let lastSelectedOptionId = evt.params.data.element.id.replaceAll('oblig', '');
        $('#incomp'+lastSelectedOptionId).attr('disabled', false);
        $('#selectedIncompatibleOptions').select2();
    });

    //method to prevent impossible dependency in incOptionSet
    $('#selectedIncompatibleOptions').on('select2:select', function(evt) {
        let lsts = evt.params.data.text;
        let expJson = { lastSelectedVal : lsts,
            selectedObligOptions : $('#selectedObligatoryOptions').val() };
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: '/newOption/checkIncOptionDependenciesToPreventImpossibleDependency/',
            data: JSON.stringify(expJson),
            success: function (result) {
                if(result===""){
                }else{
                    alert("Option '" + lsts + "' cannot be added to incompatible options list, because obligatory option " +
                        "or it's children have '" + lsts + "' in obligatory dependencies." +
                        "(to add this option you need to remove from obligatory options " +  JSON.parse(result));

                    let selectedIncompOptions = $('#selectedIncompatibleOptions').val();

                    for (let i = 0; i < selectedIncompOptions.length; i++) {
                        if(selectedIncompOptions[i] === lsts ){
                            selectedIncompOptions.splice(i);
                            let lastSelectedOptionId = evt.params.data.element.id.replaceAll('incomp', '');
                            $('#oblig'+lastSelectedOptionId).attr('disabled', false);
                            $('#selectedObligatoryOptions').select2();
                        }
                    }
                    $('#selectedIncompatibleOptions').val(selectedIncompOptions).trigger('change.select2');
                }
            }
        });
    });

    //method to prevent impossible dependency in obligOptSet
    $('#selectedObligatoryOptions').on('select2:select', function(evt) {
        let lsts = evt.params.data.text;
        let expJson = { lastSelectedVal : lsts,
            selectedIncOptions : $('#selectedIncompatibleOptions').val() };
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: '/newOption/checkOblOptionDependenciesToPreventImpossibleDependency/',
            data: JSON.stringify(expJson),
            success: function (result) {
                if(result===""){
                }else{
                    alert("Option '" + lsts + "' cannot be added to obligatory options list, because incompatible option " +
                        "or it's children have '" + lsts + "' in incompatible dependencies." +
                        "(to add this option you need to remove from incompatible options " +  JSON.parse(result));

                    let selectedIncompOptions = $('#selectedObligatoryOptions').val();

                    for (let i = 0; i < selectedIncompOptions.length; i++) {
                        if(selectedIncompOptions[i] === lsts ){
                            selectedIncompOptions.splice(i);
                            let lastSelectedOptionId = evt.params.data.element.id.replaceAll('incomp', '');
                            $('#oblig'+lastSelectedOptionId).attr('disabled', false);
                            $('#selectedIncompatibleOptions').select2();
                        }
                    }
                    $('#selectedObligatoryOptions').val(selectedIncompOptions).trigger('change.select2');
                }
            }
        });
    });

    //method to prevent recursion
    $('#selectedIncompatibleOptions').on('select2:select', function(evt) {
        let lsts = evt.params.data.text;
        let currentlyCheckedOptionId = $("#toPullOptionIdForAjax").attr('name');
        let expJson = { currentlyCheckedOptionId : currentlyCheckedOptionId,
            selectedIncOptions : $('#selectedIncompatibleOptions').val() };
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: '/checkOption/checkIncOptionDependenciesToPreventRecursion/',
            data: JSON.stringify(expJson),
            success: function (result) {
                if(result===""){
                }else{
                    alert( "To add this option you need to remove " +  $("#inputFormName").val() +
                        " from incompatible options inside " + JSON.parse(result));
                    let selectedIncompOptions = $('#selectedIncompatibleOptions').val();

                    for (let i = 0; i < selectedIncompOptions.length; i++) {
                        if(selectedIncompOptions[i] === lsts ){
                            selectedIncompOptions.splice(i);
                            let lastSelectedOptionId = evt.params.data.element.id.replaceAll('incomp', '');
                            $('#oblig'+lastSelectedOptionId).attr('disabled', false);
                            $('#selectedObligatoryOptions').select2();
                        }
                    }
                    $('#selectedIncompatibleOptions').val(selectedIncompOptions).trigger('change.select2');
                }
            }
        });
    });


    $('#selectedObligatoryOptions').on('select2:select', function(evt) {
        let lsts = evt.params.data.text;
        let currentlyCheckedOptionId = $("#toPullOptionIdForAjax").attr('name');
        let expJson = { currentlyCheckedOptionId : currentlyCheckedOptionId,
            selectedOblOptions : $('#selectedObligatoryOptions').val() };
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: '/checkOption/checkOblOptionDependenciesToPreventRecursion/',
            data: JSON.stringify(expJson),
            success: function (result) {
                if(result===""){
                }else{
                    alert( "To add this option you need to remove " +  $("#inputFormName").val() +
                        " from obligatory options inside " + JSON.parse(result));
                    let selectedObligOptions = $('#selectedObligatoryOptions').val();
                    for (let i = 0; i < selectedObligOptions.length; i++) {
                        if(selectedObligOptions[i] === lsts ){
                            selectedObligOptions.splice(i);
                            let lastSelectedOptionId = evt.params.data.element.id.replaceAll('oblig', '');
                            $('#incomp'+lastSelectedOptionId).attr('disabled', false);
                            $('#selectedObligatoryOptions').select2();
                        }
                    }
                    $('#selectedObligatoryOptions').val(selectedObligOptions).trigger('change.select2');
                }
            }
        });
    });

});







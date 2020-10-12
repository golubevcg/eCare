function makePageEditable(){
    $('[name="SaveChangesButton"]').removeAttr('hidden');
    $('[name="blockConnectedContracts"]').removeAttr('hidden');
    $('[name="deleteOptionButton"]').removeAttr('hidden');
    $('[name="checkbox2"]').removeAttr('hidden');
    $('[name="name"]').attr("disabled", false);
    $('[name="connectionCost"]').attr("disabled", false);
    $('[name="price"]').attr("disabled", false);
    $('[name="shortDiscription"]').attr("disabled", false);
    $('[name="selectedObligatoryOptions"]').attr("disabled", false);
    $('[name="selectedIncompatibleOptions"]').attr("disabled", false);

    var map = {};
    $('#selectedObligatoryOptions option').each(function () {
        if (map[this.value]) {
            $(this).remove()
        }
        map[this.value] = true;
    })

    var map = {};
    $('#selectedIncompatibleOptions option').each(function () {
        if (map[this.value]) {
            $(this).remove()
        }
        map[this.value] = true;
    })
}

// $(document).ready(function(){
//
//     let oldName = $('#inputFormName').val();
//     $.ajax({
//         type: 'GET',
//         url: '/checkOption/getIncompatibleAndObligatoryOptions/' + oldName,
//         success: function(result){
//
//             let incompatibleOptionNamesSet = result[0];
//             let obligatoryOptionNamesSet = result[1];
//
//             // $('#selectedObligatoryOptions option').each(function () {
//                 console.log(result[0].length);
//                 for (let i = 0; i < incompatibleOptionNamesSet.length; i++) {
//                     // console.log(incompatibleOptionNamesSet[i]);
//                 }
//             // })
//
//             $('#selectedIncompatibleOptions option').each(function () {
//
//             })
//         }
//     });
//
// });

$(document).ready(function(){
    $(".mul-select").select2({
        tags: true,
        tokenSeparators: ['/',',',';'," "]
    });
});

$(document).ready(function(){
    $('#selectedObligatoryOptions').on('change', function()
    {

        let selectedOption = $(this).val();

        $("#selectedIncompatibleOptions>option").map(function() {
            if(selectedOption[0] === $(this).val()){
                $(this).remove();
            }
        });

    });
});

$(document).ready(function(){
    $('#selectedIncompatibleOptions').on('change', function()
    {

        let selectedOption = $(this).val();

        $("#selectedObligatoryOptions>option").map(function() {
            if(selectedOption[0] === $(this).val()){
                $(this).remove();
            }
        });

    });
});

$(document).ready(function() {
    setTimeout(function() {
    let arrayOfSelectedObligatoryOptions = $('[name="obligatoryOptionElement"]');
    let obligatoryOptions = $('[name="obligatorySelectedElement"]');

    for (let i = 0; i < arrayOfSelectedObligatoryOptions.length; i++) {

        for (let j = 0; j < obligatoryOptions.length; j++) {
            if(arrayOfSelectedObligatoryOptions[i].text===obligatoryOptions[j].text){
                console.log(arrayOfSelectedObligatoryOptions[i].id);
                let searchedID = '#' + arrayOfSelectedObligatoryOptions[i].id;
                console.log(searchedID)
                console.log($(searchedID).id);
                $(searchedID).attr('selected', 'selected');
            }

        }
    }
    }, 2000);
});

function validateAndSubmitIfTrue(){

    let validation = "true";

    let newName = $('#inputFormName').val();

    if(newName===""){
        $('#nameFieldRequired').text("This field is required.");
        $('#nameFieldRequired').removeAttr('hidden');
        validation="false";
    }else{
        if(newName.length<4){
            $('#nameFieldRequired').text("Name must be more than 4 characters.");
            $('#nameFieldRequired').removeAttr('hidden');
            validation="false";
        }else{
            $.ajax({
                type: 'GET',
                url: '/checkOption/checkNewName/' + newName,
                success: function(result){
                    if(result==="true"){
                        $('#nameFieldRequired').text("Option with this name already exists.");
                        $('#nameFieldRequired').removeAttr('hidden');
                        validation="false";
                    }
                }
            });

        }
    }

    if($('#inputFormConnCost').val()===""){
        $('#connectionFieldRequired').removeAttr('hidden');
        validation="false";
    }

    if($('#inputFormPrice').val()===""){
        $('#priceFieldRequired').removeAttr('hidden');
        validation="false";
    }

    let inputFormShortDisc = $('#inputFormShortDisc').val();
    if(inputFormShortDisc===""){
        $('#shortDiscriptionFieldRequired').text("This field is required.");
        $('#shortDiscriptionFieldRequired').removeAttr('hidden');
        validation="false";
    }else{
        if(inputFormShortDisc.length<8){
            $('#shortDiscriptionFieldRequired').text("Name must be more than 8 characters.");
            $('#shortDiscriptionFieldRequired').removeAttr('hidden');
            validation="false";
        }
    }

    if(validation==="true"){
        $("#userDTOInputForm").submit();
    }

}

function deleteOption(){
    if (confirm("Are you sure you want to delete this option?")) {
        let optionName = $('#inputFormName').val();
        $.ajax({
            type: 'GET',
            url: '/checkOption/deleteOption/' + optionName,
            success: function(result){
                console.log(result);
                if(result==="true"){
                    location.href = '/workerOffice';
                }else{
                    alert("Option with this name was not found.")
                }
            }
        });
    } else {

    }
}





$(document).ready(function(){
    $(".mul-select").select2({
        tags: true,
        tokenSeparators: ['/',',',';'," "]
    });
});


function makePageEditable(){
    $('[name="SaveChangesButton"]').removeAttr('hidden');
    $('[name="blockConnectedContracts"]').removeAttr('hidden');
    $('[name="checkbox2"]').removeAttr('hidden');
    $('[name="deleteOptionButton"]').removeAttr('hidden');
    $('[name="name"]').attr("disabled", false);
    $('[name="price"]').attr("disabled", false);
    $('[name="shortDiscription"]').attr("disabled", false);
    $('[name="selectedOptions"]').attr("disabled", false);
}

$(document).ready(function(){
    let oldName = $('#inputFormName').val();
    $.ajax({
        type: 'GET',
        url: '/checkTariff/getAvailableOptions/' + oldName,
        success: function(result){
            let parsedResult = JSON.parse(result);
            $('#selectedOptions').val(parsedResult).change();
        }
    });

    $('#selectedOptions').on('select2:select', function(evt) {
        let lastSelectedOptionName = evt.params.data.text;

        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: '/newOption/returnAllObligatoryOptions/',
            data: JSON.stringify(lastSelectedOptionName),
            success: function (result) {
                let optionsArray = JSON.parse(result);

                if(optionsArray.length>0){
                    for (let i = 0; i < optionsArray.length; i++) {
                        let multiSelectList = $('#selectedOptions');
                        let currentDataArray = multiSelectList.val();
                        currentDataArray.push(optionsArray[i].name)
                        multiSelectList.val(currentDataArray);
                        multiSelectList.trigger('change');
                    }

                }
            }
        });

    });
});


function validateAndSubmitIfTrue(){

    let newName = $('#inputFormName').val();
    let validation = true;

    if(newName===""){
        $('#phoneNumberFieldRequired').text("This field is required.");
        $('#phoneNumberFieldRequired').removeAttr('hidden');
        validation="false";
    }else{
        if(newName.length<4){
            $('#nameFieldRequired').text("Name must be more than 4 characters.");
            $('#nameFieldRequired').removeAttr('hidden');
            validation="false";
        }else{
            $.ajax({
                type: 'GET',
                url: '/checkTariff/checkNewName/' + newName,
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

    if(validation.toString()==="true"){
        let selectedAvailableOptions = $('#selectedOptions').select2('data');

        $.ajax({
            contentType: "application/json",
            type: 'POST',
            url: '/checkTariff/submitArrayAvailableOption/',
            data: JSON.stringify(selectedAvailableOptions),
            success: function (result1) {
                $("#tariffDTOInputForm").submit();
            }
        })
    }

}

function deleteOption(){
    if (confirm("Are you sure you want to delete this tariff?")) {
        let tariffName = $('#inputFormName').val();
        $.ajax({
            type: 'GET',
            url: '/checkTariff/deleteTariff/' + tariffName,
            success: function(result){
                if(result.toString()==="true"){
                    location.href = '/workerOffice';
                }else{
                    alert("Tariff with this name was not found.")
                }
            }
        });
    } else {

    }
}


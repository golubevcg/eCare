
//edit button action event, to turn on edit mode on page (ADMIN only)
function makePageEditable(){
    $('[name="SaveChangesButton"]').removeAttr('hidden');
    $('[name="deleteOptionButton"]').removeAttr('hidden');
    $('[name="contractIsBlocked"]').removeAttr('hidden');
    $('[name="checkbox2"]').removeAttr('hidden');
    $('[name="contractNumber"]').attr("disabled", false);
    $('[name="usersList"]').attr("disabled", false);
    $('[name="selectedTariff"]').attr("disabled", false);
    $('[name="selectedOptions"]').attr("disabled", false);
}

//ajax request to load options by tariff
$(document).ready(function(){
    $('#tariffsList').on('change', function()
    {
        var selectedTariff = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/newContract/loadOptionByTariff/' + selectedTariff,
            success: function(result){
                var result = JSON.parse(result);
                var s = '';
                for(var i = 0; i < result.length; i++){
                    s+='<option value="' + result[i] + '">' + result[i] + '</option>';
                }
                $('#optionsList').html(s);
            }
        });
    });
});


$(document).ready(function(){
        var selectedTariff = $("#tariffsList").val();
        $.ajax({
            type: 'GET',
            url: '/newContract/loadOptionByTariff/' + selectedTariff,
            success: function(result){
                var result = JSON.parse(result);
                var s = '';
                for(var i = 0; i < result.length; i++){
                    s+='<option value="' + result[i] + '">' + result[i] + '</option>';
                }
                $('#optionsList').html(s);
            }
    });
});

/**
 * multi select list for tariff picking
 */
$(document).ready(function(){
    $(".mul-select").select2({
        tags: true,
        tokenSeparators: ['/',',',';'," "]
    });
})

/**
 * autocomplete search for user picking
 */
$(document).ready($(function() {
    $("#usersList").autocomplete({
        source: "/newContract/getUsersList",
        minLength: 2
    });

}));

$(document).ready(function(){
    let oldNumber = $('#numberLabel').val();
    $.ajax({
        type: 'GET',
        url: '/checkContract/getAddionalOptions/' + oldNumber,
        success: function(result){
            let parsedResult = JSON.parse(result);
            $('#optionsList').val(parsedResult).change();
        }
    });
});


function validateAndSubmitIfTrue() {
    let validation = true;

    let newNum = $('#numberLabel').val();
    let numberValidationField = $('#phoneNumberFieldRequired');
    let userValidationField = $('#selectedUserFieldRequired');

    if (newNum === "") {
        numberValidationField.text("This field is required.");
        numberValidationField.removeAttr('hidden');
        validation = false;
    } else {
        let re = new RegExp("[+]*([0-9]{11})");
        if (!re.test(newNum)) {
            numberValidationField.text("Phone number should look like this: +7XXXXXXXXXX.");
            numberValidationField.removeAttr('hidden');
            validation = false;
        } else {
            $.ajax({
                type: 'GET',
                url: '/checkContract/checkNewNumber/' + newNum,
                success: function (result) {
                    if (result === true) {
                        numberValidationField.text("Contract with this phone number already exists.");
                        numberValidationField.removeAttr('hidden');
                        validation = false;
                    }
                }
            });

        }
    }

    let selectedUser = $('#usersList').val();
    if(selectedUser === ""){
        userValidationField.text("This field is required.");
        userValidationField.removeAttr('hidden');
        validation = false;
    }else{
        let selectedUser = $('#usersList').val();
        $.ajax({
            type: 'GET',
            url: '/checkContract/checkUser/' + selectedUser,
            success: function (result) {
                if (result === false) {
                    userValidationField.text("Please select user from dropdown suggestions.");
                    userValidationField.removeAttr('hidden');
                    validation = false;
                }
            }
        });
    }

    if(validation===true){
        let newNum = $('#numberLabel').val();
        let selectedUserLogin = $('#usersList').val();
        let selectedTariff = $('#tariffsList').val();
        let selectedOptions = $('#optionsList').val();
        let isContractBlocked = $('#exampleCheck1').prop('checked');
        let exportArray = { newNum, selectedUserLogin, selectedTariff, selectedOptions, isContractBlocked};
        console.log(JSON.stringify(exportArray));
        $.ajax({
            contentType: "application/json",
            type: 'POST',
            url: '/checkContract/submitChanges/',
            data: JSON.stringify(exportArray),
            success: function(result) {
                if(result===true){
                    alert("Contract was successfully updated!");
                    location.href = '/workerOffice';
                }else{
                    alert("Error, contract was not updated");
                }
            }
        });
    }
}

function deleteContract(){
    if (confirm("Are you sure you want to delete this contract?")) {
        let contractNumber = $('#numberLabel').val();
        $.ajax({
            type: 'GET',
            url: '/checkContract/deleteContract/' + contractNumber,
            success: function(result){
                if(result===true){
                    location.href = '/workerOffice';
                }else{
                    alert("Contract with this number was not found.")
                }
            }
        });
    } else {

    }
}
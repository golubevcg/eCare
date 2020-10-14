
function makePageEditable(){
    $('#firstname').attr("disabled", false);
    $('#secondname').attr("disabled", false);
    $('#dateOfBirth').attr("disabled", false);
    $('#passportInfo').attr("disabled", false);
    $('#adress').attr("disabled", false);
    $('#email').attr("disabled", false);
    $('#login').attr("disabled", false);
    $('#saveChangesButton').removeAttr('hidden');
    $('#deleteUserButton').removeAttr('hidden');
}

$(document).ready(function(){
    $(".mul-select").select2({
        tags: true,
        tokenSeparators: ['/',',',';'," "]
    });
})

function validateAndSubmitIfTrue(){

    let validation = true;

    if($('#firstname').val()==""){
        $('#firstnameFieldRequired').removeAttr('hidden');
        validation = false;
    }

    if($('#secondname').val()==""){
        $('#secondnameFieldRequired').removeAttr('hidden');
        validation = false;
    }

    if($('#dateOfBirth').val()==""){
        $('#dateOfBirthFieldRequired').removeAttr('hidden');
        validation = false;
    }

    if($('#passportInfo').val()==""){
        $('#passportInfoFieldRequired').removeAttr('hidden');
        validation = false;
    }else{
        let newPassport = $('#passportInfo').val();
        $.ajax({
            type: 'GET',
            url: '/checkUser/checkPassportInfo/' + newPassport,
            success: function (result) {
                if (result.toString() === "true") {
                    $('#passportInfoFieldRequired').text("Passport with this number already exists.");
                    $('#passportInfoFieldRequired').removeAttr('hidden');
                    validation = "false";
                }
            }
        });
    }

    if($('#adress').val()==""){
        $('#adressInfoFieldRequired').removeAttr('hidden');
        validation = false;
    }

    if($('#email').val()==""){
        $('#emailInfoFieldRequired').removeAttr('hidden');
        validation = false;
    }else{
        let newEmail = $('#emailInfoFieldRequired').val();
        $.ajax({
            type: 'GET',
            url: '/checkUser/checkEmail/' + newEmail,
            success: function (result) {
                if (result.toString() === "true") {
                    $('#emailInfoFieldRequired').text("User with such email already registered.");
                    $('#emailInfoFieldRequired').removeAttr('hidden');
                    validation = "false";
                }
            }
        });
    }

    if($('#login').val()==""){
        $('#loginInfoFieldRequired').removeAttr('hidden');
        validation = false;
    }else{
        let newLogin = $('#login').val();
        $.ajax({
            type: 'GET',
            url: '/checkUser/checkLogin/' + newLogin,
            success: function (result) {
                if (result.toString() === "true") {
                    $('#loginInfoFieldRequired').text("User with such login already registered.");
                    $('#loginInfoFieldRequired').removeAttr('hidden');
                    validation = "false";
                }
            }
        });
    }

    if(validation){


    }





}
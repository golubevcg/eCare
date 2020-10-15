//edit button action
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
                if (result.toString() === "false") {
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
        let newEmail = $('#email').val();
        $.ajax({
            type: 'GET',
            url: '/checkUser/checkEmail/' + newEmail,
            success: function (result) {
                if (result.toString() === "false") {
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
                if (result.toString() === "false") {
                    $('#loginInfoFieldRequired').text("User with such login already registered.");
                    $('#loginInfoFieldRequired').removeAttr('hidden');
                    validation = "false";
                }
            }
        });
    }

    if(validation){

        let firstName = $('#firstname').val();
        let secondName = $('#secondname').val();
        let dateOfBirth = $('#dateOfBirth').val();
        let passportInfo = $('#passportInfo').val();
        let address = $('#adress').val();
        let email = $('#email').val();
        let login = $('#login').val();

        let exportArray = { firstName, secondName, dateOfBirth, passportInfo, address, email, login};

        console.log(dateOfBirth);
        $.ajax({
            contentType: "application/json",
            type: 'POST',
            url: '/checkUser/submitChanges/',
            data: JSON.stringify(exportArray),
            success: function(result) {
                if(result.toString()==="true"){
                    location.href = '/workerOffice';
                }else{
                    alert("Error, user was not updated");
                }
            }
        });

    }

}

function deleteUser(){
    if (confirm("Are you sure you want to delete this User?")) {
        let login = $('#login').val();
        $.ajax({
            type: 'GET',
            url: '/checkUser/deleteUser/' + login,
            success: function(result){
                if(result.toString()==="true"){
                    location.href = '/workerOffice';
                }else{
                    alert("User with this login was not found.")
                }
            }
        });
    } else {

    }
}
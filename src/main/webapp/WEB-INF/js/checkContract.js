
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

$(document).ready(function(){
    $(".mul-select").select2({
        tags: true,
        tokenSeparators: ['/',',',';'," "]
    });
})

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

    let foo = $('#selectedOptions').val();
    let validation = "true";

    let newNum = $('#numberLabel').val();
    console.log(newNum);

    var re = new RegExp("[+]*([0-9]{11})");
    if (newNum === "") {
        $('#nameFieldRequired').text("This field is required.");
        $('#nameFieldRequired').removeAttr('hidden');
        validation = "false";
    } else {
        if (re.test(newNum)) {
            $('#nameFieldRequired').text("Phone number should look like this: +7XXXXXXXXXX.");
            $('#nameFieldRequired').removeAttr('hidden');
        } else {
            $.ajax({
                type: 'GET',
                url: '/checkContract/checkNewNumber/' + newNum,
                success: function (result) {
                    if (result === "true") {
                        $('#nameFieldRequired').text("Contract with this phone number already exists.");
                        $('#nameFieldRequired').removeAttr('hidden');
                        validation = "false";
                    }
                }
            });

        }
    }

    let selectedUser = $('#usersList').val();
    if(selectedUser === ""){
        $('#nameFieldRequired').text("This field is required.");
        $('#nameFieldRequired').removeAttr('hidden');
    }else{

    }
}
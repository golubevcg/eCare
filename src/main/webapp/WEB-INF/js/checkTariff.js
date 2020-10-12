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

    var map = {};
    $('#selectedOptions option').each(function () {
        if (map[this.value]) {
            $(this).remove()
        }
        map[this.value] = true;
    })
}


function validateAndSubmitIfTrue(){


    let foo = $('#selectedOptions').val();
    console.log(foo.length);
    // let validation = "true";
    //
    // let newName = $('#inputFormName').val();
    //
    // if(newName===""){
    //     $('#nameFieldRequired').text("This field is required.");
    //     $('#nameFieldRequired').removeAttr('hidden');
    //     validation="false";
    // }else{
    //     if(newName.length<4){
    //         $('#nameFieldRequired').text("Name must be more than 4 characters.");
    //         $('#nameFieldRequired').removeAttr('hidden');
    //         validation="false";
    //     }else{
    //         $.ajax({
    //             type: 'GET',
    //             url: '/checkTariff/checkNewName/' + newName,
    //             success: function(result){
    //                 if(result==="true"){
    //                     $('#nameFieldRequired').text("Option with this name already exists.");
    //                     $('#nameFieldRequired').removeAttr('hidden');
    //                     validation="false";
    //                 }
    //             }
    //         });
    //
    //     }
    // }
    //
    // if($('#inputFormPrice').val()===""){
    //     $('#priceFieldRequired').removeAttr('hidden');
    //     validation="false";
    // }
    //
    // let inputFormShortDisc = $('#inputFormShortDisc').val();
    // if(inputFormShortDisc===""){
    //     $('#shortDiscriptionFieldRequired').text("This field is required.");
    //     $('#shortDiscriptionFieldRequired').removeAttr('hidden');
    //     validation="false";
    // }else{
    //     if(inputFormShortDisc.length<8){
    //         $('#shortDiscriptionFieldRequired').text("Name must be more than 8 characters.");
    //         $('#shortDiscriptionFieldRequired').removeAttr('hidden');
    //         validation="false";
    //     }
    // }
    //
    // if($('#inputFormPrice').val()===""){
    //     $('#selectedOptionsField').removeAttr('hidden');
    //     validation="false";
    // }
    //
    //
    //
    // if(validation==="true"){
    //     $("#userDTOInputForm").submit();
    // }

}


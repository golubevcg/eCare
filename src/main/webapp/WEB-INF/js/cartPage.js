function remove(button){

    let contractNumber = $('#contractNumber').val();
    $.ajax({
        type: 'GET',
        url: '/cartPage/removeContractFromSession/' + contractNumber,
        success: function(result){
        }
    });
    $('#' + button.attr('id')).remove();

}
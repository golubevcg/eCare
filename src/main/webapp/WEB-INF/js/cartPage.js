function removeContract(button){
    let contractNumber = $('#contractNumber').text();
    $.ajax({
        type: 'GET',
        url: '/cartPage/removeContractChangingFromSession/' + contractNumber,
    });
    $('#' + button.attr('id')).remove();
}

function removeTariff(button){
    let contractNumber = $('#contractNumber').text();
    $.ajax({
        type: 'GET',
        url: '/cartPage/removeIsBlockedInContractFromSession/' + contractNumber,
    });
    $('#' + button.attr('id')).remove();
}

function removeIsBlocked(button){
    let contractNumber = $('#contractNumber').text();
    $.ajax({
        type: 'GET',
        url: '/cartPage/removeIsBlockedInContractFromSession/' + contractNumber,
    });
    $('#' + button.attr('id')).remove();
}



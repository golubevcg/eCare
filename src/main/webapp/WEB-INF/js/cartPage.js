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
        url: '/cartPage/removeTariffChangingFromSession/' + contractNumber,
    });
    $('#' + button.attr('id')).remove();
    $(".optionsDiv").remove();
}

function removeIsBlocked(button){
    let contractNumber = $('#contractNumber').text();
    $.ajax({
        type: 'GET',
        url: '/cartPage/removeIsBlockedInContractFromSession/' + contractNumber,
    });
    $('#' + button.attr('id')).remove();
}

function removeOption(button){
    let optionName = {"optionName" : button.attr('name')};
    let contractNumber = $('#contractNumber').text();
    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: '/cartPage/removeOptionInContractFromSession/' + contractNumber,
        processData:false,
        data: JSON.stringify(optionName)
    });
    let removeName ="#" + button.attr('id').replace(" ", "")+"div";
    $(removeName).remove();
}

function submitChanges(){
    $.ajax({
        type: 'GET',
        url: '/cartPage/submitChanges/',
        processData:false,
        success(result){
            console.log(result);
            if(result.toString()==="true"){
                location.href = '/contracts';
            }else{
                alert("Error, contract was not updated");
            }
        }
    });


}


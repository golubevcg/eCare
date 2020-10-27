function submitAd(){

    let arr = [$('#tariffslist1').val(), $('#tariffslist2').val(), $('#tariffslist3').val()];

    $.ajax({
        contentType: "application/json",
        type: 'POST',
        url: '/adPage/submit',
        data: JSON.stringify(arr),
        success(result){
            alert("Ad was successfully updated!");
            location.reload();
        }
    });

}

function changeDescriptionAndPrice(listNode){
        let selectedTariff = listNode.val();
        let selectedId = listNode.attr('id');

        $.ajax({
                type: 'GET',
                url: '/adPage/getTariffInfo/' + selectedTariff,
                success: function(result){
                    let parsedResult = JSON.parse(result);
                    $("#" + selectedId + "description").text(parsedResult[0]);
                    $("#" + selectedId + "price").text(parsedResult[1]);
                }
            });

}
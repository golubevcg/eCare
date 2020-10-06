function onSubmitClick(){

    var optionName = $("[name='optionNameForm']").val();
    var connectionCost = $("[name='connectionCostForm']").val();
    var price = $("[name='priceForm']").val();
    var shortDiscription = $("[name='shortDiscriptionForm']").val();
    var incompatibleOptions = $("[name='IncompatibleOptionsList']").val();
    var oblgatoryOptions = $("[name='ObligatoryOptionsList']").val();

    var exportObject = {optionName:optionName,
        connectionCost:connectionCost,
        price:price,
        shortDiscription:shortDiscription,
        incompatibleOptions:incompatibleOptions,
        oblgatoryOptions:oblgatoryOptions}

    $.ajax({
        contentType: "application/json",
        url: '/newOption/submitvalues',
        type: 'POST',
        data: JSON.stringify(exportObject),
        success: function (result) {
        }
    });
}

$(document).ready(function(){
    $(".mul-select").select2({
        tags: true,
        tokenSeparators: ['/',',',';'," "]
    });
})

$(document).ready(function(){
    $(".mul-select").select2({
        tags: true,
        tokenSeparators: ['/',',',';'," "]
    });


    $('#selectedOptions').on('select2:select', function(evt) {
        let lastSelectedOptionName = evt.params.data.text;

        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: '/newOption/returnAllObligatoryOptions/',
            data: JSON.stringify(lastSelectedOptionName),
            success: function (result) {
                let optionsArray = JSON.parse(result);

                if(optionsArray.length>0){
                    for (let i = 0; i < optionsArray.length; i++) {
                        let multiSelectList = $('#selectedOptions');
                        let currentDataArray = multiSelectList.val();
                        currentDataArray.push(optionsArray[i].name)
                        multiSelectList.val(currentDataArray);
                        multiSelectList.trigger('change');
                    }
                }
            }
        });

    });
});
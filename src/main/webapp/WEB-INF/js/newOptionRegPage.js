$(document).ready(function(){
    $(".mul-select").select2({
        tags: true,
        tokenSeparators: ['/',',',';'," "]
    });
});

$(document).ready(function() {
    $('#selectedIncompatibleOptions').on('select2:select', function(evt) {
        let lastSelectedOptionId = evt.params.data.element.id.replaceAll('incomp', '');
        $('#oblig'+lastSelectedOptionId).attr('disabled', true);
        $('#selectedObligatoryOptions').select2();
    });

    $('#selectedIncompatibleOptions').on('select2:unselect', function(evt) {
        let lastSelectedOptionId = evt.params.data.element.id.replaceAll('incomp', '');
        $('#oblig'+lastSelectedOptionId).attr('disabled', false);
        $('#selectedObligatoryOptions').select2();
    });


    $('#selectedObligatoryOptions').on('select2:select', function(evt) {
        let lastSelectedOptionId = evt.params.data.element.id.replaceAll('oblig', '');
        $('#incomp'+lastSelectedOptionId).attr('disabled', true);
        $('#selectedIncompatibleOptions').select2();
    });

    $('#selectedObligatoryOptions').on('select2:unselect', function(evt) {
        let lastSelectedOptionId = evt.params.data.element.id.replaceAll('oblig', '');
        $('#incomp'+lastSelectedOptionId).attr('disabled', false);
        $('#selectedIncompatibleOptions').select2();
    });

    $('#selectedIncompatibleOptions').on('select2:select', function(evt) {
        let lsts = evt.params.data.text;
        let expJson = { lastSelectedVal : lsts,
                        selectedObligOptions : $('#selectedObligatoryOptions').val() };
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: '/newOption/checkIncOptionDependenciesToPreventRecursion/',
            data: JSON.stringify(expJson),
            success: function (result) {
                console.log("incResult="+result)
                if(result===""){
                }else{
                    alert("Option '" + lsts + "' cannot be added to incompatible options list, because obligatory option " +
                        "or it's children have '" + lsts + "' in obligatory dependencies." +
                        "(to add this option you need to remove from obligatory options " +  JSON.parse(result));

                    let selectedIncompOptions = $('#selectedIncompatibleOptions').val();

                    for (let i = 0; i < selectedIncompOptions.length; i++) {
                        if(selectedIncompOptions[i] === lsts ){
                            selectedIncompOptions.splice(i);
                            let lastSelectedOptionId = evt.params.data.element.id.replaceAll('incomp', '');
                            $('#oblig'+lastSelectedOptionId).attr('disabled', false);
                            $('#selectedObligatoryOptions').select2();
                        }
                    }
                    $('#selectedIncompatibleOptions').val(selectedIncompOptions).trigger('change.select2');
                }
            }
        });

    });

    $('#selectedObligatoryOptions').on('select2:select', function(evt) {
        let lsts = evt.params.data.text;
        let expJson = { lastSelectedVal : lsts,
            selectedIncOptions : $('#selectedIncompatibleOptions').val() };
        console.log(expJson);
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: '/newOption/checkOblOptionDependenciesToPreventRecursion/',
            data: JSON.stringify(expJson),
            success: function (result) {
                console.log("result="+result);
                if(result===""){
                }else{
                    alert("Option '" + lsts + "' cannot be added to obligatory options list, because incompatible option " +
                        "or it's children have '" + lsts + "' in incompatible dependencies." +
                        "(to add this option you need to remove from incompatible options " +  JSON.parse(result));

                    let selectedIncompOptions = $('#selectedObligatoryOptions').val();

                    for (let i = 0; i < selectedIncompOptions.length; i++) {
                        if(selectedIncompOptions[i] === lsts ){
                            selectedIncompOptions.splice(i);
                            let lastSelectedOptionId = evt.params.data.element.id.replaceAll('incomp', '');
                            $('#oblig'+lastSelectedOptionId).attr('disabled', false);
                            $('#selectedIncompatibleOptions').select2();
                        }
                    }
                    $('#selectedObligatoryOptions').val(selectedIncompOptions).trigger('change.select2');
                }
            }
        });

    });

});



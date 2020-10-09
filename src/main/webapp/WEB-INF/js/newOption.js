$(document).ready(function(){
    $(".mul-select").select2({
        tags: true,
        tokenSeparators: ['/',',',';'," "]
    });
});

$(document).ready(function(){
    $('#selectedObligatoryOptions').on('change', function()
    {

        let selectedOption = $(this).val();

        $("#selectedIncompatibleOptions>option").map(function() {
            if(selectedOption[0] === $(this).val()){
                $(this).remove();
            }
        });

    });
});

$(document).ready(function(){
    $('#selectedIncompatibleOptions').on('change', function()
    {

        let selectedOption = $(this).val();

        $("#selectedObligatoryOptions>option").map(function() {
            if(selectedOption[0] === $(this).val()){
                $(this).remove();
            }
        });

    });
});




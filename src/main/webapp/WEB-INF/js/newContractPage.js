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
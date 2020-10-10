$(document).ready(function(){

    $('#tariffsList').on('change', function()
    {
        var selectedTariff = $(this).val();
        $.ajax({
            type: 'GET',
            url: '${pageContext.request.contextPath}/newContract/loadOptionByTariff/' + selectedTariff,
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
    $('#tariffsList').on('change', function()
    {
        $('.mul-select').val(null).trigger('change');;
    });
});


// $(document).ready(function(){
//     $('#usersList').on('change', function()
//     {
//         var searchInput = $(this);
//         console.log($(this));
//
//         $.ajax({
//             contentType: "application/json",
//             url: '/newContract/getUsersList',
//             type: 'POST',
//             data: JSON.stringify(searchInput),
//             success: function(result){
//                 console.log(result);
//                 // var result = JSON.parse(result);
//                 // var s = '';
//                 // for(var i = 0; i < result.length; i++){
//                 //     s+='<option value="' + result[i] + '">' + result[i] + '</option>';
//                 // }
//                 // $('#optionsList').html(s);
//             }
//         });
//     });
// });

$(document).ready($(function() {
    $("#usersList").autocomplete({
        source: "/newContract/getUsersList",
        minLength: 2

    });

}));
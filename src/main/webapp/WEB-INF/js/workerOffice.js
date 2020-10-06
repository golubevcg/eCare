

// function clickContractListChangePage(){
//     let curId = $(this).attr("id");
//     console.log(curId);
    // searchContractByPhoneNumber(searchInput, i+1);
// }

function searchContractByPhoneNumber(searchInput, currentPage) {

    if(searchInput!=""){
        console.log("searchInput is undefined");
        searchInput = $('#searchByPhoneNumberInputForm').val();
        console.log("searchInput from input val: " + searchInput);
    }

    var exportStr ="";
    if(currentPage!=""){
        console.log("currentPage is undefined");
        currentPage=0;
        console.log("currentPage setted to 0");
    }

    if(searchInput != ""){
        $.ajax({
        type: 'GET',
        url: '/workerOffice/searchInContracts/' + searchInput,
        success: function (result) {

            if(currentPage){
            }else{
                currentPage=0;
            }
            let maxItemsPerPage = 5;
            let resultLength = result.length;
            let pagesAmount = Math.floor((resultLength + 1) / maxItemsPerPage);

            for (let i = currentPage * maxItemsPerPage; i < maxItemsPerPage; i++) {
                    exportStr = exportStr
                        + "<hr style=\"margin-top:10px; width:100%;\">"
                        + "<div class=\"row\">"
                        + "<div class=\"col-1\">"
                        + "<p class=\"lead\" id=\"columnDiscriptionLabels\">" + (i+1) + ".</p>"
                        + "</div>"

                        + "<div class=\"col-3\">"
                        + "<p class=\"lead\" id=\"columnContentLabels\">" + result[i].contractNumber + "</p>"
                        + "</div>"

                        + "<div class=\"col-4\">"
                        + "<p class=\"lead\" id=\"columnContentLabels\">"
                        + result[i].user.firstname + " " + result[i].user.secondname + "</p>"
                        + "</div>"

                        + "<div class=\"col-2\">"
                        + "<p class=\"lead\" id=\"columnContentLabels\">" + result[i].tariff.name + "</p>"
                        + "</div>"

                        + "<div class=\"col-2\">"
                        + "<button type=\"button\" class=\"btn btn-primary btn-lg btn-lg\" id=\"editButton\">Edit</button>"
                        + "</div>"
                        + "</div>";
            }

            if(resultLength > maxItemsPerPage){
                exportStr = exportStr
                    + "<div class='row'"
                    + "<div class=\"col-4\"></div>"
                    + "<div class=\"col-4\">"
                    + "<nav aria-label=\"...\">"
                    + "<ul class=\"pagination pagination-sm\">";

                for (let i = 0; i < pagesAmount; i++) {
                    if(i===currentPage){
                        exportStr = exportStr
                            + "<li class=\"btn page-item active\">"
                            + "<a class=\"page-link\" id=\""
                            + searchInput + "and" + (i+1) + "\">"
                            + (i+1) + "</a></li>";
                    }else{
                    exportStr = exportStr
                        + "<li class=\"btn page-item\">"
                        + "<a class=\"page-link\" name='pageContrBut' id=\"" + searchInput + "and" + (i+1) + "\">"
                        + (i+1) + "</a></li>";
                    }
                }
                    exportStr = exportStr
                      + "</ul>"
                      + "</nav>"
                      + "</div>"
                      + "<div class=\"col-3\"></div>"
                      + "</div>";

            }


            document.getElementById("foundedContractsRow")
                .innerHTML = exportStr;
        }
    })
    }else{
        document.getElementById("foundedContractsRow")
            .innerHTML = "";
    }
};

$('input[name="pageContrBut"]').on('click', function() {
    let curId = $(this).attr("id");
    console.log("CLICKINGWORKING");
    console.log(curId);
});

function searchTariffByName() {
    let searchInput = $('#searchByTariffNameInputForm').val();
    var exportStr ="";
    if(searchInput != ""){
        $.ajax({
        type: 'GET',
        url: '/workerOffice/searchInTariffs/' + searchInput,
        success: function (result) {
            console.log(result);
            for (let i = 0; i < result.length; i++) {

                    exportStr = exportStr
                        + "<hr style=\"margin-top:10px; width:100%;\">"
                        + "<div class=\"row\">"
                        + "<div class=\"col-5\">"
                        + "<p class=\"lead\" id=\"columnContentLabels\">" + result[i].name + "</p>"
                        + "</div>"
                        + "<div class=\"col-3\">"
                        + "<p class=\"lead\" id=\"columnContentLabels\">"+ result[i].price + "</p>"
                        + "</div>"

                        + "<div class=\"col-2\"></div>"

                        + "<div class=\"col-2\">"
                        + "<button type=\"button\" class=\"btn btn-primary btn-lg btn-lg\" id=\"editButton\">Edit</button>"
                        + "</div>"
                        + "</div>";

                }
            document.getElementById("foundedTariffsRow")
                .innerHTML = exportStr;
        }
    })
    }else{
        document.getElementById("foundedTariffsRow")
            .innerHTML = "";
    }
};

function searchOptionByName() {
    let searchInput = $('#searchByOptionNameInputForm').val();
    var exportStr = "";
    if(searchInput != ""){
        $.ajax({
            type: 'GET',
            url: '/workerOffice/searchInOptions/' + searchInput,
            success: function (result) {

                        for (let i = 0; i < result.length; i++) {
                            exportStr = exportStr
                                + "<hr style=\"margin-top:10px; width:100%;\">"
                                + "<div class=\"row\">"
                                + "<div class=\"col-3\">"
                                + "<p class=\"lead\" id=\"columnContentLabels\">" + result[i].name + "</p>"
                                + "</div>"

                                + "<div class=\"col-3\">"
                                + "<p class=\"lead\" id=\"columnContentLabels\">" + result[i].connectionCost + "</p>"
                                + "</div>"

                                + "<div class=\"col-3\">"
                                + "<p class=\"lead\" id=\"columnContentLabels\">" + result[i].price + " $/month </p>"
                                + "</div>"

                                + "<div class=\"col-3\">"
                                + "<button type=\"button\" class=\"btn btn-primary btn-lg btn-lg\" id=\"editButton\">Edit</button>"
                                + "</div>"
                                + "</div>";
                        }
                    document.getElementById("foundedOptionsRow")
                        .innerHTML = exportStr;

                }
        })
    }else{
        document.getElementById("foundedOptionsRow")
            .innerHTML = "";
    }
};
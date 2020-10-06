function searchContractByPhoneNumber(searchInput, currentPage) {

    if(searchInput=="0"){
        searchInput = $('#searchByPhoneNumberInputForm').val();
    }

    var exportStr = "";
    if(searchInput != ""){

        $.ajax({
        type: 'GET',
        url: '/workerOffice/searchInContracts/' + searchInput,
        success: function (result) {
            let maxItemsPerPage = 5;
            let resultLength = result.length;
            let pagesAmount = Math.ceil((resultLength + 1) / maxItemsPerPage);

            let firstArrayValue = (currentPage) * maxItemsPerPage;

            let lastArrayValue = (currentPage+1) * maxItemsPerPage;
            if(lastArrayValue>result.length){
                lastArrayValue=result.length;
            }

            for (let i = firstArrayValue; i < lastArrayValue; i++) {
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
                        + "<li class=\"btn page-item\" name = \"pageChangerButton\" onclick=\"pageChanger(this.id)\" id=\"" + searchInput + "and" + (i+1) + "\">"
                        + "<a class=\"page-link\">"
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

function pageChanger(pageButtonId){
    let stringsArray = pageButtonId.split("and");
    searchContractByPhoneNumber(stringsArray[0], stringsArray[1]-1);
}

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
                        + "<button type=\"button\" class=\"btn btn-primary btn-lg btn-lg\" "
                        + "id=\"editButton\" style=\"margin-left:10px;\">Edit</button>"
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
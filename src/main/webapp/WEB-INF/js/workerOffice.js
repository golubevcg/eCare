function searchContractByPhoneNumber() {
    let searchInput = $('#searchByPhoneNumberInputForm').val();
    $.ajax({
        type: 'GET',
        url: '/workerOffice/searchInContracts/' + searchInput,
        success: function (result) {
            var exportStr ="";
            for (let i = 0; i < result.length; i++) {

                    exportStr = exportStr
                        + "<hr style=\"margin-top:10px; width:100%;\">"
                        + "<div class=\"row\">"
                        + "<div class=\"col-4\">"
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
            document.getElementById("foundedContractsRow")
                .innerHTML = exportStr;
        }
    })
};

function searchTariffByName() {
    let searchInput = $('#searchByTariffNameInputForm').val();
    $.ajax({
        type: 'GET',
        url: '/workerOffice/searchInTariffs/' + searchInput,
        success: function (result) {
            console.log(result);
            var exportStr ="";
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
};

function searchOptionByName() {
    let searchInput = $('#searchByTariffNameInputForm').val();
    $.ajax({
        type: 'GET',
        url: '/workerOffice/searchInTariffs/' + searchInput,
        success: function (result) {
            console.log(result);
            var exportStr ="";
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
};

//disables all checkboxes if contract blocked
$(document).ready(function() {
    if ($('input[name="blockNumberCheckBox"]').attr('checked') == "checked") {
        $('input[name="tariffCheckbox"]').attr("disabled", true);
        $('input[name="optionCheckbox"]').attr("disabled", true);
        $(".columnContentLabels").css('color', '#d3d3d3');
        $(".slider").css('background-color', '#d3d3d3');
    }
});

//dynamically updates options depending on currently selected tariff
$(document).ready(function(){
    $('input[name="tariffCheckbox"]').on('change', function() {

        $('input[name="' + this.name + '"]').not(this).prop('checked', false);

        let contractNumber = $('#numberLabel').text();

        $.ajax({
            contentType: "application/json",
            url: '/contractDetails/getTariffOptions/' + contractNumber,
            type: 'POST',
            data: JSON.stringify(this.id),
            success: function(result) {

                var finexp = "";

                for (var i = 0; i < result.length; i++) {

                    var finexp = finexp
                        + "<hr style=\"margin-top:10px; width:100%;\">"
                        + "<div class=\"col-3\">"
                        + "<p class=\"lead columnContentLabels\" name=\"label" + result[i].option_id + "\">" + result[i].name + "</p>"
                        + "</div>"

                        + "<div class=\"col-2\">"
                        + "<p class=\"lead columnContentLabels\" name=\"label" + result[i].option_id + "\">" + result[i].price + "</p>"
                        + "</div>"

                        + "<div class=\"col-5\">"
                        + "<p class=\"lead columnContentLabels\" name=\"label" + result[i].option_id + "\">" + result[i].shortDiscription + "</p>"
                        + "</div>"

                        +  "<div class=\"col-1\">"
                        + "<div class=\"form-check\">"

                        + "<label class=\"switch\" style=\"clear:both; \" name=\"group1\">"
                        + "<input type=\"checkbox\"  name=\"optionCheckbox\" onchange=\"checkSwitchesAndChangeIfNeeded($(this))\" id=\"" + result[i].option_id + "\">"
                        + "<span class=\"slider round\" id=Slider" + result[i].option_id + "\"></span>"
                        + "</label>"

                        + "</div>"
                        + "</div>"

                        + "</div>";

                }

                document.getElementById("enabledOptionsContainer")
                    .innerHTML = finexp;
            },
            error: function() {
            }
        });

    });
});

//submit values and send to controller
function onSubmitClick(){

    let tariffCheckboxes = document.getElementsByName("tariffCheckbox");
    let tariffSelectedCheckboxes = Array.prototype.slice.call(tariffCheckboxes).filter(ch => ch.checked==true)
        .map(ch=>ch.id.toString());

    let optionsCheckboxes = document.getElementsByName("optionCheckbox");
    let optionsSelectedCheckboxes = Array.prototype.slice.call(optionsCheckboxes).filter(ch => ch.checked==true)
        .map(ch=>ch.id.toString());

    let blockNubCheckBArray = document.getElementsByName("blockNumberCheckBox");
    let blockNumberCheckBox = Array.prototype.slice.call(blockNubCheckBArray).map(ch=>ch.checked.toString());

    let lockedOptionsArray = Array.prototype.slice.call(optionsCheckboxes).filter(ch => ch.disabled==true)
        .map(ch=>ch.id.toString());


    let exportObject = {optionsSelectedCheckboxes:optionsSelectedCheckboxes,
        tariffSelectedCheckboxes:tariffSelectedCheckboxes,
        blockNumberCheckBox:blockNumberCheckBox,
        lockedOptionsArray:lockedOptionsArray}

    let contractNumber = $('#numberLabel').text();
    $.ajax({
        contentType: "application/json",
        url: '/contractDetails/submitvalues/' + contractNumber,
        type: 'POST',
        data: JSON.stringify(exportObject),
        success: function (result) {
            if(result.toString()==="true"){
                alert("Contract was successfully updated!");
                location.reload();
            }else{
                alert("Contract was no updated.");
            }
        }
    });
}

//get locked options list to block them when page loads
$(document).ready(function(){
    let disabledOptionNames = $("[name='optionCheckbox']:checked");
    for (let i = 0; i < disabledOptionNames.length; i++) {
        checkSwitchesAndChangeIfNeeded($("#"+disabledOptionNames[i].id));
    }
});

//get from db two lists of options which incompatible\obligatory to currently changed option
//and apply this changes to checkboxes
function checkSwitchesAndChangeIfNeeded(selectedOption){
    let isChecked = selectedOption.prop('checked');
    let selectedOptionId = selectedOption.attr('id');
    let selectedTariffName = $("[name='tariffCheckbox']:checked").attr('id');

    let disabledOptionNames = $("[name='optionCheckbox']:disabled");
    let disabledOptionIds = "";
    for (let i = 0; i < disabledOptionNames.length; i++) {
        disabledOptionIds = disabledOptionIds + "," + disabledOptionNames[i].id;
    }

    let checkedOptionNames = $("[name='optionCheckbox']:checked");
    let checkedOptionIds = "";
    for (let i = 0; i < checkedOptionNames.length; i++) {
        checkedOptionIds = checkedOptionIds + "," + checkedOptionNames[i].id;
    }

    let arrayOfArrays = [];
    arrayOfArrays.push(selectedTariffName);
    arrayOfArrays.push(isChecked);
    arrayOfArrays.push(disabledOptionIds)
    arrayOfArrays.push(checkedOptionIds)

    $.ajax({
        contentType: "application/json",
        type: 'POST',
        url: '/contractDetails/loadDependedOptions/' + selectedOptionId,
        data: JSON.stringify(arrayOfArrays),
        success: function (result) {
            let incompatibleOptionsArray = result[0];
            let obligatoryOptionsArray = result[1];
            let errorMessageArray = result[2];

            let restOptionCheckboxes = $('input[name="' + selectedOption.attr('name') + '"]').not(this).toArray();
            let enablingApproved = true;

            if (isChecked === false) {

                        for (let i = 0; i < restOptionCheckboxes.length; i++) {

                            for (let j = 0; j < incompatibleOptionsArray.length; j++) {
                                if (restOptionCheckboxes[i].getAttribute('id') === incompatibleOptionsArray[j]) {
                                    removeDisabledFromCheckBox(restOptionCheckboxes[i].getAttribute('id'));
                                }
                            }

                            for (let j = 0; j < obligatoryOptionsArray.length; j++) {
                                if (restOptionCheckboxes[i].getAttribute('id') === obligatoryOptionsArray[j]) {
                                    removeDisabledFromCheckBox(restOptionCheckboxes[i].getAttribute('id'));
                                }
                            }

                        }
            } else {

                if(errorMessageArray[0].length>0){
                    alert(errorMessageArray[0] + " should be enabled.");
                    $("#" + selectedOptionId).prop("checked", false);
                    enablingApproved = false;
                }

                //then if enablingApproved is true -> then we start to do operations under this case
                if (enablingApproved) {
                    for (let i = 0; i < restOptionCheckboxes.length; i++) {
                        for (let j = 0; j < incompatibleOptionsArray.length; j++) {
                            if (restOptionCheckboxes[i].getAttribute('id') === incompatibleOptionsArray[j]) {
                                setCheckedAndDisable(restOptionCheckboxes[i].getAttribute('id'), false);
                            }
                        }

                        for (let k = 0; k < obligatoryOptionsArray.length; k++) {
                            if (restOptionCheckboxes[i].getAttribute('id') === obligatoryOptionsArray[k]) {
                                setCheckedAndDisable(restOptionCheckboxes[i].getAttribute('id'), true);
                            }
                        }

                    }

                }

            }
        }
    });

}

function setCheckedAndDisable(current_option_id, checkedValue){
    let currOpt = $("#" + current_option_id);
    currOpt.prop("checked", checkedValue);
    currOpt.attr("disabled", true);

    $("[name=label" + current_option_id + "]").css('color', '#d3d3d3');
    if(checkedValue){
        $("#Slider" + current_option_id).css('background-color', '#9acffa');
    }else{
        $("#Slider" + current_option_id).css('background-color', '#d3d3d3');
    }
}

function removeDisabledFromCheckBox(current_option_id){
    $("#" + current_option_id).attr("disabled", false);
    $("[name=label" + current_option_id + "]").css('color', 'black');
    $("#Slider" + current_option_id ).removeAttr("style");

}


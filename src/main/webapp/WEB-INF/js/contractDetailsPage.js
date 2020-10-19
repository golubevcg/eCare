
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

        $.ajax({
            contentType: "application/json",
            url: '/contractDetails/getTariffOptions',
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
                        + "<input type=\"checkbox\"  name=\"optionCheckbox\" id=\"" + result[i].option_id + "\">"
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

    var tariffCheckboxes = document.getElementsByName("tariffCheckbox");
    var tariffSelectedCheckboxes = Array.prototype.slice.call(tariffCheckboxes).filter(ch => ch.checked==true)
        .map(ch=>ch.id.toString());

    var optionsCheckboxes = document.getElementsByName("optionCheckbox");
    var optionsSelectedCheckboxes = Array.prototype.slice.call(optionsCheckboxes).filter(ch => ch.checked==true)
        .map(ch=>ch.id.toString());

    var blockNubCheckBArray = document.getElementsByName("blockNumberCheckBox");
    var blockNumberCheckBox = Array.prototype.slice.call(blockNubCheckBArray).map(ch=>ch.checked.toString());

    var lockedOptionsArray = Array.prototype.slice.call(optionsCheckboxes).filter(ch => ch.disabled==true)
        .map(ch=>ch.id.toString());


    var exportObject = {optionsSelectedCheckboxes:optionsSelectedCheckboxes,
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
    $.ajax({
        type: 'GET',
        url: '/contractDetails/getLockedOptions',
        success: function (result) {
            var optionCheckboxes = document.getElementsByName("optionCheckbox");
            for (let i = 0; i < optionCheckboxes.length; i++) {
                for (let j = 0; j < result.length; j++) {
                    if(optionCheckboxes[i].getAttribute('id') == result[j].option_id){
                        optionCheckboxes[i].setAttribute("disabled", true);
                        $("[name=" + optionCheckboxes[i].getAttribute('id') + "label]").css('color', '#d3d3d3');
                        $("#" + optionCheckboxes[i].getAttribute('id') + "Slider").css('background-color', '#d3d3d3');

                        if($("#" + optionCheckboxes[i].getAttribute('id')).prop("checked") == true) {
                            $("#" + optionCheckboxes[i].getAttribute('id') + "Slider").css('background-color', '#9acffa');
                        }

                    }

                }
            }
        }
    })
});


$(document).change(function(){
    $( "input[name='optionCheckbox']").on('change', function(){   checkSwitchesAndChangeIfNeeded($(this))  });
})

$(document).ready(function(){
    $( "input[name='optionCheckbox']").on('change', function(){   checkSwitchesAndChangeIfNeeded($(this))  });
})

//get from db two lists of options which incompatible\obligatory to currently changed option
//and apply this changes to checkboxes
function checkSwitchesAndChangeIfNeeded(selectedOption){
    let isChecked = selectedOption.prop('checked');
    let selectedOptionId = selectedOption.attr('id');
    let selectedTariffName = $("[name='tariffCheckbox']:checked").attr('id');

    let arrayOfArrays = [];
    arrayOfArrays.push(selectedTariffName);
    arrayOfArrays.push(isChecked);
    $.ajax({
        contentType: "application/json",
        type: 'POST',
        url: '/contractDetails/loadDependedOptions/' + selectedOptionId,
        data: JSON.stringify(arrayOfArrays),
        success: function (result) {
            let incompatibleOptionsArray = result[0];
            let obligatoryOptionsArray = result[1];

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

                //first we iterate over options, in which we need to change values
                //if someone of them blocked - we cancel changes and show alert
                for (let i = 0; i < restOptionCheckboxes.length; i++) {

                    for (let j = 0; j < incompatibleOptionsArray.length; j++) {
                        if (restOptionCheckboxes[i].getAttribute('id') === incompatibleOptionsArray[j]
                            && restOptionCheckboxes[i].getAttribute('disabled') === 'disabled'
                            && restOptionCheckboxes[i].checked === true) {
                            alert("id " + restOptionCheckboxes[i] + " this option should not be disabled, please enable this option.");
                            enablingApproved = false;
                            $("#" + selectedOptionId).prop("checked", false);
                        }
                    }

                    for (let j = 0; j < obligatoryOptionsArray.length; j++) {

                        if (restOptionCheckboxes[i].getAttribute('id') === obligatoryOptionsArray[j]
                            && restOptionCheckboxes[i].getAttribute('disabled') === 'disabled'
                            && restOptionCheckboxes[i].checked === false) {
                            alert("id " + restOptionCheckboxes[i] + " this option should not be disabled, please enable this option.");
                            enablingApproved = false;
                            $("#" + selectedOptionId).prop("checked", false);
                        }

                    }

                }

                //then if enablingApproved is true -> then we start to do operations under this case
                if (enablingApproved) {
                    for (let i = 0; i < restOptionCheckboxes.length; i++) {
                        for (let j = 0; j < incompatibleOptionsArray.length; j++) {
                            if (restOptionCheckboxes[i].getAttribute('id') === incompatibleOptionsArray[j]) {
                                turnOffCheckBoxAndDisable(restOptionCheckboxes[i].getAttribute('id'));
                            }
                        }

                        for (let k = 0; k < obligatoryOptionsArray.length; k++) {
                            if (restOptionCheckboxes[i].getAttribute('id') === obligatoryOptionsArray[k]) {
                                turnOnCheckBoxAndDisable(restOptionCheckboxes[i].getAttribute('id'));
                            }
                        }

                    }

                }

            }
        }
    });

}

function turnOffCheckBoxAndDisable(current_option_id){
    $("#" + current_option_id).prop("checked", false);
    $("#" + current_option_id).attr("disabled", true);
    $("[name=label" + current_option_id + "]").css('color', '#d3d3d3');
    $("#Slider" + current_option_id).css('background-color', '#d3d3d3');
}

function turnOnCheckBoxAndDisable(current_option_id){
    $("#" + current_option_id).prop("checked", true);
    $("#" + current_option_id).attr("disabled", true);
    $("[name=label" + current_option_id + "]").css('color', '#d3d3d3');
    $("#Slider" + current_option_id).css('background-color', '#9acffa');
}

function removeDisabledFromCheckBox(current_option_id){
    // $("#" + current_option_id).removeAttr('checked');
    $("#" + current_option_id).attr("disabled", false);
    $("[name=label" + current_option_id + "]").css('color', 'black');
    $("#Slider" + current_option_id ).removeAttr("style");
}


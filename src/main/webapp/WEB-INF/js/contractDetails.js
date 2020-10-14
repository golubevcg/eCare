
$(document).ready(function() {
    if ($('input[name="blockNumberCheckBox"]').attr('checked') == "checked") {
        $('input[name="tariffCheckbox"]').attr("disabled", true);

        $('input[name="optionCheckbox"]').attr("disabled", true);

        $(".columnContentLabels").css('color', '#d3d3d3');

        $(".slider").css('background-color', '#d3d3d3');
    }
});




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
                console.log('Error occured during fetching data from controller.');
            }
        });

    });
});

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

    console.log(JSON.stringify(lockedOptionsArray));

    var exportObject = {optionsSelectedCheckboxes:optionsSelectedCheckboxes,
        tariffSelectedCheckboxes:tariffSelectedCheckboxes,
        blockNumberCheckBox:blockNumberCheckBox,
        lockedOptionsArray:lockedOptionsArray}

    console.log(JSON.stringify(exportObject));

    $.ajax({
        contentType: "application/json",
        url: '/contractDetails/submitvalues',
        type: 'POST',
        data: JSON.stringify(exportObject),
        success: function (result) {
            location.reload();
        }
    });
}

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

function checkSwitchesAndChangeIfNeeded(selectedOption){
    let isChecked = selectedOption.prop('checked');
    let selectedOptionId = selectedOption.attr('id');
    console.log("==============================" + selectedOption.attr('name'))
    let restOptionCheckboxes = $('input[name="' + selectedOption.attr('name') + '"]').not(this).toArray();

    // console.log("length:" + restOptionCheckboxes.length);
    // console.log(selectedOption.attr('id'));

    let restOptionIds = [];
    let restOptionIdsCheckedValues = [];
    for (let i = 0; i < restOptionCheckboxes.length; i++) {
        restOptionIds.push(restOptionCheckboxes[i].getAttribute('id'));
        restOptionIdsCheckedValues.push(restOptionCheckboxes[i].checked);
        console.log(restOptionCheckboxes[i].checked);
    }

    let arrayOfArrays = [];
    arrayOfArrays.push(restOptionIds);
    arrayOfArrays.push(restOptionIdsCheckedValues);

    console.log(    JSON.stringify(arrayOfArrays)
    );

    $.ajax({
        contentType: "application/json",
        type: 'POST',
        url: '/contractDetails/loadDependedOptions/' + selectedOptionId,
        data: JSON.stringify(restOptionIds),
        success: function (result){
            let incompatibleOptionsArray = result[0];
            console.log("incompatibleOptionsArray:");
            console.log(result[0]);
            let obligatoryOptionsArray = result[1];
            console.log("obligatoryOptionsArray:");
            console.log(result[1]);

            if(isChecked===false){
                console.log("checked is false, looking for options to unlock...");
                $.ajax({
                    contentType: "application/json",
                    type: 'POST',
                    url: '/contractDetails/loadDependedOptionsSingleDepth/' + selectedOptionId,
                    data: JSON.stringify(restOptionIds),
                    success: function (result1){

                        let incompatibleOptionsArray1 = result1[0];
                        let obligatoryOptionsArray1 = result1[1];

                        console.log("TRD: ioa: incompatibleOptionsArray1.length");
                        console.log("TRD: ooa: obligatoryOptionsArray1.length");

                        for (let i = 0; i < restOptionCheckboxes.length; i++) {

                            for (let j = 0; j < incompatibleOptionsArray1.length; j++) {

                                if(restOptionCheckboxes[i].getAttribute('id') === incompatibleOptionsArray1[j]){
                                    console.log("TRD Incompatible option match found...");
                                    removeDisabledFromCheckBox(restOptionCheckboxes[i].getAttribute('id'));
                                    console.log(restOptionCheckboxes[i] + " disable removed.");
                                }

                            }

                            for (let j = 0; j < obligatoryOptionsArray1.length; j++) {
                                if(restOptionCheckboxes[i].getAttribute('id') === obligatoryOptionsArray1[j]){
                                    console.log("TRD Obligatory option match found...");
                                    removeDisabledFromCheckBox(restOptionCheckboxes[i].getAttribute('id'));
                                    console.log(restOptionCheckboxes[i] + " disable removed.");

                                }
                            }

                        }
                }});


            }else{

                let enablingApproved = true;

                for (let i = 0; i < restOptionCheckboxes.length; i++) {
                    for (let j = 0; j < incompatibleOptionsArray.length; j++) {
                        if(restOptionCheckboxes[i].getAttribute('id') === incompatibleOptionsArray[j]
                                                                && restOptionCheckboxes[i].getAttribute('disabled')
                                                                && restOptionCheckboxes[i].getAttribute('checked')===true){
                            alert("id " +  restOptionCheckboxes[i] + " this option should not be disabled, please enable this option.");
                            enablingApproved = false;
                            $("#" + selectedOptionId).prop("checked", false);

                        }
                    }

                    for (let j = 0; j < obligatoryOptionsArray.length; j++) {
                        if(restOptionCheckboxes[i].getAttribute('id') === obligatoryOptionsArray[j]
                            && restOptionCheckboxes[i].getAttribute('disabled')
                            && restOptionCheckboxes[i].getAttribute('checked')===false){
                            alert("id " + restOptionCheckboxes[i] + " this option should not be disabled, please enable this option.");
                            enablingApproved = false;
                            $("#" + selectedOptionId).prop("checked", false);
                        }
                    }
                }

                console.log("ENABLING APPROOVED: " + enablingApproved);
                if(enablingApproved){
                    console.log("enabling approved = " + enablingApproved + "! working...")
                    console.log("restOptionCheckboxes.length: " + restOptionCheckboxes.length);
                    console.log("incompatibleOptionsArray.length: " + incompatibleOptionsArray.length);
                    console.log("obligatoryOptionsArray.length: " + obligatoryOptionsArray.length);

                    for (let i = 0; i < restOptionCheckboxes.length; i++) {
                        console.log("entered restOptionCheckboxes array.")

                        for (let j = 0; j < incompatibleOptionsArray.length; j++) {
                            console.log("CHECKING restOptionCheckboxes[i]:" + restOptionCheckboxes[i] + "WITH incompOptArray: " + incompatibleOptionsArray[j]);
                            if(restOptionCheckboxes[i].getAttribute('id') === incompatibleOptionsArray[j]){
                                console.log("restOptionCheckboxes[i]:" + restOptionCheckboxes[i].getAttribute('id'));
                                console.log("incompatibleOptionsArray[i]" + incompatibleOptionsArray[j]);

                                turnOffCheckBoxAndDisable(restOptionCheckboxes[i].getAttribute('id'));
                                console.log("option with id = " + restOptionCheckboxes[i].getAttribute('id') + "is turnedOff and disabled");
                            }
                        }

                        for (let k = 0; k < obligatoryOptionsArray.length; k++) {
                            console.log("CHECKING restOptionCheckboxes[i]:" + restOptionCheckboxes[i] + "WITH obligOptArray: " + obligatoryOptionsArray[k]);
                            if(restOptionCheckboxes[i].getAttribute('id') === obligatoryOptionsArray[k]){
                                turnOnCheckBoxAndDisable(restOptionCheckboxes[i].getAttribute('id'));
                                console.log("option with id = " + restOptionCheckboxes[i].getAttribute('id') + "is turnedON and disabled");

                            }
                        }
                    }

                }

            }

        }});

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


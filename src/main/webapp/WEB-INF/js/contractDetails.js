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
                        + "<p class=\"lead columnContentLabels\" name=\"optionNameLabel\" id=\"" + result[i].option_id + "label\">" + result[i].name + "</p>"
                        + "</div>"

                        + "<div class=\"col-2\">"
                        + "<p class=\"lead columnContentLabels\" id=\"" + result[i].option_id + "label\">" + result[i].price + "</p>"
                        + "</div>"

                        + "<div class=\"col-5\">"
                        + "<p class=\"lead columnContentLabels\" id=\"" + result[i].option_id + "label\">" + result[i].shortDiscription + "</p>"
                        + "</div>"

                        +  "<div class=\"col-1\">"
                        + "<div class=\"form-check\">"

                        + "<label class=\"switch\" style=\"clear:both; \" name=\"group1\">"
                        + "<input type=\"checkbox\" name=\"optionCheckbox\" id=\"" + result[i].option_id + "\">"
                        + "<span class=\"slider round\" id=" + result[i].option_id + "Slider\"></span>"
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

$(document).ready(function(){
    $( "input[name='optionCheckbox']").on('change', function()
    {   checkSwitchesAndChangeIfNeeded($(this))  });
})

function checkSwitchesAndChangeIfNeeded(selectedOption, fromObligatory)
{
    var isChecked;
    if(fromObligatory){
        isChecked=true;
    }else{
        isChecked = selectedOption.prop('checked');
    }

    var restOptionCheckboxes = $('input[name="' + selectedOption.attr('name') + '"]').not(this);
    var selectedOptionId = selectedOption.attr('id');
    let booleanResult = true;

    console.log("currently in function:" + selectedOptionId + ", isChecked = " + isChecked);

    $.ajax({
        type: 'GET',
        url: '/contractDetails/loadDependedOptions/' + selectedOptionId,
        success: function (result){
            if(result[0].length>0){
                for (let i = 0; i < restOptionCheckboxes.length; i++) {

                    for(var j = 0; j < result[0].length; j++){

                        let testedOptionId = result[0][j].option_id;

                        if(testedOptionId==restOptionCheckboxes[i].id){

                            if( checkSwitchesAndChangeIfNeeded($("#" + testedOptionId), false)){

                                console.log("(#IMCOMP)in option with id " + selectedOptionId + " recursive function returned true, working...");
                                if(isChecked){
                                    console.log("(#IMCOMP)in option with id " + selectedOptionId + " checked true");

                                    let is_option_disabled = $("#" + testedOptionId).attr('disabled') == "disabled";
                                    let is_option_turned_on = $("#" + testedOptionId).prop('checked');

                                    if (is_option_disabled && is_option_turned_on){
                                        console.log('(#IMCOMP)is_option_disabled && is_option_turned_on is true');
                                        alert(selectedOption.attr('id') + " have incompatible connection with - "
                                            + result[0][j].name + " it must not be disabled.");
                                        $("#" + selectedOptionId).removeAttr('checked');
                                    }else {
                                        turnOffCheckBoxAndDisable(testedOptionId);
                                        console.log('(#IMCOMP)is_option_disabled && is_option_turned_on is false');
                                    }
                                    console.log("(#IMCOMP)for " + selectedOptionId + " returned booleanResult: " + booleanResult);
                                    return booleanResult;

                                }else{
                                    console.log("(#IMCOMP)in option with id " + selectedOptionId + " checked FALSE");
                                    removeDisabledFromCheckBox(testedOptionId);
                                    return booleanResult;
                                }

                            } else {
                                console.log("(#IMCOMP)in option with id " + selectedOptionId + " recursive function returned FALSE, rejecting changes");
                                // $("#" + selectedOptionId).prop("checked", false);
                                booleanResult = false;
                                console.log("(#IMCOMP)for " + selectedOptionId + " returned booleanResult: " + booleanResult);
                                return booleanResult;
                            }
                        }
                    }
                }
            }

            if(result[1].length>0){
                for (let i = 0; i < restOptionCheckboxes.length; i++) {

                    for(var j = 0; j < result[1].length; j++){

                        if(result[1][j].option_id==restOptionCheckboxes[i].id){

                            if(isChecked) {
                                console.log("(#OBLIG)is checked for " + selectedOptionId + " is TRUE");

                                if( checkSwitchesAndChangeIfNeeded($("#" + result[1][j].option_id), true )){

                                        console.log("(#OBLIG)in option with id " + selectedOptionId + " recursive function returned true, working...");
                                        if ($("#" + result[1][j].option_id).attr('disabled') == "disabled") {
                                            alert(selectedOption.attr('id') + "have incompatible connection with - "
                                                + result[1][j].name + "it must not be disabled.");
                                            $("#" + selectedOption.attr('id')).prop("checked", false);
                                        } else {
                                            console.log("(#OBLIG)in option with id " + result[1][j].option_id + " NOT DISABLED working...");
                                            $("#" + result[1][j].option_id).removeAttr("checked");
                                            $("#" + result[1][j].option_id).prop('checked', true);
                                            $("#" + result[1][j].option_id).attr("disabled", true);
                                            $("[name=" + result[1][j].option_id + "label]").css('color', '#d3d3d3');
                                            $("#" + result[1][j].option_id + "Slider").css('background-color', '#9acffa');
                                            console.log("(#OBLIG)in option with id " + result[1][j].option_id
                                                + " sucefully disabled, returned boolean result " + booleanResult);
                                        }
                                        return booleanResult;


                                }else{
                                    console.log("(#OBLIG)in option with id " + $("#" + result[1][j].option_id) + " recursive function returned FALSE, rejecting changes");
                                    console.log("(#OBLIG) boolean result returned: " + booleanResult)
                                    booleanResult = false;
                                    return booleanResult;
                                }
                            } else {
                                console.log("(#OBLIG)is checked for " + selectedOptionId + " is FALSE returning boolean result: " + booleanResult);

                                $("[name=" + result[1][j].option_id + "label]").css('color', 'black');
                                $("#" + result[1][j].option_id + "Slider").removeAttr("style");
                                $("#" + result[1][j].option_id).attr("disabled", false);
                                return booleanResult;
                            }
                        }
                    }
                }
            }

            console.log('4'+booleanResult);
            return booleanResult;
        }});

        return booleanResult;

}

function turnOffCheckBoxAndDisable(current_option_id){
    $("#" + current_option_id).prop("checked", false);
    $("#" + current_option_id).attr("disabled", true);
    $("[name=" + current_option_id + "label]").css('color', '#d3d3d3');
    $("#" + current_option_id + "Slider").css('background-color', '#d3d3d3');
}

function removeDisabledFromCheckBox(current_option_id){
    $("#" + current_option_id).removeAttr('checked');
    $("#" + current_option_id).attr("disabled", false);
    $("[name=" + current_option_id + "label]").css('color', 'black');
    $("#" + current_option_id + "Slider").removeAttr("style");
}


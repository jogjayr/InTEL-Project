<?php

$title = "Feedback Test";
require_once('view/header.php');
?>

<script type="text/javascript">
    var feedbackBoxVisible = false;
    function showFeedbackBox() {
        if(!feedbackBoxVisible) {
            $("body").append('<div class="feedbackBox">'+
                '<textarea rows="10" style="width: 100%" id="feedback"></textarea><br/>'+
                '<div id="feedbackResponse" /><br/>'+
                '<input id="submitFeedbackButton" type="button" onclick="onSubmitFeedback()" value="submit"/>'+
                '<input id="cancelFeedbackButton" type="button" onclick="onCancelFeedback()" value="cancel"/>'+
                '</div>');
            feedbackBoxVisible = true;
        }
    }
    function onSubmitFeedback() {
        $.ajax({
            url: "postFeedback.php",
            type: "POST",
            data: {feedback: $("#feedback").val()},
            success: function(msg) {
                $("#feedbackResponse").addClass("infoMessage").html("Thank you! Your feedback has been submitted!");
                $("#cancelFeedbackButton").attr("value","OK");
            },
            error: function(msg) {
                $("#feedbackResponse").addClass("errorMessage").html("Error! Please contact support!");
            }
        });

        $("#feedback").attr("readonly", "true");
        //$("#submitButton").attr("disabled", "true");
        $("#submitFeedbackButton").remove();
    }

    function onCancelFeedback() {
        $(".feedbackBox").fadeOut("slow", function() {
            $(".feedbackBox").remove();
            feedbackBoxVisible = false;
        });
    }
</script>

<button onclick="showFeedbackBox()"/>


<?php

require_once('view/footer.php');
?>
<?php

$title = "Feedback Test";
require_once('view/header.php');
?>

<script type="text/javascript">
    function onSubmit() {
        $.ajax({
            url: "postFeedback.php",
            type: "POST",
            data: {feedback: $("#feedback").val()},
            success: function(msg) {
                $("#feedbackResponse").addClass("infoMessage").html("Thank you! Your feedback has been submitted!");
            },
            error: function(msg) {
                $("#feedbackResponse").addClass("errorMessage").html("Error! Please contact support!");
            }
        });

        $("#feedback").attr("readonly", "true");
        $("#submitButton").attr("disabled", "true");
    }
</script>

<div class="feedbackBox">
    <textarea rows="10" cols="100" id="feedback"></textarea><br/>
    <input id="submitButton" type="button" onclick="onSubmit()" value="submit"/>
    <div id="feedbackResponse" />
</div>


<?php

require_once('view/footer.php');
?>
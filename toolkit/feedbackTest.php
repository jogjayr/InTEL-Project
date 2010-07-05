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
                $("#form").addClass("infoMessage").html("Feedback Submitted! "+msg);
            },
            error: function(msg) {
                $("#form").addClass("errorMessage").html("Error! "+msg);
            }
        });

        $("#feedback").attr("readonly", "true");
        $("#submitButton").attr("disabled", "true");
    }
</script>


<div id="form">
    <textarea rows="10" cols="100" id="feedback"></textarea><br/>
    <input id="submitButton" type="button" onclick="onSubmit()" value="submit"/>
</div>


<?php

require_once('view/footer.php');
?>
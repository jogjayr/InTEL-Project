<?php

require_once('admin/initvars.php');
prohibitLogin();


$hasAction = false;
$message = "";
$success = false;

// the user has attempted to reset their password by pressing the button on the reset form.
if($_POST['submitReset']) {
    $hasAction = true;
    $email = $_POST['email'];
    if(getUserByEmail($email)) {
        $success = true;
        sendResetPassword($email);
        $message = "Confirmation email has been sent! Please check your email and your password will be reset.";

    } else {
        $success = false;
        $message = "Could not find a user with the email address \"$email\". Have you <a href=\"register.php\">registered</a>?";
    }
}


$title = 'Reset Password';
require_once ('header.php');


if ($hasAction) {
    if ($success) {
        para($message, 'infoMessage');
    } else {
        para($message, 'errorMessage');
    }
}

if(!$hasAction) {
?>

<p>If you have forgotten your password, please enter your email address, and we will send you a code to reset it.</p>
<form action="" method="POST">
    <input type="text" name="email"/>
    <input type="submit" name="submitReset" value="reset my password"/>
</form>

<?php } ?>


<?php
require_once ('footer.php');
?>
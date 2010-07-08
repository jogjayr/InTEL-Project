<?php
require_once('admin/initvars.php');
prohibitLogin();


$hasAction = false;
$message = "";
$success = false;
$hasReset = false;

// the user has attempted to reset their password by pressing the button on the reset form.
if (isset($_POST['submitReset'])) {
    $hasAction = true;
    $email = $_POST['email'];
    if (getUserByEmail($email)) {
        $success = true;
        sendResetPassword($email);
        $message = "Confirmation email has been sent! Please check your email and your password will be reset.";
    } else {
        $success = false;
        $message = "Could not find a user with the email address \"$email\". Have you <a href=\"register.php\">registered</a>?";
    }
}

$resetCode = "";
if (isset($_POST['changePassword'])) {
    $hasAction = true;
    $resetCode = $_POST['reset_password_code'];
    $password = $_POST['password'];
    $password2 = $_POST['password2'];
    if($password != $password2) {
        $success = false;
        $message = "Your passwords do not match!";
        $hasReset = true;
    } else {
        loginByResetPasswordCode($resetCode);
        changePassword($_SESSION['uuid'], $password);
        $success = true;
        $message = "Your password has been reset and you are now logged in. <a href=\"index.php\">Continue</a>.";
    }
}

if (isset($_GET['reset_password_code'])) {
    $hasReset = true;
    $resetCode = $_GET['reset_password_code'];
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

// If we have a password reset code, prompt the user to reset their password.
if ($hasReset) {
?>
    <p>Please give a new password.</p>
    <form method="post" action="">
        <input type="hidden" name="reset_password_code" value="<?php echo $resetCode; ?>" />
        <p>New Password: <input type="password" name="password" /></p>
        <p>New Password (again): <input type="password" name="password2" /></p>
        <p><input type="submit" name="changePassword" value="Update" /></p>
    </form>
<?php
// If we do not have an action, let the user enter an email address to reset their password.
} elseif (!$hasAction) {
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
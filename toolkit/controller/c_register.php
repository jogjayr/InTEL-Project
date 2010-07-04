<?php

require_once('admin/initvars.php');
prohibitLogin();

//initialize post variables
$err = '';
$emailAddress = '';
$firstName = '';
$lastName = '';
//$gtPrismId = '';
$password = '';
$classId = 0;

$hasAction = false;
$success = false;

//check for post data
if (isset($_POST['submit'])) {
    $hasAction = true;

    $emailAddress = $_POST['email'];
    $password = $_POST['password'];
    $password2 = $_POST['password2'];
    $firstName = $_POST['first_name'];
    $lastName = $_POST['last_name'];
    //$gtPrismId = $_POST['gt_prism_id']; removed
    if (isset($_POST['class_id'])) {
        $classId = $_POST['class_id'];
    }

    $success = true;

    if ($success && $classId == 0) {
        $err = 'Please choose which class you belong to.';
        $success = false;
    }
    //check for valid email address
    if ($success && !isEmailAddress($emailAddress)) {
        $err = 'Please enter a valid email address.';
        $success = false;
    }
    //check if passwords match
    if ($success && $password != $password2) {
        $err = 'The passwords do not match.';
        $success = false;
    }

    //check that the fields are not empty
    if ($success && ($password == '' || $firstName == '' || $lastName == '')) {
        $err = 'Please enter a password.';
        $success = false;
    }

    if ($success && !registerUser($emailAddress, $password, $firstName, $lastName, $classId)) {
        $err = 'There is already an account with this email address. Please contact <a href="mailto:' . $site_email_address . '">support</a> if you need to reset your password!';
        $success = false;
    }

    if ($success) {
        //login the newly regitered user and redirect them
        //run login function
        if (login(trim($emailAddress), $password)) {

            // go to the assignments page.
            // Should have a "registration successful" message
            redirect('myAssignments.php');
        } else {
            // registration succeeded, but login failed for some reason.
            // this should not be the case...
            $err = 'Account created but login failed. Please contact support!';
        }
    }
}
?>
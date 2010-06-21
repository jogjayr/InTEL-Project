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

//check for post data
if (isset($_POST['submit'])) {

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

    if ($success && $classId != 0) {
        $err = 'Please choose which class you belong to.';
        $success = false;
    }
    //check for valid email address
    if ($success && isEmailAddress($emailAddress)) {
        $err = 'Please enter a valid email address.';
        $success = false;
    }
    //check if passwords match
    if ($success && $password == $password2) {
        $err = 'The passwords do not match.';
        $success = false;
    }

    //check that the fields are not empty
    if ($success && $password != '' && $firstName != '' && $lastName != '') {
        $err = 'Please enter a password.';
        $success = false;
    }

    if ($success && !registerUser($emailAddress, $password, $firstName, $lastName, $classId)) {
        $err = 'Please enter another email address.';
        $success = false;
    }

    if ($success) {
        //login the newly regitered user and redirect them
        //run login function
        if (login(trim($emailAddress), $password)) {

            // go to the assignments page.
            redirectRel('myAssignments.php'); 

            //redirect page to the url before the login
//            $rURL = $_SESSION['r_login_url'];
//            if (isStudent ()) {
//                $rURL = 'myAssignments.php';
//            }
//            unset($_SESSION['r_login_url']);
//
//            //redirect user to referring url, unless the referring url is the registration complete page
//            //para($rURL);
//
//            if (strstr($rURL, 'register_complete.php') || strstr($rURL, 'change_password.php')) {
//
//                //para('from register complete');
//
//                redirectURL($base_address);
//            } else {
//
//                //para('not from register complete');
//
//                redirectURL($rURL);
//            }
            //end login
        } else {
            // registration succeeded, but login failed for some reason.
            // this should not be the case...
            $err = 'Account created but login failed. Please contact support!';
        }
    }
}
?>
<?php

/**
 * Attempts to register the given information as a new user.
 * @param string $emailAddress
 * @param string $password
 * @param string $firstName
 * @param string $lastName
 * @param int $classId
 * @return boolean whether the registration was successful
 */
function registerUser($emailAddress, $password, $firstName, $lastName, $classId) {
    if (addUser($emailAddress, $password, $firstName, $lastName, $classId)) {
        //sendRegistrationEmail($emailAddress);
        return true;
    } else {
        return false;
    }
}

/**
 * Returns true if the current user is logged in.
 * @return boolean
 */
function isLoggedIn() {
    return (isset($_SESSION['is_logged_in']) && $_SESSION['is_logged_in'] == true);
}

/**
 * If the current user is not logged in, then this redirects to the login page.
 * This must be called before any text is written, as it sets the HTTP header.
 */
function requireLogin() {
    if (!isLoggedIn()) {
        redirect('login.php');
    }
}

/**
 * If the current user is not an instructor, or an admin, this redirects to the index page.
 * This must be called before any text is written, as it sets the HTTP header.
 */
function requireInstructor() {
    if (!isAdmin() && !isInstructor()) {
        redirect('index.php');
    }
}

/**
 * If the current user is logged in, then this redirects to the index page.
 * This must be called before any text is written, as it sets the HTTP header.
 */
function prohibitLogin() {
    if (isLoggedIn ()) {
        redirect('index.php');
    }
}

/**
 * Returns true if the current user is an admin.
 * @return boolean
 */
function isAdmin() {
    if (isLoggedIn ()) {
        $uuid = $_SESSION['uuid'];
        if (getUserType($uuid) == 'admin') {
            return true;
        }
    }

    return false;
}

/**
 * Returns true if the current user is an instructor.
 * @return boolean
 */
function isInstructor() {
    if (isLoggedIn ()) {
        $uuid = $_SESSION['uuid'];
        if (getUserType($uuid) == 'instructor') {
            return true;
        }
    }

    return false;
}


/**
 * Returns true if the current user is anonymous (typically if the user is not logged in)
 * @return boolean
 */
function isAnonymous() {
    if (isLoggedIn ()) {
        $uuid = $_SESSION['uuid'];
        if (getUserType($uuid) == 'anonymous') {
            return true;
        }
    } else {
        return true;
    }
    return false;
}

/**
 * Returns true if the current user is a student.
 * @return boolean
 */
function isStudent() {
    if (isLoggedIn ()) {
        $uuid = $_SESSION['uuid'];
        if (getUserType($uuid) == 'student') {
            return true;
        }
    }

    return false;
}

/**
 * This logs the current user out, clearing the session and redirecting to the login page.
 */
function logout() {
    unset($_SESSION);
    session_destroy();
    redirect('login.php');
}

/**
 * Attempts to log in the current user.
 * @param string $emailAddress
 * @param string $password
 * @return boolean
 */
function login($emailAddress, $password) {
    //sets the session variables to indicate that the user is logged in

    $uuid = authenticate($emailAddress, $password);
    if ($uuid != '') {
        $_SESSION['is_logged_in'] = true;
        $_SESSION['uuid'] = $uuid;

        $user = getUserByUUId($uuid);
        $_SESSION['user_first_name'] = $user['first_name'];
        $_SESSION['user_last_name'] = $user['last_name'];

        return true;
    } else {
        return false;
    }
}

function loginByResetPasswordCode($resetPasswordCode) {
    //if the reset password code is correct, then it logs the user in and clears the reset password code
    //returns true if successful, else returns false

    global $db;
    $q_resetPasswordCode = t2sql($resetPasswordCode);
    $query = "SELECT uuid, first_name, last_name FROM app_user WHERE reset_password_code <> '' AND reset_password_code='{$q_resetPasswordCode}'";
    $results = aquery($query, $db);
    if (count($results) > 0) {

        $_SESSION['is_logged_in'] = true;
        $_SESSION['uuid'] = $results[0]['uuid'];
        $_SESSION['user_first_name'] = $results[0]['user_first_name'];
        $_SESSION['user_last_name'] = $results[0]['user_last_name'];

        $q_id = t2sql($results[0]['uuid']);
        $query = "UPDATE users SET reset_password_code='' WHERE id='{$q_id}'";
        query($query, $db);
        return true;
    } else {
        return false;
    }
}

//function loginByRegConfirmationCode($regConfirmationCode) {
//    //if the registration confirmation code is correct, logs the user in
//    //returns true if successful, else returns false
//
//
//    global $db;
//    $q_regConfirmationCode = t2sql($regConfirmationCode);
//    $query = "SELECT uuid, first_name, last_name FROM app_user WHERE reg_confirmation_code <> '' AND reg_confirmation_code='{$q_regConfirmationCode}'";
//    $results = aquery($query, $db);
//    if (count($results) > 0) {
//
//        $_SESSION['is_logged_in'] = true;
//        $_SESSION['uuid'] = $results[0]['uuid'];
//        $_SESSION['user_first_name'] = $results[0]['user_first_name'];
//        $_SESSION['user_last_name'] = $results[0]['user_last_name'];
//
//        $q_id = t2sql($results[0]['uuid']);
//        $query2 = "UPDATE users SET is_active = 1 WHERE uuid='{$q_id}'";
//        query($query2, $db);
//
//        return true;
//    } else {
//        return false;
//    }
//}
//
//function sendRegistrationEmail($to_email_address) {
//
//    global $base_address;
//    global $site_email_address;
//    global $site_title;
//    global $db;
//
//    $register_complete_file = '../view/register_complete.php';
//
//    //generate confirmation id
//    $reg_confirmation_code = md5($to_email_address . rand(0, 100000000));
//
//    //store confirmation id
//
//    $query = "UPDATE app_user SET reg_confirmation_code='{$reg_confirmation_code}' WHERE email='{$to_email_address}'";
//    $results = query($query, $db);
//
//    $to = $to_email_address;
//    $from = $site_title . ' <' . $site_email_address . '>';
//    $subject = 'Registration Confirmation';
//    $message = "Thank you for registering with " . $site_title . "\r\n\r\n" . "Click the following link to confirm your registration and login:\r\n\r\n " . $base_address . $register_complete_file . "?reg_confirmation_code=" . $reg_confirmation_code;
//    secureEmail($to, $from, $subject, $message);
//}

function sendResetPassword($to_email_address) {

    global $site_email_address;
    global $site_title;
    global $base_address;
    global $db;

    $reset_password_file = 'reset_password.php';

    //generate confirmation id
    $reset_password = $to_email_address . getRandomPassword(8);
    $reset_password_code = md5($reset_password);

    //store confirmation id
    $o_reset_password_code = mysql_real_escape_string($reset_password_code);
    $o_to_email_address = mysql_real_escape_string($to_email_address);

    $query = "UPDATE app_user SET reset_password_code='{$o_reset_password_code}' WHERE email='{$o_to_email_address}'";
    $results = query($query, $db);

    $to = $to_email_address;
    $from = $site_title . ' <' . $site_email_address . '>';
    $subject = 'Reset Password';
    $message = "Your password has been reset on the " . $site_title . "\r\n\r\n" . "Click the following link to login and reset your new password:\r\n\r\n" . $base_address . $reset_password_file . "?reset_password_code=" . $reset_password_code;
    secureEmail($to, $from, $subject, $message);
}
?>
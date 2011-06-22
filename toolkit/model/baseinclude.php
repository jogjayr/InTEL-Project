<?php

// This loads all of the library functions that are used by the PHP backend.
require_once('lib_accounts.php');
require_once('lib_assignments.php');
require_once('lib_classes.php');
require_once('lib_submissions.php');
require_once('util.php');

// information for titling and contact information
$site_title = "Interactive Toolkit for Engineering Learning";
$site_email_address = "eschechter3@gatech.edu";

// addresses used for redirects
$base_address = 'http://intel.gatech.edu/toolkit/';
$base_file_path = '/www/virtual/intel/toolkit/';

// error string if present.
$err = '';

// The user may attempt to log in at any page.
// this attempts to log the user in and retrieve the user variable
if (isset($_POST['login'])) {
    if (login(trim($_POST['email']), $_POST['password'])) {
        // login ok
    } else {
        $err = 'Either your email or your password is incorrect.';
    }
}

// the user has logged out.
if (isset($_POST['logout'])) {
    logout();
}

// setup user variable
$uuid = '';
if (isset($_SESSION['uuid'])) {
    $uuid = $_SESSION['uuid'];
}
$user = getUserByUUID($uuid);


?>
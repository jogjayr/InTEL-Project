<?php

require_once('model/lib_data.php');
require_once('model/m_lib_accounts.php');
require_once('controller/c_lib_accounts.php');
require_once('controller/util.php');


// information for titling and contact information
$site_title = "Interactive Toolkit for Engineering Learning";
$site_email_address = "eschechter3@gatech.edu";

// addresses used for redirects
$base_address = 'http://intel.gatech.edu/toolkit-dev/';
$base_file_path = '/www/virtual/intel/toolkit-dev/';

// error string if present.
$err = '';



// The user may attempt to log in at any page.
// this attempts to log the user in and retrieve the user variable
if (isset($_POST['login'])) {
    if (login(trim($_POST['email']), $_POST['password'])) {
        // login ok
    }
}




// setup user variable
$uuid = '';
if (isset($_SESSION['uuid'])) {
    $uuid = $_SESSION['uuid'];
}
$user = getUserByUUID($uuid);


?>
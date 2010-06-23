<?php

require_once('model/lib_data.php');
require_once('model/m_lib_accounts.php');
require_once('controller/c_lib_accounts.php');
require_once('controller/util.php');


$site_title = "Interactive Toolkit for Engineering Learning";
$site_email_address = "eschechter3@gatech.edu";

$base_address = 'http://intel.gatech.edu/toolkit/';
$base_file_path = '/www/virtual/intel/toolkit/';


// setup user variables.

$uuid = '';
if (isset($_SESSION['uuid'])) {
    $uuid = $_SESSION['uuid'];
}
$user = getUserByUUID($uuid);



?>
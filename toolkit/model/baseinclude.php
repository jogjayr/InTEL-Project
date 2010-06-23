<?php

require_once('model/mysql.php');
require_once('model/lib_data.php');
require_once('model/m_lib_accounts.php');
require_once('controller/c_lib_accounts.php');
require_once('controller/util.php');

// setup user variables.

$uuid = '';
if (isset($_SESSION['uuid'])) {
    $uuid = $_SESSION['uuid'];
}
$user = getUserByUUID($uuid);



?>
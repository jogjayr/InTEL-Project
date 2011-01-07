<?php 
error_reporting(E_ALL);
ini_set("display_errors", 1);

$maxUploadFileSizeInMegabytes = '50';

ini_set('post_max_size', $maxUploadFileSizeInMegabytes . 'M');

require_once('model/mysql.php');

$dbusername = 'idtweb';
$dbpassword = 'gt00www';
$dbname = 'intel_dev';
$dbserver = 'localhost';

$appletFolder = '../applet-dev';

$db = connect($dbserver, $dbusername, $dbpassword, $dbname);

session_start();

require_once('model/baseinclude.php');

?>
<?php 
error_reporting(E_ALL);
ini_set("display_errors", 1);

$maxUploadFileSizeInMegabytes = '50';

ini_set('post_max_size', $maxUploadFileSizeInMegabytes . 'M');

require_once('model/mysql.php');
require_once('model/lib_data.php');
require_once('model/m_lib_accounts.php');
require_once('controller/c_lib_accounts.php');
require_once('controller/util.php');

$site_title = "Interactive Toolkit for Engineering Learning";
$site_email_address = "dupton3@gatech.edu";

$base_address = 'http://intel.gatech.edu/toolkit/';
$base_file_path = '/www/virtual/intel/toolkit/';

$dbusername = 'idtweb';
$dbpassword = 'gt00www';
$dbname = 'intel';
$dbserver = 'localhost';

$smtp_server = "";$smtp_username = "";$smtp_password = "";$smtp_default_from = $site_email_address;

$ftp_server = '';
$ftp_user_name = '';
$ftp_user_pass = '';

$db = connect($dbserver, $dbusername, $dbpassword, $dbname);

session_start();
?>
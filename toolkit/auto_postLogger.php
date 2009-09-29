<?php

/**
 * About this page:
 * 
 * This is the server-side page that regisers log messages generated by the applet.
 * These messages should be sent with some frequency and regularity, so the
 * information payload will be pretty small. This page should never be viewed by
 * a user directly.
 */

error_reporting(E_ALL);
ini_set("display_errors", 1);

require_once("admin/initvars.php");
require_once("model/m_auto_posting.php");

// here is our data:
$problemId =  addslashes($_POST["problem_id"]);
$userId =     addslashes($_POST["user_id"]);
$sessionId =  addslashes($_POST["session_id"]);
$javaClass =  addslashes($_POST["java_class"]);
$javaMethod = addslashes($_POST["java_method"]);
$message =    addslashes($_POST["message"]);
$timestamp =  addslashes($_POST["timestamp"]);

$success = post_logger($problemId, $userId, $sessionId, $javaClass, $javaMethod, $message, $timestamp);

// report back
if($success) {
  echo "log post successful";
}else{
  echo "log post failed!";
}

?>
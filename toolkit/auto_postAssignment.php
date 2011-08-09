<?php

/**
 * About this page:
 *
 * This is the server-side page that regisers messages from the applet to update the problem state
 * These messages are sent in the background, so this page should never be visible by users
 */

error_reporting(E_ALL);
ini_set("display_errors", 1);

require_once("admin/initvars.php");
require_once("model/m_auto_posting.php");

// here is our data:
$assignmentId =   addslashes($_POST["assignment_id"]);
$userId =         addslashes($_POST["user_id"]);
$exerciseStatus = addslashes($_POST["exercise_status"]);
$stateData =      addslashes($_POST["state_data"]);
$verifierKey =    addslashes($_POST["verifier_key"]);
$timestamp =      addslashes($_POST["timestamp"]);

$success = post_exercise($assignmentId, $userId, $exerciseStatus, $stateData, $verifierKey, $timestamp);

// report back
if($success) {
  echo "assignment post successful";
}else{
  echo "assignment post failed!";
}

?>
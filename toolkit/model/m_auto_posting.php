<?php

require_once("model/mysql.php");

/**
 * All Strings passed to this method must be sanitized for MySQL!
 * @param problemId int The ID of the problem being logged about
 * @param sessionId int The session ID for the problem
 * @param javaClass string The class that is creating the log message
 * @param javaMethod string The method that is creating the log message
 * @param message string The message itself
 * @param timestamp long The timestamp, according to Java, when this message was posted
 * @return boolean true if the post is successful, false otherwise
 */
function post_logger($problemId, $sessionId, $javaClass, $javaMethod, $message, $timestamp) {

  global $db;

  //add logger entry
  $query =
    "INSERT INTO app_problem_usage_log (problem_id, java_problem_session_id, java_class, java_method, message, created_on)
    VALUES ({$problemId}, {$sessionId}, '{$javaClass}', '{$javaMethod}', '{$message}', {$timestamp})";

  query($query, $db);

  return true;
}

/**
 * All Strings passed to this method must be sanitized for MySQL!
 * @param assignmentId int The assignment ID being posted about
 * @param userId int The ID of the user who is working on the problem
 * @param exerciseStatus int The status ID of the problem
 * @param stateData string The state data of the problem
 * @param verifierKey string The verifier key which is fitted to the problem data, and should ensure that no false posts are being made
 * @param timestamp long The timestamp, according to Java, of when the post was made
 * @return boolean true if the post is successful, false otherwise
 */
function post_exercise($assignmentId, $userId, $exerciseStatus, $stateData, $verifierKey, $timestamp) {

  global $db;

  // first, check the verifier key:
  $preHash = "$userId:$assignmentId:$exerciseStatus:$stateData";
  $verifierKeyCheck = substr(md5($preHash),0,8);

  if($verifierKeyCheck != $verifierKey) {
    // the verifier test failed!
    return false;
  }

  // now check to see if we are updating, or inserting
  $query = "SELECT id FROM app_user_assignment WHERE user_id=$userId AND assignment_id=$assignmentId";
  $result = aquery($query, $db);

  if(sizeof($result) == 0) {
    // new entry, we insert
    $query =
      "INSERT INTO app_user_assignment (user_id, assignment_id, submission_status_id, state, created_on, updated_on)
      VALUES ({$problemId}, {$assignmentId}, {$exerciseStatus}, '{$stateData}', {$timestamp}, {$timestamp})";
    query($query, $db);

  } else {
    // update
    $id = $result['id'];

    $query =
    "UPDATE app_user_assignment SET
      submission_status_id={$exerciseStatus}, state='$stateData' WHERE id=$id";
    query($query, $db);
  }
  
  return true;
}

?>
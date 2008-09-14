<?php

/**
 * @param int $problemId The ID of the problem being logged about
 * @param int $sessionId The session ID for the problem
 * @param string $javaClass The class that is creating the log message
 * @param string $javaMethod The method that is creating the log message
 * @param string $message The message itself
 * @param long $timestamp The timestamp, according to Java, when this message was posted
 * @return boolean true if the post is successful, false otherwise
 */
function post_logger($problemId, $sessionId, $javaClass, $javaMethod, $message, $timestamp) {


  return true;
}

/**
 * @param int $exerciseId The exercise ID being posted about
 * @param int $userId The ID of the user who is working on the problem
 * @param int $exerciseStatus The status ID of the problem
 * @param string $stateData The state data of the problem
 * @param string $verifierKey The verifier key which is fitted to the problem data, and should ensure that no false posts are being made
 * @param long $timestamp The timestamp, according to Java, of when the post was made
 * @return boolean true if the post is successful, false otherwise
 */
function post_exercise($exerciseId, $userId, $exerciseStatus, $stateData, $verifierKey, $timestamp) {

  return true;
}

?>
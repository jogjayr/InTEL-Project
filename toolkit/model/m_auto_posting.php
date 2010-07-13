<?php

require_once("model/mysql.php");

/**
 * All Strings passed to this method must be sanitized for MySQL!
 * @param problemId int The ID of the problem being logged about
 * @param userId int the ID of the user working on the problem
 * @param sessionId int The session ID for the problem
 * @param javaClass string The class that is creating the log message
 * @param javaMethod string The method that is creating the log message
 * @param level string The level of the log message
 * @param message string The message itself
 * @param timestamp long The timestamp, according to Java, when this message was posted
 * @return boolean true if the post is successful, false otherwise
 */
function post_logger($problemId, $userId, $sessionId, $javaClass, $javaMethod, $level, $message, $timestamp) {

    global $db;

    // first, get the session id.
    $query = "SELECT * FROM app_problem_usage_sessions WHERE java_problem_session_id = {$sessionId}";
    $results = aquery($query, $db);
    if (sizeof($results) == 0) {
        // insert a new row.
        $query = "INSERT INTO app_problem_usage_sessions (problem_id, user_id, java_session_id, start_time, end_time)
            VALUES ({$problemId}, {$userId}, {$sessionId}, now(), now())";
        query($query, $db);

        // try the select again
        $query = "SELECT * FROM app_problem_usage_sessions WHERE java_problem_session_id = {$sessionId}";
        $results = aquery($query, $db);
        $sessionData = $results[0];
    } else {
        $sessionData = $results[0];
        $query = "UPDATE app_problem_usage_sessions SET end_time=now() WHERE id={$sessionData['id']}";
        query($query, $db);
    }

    //add logger entry

    $query = "INSERT INTO app_problem_usage_log (session_id, java_class, java_method, level, message, created_on)
        VALUES ({$sessionData['id']} ,'{$javaClass}', '{$javaMethod}', '{$level}', '{$message}', now())";

//    $query =
//            "INSERT INTO app_problem_usage_log (problem_id, user_id, java_problem_session_id, java_class, java_method, level, message, created_on)
//    VALUES ({$problemId}, {$userId}, {$sessionId}, '{$javaClass}', '{$javaMethod}', '{$level}', '{$message}', now())";

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
    $verifierKeyCheck = substr(md5($preHash), 0, 8);

    //echo "checking verifier...\n";

    if ($verifierKeyCheck != $verifierKey) {
        // the verifier test failed!
        return false;
    }

    //echo "verifier ok!\n";
    // now check to see if we are updating, or inserting
    $query = "SELECT id, submission_status_id FROM app_user_assignment WHERE user_id=$userId AND assignment_id=$assignmentId";
    $result = aquery($query, $db);

    //echo "checking if we have a result:\n";
    //echo "error: "+mysql_error();

    if (sizeof($result) == 0) {
        // new entry, we insert
        $query =
                "INSERT INTO app_user_assignment (user_id, assignment_id, submission_status_id, state, created_on, updated_on)
      VALUES ({$userId}, {$assignmentId}, {$exerciseStatus}, '{$stateData}', {$timestamp}, {$timestamp})";
        query($query, $db);

        echo "inserting new record\n";
        //echo "error: " +mysql_error();
    } else {
        // update
        $id = $result[0]['id'];

        $previousStatus = $result[0]['submission_status_id'];
        if ($previousStatus > $exerciseStatus) {
            // do not overwrite their old successful score
            echo "exercise has been previously saved at a higher point of progress\n";
            echo "no update\n";
            return true;
        }

        $query =
                "UPDATE app_user_assignment SET
      submission_status_id={$exerciseStatus}, state='$stateData' WHERE id=$id";
        query($query, $db);

        echo "updating record\n";
        //echo "error: " +mysql_error();
    }

    return true;
}
?>
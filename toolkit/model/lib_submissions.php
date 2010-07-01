<?php

/**
 * Retrieves all types of submission statuses (not started, started, in progress, completed).
 * @global resource $db
 * @return array
 */
function getSubmissionTypes() {
    global $db;

    $query = "SELECT * FROM app_submission_status WHERE is_active=1";
    $results = aquery($query, $db);

    return $results;
}

/**
 * Fetches assignment submissions. This is a big database call that searches based on the owner,
 * the problem, the class, and the student name. There is a lot of data in the submissions table,
 * so it is particularly useful to give one or two search terms.
 * @global resource $db
 * @param string $ownerUuid the instructor uuid (defaults to '')
 * @param int $problemId the id of the problem (defaults to -1)
 * @param int $classId the id of the class (defaults to -1)
 * @param string $userName the name of the student (defaults to '')
 * @return array
 */
function getSubmissions($ownerUuid='', $problemId=-1, $classId=-1, $userName='') {
    global $db;

    $ownerLine = "";
    if ($ownerUuid != '') {
        $owner = getUserByUUID($ownerUuid);
        $ownerLine = "AND app_class.owner_user_id={$owner['id']} ";
    }

    $problemLine = "";
    if ($problemId != -1) {
        $problemId = mysql_escape_string($problemId);
        $problemLine = "AND app_problem.id = $problemId ";
    }

    $classLine = "";
    if ($classId != -1) {
        $classId = mysql_escape_string($classId);
        $classLine = "AND app_class.id = $classId ";
    }

    $userLine = "";
    if ($userName != '') {

        // attempt to find the user by name
        $users = getUsersByName($userName);

        if (sizeof($users) == 0) {
            // couldn't find them
            return array(); // return an empty array
        }
        // line to search for users....
        //$userLine = ????
    }

    $query = "SELECT app_user.first_name, app_user.last_name, app_user.email, app_problem.name,
        app_assignment.class_id, app_submission_status.status, app_user_assignment.updated_on,
        app_user_assignment.submission_status_id, app_user_assignment.user_id, app_user_assignment.assignment_id,
        app_assignment_type.type
        FROM app_user, app_assignment, app_submission_status, app_user_assignment, app_class, app_problem, app_assignment_type
        WHERE app_user_assignment.is_active=1
        $ownerLine $problemLine $classLine $userLine
        AND app_assignment.is_active=1
        AND app_user.id=app_user_assignment.user_id
        AND app_assignment.id=app_user_assignment.assignment_id
        AND app_submission_status.id=app_user_assignment.submission_status_id
        AND app_class.id=app_assignment.class_id
        AND app_problem.id=app_assignment.problem_id
        AND app_assignment.assignment_type_id=app_assignment_type.id
        ORDER BY app_assignment.class_id, app_user_assignment.assignment_id, app_user_assignment.updated_on DESC";

    $results = aquery($query, $db);

    return $results;
}


?>
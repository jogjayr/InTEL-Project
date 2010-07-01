<?php


/**
 * This fetches the problem row for the given problem id. If it does not exist, false is returned.
 * @global resource $db
 * @param int $problemId
 * @return array This may also return false if the class cannot be found.
 */
function retrieveProblem($problemId) {
    global $db;

    $query = "SELECT * FROM app_problem WHERE id={$problemId}";
    $results = aquery($query, $db);

    if (count($results) > 0) {
        return $results[0];
    } else {
        return false;
    }
}

/**
 * Retrieves all the problems that currently exist.
 * @global resource $db
 * @return array
 */
function getAllProblems() {
    global $db;

    $query = "SELECT * FROM app_problem WHERE is_active=1";
    $results = aquery($query, $db);

    return $results;
}


/**
 * This fetches all of the currently active assignments that are available for the given user.
 * If the user is anonymous, then this fetches all problems.
 * @global resource $db
 * @param string $uuid
 * @return array
 */
function getAssignments($uuid) {
    global $db;

    if (isAnonymous ()) {
        $query = "SELECT id AS problem_id, name, description FROM app_problem WHERE is_active=1 ORDER BY name DESC";
    } else {
        $class = getClassByUUID($uuid);
        $dateTime = mktime();
        $user = getUserByUUID($uuid);
        $query = "SELECT app_assignment.id, app_assignment.problem_id, app_problem.name, app_problem.description, app_submission_status.status, app_user_assignment.state, app_assignment_type.type, open_date, close_date
                  FROM app_problem, app_user_assignment, app_assignment, app_submission_status, app_assignment_type
                  WHERE app_user_assignment.user_id={$user['id']}
                  AND app_assignment.open_date<={$dateTime}
                  AND app_assignment.close_date>={$dateTime}
                  AND app_user_assignment.assignment_id=app_assignment.id
                  AND app_assignment.problem_id=app_problem.id
                  AND app_user_assignment.submission_status_id=app_submission_status.id
                  AND app_assignment.assignment_type_id=app_assignment_type.id
                  AND app_assignment.is_active=1
                  AND app_assignment.class_id={$class['class_id']}
                  ORDER BY app_assignment.open_date DESC";
    }
    $results = aquery($query, $db);

    return $results;
}

/**
 * This retrieves all the problems for the current user, including the ones that are not currently active.
 * @global resource $db
 * @param string $uuid
 * @return array
 */
function getAllAssignmentsByUuid($uuid) {
//retrieves problem_id for current users current class, or all active for anaymous
// retrieves all problems, whether they are closed are not.

    global $db;

    if (isAnonymous ()) {
        // if anonymous, don't bother with this.
        return getAssignments('');
    }

    $class = getClassByUUID($uuid);
    $dateTime = mktime();
    $user = getUserByUUID($uuid);
    $query = "SELECT app_assignment.id, app_assignment.problem_id, app_problem.name, app_problem.description, app_submission_status.status, app_user_assignment.state, app_assignment_type.type
              FROM app_problem, app_user_assignment, app_assignment, app_submission_status, app_assignment_type
              WHERE app_user_assignment.user_id={$user['id']}
              AND app_user_assignment.assignment_id=app_assignment.id
              AND app_assignment.problem_id=app_problem.id
              AND app_user_assignment.submission_status_id=app_submission_status.id
              AND app_assignment.assignment_type_id=app_assignment_type.id
              AND app_assignment.is_active=1
              AND app_assignment.class_id={$class['class_id']}
              ORDER BY app_assignment.open_date DESC";

    $results = aquery($query, $db);

    return $results;
}

/**
 * This fetches the assignment row for the given assignment id. If it does not exist, false is returned.
 * @global resource $db
 * @param int $id
 * @return array This may also return false if the assignment cannot be found.
 */
function getAssignmentById($id) {
    global $db;

    $query = "SELECT *
              FROM app_assignment
              WHERE app_assignment.id={$id}";

    $results = aquery($query, $db);

    if (count($results) > 0) {
        return $results[0];
    } else {
        return false;
    }
}

/**
 * This fetches the class row for the given class id. If it does not exist, false is returned.
 * @global resource $db
 * @param int $id
 * @return array This may also return false if the class cannot be found.
 */
function getClassById($id) {
//returns an assignment by its id

    global $db;

    $query = "SELECT *
              FROM app_class
              WHERE app_class.id={$id}";

    $results = aquery($query, $db);

    if (count($results) > 0) {
        return $results[0];
    } else {
        return false;
    }
}

/**
 * Retrieves the user-class information for the user with the given uuid.
 * If the user does not exist (or does not belong to a class), false is returned.
 * @global resource $db
 * @param string $uuid
 * @return array This will return false if the user does not exist.
 */
function getClassByUUID($uuid) {
//returns a users class id
    global $db;

    $user = getUserByUUID($uuid);

    $query = "SELECT *
              FROM app_user_class
              WHERE app_user_class.user_id={$user['id']}
              AND is_active=1";

    $results = aquery($query, $db);

    if (count($results) > 0) {
        return $results[0];
    } else {
        return false;
    }
}

/**
 * This returns an array of the classes that belong to the given instructor.
 * @global resource $db
 * @param string $uuid
 * @return array
 */
function getClassesByOwner($uuid) {
    global $db;

    $user = getUserByUUID($uuid);

    $query = "SELECT *
              FROM app_class
              WHERE app_class.owner_user_id={$user['id']}
              AND is_active=1";

    $results = aquery($query, $db);

    return $results;
}

/**
 * This returns an array of all assignments that belong to the given instructor.
 * @global resource $db
 * @param string $uuid
 * @return array
 */
function getAssignmentByClassOwner($uuid) {
    global $db;

    $user = getUserByUUID($uuid);

    $query = "SELECT app_assignment.id, app_assignment.class_id, app_problem.name, 
              app_problem.description, app_assignment_type.type, app_assignment.open_date, app_assignment.close_date,
              app_assignment.created_on, app_assignment.updated_on
              FROM app_problem, app_assignment, app_class, app_assignment_type
              WHERE app_class.owner_user_id={$user['id']}
              AND app_assignment.class_id=app_class.id
              AND app_assignment.problem_id=app_problem.id
              AND app_assignment.assignment_type_id=app_assignment_type.id
              AND app_assignment.is_active=1
              ORDER BY app_assignment.id DESC";

    $results = aquery($query, $db);

    return $results;
}

/**
 * This retrieves all the assignments that currently exist. This is intended for use by admins.
 * @global resource $db
 * @return array
 */
function getAllAssignments() {
//retrieves all assignments for use by admin

    global $db;

    $query = "SELECT app_assignment.id, app_assignment.class_id, app_problem.name, 
              app_problem.description, app_assignment_type.type, app_assignment.open_date, app_assignment.close_date,
              app_assignment.created_on, app_assignment.updated_on
              FROM app_problem, app_assignment, app_assignment_type
              WHERE app_assignment.is_active=1
              AND app_assignment.assignment_type_id=app_assignment_type.id
              AND app_assignment.problem_id=app_problem.id
              ORDER BY app_assignment.id DESC";

    $results = aquery($query, $db);

    return $results;
}

/**
 * Returns true if the given user owns the given assignment.
 * @global resource $db
 * @param string $uuid
 * @param int $assignmentId the id of the assignment
 * @return boolean
 */
function isAssignmentOwner($uuid, $assignmentId) {
    global $db;

    $user = getUserByUUID($uuid);

    $query = "SELECT *
              FROM app_assignment, app_class
              WHERE app_assignment.id={$assignmentId}
              AND app_class.owner_user_id={$user['id']}
              AND app_assignment.is_active=1";

    $results = aquery($query, $db);

    if (count($results) > 0) {
        return true;
    } else {
        return false;
    }
}

/**
 * Retrieves all assignments that belong to the given class.
 * @global resource $db
 * @param int $classId
 * @return array
 */
function retrieveAssignments($classId) {

    global $db;

    $query = "SELECT * FROM app_assignment
              WHERE class_id={$classId}
              ORDER BY id DESC";
    $results = aquery($query, $db);

    return $results;
}
//
//function retrieveNewAssignmentByClass($classId) {
//
//    global $db;
//
//    $query = "SELECT * FROM app_assignment
//              WHERE class_id={$classId}
//              ORDER BY id DESC";
//    $results = aquery($query, $db);
//
//    return $results[0];
//}


/**
 * Creates a new assignment for the given problem, with the given class, type, and open and close dates.
 * @global resource $db
 * @param int $problemId
 * @param int $classId
 * @param int $typeId
 * @param date $openDate
 * @param date $closeDate
 * @return boolean
 */
function addAssignment($problemId, $classId, $typeId, $openDate, $closeDate) {

    global $db;

    $q_created_on = mktime();
    $q_updated_on = $q_created_on;

    //add assignment to app_assignment table
    $query2 = "INSERT INTO app_assignment (problem_id, class_id, assignment_type_id, open_date, close_date, created_on, updated_on)
               VALUES ({$problemId}, {$classId},  {$typeId}, '{$openDate}', '{$closeDate}', '{$q_created_on}', '{$q_updated_on}')";
    query($query2, $db);

    return true;
}

/**
 * Attempts to delete the given assignment.
 * @global resource $db
 * @param int $assignmentId
 * @return boolean
 */
function deleteAssignment($assignmentId) {
//sets an assignment is_active=0
//if successful, returns true

    global $db;

    $date = mktime();

    $query = "UPDATE app_assignment
              SET is_active=0, updated_on='{$date}'
              WHERE id={$assignmentId}";

    query($query, $db);

    return true;
}

/**
 * retrieves all of the types of assignments (homework, practice, extra credit).
 * @global resource $db
 * @return array
 */
function getAssignmentTypes() {
    global $db;

    $query = "SELECT *
              FROM app_assignment_type
              WHERE is_active=1";

    $results = aquery($query, $db);

    return $results;
}

/**
 * Updates the given assignment to set its problem, class, type, and open and close dates.
 * @global resource $db
 * @param int $assignmentId the id of the assignment that is being changed.
 * @param int $problemId
 * @param int $classId
 * @param int $typeId
 * @param date $openDate
 * @param date $closeDate
 * @return boolean
 */
function updateAssignment($assignmentId, $problemId, $classId, $typeId, $openDate, $closeDate) {
    global $db;

    $date = mktime();

    $query = "UPDATE app_assignment
              SET problem_id={$problemId}, class_id={$classId}, assignment_type_id={$typeId}, open_date={$openDate}, close_date={$closeDate}, updated_on='{$date}'
              WHERE id={$assignmentId}";

    query($query, $db);

    return true;
}

?>
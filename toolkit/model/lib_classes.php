<?php

/**
 * Retrieves all the students who belong to the given class.
 * @global resource $db
 * @param int $classId
 * @return array
 */
function getUsersbyClass($classId) {
//retrieves users owned by class

    global $db;

    $query = "SELECT user_id
              FROM app_user_class
              WHERE is_active=1
              AND class_id={$classId}
              ORDER BY user_id DESC";

    $results = aquery($query, $db);

    return $results;
}


/**
 * Retrieves all classes that currently exist.
 * @global resource $db
 * @return array
 */
function getClasses() {
    global $db;

    $query = "SELECT *
              FROM app_class
              WHERE is_active=1
              ORDER BY description DESC";

    $results = aquery($query, $db);

    return $results;
}

/**
 * Returns true if the given user is an instructor who owns the given class.
 * @global resource $db
 * @param string $uuid
 * @param int $classId the id of the class in question.
 * @return boolean
 */
function isClassOwner($uuid, $classId) {
    global $db;

    $user = getUserByUUID($uuid);

    $query = "SELECT *
              FROM app_class
              WHERE app_class.id={$classId}
              AND app_class.owner_user_id={$user['id']}
              AND app_class.is_active=1";

    $results = aquery($query, $db);

    if (count($results) > 0) {
        return true;
    } else {
        return false;
    }
}

/**
 * Updates the given class to set the owner id and the description.
 * @global resource $db
 * @param int $classId
 * @param int $ownerId
 * @param string $description
 * @return boolean
 */
function updateClass($classId, $ownerId, $description) {
    global $db;

    $date = mktime();

    $q_description = t2sql($description);

    $query = "UPDATE app_class
              SET owner_user_id={$ownerId}, description='{$q_description}', updated_on='{$date}'
              WHERE id={$classId}";

    query($query, $db);

    return true;
}

/**
 * Attempts to delete the given class. This works by making the class inactive.
 * @global resource $db
 * @param int $classId
 * @return boolean
 */
function deleteClass($classId) {
    global $db;

    $date = mktime();

    $query = "UPDATE app_class
              SET is_active=0, updated_on='{$date}'
              WHERE id={$classId}";

    query($query, $db);

    return true;
}

/**
 * Creates a new class with the given description and owned by the given user.
 * @global resource $db
 * @param int $ownerId the id of the instructor who owns the class.
 * @param string $description
 * @return boolean
 */
function addClass($ownerId, $description) {

    global $db;

    $q_created_on = mktime();
    $q_updated_on = $q_created_on;
    $q_description = t2sql($description);
    $user = getUserById($ownerId);
    $name = $user['first_name'] . ' ' . $user['last_name'];
    //add assignment
    $query2 = "INSERT INTO app_class (owner_user_id, teacher, description, created_on, updated_on)
               VALUES ({$ownerId}, '{$name}', '{$q_description}', '{$q_created_on}', '{$q_updated_on}')";
    query($query2, $db);

    return true;
}

?>
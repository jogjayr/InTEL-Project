<?php

///**
// *
// * @global resource $db
// * @return array
// */
//function getUsers() {
//
//    //returns an array of users
//
//    global $db;
//
//    $query = "SELECT * FROM app_user";
//    $results = aquery($query, $db);
//
//    return $results;
//}

/**
 * Attempts to register the user.
 * @global resource $db
 * @param string $emailAddress
 * @param string $password
 * @param string $firstName
 * @param string $lastName
 * @param int $classId
 * @return boolean
 */
function addUser($emailAddress, $password, $firstName, $lastName, $classId) {

    // takes a email address, password, and profile name
    // returns true if user was added, else returns false

    global $db;
    //append password salt
    $password_salt = getRandomPassword(10);
    $password2 = md5($password_salt . $password);

    //make fields sql friendly
    $q_uuid = md5(uniqid());
    $q_emailAddress = t2sql($emailAddress);
    $q_password = ($password2);
    $q_passwordSalt = ($password_salt);
    $q_firstName = t2sql($firstName);
    $q_lastName = t2sql($lastName);
    $q_userTypeId = 2; //student
    $q_created_on = mktime();
    $q_updated_on = $q_created_on;
    $q_class_id = $classId;
    //check if user exists
    $query = "SELECT * FROM app_user WHERE email='{$q_emailAddress}'";
    $results = aquery($query, $db);
    if (count($results) > 0) {
        return false; //user already in database
    }
    //add user
    $query2 = "INSERT INTO app_user (uuid, email, password, password_salt, first_name, last_name, user_type_id, created_on, updated_on) VALUES ('{$q_uuid}', '{$q_emailAddress}', '{$q_password}', '{$q_passwordSalt}', '{$q_firstName}', '{$q_lastName}', '{$q_userTypeId}', '{$q_created_on}', '{$q_updated_on}')";
    query($query2, $db);

    // add selected class
    $user = getUserByUUID($q_uuid);
    $q_userId = $user['id'];

    $query2 = "INSERT INTO app_user_class (user_id, class_id, created_on, updated_on) VALUES ('{$q_userId}', '{$q_class_id}', '{$q_created_on}', '{$q_updated_on}')";
    query($query2, $db);

    //get class assignments
//    $assignments = retrieveAssignments($q_class_id);
    //add assignments to user
//    foreach ($assignments as $assignment) {
//
//        $query2 = "INSERT INTO app_user_assignment (user_id, assignment_id, submission_status_id, created_on, updated_on) VALUES ('{$q_userId}', '{$assignment['id']}', 1, '{$q_created_on}', '{$q_updated_on}')";
//        query($query2, $db);
//    }
    return true;
}

/**
 * This adds submissions to the given user uuid. This is used to make sure that a user will have submission entries
 * when they view problems. This call loopusers through all the existing assignments and makes sure that there is an entry if the user has the problem assigned.
 * @global resource $db
 * @param string $uuid
 */
function updateUserSubmissions($uuid) {
    global $db;
    //get the user
    $user = getUserByUUID($uuid);
    //get the class
    $userClass = getClassByUUID($uuid);
    //get class assignments
    $class_assignments = retrieveAssignments($userClass['class_id']);
    //get the existing user assignments
    $user_assignments = getAssignments($uuid);
    //record the time
    $q_created_on = mktime();
    $q_updated_on = $q_created_on;

    //add assignments to user IF THEY DO NOT EXIST YET
    foreach ($class_assignments as $assignment) {
        if (!userHasAssignment($uuid, $assignment['id'])) {
            $query2 = "INSERT INTO app_user_assignment (user_id, assignment_id, submission_status_id, created_on, updated_on) VALUES ('{$user['id']}', '{$assignment['id']}', 1, '{$q_created_on}', '{$q_updated_on}')";
            query($query2, $db);
        }
    }
}

/**
 * Checks to see whether the user has a submission entry for the given assignment.
 * @global resource $db
 * @param string $uuid
 * @param int $assignment_id
 * @return boolean
 */
function userHasAssignment($uuid, $assignment_id) {
    //checks to see if a user has an assignment
    global $db;
    //get the user
    $user = getUserByUUID($uuid);
    $q_userId = $user['id'];
    $query = "SELECT * FROM app_user_assignment
              WHERE user_id={$q_userId}
              AND assignment_id={$assignment_id}";
    $results = aquery($query, $db);

    if (count($results) > 0) {
        return true;
    } else {
        return false;
    }
}

/**
 * Fetches the given user by id. Returns false if the id does not exist.
 * @global resource $db
 * @param int $userId
 * @return array
 */
function getUserById($userId) {
    global $db;

    $q_userId = t2sql($userId);
    $query = "SELECT * FROM app_user WHERE id='{$q_userId}'";
    $results = aquery($query, $db);

    if (count($results) > 0) {
        return $results[0];
    } else {
        return false;
    }
}

/**
 * Fetches the user by uuid. Returns false if the uuid does not correspond to a user.
 * @global resource $db
 * @param string $uuid
 * @return array
 */
function getUserByUUID($uuid) {
    global $db;

    $q_uuid = t2sql($uuid);
    $query = "SELECT * FROM app_user WHERE uuid='{$q_uuid}'";
    $results = aquery($query, $db);

    if (count($results) > 0) {
        return $results[0];
    } else {
        return false;
    }
}

/**
 * Returns all users who are admins or instructors.
 * @global resource $db
 * @return array
 */
function getOwners() {
    global $db;

    $query = "SELECT * FROM app_user
              WHERE user_type_id=3
              OR user_type_id=4";
    $results = aquery($query, $db);

    return $results;
}

/**
 * Returns the type of the user (i.e. admin, instructor, student).
 * @global resource $db
 * @param string $uuid
 * @return string
 */
function getUserType($uuid) {
    global $db;

    $user = getUserByUUID($uuid);

    if ($user == '') {
        return false;
    }

    $query = "SELECT * FROM app_user_type WHERE id={$user['user_type_id']}";
    $results = aquery($query, $db);

    if (count($results) > 0) {
        return $results[0]['type'];
    } else {
        return '';
    }
}

/**
 * Changes the password for the given user.
 * @global resource $db
 * @param string $uuid
 * @param string $new_password
 * @return boolean
 */
function changePassword($uuid, $new_password) {
    global $db;

    $user = getUserByUUID($uuid);

    if ($user == '') {
        return false;
    }

    $new_password_salt = getRandomPassword(10);

    $q_new_password = t2sql(md5($new_password_salt . $new_password));
    $q_new_password_salt = t2sql($new_password_salt);
    $q_uuid = $uuid;

    $query = "UPDATE app_user SET password_salt='{$q_new_password_salt}', password='{$q_new_password}' WHERE uuid='{$q_uuid}'";
    query($query, $db);

    return true;
}

/**
 * Verifies whether the user email/password pair matches.
 * Returns the user's UUID if authentication is successful, false otherwise.
 * @global resource $db
 * @param string $emailAddress
 * @param string $password
 * @return string The UUID of the user if successful, false on failure.
 */
function authenticate($emailAddress, $password) {
    // takes emailAddress and password
    // returns the uuid of the authenticated use if the user is active and registered, else returns 0

    global $db;

    $q_emailAddress = t2sql($emailAddress);

    $query = "SELECT uuid, password, password_salt FROM app_user WHERE email='{$q_emailAddress}' AND is_active = 1";
    $results = aquery($query, $db);
    if (count($results) > 0) {
        $user = $results[0];

        if ($user['password'] == md5($user['password_salt'] . $password)) {
            return $user['uuid']; //user authenticated
        }
    }

    return 0;
}

/**
 * Updates the user with the given uuid, changing the name, email, and class id.
 * @global resource $db
 * @param string $uuid
 * @param string $firstName
 * @param string $lastName
 * @param string $email
 * @param int $classId
 * @return boolean
 */
function updateAccount($uuid, $firstName, $lastName, $email, $classId) {
    global $db;

    $q_uuid = t2sql($uuid);
    $q_firstName = t2sql($firstName);
    $q_lastName = t2sql($lastName);
    $q_email = t2sql($email);
    $q_classId = $classId;

    $query = "UPDATE app_user
              SET first_name='{$q_firstName}', last_name='{$q_lastName}', email='{$q_email}',
              WHERE uuid='{$q_uuid}'";

    query($query, $db);

    //update user_class table
    $user = getUserByUUID($q_uuid);
    $query2 = "UPDATE app_user_class
               SET class_id={$q_classId}
               WHERE user_id={$user['id']}";

    query($query2, $db);

    $_SESSION['user_first_name'] = $firstName;
    $_SESSION['user_last_name'] = $lastName;

    return true;
}
?>
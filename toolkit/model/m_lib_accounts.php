<?php

function getUsers() {

	//returns an array of users

	global $db;
	
	$query = "SELECT * FROM app_user";
	$results = aquery($query, $db);
	
	return $results;

}

function addUser($emailAddress, $password, $gtPrismId, $firstName, $lastName, $classId) {
	
	// takes a email address, password, and profile name
	// returns true if user was added, else returns false

	global $db;
	//append password salt
	$password_salt = getRandomPassword(10);
	$password2 = md5($password_salt.$password);
	
  //make fields sql friendly
	$q_uuid = md5(uniqid());
	$q_gtPrismId = t2sql($gtPrismId);
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
	$query2 = "INSERT INTO app_user (uuid, gt_prism_id, email, password, password_salt, first_name, last_name, user_type_id, created_on, updated_on) VALUES ('{$q_uuid}', '{$q_gtPrismId}', '{$q_emailAddress}', '{$q_password}', '{$q_passwordSalt}', '{$q_firstName}', '{$q_lastName}', '{$q_userTypeId}', '{$q_created_on}', '{$q_updated_on}')";
	query($query2, $db);
  
  // add selected class
  $user = getUserByUUID($q_uuid);
  $q_userId = $user['id'];
  
  $query2 = "INSERT INTO app_user_class (user_id, class_id, created_on, updated_on) VALUES ('{$q_userId}', '{$q_class_id}', '{$q_created_on}', '{$q_updated_on}')";
	query($query2, $db);
  
  //get class assignments
  $assignments = retrieveAssignments($q_class_id);
  
  //add assignments to user
  foreach($assignments as $assignment) {
  
  $query2 = "INSERT INTO app_user_assignment (user_id, assignment_id, submission_status_id, created_on, updated_on) VALUES ('{$q_userId}', '{$assignment['id']}', 1, '{$q_created_on}', '{$q_updated_on}')";
	query($query2, $db);
	
  }
	return true;
}

function getUserById($userId) {
	//returns the user associated with userId if they exist, else returns an empty string
	
	global $db;
	
	$q_userId = t2sql($userId);
	$query = "SELECT * FROM app_user WHERE id='{$q_userId}'";
	$results = aquery($query, $db);
	
	if (count($results) > 0) {
		return $results[0];
	} else {
		return '';
	}

}

function getUserByUUID($uuid) {
	//returns the user associated with userId if they exist, else returns an empty string
	
	global $db;
	
	$q_uuid = t2sql($uuid);
	$query = "SELECT * FROM app_user WHERE uuid='{$q_uuid}'";
	$results = aquery($query, $db);
	
	if (count($results) > 0) {
		return $results[0];
	} else {
		return '';
	}

}

function getOwners(){
  //returns users that are admins or instructors
	
	global $db;
	
	$query = "SELECT * FROM app_user 
  WHERE user_type_id=3 
  OR user_type_id=4";
	$results = aquery($query, $db);
	
	if (count($results) > 0) {
		return $results;
	} else {
		return false;
	}
}

function getUserType($uuid) {
  //returns the user type (i.e. admin, instructor, student)
  
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

function changePassword($uuid, $new_password) {
	//changes the password for a user.
	//returns true if the change is successful, else returns false
	
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

function updateAccount($uuid, $firstName, $lastName, $email, $gtPrismId, $err) {
	//updates a users account information
	//if successful, returns true, else puts an error message into the err variable and returns false
	
	global $db;
	
	$q_uuid = t2sql($uuid);
	$q_firstName = t2sql($firstName);
  $q_lastName = t2sql($lastName);
  $q_email = t2sql($email);
  $q_gt_prism_id = t2sql($gtPrismId);
	
	$query = "UPDATE app_user 
  SET first_name='{$q_firstName}', last_name='{$q_lastName}', email='{$q_email}', gt_prism_id='{$q_gt_prism_id}' 
  WHERE uuid='{$q_uuid}'";
	
  query($query, $db);
  
	$_SESSION['user_first_name'] = $firstName;
  $_SESSION['user_last_name'] = $lastName;
	
	return true;	
}

?>
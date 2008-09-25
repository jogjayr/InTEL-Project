<?php

function getAssignments($uuid) {
  //retrieves problem_id for current user, or all active for anaymous
  
  global $db;
  
  if (isAnonymous()) {
    $query = "SELECT id AS problem_id, name, description FROM app_problem WHERE is_active=1 ORDER BY name DESC";
  }
  else{
    $dateTime = mktime();
    $user = getUserByUUID($uuid);
    $query = "SELECT app_assignment.id, app_assignment.problem_id, app_problem.name, app_problem.description, app_submission_status.status 
    FROM app_problem, app_user_assignment, app_assignment, app_submission_status 
    WHERE app_user_assignment.user_id={$user['id']} 
    AND app_assignment.open_date<={$dateTime} 
    AND app_assignment.close_date>={$dateTime} 
    AND app_user_assignment.assignment_id=app_assignment.id 
    AND app_assignment.problem_id=app_problem.id 
    AND app_user_assignment.submission_status_id=app_submission_status.id
    ORDER BY app_assignment.open_date DESC";
  }
  $results = aquery($query, $db);
	
	return $results;
}

function getAssignmentById($id){
  //returns an assignment by its id
    
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

function getClassById($id){
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

function getClassesByOwner($uuid){
  //returns classes belonging to an owner
    
  global $db;
  
  $user = getUserByUUID($uuid);
  
  $query = "SELECT * 
  FROM app_class 
  WHERE app_class.owner_user_id={$user['id']} 
  AND is_active=1";
    
  $results = aquery($query, $db);
	
  if (count($results) > 0) {
		return $results;
	} else {
		return false;
	}
}

function getAssignmentByClassOwner($uuid){
  //retrieves assignments that belong to a class owned by this user
  
  global $db;
  
  $user = getUserByUUID($uuid);
  
  $query = "SELECT app_assignment.id, app_assignment.class_id, app_problem.name, app_problem.description, app_assignment.open_date, app_assignment.close_date 
  FROM app_problem, app_assignment, app_class 
  WHERE app_class.owner_user_id={$user['id']} 
  AND app_assignment.class_id=app_class.id 
  AND app_assignment.problem_id=app_problem.id 
  AND app_assignment.is_active=1
  ORDER BY app_assignment.open_date DESC";
    
  $results = aquery($query, $db);
	
	return $results;
}

function getAllAssignments(){
  //retrieves all assignments for use by admin
  
  global $db;
  
  $query = "SELECT app_assignment.id, app_assignment.class_id, app_problem.name, app_problem.description, app_assignment.open_date, app_assignment.close_date 
  FROM app_problem, app_assignment 
  WHERE app_assignment.is_active=1 
  AND app_assignment.problem_id=app_problem.id 
  ORDER BY name DESC";
  
  $results = aquery($query, $db);
	
	return $results;
}

function isAssignmentOwner($uuid, $assignmentId){
  //verifies that user owns this assignment
  
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

function isClassOwner($uuid, $classId){
  //verifies that user owns this assignment
  
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

function getClasses(){
  //retrieves all active classes
  
  global $db;
  
  $query = "SELECT *  
  FROM app_class 
  WHERE is_active=1 
  ORDER BY description DESC";

  $results = aquery($query, $db);
	
	return $results;
}


function getClassByOwner($uuid){
  //retrieves classes owned by user
  
  global $db;
  
  $user = getUserByUUID($uuid);
  
  $query = "SELECT * 
  FROM app_class 
  WHERE is_active=1
  AND owner_user_id={$user['id']}
  ORDER BY description DESC";

  $results = aquery($query, $db);
	
	return $results;
}

function updateClass($classId, $ownerId, $description){
	//updates a class
	//if successful, returns true
	
	global $db;
	
  $date = mktime();
  
  $q_description = t2sql($description);
  
	$query = "UPDATE app_class 
  SET owner_user_id={$ownerId}, description='{$q_description}', updated_on='{$date}' 
  WHERE id={$classId}";
  
	query($query, $db);
  
	return true;	

}

function deleteClass($classId){
	//sets a class is_active=0
	//if successful, returns true
	
	global $db;
	
  $date = mktime();
  
	$query = "UPDATE app_class 
  SET is_active=0, updated_on='{$date}'
  WHERE id={$classId}";
  
	query($query, $db);
  
	return true;	
}

function addClass($ownerId, $description){

  global $db;
  
  $q_created_on = mktime();
  $q_updated_on = $q_created_on;
  $q_description = t2sql($description);
  $user = getUserById($ownerId);
  $name = $user['first_name'].' '.$user['last_name'];
  //add assignment
	$query2 = "INSERT INTO app_class (owner_user_id, teacher, description, created_on, updated_on) 
  VALUES ({$ownerId}, '{$name}', '{$q_description}', '{$q_created_on}', '{$q_updated_on}')";
	query($query2, $db);

  return true;
}

function getSubmissions(){
  //retrieves all active submissions
  
  global $db;
  
  $query = "SELECT app_user.first_name, app_user.last_name, app_user.email, app_problem.name, app_assignment.class_id, app_submission_status.status, app_user_assignment.updated_on 
  FROM app_user, app_assignment, app_submission_status, app_user_assignment, app_problem 
  WHERE app_user_assignment.is_active=1 
  AND app_assignment.is_active=1 
  AND app_user.id=app_user_assignment.user_id
  AND app_assignment.id=app_user_assignment.assignment_id 
  AND app_problem.id=app_assignment.problem_id 
  AND app_submission_status.id=app_user_assignment.submission_status_id 
  ORDER BY app_assignment.class_id, app_user_assignment.assignment_id, app_user_assignment.updated_on DESC";

  $results = aquery($query, $db);
	
	return $results;

}

function getSubmissionsByOwner($uuid){
  //retrieves all active submissions by this owner
  
  global $db;
  
  $user = getUserByUUID($uuid);
  
  $query = "SELECT app_user.first_name, app_user.last_name, app_user.email, app_problem.name, app_assignment.class_id, app_submission_status.status, app_user_assignment.updated_on 
  FROM app_user, app_assignment, app_submission_status, app_user_assignment, app_class, app_problem 
  WHERE app_user_assignment.is_active=1 
  AND app_assignment.is_active=1 
  AND app_user.id=app_user_assignment.user_id
  AND app_assignment.id=app_user_assignment.assignment_id 
  AND app_submission_status.id=app_user_assignment.submission_status_id 
  AND app_class.id=app_assignment.class_id 
  AND app_problem.id=app_assignment.problem_id 
  AND app_class.owner_user_id={$user['id']} 
  ORDER BY app_assignment.class_id, app_user_assignment.assignment_id, app_user_assignment.updated_on DESC";

  $results = aquery($query, $db);
	
	return $results;

}
function retrieveAssignments($classId) {
 
  global $db;
  
  $query = "SELECT * FROM app_assignment WHERE class_id={$classId}";
  $results = aquery($query, $db);
  
	return $results;
}

function retrieveProblem($problemId) {
 
  global $db;
  
  $query = "SELECT * FROM app_problem WHERE id={$problemId}";
  $results = aquery($query, $db);
  
  if (count($results) > 0) {
		return $results[0];
	} else {
		return '';
	}
}

function getAllProblems(){
  //retrieves all active problems
  
  global $db;
  
  $query = "SELECT * FROM app_problem WHERE is_active=1";
  $results = aquery($query, $db);
  
  $results = aquery($query, $db);
	
	return $results;
}

function updateAssignment($assignmentId, $problemId, $classId, $openDate, $closeDate){

	//updates an assignment
	//if successful, returns true
	
	global $db;
	
  $date = mktime();
  
	$query = "UPDATE app_assignment 
  SET problem_id={$problemId}, class_id={$classId}, open_date={$openDate}, close_date={$closeDate}, updated_on='{$date}' 
  WHERE id={$assignmentId}";
  
	query($query, $db);
  
	return true;	

}

function addAssignment($problemId, $classId, $openDate, $closeDate){

  global $db;
  
  $q_created_on = mktime();
  $q_updated_on = $q_created_on;
  
  //add assignment
	$query2 = "INSERT INTO app_assignment (problem_id, class_id, open_date, close_date, created_on, updated_on) 
  VALUES ({$problemId}, {$classId}, '{$openDate}', '{$closeDate}', '{$q_created_on}', '{$q_updated_on}')";
	query($query2, $db);

  return true;
}

function deleteAssignment($assignmentId){
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
?>
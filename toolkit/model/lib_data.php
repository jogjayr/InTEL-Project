<?php

function getAssignments($uuid) {
  //retrieves problem_id for current user, or all active for anaymous
  
  global $db;
  
  if (isAnonymous()) {
    $query = "SELECT id, name, description FROM app_problem WHERE is_active=1 ORDER BY name DESC";
  }
  else{
    $dateTime = mktime();
    $user = getUserByUUID($uuid);
    $query = "SELECT app_problem.id, app_problem.name, app_problem.description, app_submission_status.status 
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
  
  $query = "SELECT app_assignment.id, app_problem.name, app_problem.description, app_assignment.open_date, app_assignment.close_date 
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

function getClasses(){
  //retrieves all classes
  
  global $db;
  
  $query = "SELECT teacher, description, id 
  FROM app_class 
  WHERE is_active=1 
  ORDER BY description DESC";

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

function updateAssignment($assignmentId, $problemId, $classId, $openDate, $closeDate){

	//updates an assignment
	//if successful, returns true
	
	global $db;
	
	$query = "UPDATE app_assignment 
  SET problem_id={$problemId}, class_id={$classId}, open_date={$openDate}, close_date={$closeDate} 
  WHERE id={$assignmentId}";
  
	query($query, $db);
  
	return true;	

}

function deleteAssignment($assignmentId){
	//sets an assignment is_active=0
	//if successful, returns true
	
	global $db;
	
	$query = "UPDATE app_assignment 
  SET is_active=0 
  WHERE id={$assignmentId}";
  
	query($query, $db);
  
	return true;	

}
?>
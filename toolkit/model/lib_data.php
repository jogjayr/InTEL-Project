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

function getClasses(){
  //retrieves problem_id for current user, or all active for anaymous
  
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

?>
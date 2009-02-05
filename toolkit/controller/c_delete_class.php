<?php
 	require_once('admin/initvars.php');
	requireLogin(); 
  
  //verify rights
  if (!isInstructor() && !isAdmin()) {
		redirectRel('index.php');
	}
  
  //if get data set the class id
  $permission = false;
  if (isset($_GET['id'])){
    $classId = $_GET['id'];
    //check if user is admin or owner of assignment
    if(isInstructor() && isClassOwner($_SESSION['uuid'], $classId) || isAdmin()){
      $permission = true;
    }
    else{
      //user shouldn't be here
      redirectRel('index.php');
    }
  }
  if ($permission){
    deleteClass($classId);
    redirectRel('manageClasses.php');
  }
	
?>

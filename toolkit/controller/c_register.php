<?php
  require_once('admin/initvars.php');
	prohibitLogin();

  //get all classes
  $classes = getClasses();
  
  //initialize post variables
  $success = false;
	$err = '';
  $emailAddress = '';
  $firstName = '';
  $lastName = '';
  $gtPrismId = '';
  $password = '';
  $classId = 0;
  
  //check for post data
	if (isset($_POST['submit'])) {
		
		$emailAddress = $_POST['email'];
		$password = $_POST['password'];
		$password2 = $_POST['password2'];
    $firstName = $_POST['first_name'];
    $lastName = $_POST['last_name'];
    $gtPrismId = $_POST['gt_prism_id'];
    if (isset($_POST['class_id'])){
      $classId = $_POST['class_id'];
    }
    
    //check for valid email address
    if ($classId!=0){
  		if (isEmailAddress($emailAddress)) {
        //check if passwords match
  			if ($password == $password2) {
          //check that the fields are not empty
  				if ($password != '' && $firstName != '' && $lastName != '' && $gtPrismId != '') {
  					if (registerUser($emailAddress, $password, $gtPrismId, $firstName, $lastName, $classId)) {
  						$success = true;
  					} else {
  						$err = 'Please enter another email address.';
  					}
  				} else {
  					$err = 'Please enter a password.';
  				}
  			} else {
  				$err = 'The passwords do not match.';
  			}
  		} else {
  			$err = 'Please enter a valid email address.';
  		}
    } else {
  			$err = 'Please choose which class you belong to.';
  	}
	}
		
?>
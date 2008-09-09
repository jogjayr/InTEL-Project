<?php

	require_once('admin/initvars.php');
	requireLogin(); 
  
	$success = false;
	$err = '';
  
  //if post data is sent update the database
	if (isset($_POST['submit'])) {
    $email = $_POST['email'];
    $oldPassword = $_POST['old_password'];
    $newPassword = $_POST['password'];
    $newPassword2 = $_POST['password2'];
    
    //authenticate the user
    $uuid = authenticate($email, $oldPassword);
  	if ($uuid > 0) {
    
    //check password and change
      if ($newPassword == $newPassword2) {
    		if (changePassword($uuid, $newPassword)) {
    			$success = true;
    		}
      }
      else{
        $err = 'The new passwords do not match.';
      }
    }
    else{
      $err = 'The email address and old passwords are not correct.';
    }
  }
  if ($success) {
		para('Your profile has been updated.');
	} else {
		paraErr($err);
	}
?>
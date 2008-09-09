<?php

	require_once('admin/initvars.php');
	requireLogin(); 
  
	$success = false;
	$err = '';
  
  //if post data is sent update the database
	if (isset($_POST['submit'])) {
		$firstName = $_POST['first_name'];
    $lastName = $_POST['last_name'];
    $email = $_POST['email'];
    $gtPrismId = $_POST['gt_prism_id'];
		if (updateAccount($_SESSION['uuid'], $firstName, $lastName, $email, $gtPrismId, $err)) {
			$success = true;
		}
	}
  
  
  if ($success) {
		para('Your profile has been updated.');
	} else {
		paraErr($err);
	}
?>
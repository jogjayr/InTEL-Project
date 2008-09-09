<?php require_once('admin/initvars.php'); 
  $isAnonymous = isAnonymous();
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><?php echo t2h($site_title); if ($title != '') { echo ' - ' . t2h($title); } ?></title>
<link rel="stylesheet" type="text/css" href="styles/toolkit.css" />
</head>

<body>
<div id="header">
	<h1>Interactive Toolkit for Engineering Learning</h1>
</div>

<div id="main_nav">
<ul>
	<li><a href="about.php">About</a></li>
  <?php
    if (isStudent()){
  ?>
    <li><a href="myAssignments.php">My Assignments</a></li>
  <?php
    } //end if	
    if (isInstructor()){
  ?>
    <li><a href="manageAssignments.php">Manage Assignments</a></li>
    <li><a href="viewSubmissions.php">View Submissions</a></li>
    <li><a href="addClass.php">Add a Class</a></li>
  <?php
    } //end if	
    if (isAdmin()){
  ?>
    <li><a href="myAssignments.php">My Assignments</a></li>
    <li><a href="manageAssignments.php">Manage Assignments</a></li>
    <li><a href="viewSubmissions.php">View Submissions</a></li>
	<?php 
    } //end if	
		if ($isAnonymous) {
	?>
    <li><a href="myAssignments.php">View Problems</a></li>
	<?php 
    } //end if	
		if (isLoggedIn()) {
	?>
		<li><a href="logout.php">Logout</a></li>
    <li><a href="account.php">Account</a></li>
	<?php
		} else {
	?>
		<li><a href="register.php">Register</a></li>
		<li><a href="login.php">Login</a></li>
	<?php
		} //end if		
	?>
  <li><a href="help.php">Instructions</a></li>
  <li><a href="contact.php">Contact</a></li>
</ul>
</div>
<div id="content">
<h2><?php echo t2h($title); ?></h2>

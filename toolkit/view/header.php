<?php require_once('admin/initvars.php'); 
  //ini_set("display_errors","2");
  //ERROR_REPORTING(E_ALL);
  
  $isAnonymous = isAnonymous();
  if ($title == 'Problems' && !$isAnonymous){
    //quick title fix  This can be changed in the future.
    $title = 'My Assignments';
  }
  //compact nav test for launch app window
  //$navId = 'main_nav'; //change this to get a side bar when the app is not running
  $navId = 'compact_nav';
  if(isset($type)){
    if ($type = 'compact'){
      $navId = 'compact_nav';
    }
  }
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
  <a href="http://intel.gatech.edu"><img src="resources/logo.gif" alt = "InTEL" /></a><br />
	<h1>Statics Toolkit</h1>
</div>

<div id="<?php echo $navId ?>">
  <?php
    if (isStudent()){
  ?>
    <div class='nav_button'><a href="myAssignments.php">My Assignments</a></div>
  <?php
    } //end if	
    if (isInstructor()){
  ?>
    <div class='nav_button'><a href="manageAssignments.php">Manage Assignments</a></div>
    <div class='nav_button'><a href="manageClasses.php">Manage Classes</a></div>
    <div class='nav_button'><a href="viewSubmissions.php">View Submissions</a></div>
  <?php
    } //end if	
    if (isAdmin()){
  ?>
    <div class='nav_button'><a href="myAssignments.php">My Assignments</a></div>
    <div class='nav_button'><a href="manageClasses.php">Manage Classes</a></div>
    <div class='nav_button'><a href="manageAssignments.php">Manage Assignments</a></div>
    <div class='nav_button'><a href="viewSubmissions.php">View Submissions</a></div>
    <div class='nav_button'><a href="viewLoggerData.php">LoggerData</a></div>
	<?php 
    } //end if	
		if ($isAnonymous) {
	?>
    <div class='nav_button'><a href="myAssignments.php">View Problems</a></div>
	<?php 
    } //end if	
		if (isLoggedIn()) {
	?>
		<div class='nav_button'><a href="logout.php">Logout</a></div>
    <div class='nav_button'><a href="account.php">Account</a></div>
	<?php
		} else {
	?>
		<div class='nav_button'><a href="register.php">Register</a></div>
		<div class='nav_button'><a href="login.php">Login</a></div>
	<?php
		} //end if		
	?>
  <div class='nav_button'><a href="help.php">Instructions</a></div>
  <div class='nav_button'><a href="mailto:dupton3@gatech.edu">Contact</a></div>
</ul>
</div>
<div id="content">
<h2><?php echo t2h($title); ?></h2>

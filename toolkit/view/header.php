<?php
require_once('admin/initvars.php');

//compact nav test for launch app window
//$navId = 'main_nav'; //change this to get a side bar when the app is not running
//$navId = 'compact_nav';
//  if(isset($type)){
//    if ($type == 'compact'){
//      $navId = 'compact_nav';
//    }
//  }
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title><?php
echo t2h($site_title);
if ($title != '') {
    echo ' - ' . t2h($title);
}
?></title>
        <link rel="stylesheet" type="text/css" href="styles/toolkit.css" />
        <?php if (isset($headExtra))
            echo $headExtra; ?>
    </head>

    <body <?php if (isset($bodyExtra))
            echo $bodyExtra; ?>>

        <div class="loginpanel">
            <?php
            // this is the login panel; the user can login and logout from this panel.
            if (isLoggedIn ()) {
                echo "Welcome, {$user['first_name']} {$user['last_name']}<br/>";
                $userClass = getClassByUUID($uuid);
                $class = getClassById($userClass['class_id']);
                echo "You are in section: {$class['description']}<br/>";
                //echo '<a href="account.php">Account</a> <a href="logout.php">Logout</a>';
                echo '<form method="post" action=""><input type="submit" name="logout" value="Logout" /></form>';
            } else {
                echo '<form method="post" action="">';
                if ($err != '')
                    para($err, 'errorMessage');
                echo '<p>Email Address: <input type="text" style = "width:300px" name="email" /></p>';
                echo '<p>Password: <input type="password" name="password" /></p>';
                echo '<p><input type="submit" name="login" value="Login" /> <a href="help.php">Forgot your password?</a> <a href="register.php">Register</a></p>';
                echo '</form>';
            }
            ?>
        </div>

        <div id="header">
            <a href="http://intel.gatech.edu"><img src="resources/logo.gif" alt = "InTEL" /></a><br />
            <h1>Statics Toolkit</h1>
        </div>

        <?php
            // primary navigation:
        ?>
            <div id="compact_nav">
            <?php
            if (isAnonymous ()) {
            ?>
                <div class='nav_button'><a href="myAssignments.php">View Problems</a></div>
            <?php
            } elseif (isStudent ()) {
            ?>
                <div class='nav_button'><a href="myAssignments.php">My Assignments</a></div>
            <?php
            } elseif (isInstructor ()) {
            ?>
                <div class='nav_button'><a href="viewSubmissions.php">View Submissions</a></div>
                <div class='nav_button'><a href="manageAssignments.php">Manage Assignments</a></div>
                <div class='nav_button'><a href="manageClasses.php">Manage Classes</a></div>
            <?php
            } elseif (isAdmin ()) {
            ?>
                <div class='nav_button'><a href="viewSubmissions.php">View Submissions</a></div>
                <div class='nav_button'><a href="myAssignments.php">My Assignments</a></div>
                <div class='nav_button'><a href="manageAssignments.php">Manage Assignments</a></div>
                <div class='nav_button'><a href="manageClasses.php">Manage Classes</a></div>
                <div class='nav_button'><a href="viewLoggerData.php">LoggerData</a></div>
            <?php
            } // end user type switch
            ?>
            <div class='nav_button'><a href="help.php">Instructions</a></div>
            <div class='nav_button'><a href="mailto:<?php echo $site_email_address; ?>">Contact</a></div>
            <!--            </ul>-->
        </div>
        <div id="content">
            <h2><?php echo t2h($title); ?></h2>

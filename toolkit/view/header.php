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
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript">
            var feedbackBoxVisible = false;
            function showFeedbackBox() {
                if(!feedbackBoxVisible) {
                    $("body").append('<div class="feedbackBox">'+
                        '<p>Please give us feedback! We want to know what we did right, what we did wrong, how the software helped, and how it didn\'t.</p>'+
                        '<textarea rows="10" style="width: 100%" id="feedback"></textarea><br/>'+
                        '<div id="feedbackResponse" /><br/>'+
                        '<input id="submitFeedbackButton" type="button" onclick="onSubmitFeedback()" value="submit"/>'+
                        '<input id="cancelFeedbackButton" type="button" onclick="onCancelFeedback()" value="cancel"/>'+
                        '</div>');
                    feedbackBoxVisible = true;
                }
            }
            function onSubmitFeedback() {
                $.ajax({
                    url: "postFeedback.php",
                    type: "POST",
                    data: {feedback: $("#feedback").val()},
                    success: function(msg) {
                        $("#feedbackResponse").addClass("infoMessage").html("Thank you! Your feedback has been submitted!");
                        $("#cancelFeedbackButton").attr("value","OK");
                    },
                    error: function(msg) {
                        $("#feedbackResponse").addClass("errorMessage").html("Error! Please contact support!");
                    }
                });

                $("#feedback").attr("readonly", "true");
                //$("#submitButton").attr("disabled", "true");
                $("#submitFeedbackButton").remove();
            }

            function onCancelFeedback() {
                $(".feedbackBox").fadeOut("slow", function() {
                    $(".feedbackBox").remove();
                    feedbackBoxVisible = false;
                });
            }
        </script>

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
                echo "Welcome, <strong>{$user['first_name']} {$user['last_name']}</strong><br/>";
                $userClass = getClassByUUID($uuid);
                $class = getClassById($userClass['class_id']);
                echo "You are in section: {$class['description']}<br/>";
                echo 'Account type: ' . getUserType($uuid) . '<br/>';
                echo '<a href="account.php">Manage account</a><br/>';
                echo '<form method="post" action=""><input type="submit" name="logout" value="Logout" /></form>';
            } else {

                // if there was an attempted login and there is an error message, display it here.
                if (isset($_POST['login']) && $err != '')
                    para($err, 'errorMessage');
            ?>
                <form method="post" action="">
                    <table>
                        <tr><td>Email Address: </td><td><input type="text"  name="email" /></td></tr>
                        <tr><td>Password: </td><td><input type="password"  name="password" /></td></tr>
                    </table>
                    <p><input type="submit" name="login" value="Login" /> <a href="help.php">Forgot your password?</a> <a href="register.php">Register</a></p>
                </form>
            <?
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
            <div class='nav_button'><a href="javascript:showFeedbackBox()">Give us feedback!</a></div>
            <div class='nav_button'><a href="mailto:<?php echo $site_email_address; ?>">Contact</a></div>
            <!--            </ul>-->
        </div>
        <div id="content">
            <h2><?php echo t2h($title); ?></h2>

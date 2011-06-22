<?php
require_once('admin/initvars.php');/*Calls all Components*/
$banner = '../images/bridgeRender.jpg';


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
			function loginNo(){
        		document.getElementById('login_form').style.display = "none"
        	}
        	function loginYes(){
        	document.getElementById('login_form').style.display = "block"
        	}
			function submitForm(fieldValue){
  			document.logoutForm.logout.value=fieldValue;
  			document.logoutForm.submit();
			}
        </script>
<script type="text/javascript">
        
          var _gaq = _gaq || [];
          _gaq.push(['_setAccount', 'UA-23394980-1']);
          _gaq.push(['_trackPageview']);
        
          (function() {
            var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
          })();
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
            

  <div id="header">
  <div class="loginpanel">
			<?php
            // this is the login panel; the user can login and logout from this panel.
            if (isLoggedIn ()) {
                echo "Welcome, <strong>{$user['first_name']} {$user['last_name']}</strong>&nbsp;&nbsp;";
                $userClass = getClassByUUID($uuid);
                $class = getClassById($userClass['class_id']);
                echo "Section: {$class['description']}&nbsp;&nbsp;";
                /*echo 'Account type: ' . getUserType($uuid) . '<br/>';*/
				getUserType($uuid);
                echo '<a href="account.php">Manage account</a>&nbsp;&nbsp;<form method="post" action=""><input type="submit" name="logout" value="Logout" /></form>';
				/*echo '<a href="account.php">Manage account</a>&nbsp;&nbsp;
					<form id="logoutForm" method="post" action="">
  						<input type="hidden" name="logout" value="Logout">
						<a href="javascript:;" onclick="submitForm(\'Logout\')">Log out</a>
					</form>';*/
					
				/*echo '<a href="account.php">Manage account</a>&nbsp;&nbsp;
				<form name="logoutForm">
						<input type="hidden" name="myHiddenField" value="Logout">
						<a href="javascript:submitForm('Logout')">Log out</a>
					</form>';*/
					
				/*echo '
				<form id=/"logoutForm/" method=/"post/" action=/"/">
  					<input type=/"hidden/" name=/"logout/" value=/"Logout/">
				</form>
				<a href=/"index.php/" onclick=/"submitForm.submit('Logout')/">';*/
				
				/*echo '<a href="account.php">Manage account</a>&nbsp;&nbsp;
				<form id="logoutForm" method="post" action="">
  					<input type="hidden" name="logout" value="Logout">
					<a href="javascript:submitForm(\'Logout\')">Log out</a>
				</form>';*/
				
				/*<a href="index.php" onclick="logoutUser(\'Logout\')">Log out</a>';*/
					  /*<a href="" onclick="submitForm()">Log out</a>';*/

            } else {

                // if there was an attempted login and there is an error message, display it here.
                if (isset($_POST['login']) && $err != '')
                    para($err, 'errorMessage');
			}?>
  </div><!--login panel-->	
<div class="navBodyWrapper">		
  <div id="navWrapper">
  		<div id="navTop"></div><!--Gray Line-->
  		<div class="navContainer"><a href="http://intel.gatech.edu/" target="_blank"><img src="../images/InTel_logo.jpg" width="264" border="0" height="114" /></a></div>
  		<div class="navContainer"><img src="../images/header.jpg" width="264" height="69" /></div>
  </div><!--navWrapper-->
  <div id="bodyWrapper">
			<div id="bodyTop"></div><!--Green line-->
			<div id="bodyBanner"><img src="<?php echo($banner); ?>" width="600" height="153" /></div>
			<div class="bodyContainer">
				<div ALIGN="left" class="bodyTitle">
					<?php echo($title); ?>
				</div>
			</div><!--BodyContainer-->
</div><!--BodyWrapper-->
</div><!--navBodyWrapper-->
</div><!--header-->
		
  <!-- table-->
 <?php
            if (isAnonymous ()) {
            ?>
<div id="loginWrapper">
<!--table>
  <tr>
    <td>Register?&nbsp;No
      <INPUT TYPE="radio" NAME="login" VALUE="no"  onclick="loginNo()"/>
      &nbsp;&nbsp;Yes
      <INPUT TYPE="radio" NAME="login" VALUE="yes" onclick="loginYes()"/></td>
  </tr>
  <tr id="login_form" style="display:none">
  <td>
 	 <form method="post" action="">
        <table>
          <tr>
            <td>Email Address: </td>
            <td><input type=\"text\"  name=\"email\" /></td>
          </tr>
          <tr>
            <td>Password: </td>
            <td><input type="password"  name="password" /></td>
          </tr>
          <tr>
        </table>
        <p>
          <input type="submit" name="login" value="Login" />
          <a href="resetPassword.php">Forgot your password?</a> <a href="register.php">Register</a></p>
      </form>
      </td>
      </tr>
</table-->

	<!--table id="loginWrapper">
    <tr>
      <td>Are you a student taking this for class credit? <a href="javascript:;" onclick="loginYes()">Log in</a></td>
    </tr>
    <tr id="login_form" style="display:none">
      <td><form method="post" action="">
          <table>
            <tr>
              <td>Email Address: </td><td><input type=\"text\"  name=\"email\" /></td>
            </tr>
            <tr>
              <td>Password: </td><td><input type="password"  name="password" /></td>
            </tr>
          </table>
          <p>
            <input type="submit" name="login" value="Login" />
            <a href="resetPassword.php">Forgot your password?</a> <a href="register.php">Register</a></p>
        </form>
      </td>
    </tr>
  </table-->
  <table>
  	<tr>
    	<td>Are you a student taking this for class credit? <a href="javascript:;" onclick="loginYes()">Log in</a>
        </td>
    </tr>
    <tr id="login_form" style="display:none">
    	<td>
  <form method="post" action="">
                    <table>
                        <tr ><td>Email Address: </td><td><input type="text"  name="email" /></td></tr>
                        <tr><td>Password: </td><td><input type="password"  name="password" /></td></tr>
                    </table>
                    <p><input type="submit" name="login" value="Login" /> <a href="resetPassword.php">Forgot your password?</a> <a href="register.php">Register</a></p>
                </form>
</td>
    </tr>
  </table>
  <!--? was for end of if
            }
            ?-->
</div><!--loginWrapper-->
 <?php
            }
            ?>

<!--*************LOGIN***************-->
            <!-- ******LOGO *******-->
  <!--div id="navWrapper">
  		<div id="navTop"></div>
  		<div class="navContainer"><a href="index.php" target="_blank"><img src="../images/InTel_logo.jpg" width="264" border="0" height="114" /></a></div>
  		<div class="navContainer"><img src="../images/header.jpg" width="264" height="69" /></div>
  </div--><!--navWrapper-->
  
  <!--*************BANNER***************-->
  <!--div id="bodyWrapper">
			<div id="bodyTop"></div>
			<div id="bodyBanner"><img src="<!--?php echo($banner); ?>" width="600" height="153" /></div-->
			<!--div class="bodyContainer">
				<span class="bodyTitle"-->
					<!--?php echo($title); ?-->
				<!--/span>
			</div--><!--BodyContainer1-->
<!--/div--><!--BodyWrapper-->

<?php
            // primary navigation:
        ?>
<!-- nav -->
<div id="compact_nav">
  <?php
            if (isAnonymous ()) {
            ?>
<!--div id="loginWrapper"-->
   <!--table id="loginWrapper">
    <tr>
      <td>Are you a student taking this for class credit? <a href="javascript:;" onclick="loginYes()">Log in</a></td>
    </tr>
    <tr id="login_form" style="display:none">
      <td><form method="post" action="">
          <table>
            <tr>
              <td>Email Address: </td>
              <td><input type=\"text\"  name=\"email\" /></td>
            </tr>
            <tr>
              <td>Password: </td>
              <td><input type="password"  name="password" /></td>
            </tr>
          </table>
          <p>
            <input type="submit" name="login" value="Login" />
            <a href="resetPassword.php">Forgot your password?</a> <a href="register.php">Register</a></p>
        </form></td>
    </tr>
  </table-->
  <!--/div--><!--loginWrapper-->

<!--*************LOGIN***************-->
<!--/div--><!--header--> 
 
  
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
  <div class='nav_button'><a href="manageAccounts.php">Manage Accounts</a></div>
  <div class='nav_button'><a href="viewLoggerData.php">Logger Data</a></div>
  <div class='nav_button'><a href="viewFeedback.php">View Feedback</a></div>
  <?php
    } // end user type switch
   ?>
  <div class='nav_button'><a href="help.php">Instructions</a></div>
  <div class='nav_button'><a href="javascript:showFeedbackBox()">Give us feedback!</a></div>
  <div class='nav_button'><a href="mailto:<?php echo $site_email_address; ?>">Email Site Support</a></div>
  <!--            </ul>-->
</div>
<div id="content">
<h2><?php echo t2h($title); ?></h2>

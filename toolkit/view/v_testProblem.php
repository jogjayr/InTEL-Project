<?php

require_once('admin/initvars.php');
$title = 'Manage Assignments';

requireLogin(); 

//verify that user is instructor or admin
if (!isInstructor() && !isAdmin()) {
    redirectRel('index.php');
}

require_once('header.php');

//retrieve uuid
$uuid = '';
if (isset($_SESSION['uuid'])) {
    $uuid = $_SESSION['uuid'];
}

$assignmentId = 0;
if(isset($_GET['exercise_id'])) {
    $assignmentId = addslashes($_GET['exercise_id']);
}

$userId = addslashes($_GET['user_id']);
$user = getUserById($userId);
$userUuid = $user['uuid'];

$userId = -1; // assign value to user id to prevent changes accidentally being made to the student's state.

// instead of looking for assignments for the current user (the instructor/admin),
// we look for assignments for the given user (the student).

$assignments = getAllAssignmentsByUuid( $userUuid );
$thisAssignment = null;
foreach($assignments as $assignment) {
    if(isset($assignment['id'])) {
        if($assignment['id'] == $assignmentId) {
            $thisAssignment = $assignment;
        }
    }
}

if($thisAssignment == null) {
    echo "Can not find the appropriate assignment for this user!<br/>";
}

//print_r($thisAssignment);

//$problemId = addslashes($_GET['problem_id']);
$problemId = $thisAssignment['problem_id'];
$problem = retrieveProblem($problemId);
$problemName = $problem['name'];


echo "<h1>Testing Mode</h1>";
echo "User: ".$user['first_name']." ".$user['last_name']."<br/>";
echo "Problem name: $problemName<br/>";


//main java apps
if ($problem["type"]=="java") {
    $exerciseJar = $problem["java_jar_name"];//"PurseProblem.jar";
    $jarPath = "../applet/";


    $loader = "edu.gatech.statics.applet.AppletLoader";
    $loaderArchive = $jarPath."AppletLoader.jar";
    $launcher = "edu.gatech.statics.application.AppletLauncher";
    $exerciseClass = $problem["java_class_name"];

    $jars = Array(
        $exerciseJar,
        "Statics.jar",
        "BUI.jar",
        "JavaMonkeyEngine.jar",
        "lwjgl_applet.jar",
        "lwjgl.jar",
        "lwjgl_util.jar",
        "jinput.jar",
        "jme-colladabinding.jar"
    );

    $archiveString = "";
    foreach($jars as $jar) {
        if(strlen($archiveString) == 0)
            $archiveString .= $jarPath.$jar;
        else	$archiveString .= ", ".$jarPath.$jar;
    }

    $resWidth = $problem["width"]; // 1100;
    $resHeight = $problem["height"]; // 768;

    $state = "";
    if($thisAssignment != null && isset($thisAssignment['state'])) {
        $state = $thisAssignment['state'];
    }

    //$preHash = "$userId:$problemId:$assignmentId:$problemName:$state";
    $preHash = "$userId:$problemId:$problemName:$state";
    $verifierKey = substr(md5($preHash),0,8);
    ?>

    <?php if($userId == 0) { ?>
<em>Note:</em> You are not logged in. If you work on this problem right now, you will not get credit.
    <?php } ?>

<div style="margin: 5px; padding: 5px; border: thin solid #aec3ff;">
    <applet
        archive="<?php echo $loaderArchive; ?>"
        code="<?php echo $loader; ?>"
        width="<?php echo $resWidth; ?>" height="<?php echo $resHeight; ?>">
        
    	  <param name="al_title" value="InTEL"/>
    	  <param name="al_main" value="<?php echo $launcher; ?>"/>
    	  <param name="al_jars" value="<?php echo $archiveString; ?>"/>
    	  <param name="al_windows" value="<?php echo $jarPath; ?>natives_windows.jar"/>
    	  <param name="al_linux" value="<?php echo $jarPath; ?>natives_linux.jar"/>
    	  <param name="al_mac" value="<?php echo $jarPath; ?>natives_macosx.jar"/>
        
        <param name="exercise" value="<?php echo $exerciseClass;?>"/>
        <param name="width" value="<?php echo $resWidth; ?>"/>
        <param name="height" value="<?php echo $resHeight ?>"/>
        <param name="problemID" value="<?php echo $problemId ?>"/>
        <param name="assignmentID" value="<?php echo $assignmentId ?>"/>
        <param name="userID" value="<?php echo $userId ?>"/>
        <param name="problemName" value="<?php echo $problemName ?>"/>
        <param name="exerciseState" value="<?php echo $state; ?>">
        <param name="verifierKey" value="<?php echo $verifierKey; ?>"/>
        Java 1.6 or higher is required to run this applet. Please download a JRE from <a href="http://www.java.com">www.java.com</a>.
    </applet>
</div>

<?
} // end if for java problem


?>
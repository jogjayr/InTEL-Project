<?php
require "null";

$title = 'Launch Problem';
require_once('header.php');
require_once('model/lib_data.php');

$user = getUserByUUID($_SESSION['uuid']);

$problemId = addslashes($_GET['problem_id']);
$exerciseId = addslashes($_GET['exercise_id']);

if($problemId != null) {
    $problem = retrieveProblem($problemId);
} else if($exerciseId != null)  {
    // How should we do this?
    //$problem = retrieveProblemByAssignmentId($exerciseId);
}


$exerciseJar = $problem["java_jar_name"];//"PurseProblem.jar";
$jarPath = "../applet/";
$launcher = "edu.gatech.statics.application.AppletLauncher";
$exerciseClass = $problem["java_class_name"];//"example01.PurseExerciseGraded2";

$jars = Array(
    $exerciseJar,
        "Statics.jar",
        "BUI.jar",
        "JavaMonkeyEngine.jar",
        "lwjgl_applet.jar",
        "lwjgl_util_applet.jar",
        "lwjgl.jar",
        "lwjgl_util.jar",
        "lwjgl_fmod3.jar",
        "lwjgl_devil.jar",
        "natives.jar",
        "jinput.jar"
);

$archiveString = "";
foreach($jars as $jar) {
    if(strlen($archiveString) == 0)
    $archiveString .= $jarPath.$jar;
    else	$archiveString .= ", ".$jarPath.$jar;
}

$resWidth = 1100; // 900;
$resHeight = 768; // 675;
?>

<applet
    archive="<?php echo $archiveString; ?>"
    code="<?php echo $launcher; ?>"
    width="<?php echo $resWidth; ?>" height="<?php echo $resHeight; ?>">
    <param name="exercise" value="<?php echo $exerciseClass;?>"/>
    <param name="width" value="<?php echo $resWidth; ?>"/>
    <param name="height" value="<?php echo $resHeight ?>"/>
    Java 1.5 or higher is required to run this applet. Please download a JRE from <a href="http://java.sun.com">java.sun.com</a>.
</applet>

<?php 
require_once('footer.php'); 
?>
<?php
//require "null";
// ****************
//  IMPORTANT TODO:
//  Check the type of user, and the type of problem;
//  on load, if the user is logged in, and there is not an assignment id, redirect to the "my assignments" page
//  Conversely, redirect if the user is not logged in and there is an assignment id.
// ****************


$headExtra = "<script type=\"text/javascript\">
function changeScreenSize()
     {
		var appletWidth = 1100 + 20;
		var appletHeight = 768 + 20;
		
		var deltaX = appletWidth - document.body.clientWidth;
		var deltaY = appletHeight - document.body.clientHeight;
		if(deltaX < 0) deltaX = 0;
		if(deltaY < 0) deltaY = 0;
		
		if(deltaX > 0 || deltaY > 0)
		  window.resizeBy(deltaX, deltaY);
		
	   window.scrollTo(0, 10000);
     }
</script>";
$bodyExtra = 'onLoad="javascript:changeScreenSize()"';

$title = 'Launch Problem';
require_once('header.php');


$problemId = addslashes($_GET['problem_id']);
$assignmentId = 0;
if (isset($_GET['exercise_id'])) {
    $assignmentId = addslashes($_GET['exercise_id']);
}

if ($problemId != null) {
    $problem = retrieveProblem($problemId);
} else {
// hopefully this will never happen
}

// these variables need to be defined.
if ($user == null)
    $userId = 0;
else
    $userId = $user['id'];
$problemName = $problem['name'];



$assignments = getAssignments($uuid);
$thisAssignment = null;
foreach ($assignments as $assignment) {
    if (isset($assignment['id'])) {
        if ($assignment['id'] == $assignmentId) {
            $thisAssignment = $assignment;
        }
    }
}

echo "<h1>$problemName</h1>";

if (isset($problem["extra"])) {
    // show the extra information associated with this problem.
    echo $problem["extra"];
}

//main java apps
if ($problem["type"] == "java") {
    $exerciseJar = $problem["java_jar_name"]; //"PurseProblem.jar";
    $jarPath = $appletFolder;

    $loader = "edu.gatech.statics.applet.AppletLoader";
    $loaderArchive = $jarPath . "AppletLoader.jar";
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
    foreach ($jars as $jar) {
        if (strlen($archiveString) == 0)
            $archiveString .= $jarPath . $jar;
        else
            $archiveString .= ", " . $jarPath . $jar;
    }

    // deal with versioning.
    $versionString = file_get_contents($jarPath . "version.json");
    

    $resWidth = $problem["width"]; // 1100;
    $resHeight = $problem["height"]; // 768;

    $state = "";
    if ($thisAssignment != null && isset($thisAssignment['state'])) {
        $state = $thisAssignment['state'];
    }

    //$preHash = "$userId:$problemId:$assignmentId:$problemName:$state";
    $preHash = "$userId:$problemId:$problemName:" . preg_replace('/\s/', '', $state);
    $verifierKey = substr(md5($preHash), 0, 8);

    $urlBase = "http://" . $_SERVER['SERVER_NAME'] . $_SERVER['REQUEST_URI'];
    $urlBase = substr($urlBase, 0, 1 + strrpos($urlBase, '/'));
?>

<?php if ($userId == 0) { ?>
        <p style="font-size: 150%"><em>Note:</em> You are not logged in. If you were assigned this problem, you will not get credit!!</p>
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
            <param name="al_versions" value="<?php echo $versionString; ?>"/>

            <param name="exercise" value="<?php echo $exerciseClass; ?>"/>
            <param name="width" value="<?php echo $resWidth; ?>"/>
            <param name="height" value="<?php echo $resHeight ?>"/>
            <param name="problemID" value="<?php echo $problemId ?>"/>
            <param name="assignmentID" value="<?php echo $assignmentId ?>"/>
            <param name="userID" value="<?php echo $userId ?>"/>
            <param name="problemName" value="<?php echo $problemName ?>"/>
            <param name="exerciseState" value="<?php echo $state; ?>">
            <param name="verifierKey" value="<?php echo $verifierKey; ?>"/>
            <param name="urlBase" value="<?php echo $urlBase; ?>"/>
            Java 1.6 or higher is required to run this applet. Please download a JRE from <a href="http://www.java.com">www.java.com</a>.
        </applet>
    </div>

<?
}
//simple java apps (for processing apps)
if ($problem["type"] == "simplejava") {
    $exerciseJar = $problem["java_jar_name"]; //"PurseProblem.jar";
    $jarPath = "../simpleapplet/";
    $exerciseClass = $problem["java_class_name"]; //"example01.PurseExerciseGraded2";

    $resWidth = $problem["width"]; // 1100;
    $resHeight = $problem["height"]; // 768;
?>

<?php if ($userId == 0) { ?>
        <p style="font-size: 150%"><em>Note:</em> You are not logged in. If you were assigned this problem, you will not get credit!!</p>
<?php } ?>

    <br/>
    <!--[if !IE]> -->
    <object classid="java:<?php echo $exerciseClass; ?>.class"
            type="application/x-java-applet"
            archive="<? echo $exerciseJar; ?>.jar"
            width="<?php echo $resWidth; ?>" height="<?php echo $resHeight ?>"
            standby="Loading..." >

        <param name="archive" value="<? echo $jarPath . $exerciseJar; ?>.jar" />
        <param name="mayscript" value="true" />
        <param name="scriptable" value="true" />
        <param name="image" value="loading.gif" />
        <param name="boxmessage" value="Loading..." />
        <param name="boxbgcolor" value="#FFFFFF" />
        <param name="test_string" value="outer" />
        <!--<![endif]-->
        <object classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
                codebase="http://java.sun.com/update/1.4.2/jinstall-1_4_2_12-windows-i586.cab"
                width="<?php echo $resWidth; ?>" height="<?php echo $resHeight ?>"
                standby="Loading..."  >
            <param name="code" value="<? echo $exerciseJar; ?>" />
            <param name="archive" value="<? echo $jarPath . $exerciseJar; ?>.jar" />
            <param name="mayscript" value="true" />
            <param name="scriptable" value="true" />
            <param name="boxmessage" value="Loading..." />
            <param name="boxbgcolor" value="#FFFFFF" />
            <param name="test_string" value="inner" />
            <p>
                <strong>
    							This browser does not have a Java Plug-in.
                    <br />
                    <a href="http://java.sun.com/products/plugin/downloads/index.html" rel="external" title="Download Java Plug-in">
    								Get the latest Java Plug-in here.
                    </a>
                </strong>
            </p>

        </object>

        <!--[if !IE]> -->
    </object>
    <!--<![endif]-->
    <div id="spacer" style="height:25px;"></div>
<?php
}
if ($problem["type"] == "flash") {
    $exerciseSwf = $problem["java_jar_name"]; //"Matchingas3_v2";
    $swfPath = "../flash/";
    $swfString = $swfPath . $exerciseSwf;
    $resWidth = $problem["width"]; // 550;
    $resHeight = $problem["height"]; // 400;

    if ($userId == 0) {
?>
        <p style="font-size: 150%"><em>Note:</em> You are not logged in. If you were assigned this problem, you will not get credit!!</p>
<?php } ?>
    <br/>
    <script language="javascript">AC_FL_RunContent = 0;</script>
    <script src="<? echo $swfPath; ?>AC_RunActiveContent.js" language="javascript"></script>
    <script language="javascript">
        if (AC_FL_RunContent == 0) {
            alert("This page requires AC_RunActiveContent.js.");
        } else {
            AC_FL_RunContent(
            'codebase', 'http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0',
            'width', '<? echo $resWidth; ?>',
            'height', '<? echo $resHeight; ?>',
            'src', '<? echo $swfString; ?>',
            'quality', 'high',
            'pluginspage', 'http://www.macromedia.com/go/getflashplayer',
            'align', 'middle',
            'play', 'true',
            'loop', 'true',
            'scale', 'showall',
            'wmode', 'window',
            'devicefont', 'false',
            'id', '<? echo $exerciseSwf; ?>',
            'bgcolor', '#ffffff',
            'name', '<? echo $exerciseSwf; ?>',
            'menu', 'true',
            'allowFullScreen', 'false',
            'allowScriptAccess','sameDomain',
            'movie', '<? echo $swfString; ?>',
            'salign', ''
        ); //end AC code
        }
    </script>
    <noscript>
        <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0" width="<? echo $resWidth; ?>" height="<? echo $resHeight; ?>" id="<? echo $exerciseSwf; ?>" align="middle">
            <param name="allowScriptAccess" value="sameDomain" />
            <param name="allowFullScreen" value="false" />
            <param name="movie" value="<? echo $swfString; ?>.swf" /><param name="quality" value="high" /><param name="bgcolor" value="#ffffff" />	<embed src="<? echo $swfString; ?>.swf" quality="high" bgcolor="#ffffff" width="550" height="400" name="<? echo $exerciseSwf; ?>" align="middle" allowScriptAccess="sameDomain" allowFullScreen="false" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
        </object>
    </noscript>
    <div id="spacer" style="height:25px;"></div>
<?php
}
require_once('footer.php');
?>

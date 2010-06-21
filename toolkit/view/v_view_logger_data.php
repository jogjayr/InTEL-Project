<?php

require_once('admin/initvars.php');
$title = 'Logger Data';

requireLogin(); 

//verify that user is admin
if (!isAdmin()) {
  redirect('index.php');
}

require_once('header.php');


// *******
// function declarations

function getSessionTime($sessionId) {
  global $db;
  $sessionTimeResult = aquery("SELECT MAX(created_on)-MIN(created_on) as time FROM app_problem_usage_log WHERE java_problem_session_id=$sessionId", $db);
  $sessionTime = $sessionTimeResult[0]['time'];
  return $sessionTime;
}


//retrieve uuid
$uuid = '';
if (isset($_SESSION['uuid'])){
  $uuid = $_SESSION['uuid'];
}

$sessionId = '';
if(isset($_GET['session'])) {
  $sessionId = addslashes($_GET['session']);
}

if($sessionId == '') {

  // following is index of sessions
  $sessionResults = aquery("SELECT DISTINCT java_problem_session_id FROM app_problem_usage_log", $db);
  ?>

<h1>All Log Sessions</h1>
<table>
  <tr>
    <th>Session ID</th>
    <th>Time spent</th>
  </tr>
  <?php
  foreach($sessionResults as $result) {
    $sessionId = $result['java_problem_session_id'];
    $sessionTime = getSessionTime($sessionId);

    echo "<tr>";
    echo "<td><a href=\"viewLoggerData.php?session=$sessionId\">$sessionId</a></td>";
    echo "<td>$sessionTime</td>";
    echo "</tr>";
  }
  ?>
</table>

  <?php
} else {
  // following is query of specific session
  $results = aquery("SELECT * FROM app_problem_usage_log WHERE java_problem_session_id = $sessionId ORDER BY created_on", $db);
  ?>

<h1>Log Session: <?php echo $sessionId; ?></h1>
<table>
  <tr>
    <th>Class</th>
    <th>Method</th>
    <th>Message</th>
  </tr>
  <?php
  foreach($results as $result) {
    echo "<tr>";
    echo "<td>{$result['java_class']}</td>";
    echo "<td>{$result['java_method']}</td>";
    echo "<td>{$result['message']}</td>";
    echo "</tr>";
  }
  ?>
</table>


  <?php
}

require_once('footer.php');
?>
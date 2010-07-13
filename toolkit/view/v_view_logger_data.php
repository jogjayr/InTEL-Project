<?php
require_once('admin/initvars.php');

requireLogin();

//verify that user is admin
if (!isAdmin()) {
    redirect('index.php');
}

$title = 'Logger Data';
require_once('header.php');



$query = "SELECT *,app_problem_usage_sessions.id AS session_id  FROM app_problem_usage_sessions, app_user, app_problem WHERE
    app_user.id = user_id AND app_problem.id = problem_id";
$results = aquery($query, $db);
?>

<h2>Session index</h2>
<script type="text/javascript" src="js/sortable.js"></script>
<table class="sortable" id="sortabletable">
    <tr>
        <th>id</th>
        <th>user</th>
        <th>problem</th>
        <th>start time</th>
        <th>end time</th>
        <th>duration</th>
    </tr>
    <?php
    foreach ($results as $appSession) {
        echo "<tr>";

        $startTimestamp = strtotime($appSession['start_time']);
        $endTimestamp = strtotime($appSession['end_time']);

        $startTime = date("g:i a m/d/y", $startTimestamp);
        $endTime = date("g:i a m/d/y", $endTimestamp);
        $duration = date_diff($startTimestamp, $endTimestamp);

        echo "<td>{$appSession['session_id']}</td>";
        echo "<td>{$appSession['first_name']} {$appSession['last_name']}</td>";
        echo "<td>{$appSession['name']}</td>";
        echo "<td>{$startTime}</td>";
        echo "<td>{$endTime}</td>";
        echo "<td>{$duration}</td>";
        echo "</tr>";
    }
    ?>
</table>

<?php
require_once('footer.php');
?>
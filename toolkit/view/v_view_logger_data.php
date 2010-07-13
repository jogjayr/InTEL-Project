<?php
require_once('admin/initvars.php');

requireLogin();

//verify that user is admin
if (!isAdmin()) {
    redirect('index.php');
}

$title = 'Logger Data';
require_once('header.php');


if (isset($_GET['session_id'])) {
    // looking at a specific session, so fetch header data.
    $sessionId = t2sql($_GET['session_id']);

    $query = "SELECT * FROM app_problem_usage_sessions, app_user, app_problem WHERE
        app_user.id = user_id AND app_problem.id = problem_id AND app_problem_usage_sessions.id=$sessionId";
    $results = aquery($query, $db);
    $appSession = $results[0];

    echo "<h2>Session Review</h2>";

    echo "Session: $sessionId<br/>";
    echo "User: {$appSession['first_name']} {$appSession['last_name']}<br/>";

    $startTime = date("g:i a m/d/y", strtotime($appSession['start_time']));
    $endTime = date("g:i a m/d/y", strtotime($appSession['end_time']));
    $duration = date_diff($appSession['start_time'], $appSession['end_time']);

    echo "Start time: {$startTime}<br/>";
    echo "End time: {$endTime}<br/>";
    echo "Duration: {$duration}<br/>";

    $query = "SELECT * FROM app_problem_usage_log WHERE session_id=$sessionId";
    $logData = aquery($query, $db);
?>
    <table class="sortable" >
        <tr>
            <th>timestamp</th>
            <th>level</th>
            <th>class</th>
            <th>method</th>
            <th>message</th>
        </tr>
    <?php

    foreach ($logData as $entry) {

        $timestamp = date("g:i a m/d/y", strtotime($entry['created_on']));

        echo "<tr>";
        echo "<td>{$timestamp}</td>";
        echo "<td>{$entry['level']}</td>";
        echo "<td>{$entry['java_class']}</td>";
        echo "<td>{$entry['java_method']}</td>";
        echo "<td>{$entry['message']}</td>";
        echo "</tr>";
    }

    ?>
</table>

<?php
} else {

    // we have no session id, so produce a list of all sessions
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

        $startTime = date("g:i a m/d/y", strtotime($appSession['start_time']));
        $endTime = date("g:i a m/d/y", strtotime($appSession['end_time']));
        $duration = date_diff($appSession['start_time'], $appSession['end_time']);

        echo "<td><a href=\"viewLoggerData.php?session_id={$appSession['session_id']}\">{$appSession['session_id']}</a></td>";
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
}
require_once('footer.php');
?>
<?php

require_once('admin/initvars.php');
$title = 'View Feedback';

requireLogin();

//verify that user is admin
if (!isAdmin()) {
    redirect('index.php');
}

require_once('header.php');

$startIndex = 0;
$totalRecords = 30;
if (isset($_GET['start_index']))
    $startIndex = t2sql($_GET['start_index']);

$query = "SELECT * FROM app_feedback LIMIT $startIndex, $totalRecords";
$records = aquery($query, $db);
$numberRecords = sizeof($records);

if ($numberRecords > 0) {
    echo "<p>Showing feedback entries from " . ($startIndex + 1) . " to " . ($startIndex + $numberRecords) . "</p>";

    echo "<table class=\"sortable\">";
    echo "<tr><th>id</th><th>user</th><th>timestamp</th><th>feedback</th></tr>";

    foreach ($records as $record) {

        $commenterName = "Anonymous";
        $commenterId = $record['user_id'];
        if($commenterId > 0) {
            $commenter = getUserById($commenterId);
            $commenterName = "{$commenter['first_name']} {$commenter['last_name']}";
        }

        echo "<tr>";
        echo "<td>{$record['id']}</td>";
        echo "<td>{$commenterName}</td>";
        echo "<td>{$record['timestamp']}</td>";
        echo "<td>{$record['contents']}</td>";
        echo "</tr>";
    }

    echo "</table>";
} else {
    // no records
    echo "<p>No records!</p>";
}

echo "<p>";
if ($startIndex > 0) {
    $prevIndex = $startIndex - $totalRecords;
    echo "<a href=\"viewFeedback.php?start_index=$prevIndex\">previous</a> ";
}
if ($numberRecords == $totalRecords) {
    $nextIndex = $startIndex + $totalRecords;
    echo "<a href=\"viewFeedback.php?start_index=$nextIndex\">next</a>";
}
echo "</p>";


require_once('footer.php');
?>
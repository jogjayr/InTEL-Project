<?php

require_once('admin/initvars.php');

$userId = -1;
if ($user)
    $userId = $user['id'];

$feedback = t2sql($_POST['feedback']);

$query = "INSERT INTO app_feedback (user_id, contents) VALUES ($userId, '$feedback')";
query($query, $db);

header("Content-Type: text/xml");
echo '<?xml version="1.0" encoding="iso-8859-1"?>';
echo '<result>successful</result>';
?>
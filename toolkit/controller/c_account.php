<?php

require_once('admin/initvars.php');
requireLogin();

$success = false;
$hasAction = false;
$err = '';

//if post data is sent update the database
if (isset($_POST['submit'])) {
    $hasAction = true;
    $firstName = $_POST['first_name'];
    $lastName = $_POST['last_name'];
    $email = $_POST['email'];
    $classId = $_POST['class_id'];
    if (updateAccount($_SESSION['uuid'], $firstName, $lastName, $email, $classId)) {
        $success = true;
    }
}

?>
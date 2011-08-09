<?php

require_once('admin/initvars.php');
requireLogin();
requireInstructor();

//if get data set the assignment id
$permission = false;
if (isset($_GET['id'])) {
    $assignmentId = $_GET['id'];
    //check if user is admin or owner of assignment
    if (isInstructor() && isAssignmentOwner($_SESSION['uuid'], $assignmentId) || isAdmin()) {
        $permission = true;
    } else {
        //user shouldn't be here
        redirect('index.php');
    }
}
if ($permission) {
    deleteAssignment($assignmentId);
    redirect('manageAssignments.php');
}
?>

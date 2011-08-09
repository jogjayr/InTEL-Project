<?php

require_once('admin/initvars.php');
requireLogin();
requireInstructor();

//if get data set the class id
$permission = false;
if (isset($_GET['id'])) {
    $classId = $_GET['id'];
    //check if user is admin or owner of assignment
    if (isInstructor() && isClassOwner($_SESSION['uuid'], $classId) || isAdmin()) {
        $permission = true;
    } else {
        //user shouldn't be here
        redirect('index.php');
    }
}
if ($permission) {
    deleteClass($classId);
    redirect('manageClasses.php');
}
?>

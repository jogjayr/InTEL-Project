<?php
require_once('admin/initvars.php');
$title = 'View Submissions';

requireLogin(); 

//verify that user is instructor or admin
if (!isInstructor() && !isAdmin()) {
    redirectRel('index.php');
}

require_once('header.php');

//retrieve uuid
$uuid = '';
if (isset($_SESSION['uuid'])){
    $uuid = $_SESSION['uuid'];
}

//get assignments & classes that belong to this user, or all for admin
$assignments = '';
$classes = '';
if (isInstructor()){
    $assignments = getAssignmentByClassOwner($uuid);
    $classes = getClassesByOwner($uuid);
    $submissions = getSubmissionsByOwner($uuid);
}
if (isAdmin()){
    $assignments = getAllAssignments();
    $classes = getClasses();
    $submissions = getSubmissions();
}

if (count($submissions) > 0) {
    ?>
<script type="text/javascript" src="js/sortable.js"></script>

    <?php

    foreach($classes as $class) {

        echo "Class: <b>{$class['description']}</b>";
        echo "<table>";
        echo "<tr><td>Student</td><td>email</td>";

        $query = "SELECT problem_id, id FROM app_assignment WHERE class_id={$class['id']}";
        $assignmentids = aquery($query, $db);

        // put up the table headings
        foreach($assignmentids as $id) {
            $query = "SELECT name FROM app_problem WHERE id = {$id['problem_id']}";
            $name1 = aquery($query, $db);

            echo "<td>";
            echo $name1[0]['name'];
            echo "</td>";
        }
        echo "</tr>";

        $query = "SELECT app_user.id, email, first_name, last_name FROM app_user, app_user_class WHERE ".
                "app_user.id = app_user_class.user_id AND class_id = {$class['id']}";
        $users = aquery($query, $db);

        foreach($users as $user) {

            echo "<tr>";

            echo "<td>{$user['last_name']}, {$user['first_name']}</td>";
            echo "<td>{$user['email']}</td>";

            foreach($assignmentids as $id) {
                $query = "SELECT status FROM app_user_assignment, app_submission_status WHERE ".
                        "app_submission_status.id = submission_status_id AND user_id = {$user['id']} AND assignment_id = {$id['id']}";
                $status1 = aquery($query, $db);

                echo "<td>";
                echo $status1[0]['status'];
                echo "</td>";
            }

            echo "</tr>";
        }

        echo "</table>";
    }




    ?>
    <?php
}  else {
    para('No submissions available.');
}//end if
?>

<?php

require_once('footer.php') 
?>





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
if (isset($_SESSION['uuid'])) {
    $uuid = $_SESSION['uuid'];
}

//get assignments & classes that belong to this user, or all for admin
$assignments = '';
$classes = '';
if (isInstructor()) {
    $assignments = getAssignmentByClassOwner($uuid);
    $classes = getClassesByOwner($uuid);
    //    $submissions = getSubmissions(NULL);
    $ownerUuid = $uuid;
}
if (isAdmin()) {
    $assignments = getAllAssignments();
    $classes = getClasses();
    //    $submissions = getSubmissions();
    $all_status = getStatus();
    $problems = getAllProblems();
    $ownerUuid = NULL;
}

$search_class = -1;
$search_problem = -1;
$search_user = NULL;

if(isset($_GET['class_id']))
    $search_class = $_GET['class_id'];

if(isset($_GET['problem_id']))
    $search_problem = $_GET['problem_id'];

// let's see if we're actually searching for something.
if($search_class != -1 || $search_problem != -1) {

    $submissions = getSubmissions($ownerUuid, $search_problem, $search_class, $search_user);
}

?>

<script type="text/javascript" src="js/sortable.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>

<form action="viewSubmissions.php" method="GET">
    <div class="form_area">
        Class
        <select id="class_id" name="class_id">
            <?php
            $selectedString = "";
            if($search_class == -1)
                $selectedString = ' selected="true"';

            echo '<option value="-1"'.$selectedString.'>All Classes</option>';
            foreach($classes as $class) {
                $selectedString = "";
                if($class['id'] == $search_class)
                    $selectedString = ' selected="true"';
                echo '<option value="'.$class['id'].'"'.$selectedString.'>'.$class['description'].'</option>';
            }
            ?>
        </select>
    </div>
    <div class="form_area">
        Problem
        <select id="problem_id" name="problem_id">
            <?php
            $selectedString = "";
            if($search_problem == -1)
                $selectedString = ' selected="true"';

            echo '<option value="-1"'.$selectedString.'>All Problems</option>';
            foreach($problems as $problem) {
                $selectedString = "";
                if($problem['id'] == $search_problem)
                    $selectedString = ' selected="true"';
                echo '<option value="'.$problem['id'].'"'.$selectedString.'>'.$problem['name'].'</option>';
            }
            ?>
        </select>
    </div>
    <input type="submit" value="Search"/>
</form>


<?php
if (isset($submissions)) {
    if (count($submissions) > 0) {
        ?>

<table class="sortable" id="sortabletable">
    <tr class="table_title">
        <th>First</th>
        <th class="startsort">Last</th>
        <th>Email Address</th>
        <th>Assignment</th>
        <th>Type</th>
        <th>Class</th>
        <th>Status</th>
        <th>Date Submitted</th>
        <th>Time Submitted</th>
    </tr>
            <?php
            foreach($submissions as $sub) {

                $first = $sub['first_name'];
                $last = $sub['last_name'];
                $email = $sub['email'];
                $assignment = $sub['name'];
                $class = getClassById($sub['class_id']);
                $className = $class['description'];
                $status = $sub['status'];
                $date = date("m/d/y", $sub['updated_on']);
                $time = date("g:i a", $sub['updated_on']);
                $statusId = $sub['submission_status_id'];
                $userId = $sub['user_id'];
                $assignmentId = $sub['assignment_id'];
                $type = $sub['type'];

                echo '<tr class="record">';
                echo '<td>' .t2h($first). '</td>';
                echo '<td>' . t2h($last) . '</td>';
                echo '<td>' . t2h($email) . '</td>';
                echo '<td>' . t2h($assignment) . '</td>';
                echo '<td>' . t2h($type) . '</td>';
                echo '<td>' . t2h($className) . '</td>';
                if($statusId != 1) {
                    $testProblemLink = "testProblem.php?exercise_id=$assignmentId&user_id=$userId";
                    echo '<td><a href="'.$testProblemLink.'">' . t2h($status) . '</a></td>';
                }else {
                    echo '<td>' . t2h($status) . '</td>';
                }
                echo '<td>' . t2h($date) . '</td>';
                echo '<td>' . t2h($time) . '</td>';
                echo '</tr>';
            }

            ?>
</table>
    <?php
    }  else {
    // submissions is empty.
        para('No submissions available.');
    } } else {
// submissions is not set.
    para('Please select a class and/or problem.');
}
?>

<?php
require_once('footer.php') 
?>
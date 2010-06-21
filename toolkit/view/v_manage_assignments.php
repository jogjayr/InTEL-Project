<?php
require_once('admin/initvars.php');
$title = 'Manage Assignments';

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
$classes = '';
if (isInstructor ()) {
    $classes = getClassesByOwner($uuid);
}
if (isAdmin ()) {
    $classes = getClasses();
}
//get problems
$problems = getAllProblems();
//get the assignment types
$assignmentTypes = getAssignmentTypes();



// Handle add action, if action is present.
//initialize post variables
$hasAction = false;
$actionSuccess = false;
$message = '';
$problemId = '';
$classId = '';
$typeId = '';
$openDate = '';
$closeDate = '';

//check for post data
if (isset($_POST['addAssignment'])) {

    $hasAction = true;
    $problemId = $_POST['problem_id'];
    $classId = $_POST['class_id'];
    $typeId = $_POST['assignment_type_id'];
    $openDate = $_POST['open_date'];
    $closeDate = $_POST['close_date'];

    //check for valid dates
    if (strtotime($openDate) && strtotime($closeDate)) {
        if (addAssignment($problemId, $classId, $typeId, strtotime($openDate), strtotime($closeDate))) {
            $actionSuccess = true;
            $message = 'Your assignment has been created';
        } else {
            $message = 'Your assignment was not added. Please try again later.';
        }
    } else {
        $message = 'Please enter valid dates in the form of mm/dd/yyyy.';
    }
}


//get assignments that belong to classes owned by this user, or all for admin
$assignments = '';
if (isInstructor ()) {
    $assignments = getAssignmentByClassOwner($uuid);
}
if (isAdmin ()) {
    $assignments = getAllAssignments();
}

?>

<script type="text/javascript">
    function confirm_delete(dest){
        var r=confirm("Are you sure you want to delete this assignment?");
        if (r==true){
            window.location = dest;
        }
    }
</script>
<script type="text/javascript" src="js/sortable.js"></script>
<!--<p><a href="addAssignment.php">Add Assignment</a></p>-->


<?php
// report message from action.
if ($hasAction) {
    if (!$actionSuccess) {
        paraErr($message);
    } else {
        para($message);
    }
}

// DEBUG
//print_r($_POST);
?>

<form method="post" action="">
    <p>Problem:
        <select name="problem_id">
            <?php
            foreach ($problems as $prob) {
                echo '<option value="' . $prob['id'] . '">' . $prob['name'] . '</option>';
            }
            ?>
        </select>
    </p>
    <p>Class:
        <select name="class_id">
            <?php
            foreach ($classes as $cls) {
                echo '<option value="' . $cls['id'] . '">' . $cls['description'] . '</option>';
            }
            ?>
        </select>
    </p>
    <p>Assignment Type:
        <select name="assignment_type_id">
            <?php
            foreach ($assignmentTypes as $at) {
                if ($at['id'] == 3) {
                    echo '<option value="' . $at['id'] . '" selected="selected">' . $at['type'] . '</option>';
                } else {
                    echo '<option value="' . $at['id'] . '">' . $at['type'] . '</option>';
                }
            }
            ?>
        </select>
    </p>
    <p>Open Date (mm/dd/yyyy): <input type="text" name="open_date" value="" /></p>
    <p>Close Date (mm/dd/yyyy): <input type="text" name="close_date" value="" /></p>
    <p><input type="submit" name="addAssignment" value="Add Assignment" /></p>
</form>




<?php
            if (count($assignments) > 0) {
?>
                <table class="sortable" id="sortabletable">
                    <tr>
                        <th class="startsort">Class</th>
                        <th>Problem</th>
                        <th>Description</th>
                        <th>Type</th>
                        <th>Open Date</th>
                        <th>Close Date</th>
                        <th class="unsortable"></th>
                        <th class="unsortable"></th>
                    </tr>
    <?php
                foreach ($assignments as $app) {
                    $class = getClassById($app['class_id']);
                    $classDescription = $class['description'];
                    $name = $app['name'];
                    $description = $app['description'];
                    $type = $app['type'];
                    $openDate = date("m.d.y", $app['open_date']);
                    $closeDate = date("m.d.y", $app['close_date']);
                    $urlEdit = 'editAssignment.php?id=' . $app['id'];
                    $urlDelete = 'deleteAssignment.php?id=' . $app['id'];


                    echo '<tr>';
                    echo '<td>' . t2h($classDescription) . '</td>';
                    echo '<td>' . t2h($name) . '</td>';
                    echo '<td>' . t2h($description) . '</td>';
                    echo '<td>' . t2h($type) . '</td>';
                    echo '<td>' . t2h($openDate) . '</td>';
                    echo '<td>' . t2h($closeDate) . '</td>';
                    echo '<td><a href="' . $urlEdit . '">edit</a></td>';
                    echo '<td><a href="#" onclick="confirm_delete(\'' . $urlDelete . '\')">delete</a></td>';
                    echo '</tr>';
                }
    ?>
            </table>
<?php
            } else {
                para('No Assignments available.');
            }//end if
?>

<?php
            require_once('footer.php')
?>
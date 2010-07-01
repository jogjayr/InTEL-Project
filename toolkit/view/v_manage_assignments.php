<?php
require_once('admin/initvars.php');
$title = 'Manage Assignments';

requireLogin();
requireInstructor();

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

    function show_add_form() {
        $("#addEntry").show("slow");
        $("#addButton").hide();
    }

</script>
<script type="text/javascript" src="js/sortable.js"></script>
<!--<p><a href="addAssignment.php">Add Assignment</a></p>-->


<?php
// report message from action.
if ($hasAction) {
    if (!$actionSuccess) {
        para($err, 'errorMessage');
    } else {
        para($message);
    }
}

// DEBUG
print_r($_POST);
?>

<div id="addButton">
    <button onclick="show_add_form();">Add Assignment</button>
</div>
<div id="addEntry" style="display: none;">
    <form method="post" action="">
        <table>
            <tr>
                <td>Class:<br/>(You can select multiple sections)</td>
                <td>Problem:</td>
                <td>Assignment Type:</td>
                <td>Open Date:</td>
                <td>Close Date:</td>
            </tr>
            <tr>
                <td>
                    <select name="class_id" multiple="true">
                        <?php
                        foreach ($classes as $class) {
                            $selectedString = '';
                            if (($hasAction && $class['id'] == $classId)) {
                                $selectedString = ' selected="selected"';
                            }
                            echo '<option value="' . $class['id'] . '"' . $selectedString . '>' . $class['description'] . '</option>';
                        }
                        ?>
                    </select>
                </td>
                <td>
                    <select name="problem_id">
                        <?php
                        foreach ($problems as $problem) {
                            $selectedString = '';
                            if (($hasAction && $problem['id'] == $problemId)) {
                                $selectedString = ' selected="selected"';
                            }
                            echo '<option value="' . $problem['id'] . '"' . $selectedString . '>' . $problem['name'] . '</option>';
                        }
                        ?>
                    </select>
                </td>
                <td>
                    <select name="assignment_type_id">
                        <?php
                        foreach ($assignmentTypes as $at) {
                            $selectedString = '';
                            if (($hasAction && $at['id'] == $typeId) || (!$hasAction && $at['id'] == 3)) {
                                $selectedString = ' selected="selected"';
                            }
                            echo '<option value="' . $at['id'] . '"' . $selectedString . '>' . $at['type'] . '</option>';
                        }
                        ?>
                    </select>
                </td>
                <td>
                    <input type="text" name="open_date" value="<?php
                        if ($hasAction) {
                            echo $openDate;
                        }
                        ?>" />
                </td>
                <td>
                    <input type="text" name="close_date" value="<?php
                           if ($hasAction) {
                               echo $closeDate;
                           }
                        ?>" />
                </td>
            </tr>
        </table>
        <p><input type="submit" name="addAssignment" value="Add Assignment" /></p>
    </form>
</div>

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
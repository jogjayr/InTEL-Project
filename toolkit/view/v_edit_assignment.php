<?php
require_once('admin/initvars.php');

requireLogin(); 

//ensure that user is admin or instructor
if (!isInstructor() && !isAdmin()) {
    redirectRel('index.php');
}

$success = false;
$err = '';

//if get data set the assignment id
if (isset($_GET['id'])) {
    $assignmentId = $_GET['id'];
    //check if user is admin or owner of assignment
    if(isInstructor() && isAssignmentOwner($_SESSION['uuid'], $assignmentId) || isAdmin()) {
        $assignment = getAssignmentById($assignmentId);
    }
    else {
    //user shouldn't be here
        redirectRel('index.php');
    }
}

$problem = '';
$description = '';
$openDate = '';
$closeDate = '';
$typeId = '';

if ($assignment) {
//print_r(array_keys($assignment));
//$assignmentId = $assignment['id'];
    $classId = $assignment['class_id'];
    $problemId = $assignment['problem_id'];
    $typeId = $assignment['assignment_type_id'];
    $openDate = date("g:i a m/d/y", $assignment['open_date']);
    $closeDate = date("g:i a m/d/y",$assignment['close_date']);
}else {
    echo "Assignment not found."; //FIX
}

//if post data is sent update the database
if (isset($_POST['submit'])) {
    $assignmentId = $_POST['assignment_id'];
    $problemId = $_POST['problem_id'];
    $classId = $_POST['class_id'];
    $typeId = $_POST['assignment_type_id'];
    $openDate = strtotime($_POST['open_date']);
    $closeDate = strtotime($_POST['close_date']);
    //ensure that the date is formatted correctly
    if($openDate && $closeDate) {
        if (updateAssignment($assignmentId, $problemId, $classId, $typeId, $openDate, $closeDate)) {
            $success = true;
            //reformat for display
            $openDate = date("g:i a m/d/y",$openDate);
            $closeDate = date("g:i a m/d/y",$closeDate);
        }
    }else {
        $err = 'Please enter a date in the format mm/dd/yyyy.';
    }
}

//get all problems for form
$problems = getAllProblems();
//get all classes that belong to this user for form
if (isAdmin()) {
    $classes = getClasses();
}else {
    $classes = getClassByOwner($_SESSION['uuid']);
}
//get the assignment types
$assignmentTypes = getAssignmentTypes();

$title = 'Edit Assignment';
require_once('header.php');

if ($success) {
    para('The assignment has been updated.');
} else {
    paraErr($err);
}

?>
<form method="post" action="">
    <input type="hidden" name="assignment_id" value="<?php echo $assignmentId; ?>" />
    <p>Problem:
        <select name="problem_id">
            <?php
            foreach ($problems as $prob) {
                if ($prob['id']==$problemId) {
                    echo '<option value="'.$prob['id'].'" selected="selected">'.$prob['name'].'</option>';
                }else {
                    echo '<option value="'.$prob['id'].'">'.$prob['name'].'</option>';
                }
            }
            ?>
        </select>
    </p>
    <p>Class:
        <select name="class_id">
            <?php
            foreach ($classes as $cls) {
                if ($cls['id']==$classId) {
                    echo '<option value="'.$cls['id'].'" selected="selected">'.$cls['description'].'</option>';
                }else {
                    echo '<option value="'.$cls['id'].'">'.$cls['description'].'</option>';
                }
            }
            ?>
        </select>
    </p>
    <p>Assignment Type:
        <select name="assignment_type_id">
            <?php
            foreach ($assignmentTypes as $at) {
                if ($at['id']==$typeId) {
                    echo '<option value="'.$at['id'].'" selected="selected">'.$at['type'].'</option>';
                }else {
                    echo '<option value="'.$at['id'].'">'.$at['type'].'</option>';
                }
            }
            ?>
        </select>
    </p>
    <p>Open Date (mm/dd/yyyy): <input type="text" name="open_date" value="<?php echo $openDate; ?>" /></p>
    <p>Close Date (mm/dd/yyyy): <input type="text" name="close_date" value="<?php echo $closeDate; ?>" /></p>

    <p><input type="submit" name="submit" value="Update" /></p>
</form>

<?php
require_once('footer.php') 
?>
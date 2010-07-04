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
$classIds = '';
$typeId = '';
$openDate = '';
$closeDate = '';

//check for post data
if (isset($_POST['addAssignment'])) {

    // we are attempting to add a new assignment.
    $hasAction = true;
    $problemId = $_POST['problem_id'];
    $classIds = $_POST['class_id'];
    $typeId = $_POST['assignment_type_id'];
    $openDate = $_POST['open_date'];
    $closeDate = $_POST['close_date'];

    //check for valid dates
    if (strtotime($openDate) && strtotime($closeDate)) {
        foreach ($classIds as $classId) {
            if (addAssignment($problemId, $classId, $typeId, strtotime($openDate), strtotime($closeDate))) {
                $actionSuccess = true;
                $message = 'Your assignments have been created';
            } else {
                $message = 'Your assignments were not added. Please contact <a href="mailto:' . $site_email_address . '">support</a> as soon as possible.';
            }
        }
    } else {
        $message = 'Please enter valid dates in the form of mm/dd/yyyy.';
    }
} elseif (isset($_POST['editAssignment'])) {

    // we are attempting to edit an existing assignment.
    $hasAction = true;
    $problemId = $_POST['problem_id'];
    $classId = $_POST['class_id'];
    $typeId = $_POST['assignment_type_id'];
    $openDate = $_POST['open_date'];
    $closeDate = $_POST['close_date'];
    $assignmentId = $_POST['assignment_id'];

    //check for valid dates
    if (strtotime($openDate) && strtotime($closeDate)) {
        if (updateAssignment($assignmentId, $problemId, $classId, $typeId, strtotime($openDate), strtotime($closeDate))) {
            $actionSuccess = true;
            $message = 'Your assignment has been modified';
        } else {
            $message = 'Your assignment was not modified. Please contact <a href="mailto:' . $site_email_address . '">support</a> as soon as possible.';
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

var existingEdit = -1;
function show_edit(id) {

    if(existingEdit != -1)
        cancel_edit(existingEdit);
    existingEdit = id;

    var editContents =
        "<tr id=\"rowedit"+id+"\">"+
        "<td id=\"editClass\"/>"+
        "<td id=\"editProblem\"/>"+
        "<td id=\"editType\"/>"+
        "<td id=\"editOpen\"/>"+
        "<td id=\"editClose\"/>"+
        "<td><input type=\"hidden\" name=\"assignment_id\" value=\""+id+"\">"+
        "<input type=\"submit\" name=\"editAssignment\"></td>"+
        "<td><a href=\"javascript:cancel_edit("+id+")\">cancel</a></td>"+
    "</tr>";
    $("#row"+id).after(editContents);
    $("#row"+id).hide();

    // the order is class, problem, type.
    // these variables contain the text of the old values.
    var oldClass = $("#row"+id+" td:nth-child(1)").text();
    var oldProblem = $("#row"+id+" td:nth-child(2)").text();
    var oldType = $("#row"+id+" td:nth-child(3)").text();
    var oldOpenDate = $("#row"+id+" td:nth-child(4)").text();
    var oldCloseDate = $("#row"+id+" td:nth-child(5)").text();
   
<?php
// not doing any string replacement here.
// this is kind of dangerous, if the name of a class or a problem contains some peculiar characters, ie double-quotes,
// that will mess up the javascript and produce something strange.
echo 'var classStuff = "';
echo '<select name=\"class_id\">';
foreach ($classes as $class) {
    echo '<option value=\"' . $class['id'] . '\">' . $class['description'] . '</option>';
}
echo '</select>";' . "\n";

echo 'var problemStuff = "';
echo '<select name=\"problem_id\">';
foreach ($problems as $problem) {
    echo '<option value=\"' . $problem['id'] . '\">' . $problem['name'] . '</option>';
}
echo '</select>";' . "\n";

echo 'var assignmentTypeStuff = "';
echo '<select name=\"assignment_type_id\">';
foreach ($assignmentTypes as $at) {
    echo '<option value=\"' . $at['id'] . '\">' . $at['type'] . '</option>';
}
echo '</select>";' . "\n";
?>

    $("#rowedit"+id+" #editClass").append(classStuff);
    $("#rowedit"+id+" #editProblem").append(problemStuff);
    $("#rowedit"+id+" #editType").append(assignmentTypeStuff);
    $("#rowedit"+id+" #editOpen").append("<input type=\"text\" name=\"open_date\" value=\""+oldOpenDate+"\"/>");
    $("#rowedit"+id+" #editClose").append("<input type=\"text\" name=\"close_date\" value=\""+oldCloseDate+"\"/>");

    $("#rowedit"+id+" #editClass option:contains('"+oldClass+"')").attr("selected","selected");
    $("#rowedit"+id+" #editProblem option:contains('"+oldProblem+"')").attr("selected","selected");
    $("#rowedit"+id+" #editType option:contains('"+oldType+"')").attr("selected","selected");
}

function cancel_edit(id) {
    $("#row"+id).show();
    $("#rowedit"+id).remove();
    existing_edit = -1;
}

function select_problem(id) {
<?php
echo "var problemThumbnails = Array();\n";
echo "var problemDescriptions = Array();\n";
echo "var problemDescriptions2 = Array();\n";
foreach ($problems as $problem) {
    echo "problemThumbnails[{$problem['id']}] = \"{$problem['image']}\";\n";
    $description = str_replace("\n", "", $problem['description']);
    $description2 = str_replace("\n", "", $problem['instructor_description']);
    echo "problemDescriptions[{$problem['id']}] = \"{$description}\";\n";
    echo "problemDescriptions2[{$problem['id']}] = \"{$description2}\";\n";
}
?>
    $("#problemThumbnail").attr("src", "images/"+problemThumbnails[id]);
    $("#problemDescription").text(problemDescriptions[id]);
    $("#problemDescription2").text(problemDescriptions2[id]);
}

</script>
<script type="text/javascript" src="js/sortable.js"></script>

<?php
// report message from action.
// if the user attempted to do something, post the message.
if ($hasAction) {
    if (!$actionSuccess) {
        para($message, 'errorMessage');
    } else {
        para($message, 'infoMessage');
    }
}

// DEBUG
//print_r($_POST);
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
                    <select name="class_id[]" multiple="true">
                        <?php
                        foreach ($classes as $class) {
                            $selectedString = '';
                            if ($hasAction && in_array($class['id'], $classIds)) {
                                $selectedString = ' selected="selected"';
                            }
                            echo '<option value="' . $class['id'] . '"' . $selectedString . '>' . $class['description'] . '</option>';
                        }
                        ?>
                    </select>
                </td>
                <td>
                    <select name="problem_id" onchange="select_problem(this.options[this.selectedIndex].value)">
                        <?php
                        foreach ($problems as $problem) {
                            $selectedString = '';
                            if ($hasAction && $problem['id'] == $problemId) {
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
    <table>
        <tr>
            <td>Thumbnail</td>
            <td>Description</td>
            <td>Description2 (not visible to students)</td>
        </tr>
        <tr>
            <td>
                <img id="problemThumbnail" src=""/>
            </td>
            <td><div id="problemDescription"/></td>
            <td><div id="problemDescription2"/></td>
        </tr>
    </table>
</div>

<?php
if (count($assignments) > 0) {
?>
    <form method="post" action="">
        <table class="sortable" id="sortabletable">
            <tr>
                <th class="startsort">Class</th>
                <th>Problem</th>
                <!--<th>Description</th>-->
                <th>Type</th>
                <th>Open Date</th>
                <th>Close Date</th>
                <th class="unsortable"></th>
                <th class="unsortable"></th>
            </tr>
        <?php
        
        // if we successfully modified or added rows,
        // highlight all rows whose update time equals this value.
        // precisely, this highlights the rows whose update times are the most recent.
        $highlightTime = -1;
        if ($actionSuccess) {
            foreach ($assignments as $app) {
                $updateTime = $app['updated_on'];
                if ($updateTime > $highlightTime) {
                    $highlightTime = $updateTime;
                }
            }
        }

        // loop through the assignments and populate the table.
        foreach ($assignments as $app) {
            $class = getClassById($app['class_id']);
            $classDescription = $class['description'];
            $name = $app['name'];
            $description = $app['description'];
            $type = $app['type'];
            $openDate = date("g:i a m/d/y", $app['open_date']);
            $closeDate = date("g:i a m/d/y", $app['close_date']);
            //$urlEdit = 'editAssignment.php?id=' . $app['id'];
            $urlEdit = "javascript:show_edit({$app['id']})";
            $urlDelete = 'deleteAssignment.php?id=' . $app['id'];
            $updateTime = $app['updated_on'];

            $rowStyle = "";
            if($updateTime == $highlightTime) {
                $rowStyle = ' class="highlightRow"';
            }
                
            echo '<tr id="row' . $app['id'] . '"' . $rowStyle . '>';
            echo '<td>' . t2h($classDescription) . '</td>';
            echo '<td>' . t2h($name) . '</td>';
            //echo '<td>' . t2h($description) . '</td>';
            echo '<td>' . t2h($type) . '</td>';
            echo '<td>' . t2h($openDate) . '</td>';
            echo '<td>' . t2h($closeDate) . '</td>';
            echo '<td><a href="' . $urlEdit . '">edit</a></td>';
            echo '<td><a href="#" onclick="confirm_delete(\'' . $urlDelete . '\')">delete</a></td>';
            echo '</tr>';
        }
        ?>
    </table>
</form>
<?php
    } else {
        para('No Assignments available.', 'errorMessage');
    }//end if
?>

<?php
    require_once('footer.php')
?>
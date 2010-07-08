<?php
require_once('admin/initvars.php');
$title = 'Manage Classes';

requireLogin();
requireInstructor();

require_once('header.php');

//retrieve uuid
$uuid = '';
if (isset($_SESSION['uuid'])) {
    $uuid = $_SESSION['uuid'];
}

//get classes that belong to this user, or all for admin
$classes = '';
if (isInstructor ()) {
    $classes = getClassesByOwner($uuid);
}
if (isAdmin ()) {
    $classes = getClasses();
}

$owners = getInstructors();

// Handle add action, if action is present.
//initialize post variables
$hasAction = false;
$actionSuccess = false;
$ownerId = '';
$description = '';

if (isset($_POST['submit'])) {
    $hasAction = true;
    $ownerId = $_POST['owner_user_id'];
    $description = $_POST['description'];

    //check for valid data
    if ($description != '') {
        if (addClass($ownerId, $description)) {
            $success = true;
            $message = 'The class has been added.';
        } else {
            $message = 'Your class was not added. Please contact support!';
        }
    } else {
        $message = 'Please enter a class name.';
    }
}


?>
<script type="text/javascript">
    function confirm_delete(dest){
        var r=confirm("Are you sure you want to delete this class?");
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
            "<td id=\"editDescription\"/>"+
            "<td id=\"editOwner\"/>"+
            "<td/>"+
            "<td><input type=\"hidden\" name=\"class_id\" value=\""+id+"\">"+
            "<input type=\"submit\" name=\"editClass\"></td>"+
            "<td><a href=\"javascript:cancel_edit("+id+")\">cancel</a></td>"+
            "</tr>";
        $("#row"+id).after(editContents);
        $("#row"+id).hide();

        // the order is class, problem, type.
        // these variables contain the text of the old values.
        var oldDescription = $("#row"+id+" td:nth-child(1)").text();
        var oldOwner = $("#row"+id+" td:nth-child(2)").text();

<?php
        // not doing any string replacement here.
        // this is kind of dangerous, if the name of a class or a problem contains some peculiar characters, ie double-quotes,
        // that will mess up the javascript and produce something strange.
        echo 'var ownerStuff = "';
        echo '<select name=\"owner_user_id\">';
        foreach ($owners as $owner) {
            if ($owner['id'] == $user['id'] || isAdmin()) {
                echo '<option value=\"' . $owner['id'] . '\">' . $owner['first_name'] . ' ' . $owner['last_name'] . '</option>';
            }
        }
        echo '</select>";' . "\n";
?>

        $("#rowedit"+id+" #editOwner").append(ownerStuff);
        $("#rowedit"+id+" #editDescription").append("<input type=\"text\" name=\"description\" value=\""+oldDescription+"\"/>");

        $("#rowedit"+id+" #editOwner option:contains('"+oldOwner+"')").attr("selected","selected");
    }

    function cancel_edit(id) {
        $("#row"+id).show();
        $("#rowedit"+id).remove();
        existing_edit = -1;
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
        <p>Owner:
            <select name="owner_user_id">
                <?php
                foreach ($owners as $owner) {
                    if ($owner['id'] == $user['id']) {
                        echo '<option value="' . $owner['id'] . '" selected="selected">' . $owner['first_name'] . ' ' . $owner['last_name'] . '</option>';
                    } else {
                        if (isAdmin ()) {
                            echo '<option value="' . $owner['id'] . '">' . $owner['first_name'] . ' ' . $owner['last_name'] . '</option>';
                        }
                    }
                }
                ?>
            </select>
        </p>
        <p>For the class name, please include the school, the year, the semester, and the section, eg. <em>GaTech Fall 2009: Section L</em></p>
        <p>Class Name: <input type="text" name="description" value="" /></p>
        <p><input type="submit" name="addClass" value="Add Class" /></p>
    </form>
</div>

<?php
if (count($classes) > 0) {
?>
    <table class="sortable" id="sortabletable">
        <tr>
            <th class="startsort">Class</th>
            <th>Instructor</th>
            <th>Last Updated</th>
            <th class="unsortable"></th>
            <th class="unsortable"></th>
        </tr>
    <?php

    // if we successfully modified or added rows,
    // highlight all rows whose update time equals this value.
    // precisely, this highlights the rows whose update times are the most recent.
    $highlightTime = -1;
    if ($actionSuccess) {
        foreach ($classes as $cls) {
            $updateTime = $cls['updated_on'];
            if ($updateTime > $highlightTime) {
                $highlightTime = $updateTime;
            }
        }
    }

    foreach ($classes as $cls) {
        $classId = $cls['id'];
        $description = t2h($cls['description']);
        $updatedDate = t2h(date("m.d.y", $cls['updated_on']));
//        $urlEdit = 'editClass.php?id=' . $classId;
        $urlEdit = "javascript:show_edit({$cls['id']})";
        $urlDelete = 'deleteClass.php?id=' . $classId;

        $ownerId = $cls['owner_user_id'];
        $owner = getUserById($ownerId);
        $ownerName = $owner['first_name'] . ' ' . $owner['last_name'];
        $updateTime = $cls['updated_on'];

        $rowStyle = "";
        if($updateTime == $highlightTime) {
            $rowStyle = ' class="highlightRow"';
        }

        echo '<tr id="row' . $cls['id'] . '"' . $rowStyle . '>';
        echo "<td>$description</td>";
        echo "<td>$ownerName</td>";
        echo "<td>$updatedDate</td>";
        echo '<td><a href="' . $urlEdit . '">edit</a></td>';
        echo '<td><a href="#" onclick="confirm_delete(\'' . $urlDelete . '\')">delete</a></td>';
        echo '</tr>';
    }
    ?>
</table>
<?php
} else {
    para('No Classes available.', 'errorMessage');
}//end if

require_once('footer.php')
?>
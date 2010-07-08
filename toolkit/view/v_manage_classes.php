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
            $message = 'Your class was not added. Please try again later.';
        }
    } else {
        $message = 'Please enter a class description.';
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
                $owners = getInstructors();
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
<!--            <th>Last Updated</th>-->
            <th>Instructor</th>
            <th class="unsortable"></th>
            <th class="unsortable"></th>
        </tr>
    <?php
    foreach ($classes as $cls) {
        $classId = $cls['id'];
        $description = t2h($cls['description']);
        $updatedDate = date("m.d.y", $cls['updated_on']);
        $urlEdit = 'editClass.php?id=' . $classId;
        $urlDelete = 'deleteClass.php?id=' . $classId;

        $ownerId = $cls['owner_user_id'];
        $owner = getUserById($ownerId);
        $ownerName = $owner['first_name'] . ' ' . $owner['last_name'];

        echo '<tr>';
        echo "<td>$description</td>";
        echo "<td>$ownerName</td>";
//        echo '<td>' . t2h($updatedDate) . '</td>';
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
?>

<?php
require_once('footer.php')
?>





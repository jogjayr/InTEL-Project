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
?>
<script type="text/javascript">
    function confirm_delete(dest){
        var r=confirm("Are you sure you want to delete this class?");
        if (r==true){
            window.location = dest;
        }
    }
</script>
<script type="text/javascript" src="js/sortable.js"></script>
<p><a href="addClass.php">Add Class</a></p>
<?php
if (count($classes) > 0) {
?>
    <table class="sortable" id="sortabletable">
        <tr>
            <th class="startsort">Class</th>
            <th>Last Updated</th>
            <th class="unsortable"></th>
            <th class="unsortable"></th>
        </tr>
<?php
    foreach ($classes as $cls) {
        $classId = $cls['id'];
        $description = $cls['description'];
        $updatedDate = date("m.d.y", $cls['updated_on']);
        $urlEdit = 'editClass.php?id=' . $classId;
        $urlDelete = 'deleteClass.php?id=' . $classId;

        echo '<tr>';
        echo '<td>' . t2h($description) . '</td>';
        echo '<td>' . t2h($updatedDate) . '</td>';
        echo '<td><a href="' . $urlEdit . '">edit</a></td>';
        echo '<td><a href="#" onclick="confirm_delete(\'' . $urlDelete . '\')">delete</a></td>';
        echo '</tr>';
    }
?>
</table>
    <?php
} else {
    para('No Classes available.','errorMessage');
}//end if
    ?>

<?php
require_once('footer.php')
?>





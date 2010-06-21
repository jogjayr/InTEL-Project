<?php
require_once('admin/initvars.php');
requireLogin();
requireInstructor();

$success = false;
$err = '';

//if get data set the class id
if (isset($_GET['id'])) {
    $classId = $_GET['id'];
    //check if user is admin or owner of assignment
    if (isInstructor() && isClassOwner($_SESSION['uuid'], $classId) || isAdmin()) {
        $class = getClassById($classId);
    } else {
        //user shouldn't be here
        redirect('index.php');
    }
}

$ownerId = '';
$description = '';

if ($class) {
    $ownerId = $class['owner_user_id'];
    $description = $class['description'];
} else {
    echo "Class not found.";
}

//if post data is sent update the database
if (isset($_POST['submit'])) {
    $classId = $_POST['class_id'];
    $ownerId = $_POST['owner_user_id'];
    $description = $_POST['description'];
    if (updateClass($classId, $ownerId, $description)) {
        $success = true;
    }
}

//get all classes that belong to this user for form
if (isAdmin ()) {
    $classes = getClasses();
} else {
    $classes = getClassesByOwner($_SESSION['uuid']);
}
$owners = getOwners();

$title = 'Edit Class';
require_once('header.php');

if ($success) {
    para('The class has been updated.');
} else {
    para($err, 'errorMessage');
}
?>
<form method="post" action="">
    <input type="hidden" name="class_id" value="<?php echo $classId; ?>" />
    <p>Owner:
        <select name="owner_user_id">
            <?php
            foreach ($owners as $owner) {
                if ($owner['id'] == $ownerId) {
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
    <p>Class: <input type="text" name="description" value="<?php echo $description; ?>" /></p>
    <p><input type="submit" name="submit" value="Update" /></p>
</form>

<?php
            require_once('footer.php')
?>
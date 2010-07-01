<?php
require_once('admin/initvars.php');

$title = 'Add Class';

requireLogin();
requireInstructor();

require_once('header.php');

//initialize post variables
$success = false;
$err = '';
$ownerId = '';
$description = '';

//check for post data
if (isset($_POST['submit'])) {

    $ownerId = $_POST['owner_user_id'];
    $description = $_POST['description'];

    //check for valid data
    if ($description != '') {
        if (addClass($ownerId, $description)) {
            $success = true;
        } else {
            $err = 'Your class was not added. Please try again later.';
        }
    } else {
        $err = 'Please enter a class description.';
    }
}
?>
<?php
para($err, 'errorMessage');
if (!$success) {
    $owners = getInstructors();
?>
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
    <p>Class Description: <input type="text" name="description" value="" /></p>
    <p><input type="submit" name="submit" value="Update" /></p>
</form>
<?php
        } else {
            para('Your class has been created');
        }//end if
        require_once('footer.php')
?>
<?php
require_once('admin/initvars.php');
requireLogin();

$success = false;
$hasAction = false;
$err = '';

//if post data is sent update the database
if (isset($_POST['changeAccount'])) {
    $hasAction = true;
    $firstName = $_POST['first_name'];
    $lastName = $_POST['last_name'];
    $email = $_POST['email'];
    $classId = $_POST['class_id'];
    if (updateAccount($uuid, $firstName, $lastName, $email, $classId)) {
        $success = true;
    }
}

if (isset($_POST['changePassword'])) {
    $hasAction = true;
    $oldPassword = $_POST['old_password'];
    $newPassword = $_POST['password'];
    $newPassword2 = $_POST['password2'];

    //authenticate the user
    $uuid = authenticate($user['email'], $oldPassword);
    if ($uuid > 0) {

        //check password and change
        if ($newPassword == $newPassword2) {
            if (changePassword($uuid, $newPassword)) {
                $success = true;
            }
        } else {
            $err = 'The new passwords do not match.';
        }
    } else {
        $err = 'The old passwords is are not correct.';
    }
}


$title = 'Account';
require_once('header.php');

//get all classes
$classes = getClasses();

if ($hasAction) {
    if ($success && isset($_POST['changeAccount'])) {
        para('Your profile has been updated.', 'infoMessage');
    } elseif ($success && isset($_POST['changePassword'])) {
        para('Your password has been changed.', 'infoMessage');
    } else {
        para($err, 'errorMessage');
    }
}
?>

<script type="text/javascript" src="js/sortable.js"></script>
<p>Change account information</p>
<form method="post" action="">
    <?php
    $userClass = getClassByUUID($uuid);
    if (count($classes) > 0) {
    ?>
        <p>Class you belong to:</p>
        <table class="sortable" id="sortabletable">
            <tr>
                <th>Select</th>
                <th class="startsort">Teacher</th>
                <th>Description</th>
            </tr>
        <?php
        foreach ($classes as $cl) {

            $teacher = $cl['teacher'];
            $description = $cl['description'];
            $id = $cl['id'];
            echo '<tr>';
            if ($userClass['class_id'] == $id) {
                echo '<td><input type="radio" name="class_id" value=' . $id . ' CHECKED></td>';
            } else {
                echo '<td><input type="radio" name="class_id" value=' . $id . '></td>';
            }
            echo '<td>' . t2h($teacher) . '</td>';
            echo '<td>' . t2h($description) . '</td>';
            echo '</tr>';
        }
        ?>
    </table>
    <?php
    } else {
        para('No classes available.', 'errorMessage');
    }//end if
    ?>
    <p>First Name: <input type="text" name="first_name" value="<?php echo $user['first_name']; ?>" /></p>
    <p>Last Name: <input type="text" name="last_name" value="<?php echo $user['last_name']; ?>" /></p>
    <p>Email Address: <input type="text" name="email" value="<?php echo $user['email']; ?>" /></p>
    <p><input type="submit" name="changeAccount" value="Update" /></p>
</form>
<!--<p> Need to change your password?  Please contact <a href='<?php echo $site_email_address; ?>'>InTEL support</a>.</p>-->

<p>Change your password</p>
<form method="post" action="">
    <p>Old Password: <input type="password" name="old_password" /></p>
    <p>New Password: <input type="password" name="password" /></p>
    <p>New Password (again): <input type="password" name="password2" /></p>

    <p><input type="submit" name="changePassword" value="Update" /></p>
</form>

<?php
    require_once('footer.php')
?>
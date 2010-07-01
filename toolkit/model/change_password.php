<?php
require_once('admin/initvars.php');
requireLogin();

$title = 'Change Password';
require_once('header.php');

$success = false;

if (isset($_POST['submit'])) {
    if ($_POST['password'] == $_POST['password2']) {
        if ($_POST['password'] == '') {
            $err = 'Please enter a password.';
        } else {
            if (changePassword($_SESSION['user_id'], $_POST['password'])) {
                $success = true;
            } else {
                $err = 'Invalid user.';
            }
        }
    } else {
        $err = 'Please enter the same password twice.';
    }
}

if ($success == false) {
?>
    <form method="post" action="">
    <?php para($err, 'errorMessage'); ?>
    <p>Password: <input type="password" name="password" /></p>
    <p>Password (again): <input type="password" name="password2" /></p>
    <p><input type="submit" name="submit" value="Change Password" /></p>
</form>

<?php
} else {
?>
    <p>Your password has been changed.</p>
<?php
}

require_once('footer.php')
?>
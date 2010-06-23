<?php
require_once ('admin/initvars.php');
requireLogin();

$title = 'Change Password';
require_once('header.php');
require_once('controller/c_change_password.php')
?>
<form method="post" action="">
    <p>Email Address: <input type="text" name="email" value="<?php echo $user['email']; ?>" /></p>
    <p>Old Password: <input type="password" name="old_password" /></p>
    <p>New Password: <input type="password" name="password" /></p>
    <p>New Password (again): <input type="password" name="password2" /></p>

    <p><input type="submit" name="submit" value="Update" /></p>
</form>
<?php
require_once('footer.php')
?>
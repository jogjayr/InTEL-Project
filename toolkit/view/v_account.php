<?php
 
	$title = 'Account';
	require_once('header.php');
  require_once('controller/c_account.php')
	
?>
<form method="post" action="">
<?php
	$user = getUserByUUID($_SESSION['uuid']);
?>
	<p>First Name: <input type="text" name="first_name" value="<?php echo $_SESSION['user_first_name']; ?>" /></p>
  <p>Last Name: <input type="text" name="last_name" value="<?php echo $_SESSION['user_last_name']; ?>" /></p>
  <p>Email Address: <input type="text" name="email" value="<?php echo $user['email']; ?>" /></p>
  <p>GT Prism ID: <input type="text" name="gt_prism_id" value="<?php echo $user['gt_prism_id']; ?>" /></p>

	<p><input type="submit" name="submit" value="Update" /></p>
</form>
<p><a href="changePassword.php">Change Your Password</a></p>
<?php
	
	require_once('footer.php') 
?>
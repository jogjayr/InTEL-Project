<?php 
	$title = 'Login';
	require_once('header.php');
  require_once('controller/c_login.php');
	
?>
<form method="post" action="">
	<?php paraErr($err); ?>
	<p>Email Address: <input type="text" name="email" /></p>
	<p>Password: <input type="password" name="password" /></p>
	<p><input type="submit" name="submit" value="Login" /> <a href="forgot_password.php">Forgot your password?</a></p>
</form>

<?php 
	require_once('footer.php');
?>
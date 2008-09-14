<?php 
	require_once('admin/initvars.php');
	
  //check if user is logged in
	prohibitLogin();
  
  $err = '';
  
  //check for post data
	if (isset($_POST['submit'])) {
    //run login function
		if (login(trim($_POST['email']), $_POST['password'])) {
			
			//redirect page to the url before the login
			$rURL = $_SESSION['r_login_url'];
			unset($_SESSION['r_login_url']);
						
			//redirect user to referring url, unless the referring url is the registration complete page
			
			//para($rURL);
			
			if (strstr($rURL, 'register_complete.php') || strstr($rURL, 'change_password.php')) {
				
				//para('from register complete');
				
				redirectURL($base_address);
			} else {
				
				//para('not from register complete');
				
				redirectURL($rURL);			
			}
						
		} else {
			$err = 'Invalid username or password.';
		}
	} else {
		
		//save the url that referred them to the login
		$_SESSION['r_login_url'] = $_SERVER['HTTP_REFERER'];
	}
?>
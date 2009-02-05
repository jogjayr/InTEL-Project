<?php

function makedir($dirName, $dirPathFromHome="webapps/htdocs/canary/uploads/") {

	global $ftp_server;  
	global $ftp_user_name; 
	global $ftp_user_pass;

	// set up basic connection
	$conn_id = ftp_connect($ftp_server);
	
	// login with username and password
	$login_result = ftp_login($conn_id, $ftp_user_name, $ftp_user_pass);
	
	// check connection
	if ((!$conn_id) || (!$login_result)) {
	        echo "FTP connection has failed!";
	        echo "Attempted to connect to $ftp_server for user $ftp_user_name"; 
	        exit;
	    } else {
	        //echo "Connected to $ftp_server, for user $ftp_user_name";
	    }
	
	// print current directory
	//echo ftp_pwd($conn_id); // /public_html
	
	// try to change the directory to somedir
	if (ftp_chdir($conn_id, $dirPathFromHome)) {
	    //echo "Current directory is now: " . ftp_pwd($conn_id) . "\n";
	} else {
	    echo "Couldn't change directory to $dirPathFromHome \n";
	}
	
	// try to create the directory $dirName
	if (ftp_mkdir($conn_id, $dirName)) {
	   //echo "successfully created $dir\n";
	} else {
	  echo "There was a problem while creating $dirName\n";
	}
	
	// try to chmod the directory 
	if (ftp_chmod($conn_id, 0777, $dirName) !== false) { 		//echo "$file chmoded successfully to 777\n";	} else { 		echo "could not chmod $dirName\n";	}
	
	// close the connection
	ftp_close($conn_id);
}


function uploadFile($user_id, &$err, $file_extension = '.csv', $title='', $description='', $form_input_name="uploaded_file", $upload_dir_path="uploads/"){
  // takes the name of form input and uploads a file into upload directory
  // if successfull returns the assigned file name and makes error code an empty string
  // else returns the proposed file name and updates the error code variable with any error message.
  
  //here is an example form
  /*
  <form enctype="multipart/form-data" action="" method="POST">
  <input type="hidden" name="MAX_FILE_SIZE" value="100000" />
  Choose a file to upload: <input name="uploadedfile" type="file" /><br />
  <input type="submit" value="Upload File" />
  </form>
  */
  
  global $db;
  
  /* Add the original filename to our target path.  
  Result is "uploads/filename.extension" */
  $new_filename = basename( $_FILES[$form_input_name]['name']);
  
  if (strtolower(substr($new_filename, -1 * strlen($file_extension), strlen($file_extension))) != strtolower($file_extension)) {
  	$err = 'Invalid file type.  Please try again.';
  	return $new_filename;
  }
  
  $q_authorUserId = t2sql($user_id);
  $q_title = t2sql($title);
  $q_description = t2sql($description);
  
   $query = "INSERT INTO files (author_user_id, title, description, upload_datetime, is_active) VALUES ('{$q_authorUserId}', '{$q_title}', '{$q_description}', NOW(), FALSE)";
  query($query, $db);
  $fileId = lastInsertId($db);
  $target_dir = $upload_dir_path . $fileId . '/';
  
  //make a unique directory for the file, so that each file has its own directory
  //this enables urls to point to a file with the same file name as the uploaded file name
  makedir($filetId . '/', $dirPathFromHome="webapps/htdocs/canary/uploads/"); 
  
  $target_path = $target_dir . $new_filename;
  
  // save uploaded file
  if(!move_uploaded_file($_FILES[$form_input_name]['tmp_name'], $target_path)) {
      $err = "There was an error uploading the file. Please try again.";
  	  return $new_filename;
  }

  $q_fileId = t2sql($fileId);
  $q_filePath = t2sql($target_path);
  
  $query = "UPDATE files SET file_path ='{$q_filePath}', is_active=TRUE WHERE id='{$q_fileId}'";
  query($query, $db);
  
  return $new_filename;
}


?>
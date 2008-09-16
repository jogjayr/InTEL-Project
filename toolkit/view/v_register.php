<?php 

	$title = 'Register';
  require_once('controller/c_register.php');
	require_once('header.php');
	

	if ($success == false) { 
?>
<script type="text/javascript" src="js/sortable.js"></script>
  <form method="post" action="">
  	<?php paraErr($err);
    	if (count($classes) > 0) {
  ?>
  	<table class="sortable" id="sortabletable">
  		<tr>
        <th>Select</th>
  			<th class="startsort">Teacher</th>
  			<th>Description</th>
  		</tr>
  <?php
  		foreach($classes as $cl) {
  			
  			$teacher = $cl['teacher'];
        $description = $cl['description'];
        $id = $cl['id'];
  			echo '<tr>';
  				echo '<td><input type="radio" name="class_id" value='. $id .'></td>';
  				echo '<td>' . t2h($teacher) . '</td>';
  				echo '<td>' . t2h($description) . '</td>';
  			echo '</tr>';
  		}

  ?>
  	</table>
  <?php
  	}  else {
  		para('No problems available.');
  	}//end if
  ?>
    
  	<p>Email Address: <input type="text" name="email" value="<?php echo $emailAddress; ?>" /></p>
  	<p>Password: <input type="password" name="password" /></p>
  	<p>Password (again): <input type="password" name="password2" /></p>
    <p>First Name: <input type="text" name="first_name" value="<?php echo $firstName; ?>" /></p>
  	<p>Last Name: <input type="text" name="last_name" value="<?php echo $lastName; ?>" /></p>
    <p>GT Prism ID: <input type="text" name="gt_prism_id" value="<?php echo $gtPrismId; ?>" /></p>
  	<p><input type="submit" name="submit" value="Register" />
  </form>
<?php
	} else {	
?>
	<p>Thank you for registering.</p>
<?php
	}
?>
<?php 
	require_once('footer.php') 
?>
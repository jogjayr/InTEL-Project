<?php

  $title = 'Problems';

	require_once('header.php');
  
  //retrieve uuid
  $uuid = '';
  if (isset($_SESSION['uuid'])){
    $uuid = $_SESSION['uuid'];
  }
  
  //get assignments specific for user, all for anonymous
	$assignments = getAssignments($uuid);
  
	if (count($assignments) > 0) {
?>
  <script type="text/javascript" src="js/sortable.js"></script>
	<table class="sortable" id="sortabletable">
		<tr>
      <th>Problem</th>
			<th class="startsort">Name</th>
			<th>Description</th>
      <th>Status</th>
		</tr>
<?php
		foreach($assignments as $app) {
			
      $url = 'launchProblem.php?problem_id='.$app['problem_id'].'&exercise_id='.$app['id']; 
			$name = $app['name'];
      $description = $app['description'];
      $status = 'n/a';
      if ($uuid!=''){
        $status = $app['status'];
      }
      
			echo '<tr>';
				echo '<td><a href="' . $url . '">View</a></td>';
				echo '<td>' . t2h($name) . '</td>';
				echo '<td>' . t2h($description) . '</td>';
				echo '<td>' . t2h($status) . '</td>';
			echo '</tr>';
		}

?>
	</table>
<?php
	}  else {
		para('No problems available.');
	}//end if
?>

<?php
	
	require_once('footer.php') 
?>


  
  

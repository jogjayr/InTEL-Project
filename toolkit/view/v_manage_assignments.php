<?php
  require_once('admin/initvars.php');
  $title = 'Manage Assignments';
  
  requireLogin(); 
  
  //verify that user is instructor or admin
  if (!isInstructor() && !isAdmin()) {
		redirectRel('index.php');
	}
  
	require_once('header.php');
  
  //retrieve uuid
  $uuid = '';
  if (isset($_SESSION['uuid'])){
    $uuid = $_SESSION['uuid'];
  }
  
  //get assignments that belong to classes owned by this user, or all for admin
	$assignments = '';
  if (isInstructor()){
    $assignments = getAssignmentByClassOwner($uuid);
  }
  if (isAdmin()){
    $assignments = getAllAssignments();
  }
  
  

?>
  <script type="text/javascript">
    function confirm_delete(dest){
      var r=confirm("Are you sure you want to delete this assignment?");
      if (r==true){
        window.location = dest;
      }
    }
  </script>
  <script type="text/javascript" src="js/sortable.js"></script>
  <p><a href="addAssignment.php">Add Assignment</a></p>
<?php
	if (count($assignments) > 0) {
?>
	<table class="sortable" id="sortabletable">
		<tr>
      <th class="startsort">Class</th>
			<th>Problem</th>
			<th>Description</th>
      <th>Type</th>
      <th>Open Date</th>
      <th>Close Date</th>
      <th class="unsortable"></th>
      <th class="unsortable"></th> 
		</tr>
<?php
		foreach($assignments as $app) {
			$class = getClassById($app['class_id']);
      $classDescription = $class['description'];
			$name = $app['name'];
      $description = $app['description'];
      $type = $app['type'];
      $openDate = date("m.d.y", $app['open_date']);
      $closeDate = date("m.d.y",$app['close_date']);
      $urlEdit = 'editAssignment.php?id='.$app['id']; 
      $urlDelete = 'deleteAssignment.php?id='.$app['id']; 
      
      
			echo '<tr>';
        echo '<td>' . t2h($classDescription) . '</td>';
				echo '<td>' . t2h($name) . '</td>';
				echo '<td>' . t2h($description) . '</td>';
        echo '<td>' . t2h($type) . '</td>';
				echo '<td>' . t2h($openDate) . '</td>';
        echo '<td>' . t2h($closeDate) . '</td>';
        echo '<td><a href="' . $urlEdit . '">edit</a></td>';
        echo '<td><a href="#" onclick="confirm_delete(\''.$urlDelete.'\')">delete</a></td>';
			echo '</tr>';
		}

?>
	</table>
<?php
	}  else {
		para('No Assignments available.');
	}//end if
?>

<?php
	
	require_once('footer.php') 
?>


  
  

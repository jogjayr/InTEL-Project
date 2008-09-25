<?php

  $title = 'View Submissions';
    
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

  //get assignments & classes that belong to this user, or all for admin
	$assignments = '';
  $classes = '';
  if (isInstructor()){
    $assignments = getAssignmentByClassOwner($uuid);
    $classes = getClassesByOwner($uuid);
    $submissions = getSubmissionsByOwner($uuid);
  }
  if (isAdmin()){
    $assignments = getAllAssignments();
    $classes = getClasses();
    $submissions = getSubmissions();
  }
  
	if (count($submissions) > 0) {
?>
  <script type="text/javascript" src="js/sortable.js"></script>
	<table class="sortable" id="sortabletable">
		<tr>
      <th>First</th>
			<th class="startsort">Last</th>
			<th>Email Address</th>
      <th>Assignment</th>
      <th>Class</th>
      <th>Status</th>
      <th>Date Submitted</th>
		</tr>
<?php
		foreach($submissions as $sub) {
			
      $first = $sub['first_name'];
      $last = $sub['last_name'];
      $email = $sub['email'];
      $assignment = $sub['name'];
      $class = getClassById($sub['class_id']);
      $className = $class['description'];
      $status = $sub['status'];
      $date = date("m/d/y", $sub['updated_on']);
      
			echo '<tr>';
				echo '<td>' .t2h($first). '</td>';
				echo '<td>' . t2h($last) . '</td>';
				echo '<td>' . t2h($email) . '</td>';
				echo '<td>' . t2h($assignment) . '</td>';
        echo '<td>' . t2h($className) . '</td>';
        echo '<td>' . t2h($status) . '</td>';
        echo '<td>' . t2h($date) . '</td>';
			echo '</tr>';
		}

?>
	</table>
<?php
	}  else {
		para('No submissions available.');
	}//end if
?>

<?php
	
	require_once('footer.php') 
?>


  
  

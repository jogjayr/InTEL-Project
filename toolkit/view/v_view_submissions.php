<?php
  require_once('admin/initvars.php');
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
    $all_status = getStatus();
    $problems = getAllProblems();
  }
  
	if (count($submissions) > 0) {
?>
  <script type="text/javascript" src="js/sortable.js"></script>
  <script type="text/javascript" src="js/jquery.js"></script>
  <script type="text/javascript">
  $(document).ready(function()
  {
    //navigation click
    $("#filter_button").click(function(){
      $(".record").show();
      //filter by class
      var filter_by_class = "tr:not(:contains('"+$("#class_name").attr("value")+"'))";
      $(filter_by_class).hide();   

      //filter by problem
      var filter_by_problem = "tr:not(:contains('"+$("#problem_name").attr("value")+"'))";
      $(filter_by_problem).hide();  
      
      //filter by status
      var filter_by_status = "tr:not(:contains('"+$("#status_name").attr("value")+"'))";
      $(filter_by_status).hide();  
      $(".table_title").show();
    });
    //hover pointers
    $(".button").hover(
      function(){document.body.style.cursor="pointer";},function(){document.body.style.cursor="auto";}
    );
  });
  </script>
  <div class="form_area">
  Class
    <select id="class_name">
      <option value="" selected="selected"></option> 
      <?php
      foreach($classes as $cl) {
        echo '<option value="'.$cl['description'].'">'.$cl['description'].'</option>';
      }
      ?>
    </select>
  </div>
  <div class="form_area">
  Problem
    <select id="problem_name">
      <option value="" selected="selected"></option> 
      <?php
      foreach($problems as $problem) {
        echo '<option value="'.$problem['name'].'">'.$problem['name'].'</option>';
      }
      ?>
    </select>
  </div>
  <div class="form_area">
  Status
    <select id="status_name">
      <option value="" selected="selected"></option> 
      <?php
      foreach($all_status as $st) {
        echo '<option value="'.$st['status'].'">'.$st['status'].'</option>';
      }
      ?>
    </select>
  </div>
  <div class="button" id="filter_button">filter</div><br />
	<table class="sortable" id="sortabletable">
		<tr class="table_title">
      <th>First</th>
			<th class="startsort">Last</th>
			<th>Email Address</th>
      <th>Assignment</th>
      <th>Class</th>
      <th>Status</th>
      <th>Date Submitted</th>
      <th>Time Submitted</th>
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
      $time = date("g:i a", $sub['updated_on']);
      
			echo '<tr class="record">';
				echo '<td>' .t2h($first). '</td>';
				echo '<td>' . t2h($last) . '</td>';
				echo '<td>' . t2h($email) . '</td>';
				echo '<td>' . t2h($assignment) . '</td>';
        echo '<td>' . t2h($className) . '</td>';
        echo '<td>' . t2h($status) . '</td>';
        echo '<td>' . t2h($date) . '</td>';
        echo '<td>' . t2h($time) . '</td>';
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


  
  

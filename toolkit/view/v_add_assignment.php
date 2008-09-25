<?php
  require_once('admin/initvars.php');

	$title = 'Add Assignment';

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
    $classes = getClassesByOwner($uuid);
  }
  if (isAdmin()){
    $classes = getClasses();
  }
  //get problems
  $problems = getAllProblems();
  
  //initialize post variables
  $success = false;
	$err = '';
  $problemId = '';
  $classId = '';
  $openDate = '';
  $closeDate = '';
  
  //check for post data
	if (isset($_POST['submit'])) {
		
		$problemId = $_POST['problem_id'];
		$classId = $_POST['class_id'];
		$openDate = $_POST['open_date'];
    $closeDate = $_POST['close_date'];
    
    //check for valid dates
    if (strtotime($openDate) && strtotime($closeDate)){
      if (addAssignment($problemId, $classId, strtotime($openDate), strtotime($closeDate))) {
        $success = true;
      } else {
        $err = 'Your assignment was not added. Please try again later.';
      }
    } else {
  			$err = 'Please enter valid dates in the form of mm/dd/yyyy.';
  	}
	}
	
?>
<?php paraErr($err);  
  if (!$success) {
?>
<form method="post" action="">
	<p>Problem:
    <select name="problem_id">
      <?php
      foreach ($problems as $prob){
          echo '<option value="'.$prob['id'].'">'.$prob['name'].'</option>';
      }
      ?>
    </select>
  </p>
  <p>Class: 
    <select name="class_id">
      <?php
      foreach ($classes as $cls){
          echo '<option value="'.$cls['id'].'">'.$cls['description'].'</option>';
      }
      ?>
    </select>
  </p>
  <p>Open Date (mm/dd/yyyy): <input type="text" name="open_date" value="" /></p>
  <p>Close Date (mm/dd/yyyy): <input type="text" name="close_date" value="" /></p>
  <p><input type="submit" name="submit" value="Update" /></p>
</form>
<?php
	}  else {
		para('Your assignment has been created');
	}//end if
	require_once('footer.php') 
?>
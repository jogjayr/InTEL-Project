<?php
require_once ('admin/initvars.php');
$title = 'Problems';
if(!isAnonymous()) {
    $title = 'My Assignments';
}


require_once('header.php');

if (isLoggedIn ()) {
    //update class assignments for user
    updateUserSubmissions($uuid);
}

//get assignments specific for user, all for anonymous
$assignments = getAssignments($uuid);

$loggedIn = !isAnonymous();

if (count($assignments) > 0) {
?>
    <script type="text/javascript" src="js/sortable.js"></script>
    <table class="sortable" id="sortabletable">
        <tr>
            <th></th>
            <?php if($loggedIn) { echo "<th>Type</th>"; }?>
            <!--<th>Problem</th>-->
            <th class="startsort">Name</th>
            <th>Description</th>
<?php
    if ($uuid != '') {
        echo "<th>Status</th>";
        echo "<th>Due</th>";
    }
?>
    </tr>
<?php
    foreach ($assignments as $app) {

        $url = 'launchProblem.php?problem_id=' . $app['problem_id'];
        if ($uuid != '') {
            $url .= '&exercise_id=' . $app['id'];
            $type = $app['type'];
        } else {
            $type = "Example";
        }
        $name = $app['name'];
        $description = $app['description'];


        echo '<tr>';
        echo '<td style="text-align: center;"><img src="images/'.$app['image'].'"/></td>';
        if($loggedIn) {
            echo '<td><strong>' . t2h($type) . ': </strong></td>';
        }
        //echo '<td><a href="' . $url . '">View</a></td>';
        //echo '<td>' . t2h($name) . '</td>';
        echo '<td><a href="' . $url . '">' . t2h($name) . '</a></td>';
        echo '<td>' . t2h($description) . '</td>';


        if ($uuid != '') {
            $status = $app['status'];
            $dueDate = date("g:i a m/d/y", $app['close_date']);
            echo '<td>' . t2h($status) . '</td>';
            echo '<td>' . $dueDate . '</td>';
        }

        echo '</tr>';
    }
?>
</table>
    <?php
} else {
    para('No problems available.','infoMessage');
}//end if
    ?>

<?php
require_once('footer.php')
?>
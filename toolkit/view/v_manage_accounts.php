<?php
require_once('admin/initvars.php');
$title = 'Manage Accounts';

requireLogin();

//verify that user is admin
if (!isAdmin()) {
    redirect('index.php');
}

require_once('header.php');
?>
<script type="text/javascript">
    //    function confirm_delete(dest){
    //        var r=confirm("Are you sure you want to delete this account?");
    //        if (r==true){
    //            window.location = dest;
    //        }
    //    }

    var existingEdit = -1;
    function show_edit(id) {
        
    }
</script>
<script type="text/javascript" src="js/sortable.js"></script>

<table class="sortable" id="sortabletable">
    <tr>
        <th>id</th>
        <th class="startsort">Email</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Account Type</th>
        <th>Section</th>
        <th>Last Login</th>
        <th class="unsortable"></th>
        <th class="unsortable"></th>
    </tr>
    <?php
    $query = "SELECT * FROM app_user";
    $allUsers = aquery($query, $db);
    foreach ($allUsers as $userEntry) {

        $entryUuid = $userEntry['uuid'];

        $userClass = getClassByUUID($entryUuid);
        if ($userClass === false) {
            $class = "NONE";
        } else {
            $class = getClassById($userClass['class_id']);
            $class = $class['description'];
        }

        $lastLogin = date("g:i a m/d/y", $userEntry['last_login']);

        echo "<tr>";
        echo "<td>{$userEntry['id']}</td>";
        echo "<td>{$userEntry['email']}</td>";
        echo "<td>{$userEntry['first_name']}</td>";
        echo "<td>{$userEntry['last_name']}</td>";
        echo "<td>" . getUserType($entryUuid) . "</td>";
        echo "<td>{$class}</td>";
        echo "<td>{$lastLogin}</td>";
        echo "<td>edit</td>";
        echo "<td>delete</td>";
        echo "</tr>";
    }
    ?>
</table>


<?php
    require_once ('footer.php');
?>
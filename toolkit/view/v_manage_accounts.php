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

<script type="text/javascript" src="js/sortable.js"></script>

<table class="sortable" id="sortabletable">
    <tr>
        <th>id</th>
        <th class="startsort">Email</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Account Type</th>
        <th>Section</th>
    </tr>
<?php

$query = "SELECT * FROM app_users";
$allUsers = aquery($query);
foreach ($allUsers as $userEntry) {
    echo "<tr>";
    echo "<td>{$userEntry['id']}</td>";
    echo "<td>{$userEntry['email']}</td>";
    echo "<td>{$userEntry['first_name']}</td>";
    echo "<td>{$userEntry['last_name']}</td>";
    echo "<td>{$userEntry['user_type_id']}</td>";
    echo "<td>---</td>";
    echo "</tr>";
}
?>
</table>


<?php
require_once ('footer.php');
?>
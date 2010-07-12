<?php
require_once('admin/initvars.php');
$title = 'Manage Accounts';

requireLogin();

//verify that user is admin
if (!isAdmin()) {
    redirect('index.php');
}

//if(isset($_GET['action']) && $_GET['action']=='delete') {
//    $id = t2sql($_GET['id']);
//    deleteUser($id);
//    redirect("manageAccounts.php");
//}

if (isset($_POST['editAccount'])) {
    $id = t2sql($_POST['id']);
    $accountType = t2sql($_POST['accountType']);

    $query = "UPDATE app_user SET account_type='$accountType' WHERE id='$id'";
    query($query, $db);
}


require_once('header.php');
?>
<script type="text/javascript">
    //    function confirm_delete(id){
    //        var r=confirm("Are you sure you want to delete this account?");
    //        if (r==true){
    //            window.location = "manageAccounts.php?action=delete&id="+id;
    //        }
    //    }

    var existingEdit = -1;
    function show_edit(id) {

        if(existingEdit != -1)
            cancel_edit(existingEdit);
        existingEdit = id;


        var editContents =
            "<tr id=\"rowedit"+id+"\">"+
            "<td>"+id+"</td>"+
            "<td id=\"email\"/>"+
            "<td id=\"firstName\"/>"+
            "<td id=\"lastName\"/>"+
            "<td id=\"accountType\"/>"+
            "<td id=\"section\"/>"+
            "<td id=\"accountCreated\"/>"+
            //            "<td id=\"lastLogin\"/>"+
        "<td><input type=\"submit\" name=\"editAccount\"></td>"+
            "<td><a href=\"javascript:cancel_edit("+id+")\">cancel</a></td>"+
            "</tr>";
        $("#row"+id).after(editContents);
        $("#row"+id).hide();

        // these variables contain the text of the old values.
        var email = $("#row"+id+" td:nth-child(2)").text();
        var firstName = $("#row"+id+" td:nth-child(3)").text();
        var lastName = $("#row"+id+" td:nth-child(4)").text();
        var accountType = $("#row"+id+" td:nth-child(5)").text();
        var section = $("#row"+id+" td:nth-child(6)").text();
        var accountCreated = $("#row"+id+" td:nth-child(7)").text();
        //        var lastLogin = $("#row"+id+" td:nth-child(8)").text();

        var accountStuff =
            '<select name="accountType">'+
            '<option value="2">student</option>'+
            '<option value="3">instructor</option>'+
            '<option value="4">admin</option>'+
            '</select>';

        //$("#rowedit"+id+" #email").append("<input type=\"text\" name=\"email\" value=\""+email+"\"/>");
        $("#rowedit"+id+" #email").append(email);
        $("#rowedit"+id+" #firstName").append(firstName);
        $("#rowedit"+id+" #lastName").append(lastName);
        $("#rowedit"+id+" #accountType").append(accountStuff);
        $("#rowedit"+id+" #section").append(section);
        $("#rowedit"+id+" #accountCreated").append(accountCreated);

        // select the user's account type
        $("#rowedit"+id+" #accountType option:contains('"+accountType+"')").attr("selected","selected");
    }

    function cancel_edit(id) {
        $("#row"+id).show();
        $("#rowedit"+id).remove();
        existing_edit = -1;
    }
</script>
<script type="text/javascript" src="js/sortable.js"></script>

<form method="post" action="">
    <table class="sortable" id="sortabletable">
        <tr>
            <th>id</th>
            <th class="startsort">Email</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Account Type</th>
            <th>Section</th>
            <th>Account Created</th>
            <th>Last Login</th>
            <th class="unsortable"></th>
    <!--        <th class="unsortable"></th>-->
        </tr>
<?php
$query = "SELECT * FROM app_user WHERE is_active=1";
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

    $createdTime = date("g:i a m/d/y", $userEntry['created_on']);
    $lastLogin = date("g:i a m/d/y", $userEntry['last_login']);

    $urlEdit = "javascript:show_edit({$userEntry['id']})";

    echo '<tr id="row' . $userEntry['id'] . '">';
    echo "<td>{$userEntry['id']}</td>";
    echo "<td>{$userEntry['email']}</td>";
    echo "<td>{$userEntry['first_name']}</td>";
    echo "<td>{$userEntry['last_name']}</td>";
    echo "<td>" . getUserType($entryUuid) . "</td>";
    echo "<td>{$class}</td>";
    echo "<td>{$createdTime}</td>";
    echo "<td>{$lastLogin}</td>";
//            echo "<td>edit</td>";
    echo "<td><a href=\"$urlEdit\">edit</a></td>";
    //echo "<td>delete</td>";
//        echo '<td><a href="#" onclick="confirm_delete(' . $userEntry['id'] . ')">delete</a></td>';
    echo "</tr>";
}
?>
    </table>
</form>

<?php
require_once ('footer.php');
?>
<?php
require_once('admin/initvars.php');

$title = 'Account';
require_once('header.php');
require_once('controller/c_account.php');

requireLogin();

//get all classes
$classes = getClasses();

if ($hasAction) {
    if ($success) {
        para('Your profile has been updated.', 'infoMessage');
    } else {
        para($err, 'errorMessage');
    }
}
?>

<script type="text/javascript" src="js/sortable.js"></script>
<form method="post" action="">
    <?php
    $userClass = getClassByUUID($uuid);
    if (count($classes) > 0) {
    ?>
        <p>Class you belong to:</p>
        <table class="sortable" id="sortabletable">
            <tr>
                <th>Select</th>
                <th class="startsort">Teacher</th>
                <th>Description</th>
            </tr>
        <?php
        foreach ($classes as $cl) {

            $teacher = $cl['teacher'];
            $description = $cl['description'];
            $id = $cl['id'];
            echo '<tr>';
            if ($userClass['class_id'] == $id) {
                echo '<td><input type="radio" name="class_id" value=' . $id . ' CHECKED></td>';
            } else {
                echo '<td><input type="radio" name="class_id" value=' . $id . '></td>';
            }
            echo '<td>' . t2h($teacher) . '</td>';
            echo '<td>' . t2h($description) . '</td>';
            echo '</tr>';
        }
        ?>
    </table>
    <?php
    } else {
        para('No classes available.', 'errorMessage');
    }//end if
    ?>
    <p>First Name: <input type="text" name="first_name" value="<?php echo $user['first_name']; ?>" /></p>
    <p>Last Name: <input type="text" name="last_name" value="<?php echo $user['last_name']; ?>" /></p>
    <p>Email Address: <input type="text" name="email" value="<?php echo $user['email']; ?>" /></p>
    <p><input type="submit" name="submit" value="Update" /></p>
</form>
<p> Need to change your password?  Please contact <a href='<?php echo $site_email_address; ?>'>InTEL support</a>.</p>
<?php
    require_once('footer.php')
?>
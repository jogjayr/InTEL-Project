<?php
if ($handle = opendir($_POST['folder'])) {
    $xmlReturn = "<data>";

    while (false !== ($file = readdir($handle))) {
        if ($file != "." && $file != "..") {
           $xmlReturn .= '<file>'.$file.'</file>';
        }
    }
    closedir($handle);
    echo ($xmlReturn.'</data>');
}
?> 
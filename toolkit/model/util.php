<?php

/**
 * Redirects to the given address. By default, the redirect is relative.
 * If the second parameter is false, then the redirect is absolute.
 * @global string $base_address
 * @param string $address the page or URL to redirect to.
 * @param string $relative 
 */
function redirect($address, $relative = true) {

    global $base_address;

    if ($relative) {
        header("Location: " . $base_address . $address); /* Redirect browser */
    } else {
        header("Location: " . $address); /* Redirect browser */
    }
    exit;
}

/**
 * Encodes special characters from stirng so that is safe to pass in as an SQL statement.
 * This method should be called to convert any argument that goes into a SQL call.
 * @param string $s
 * @return string
 */
function t2sql($s) {
    return mysql_real_escape_string($s);
}

/**
 * Converts plain text into text suitable for HTML.
 * This is used to display text from the database.
 * @param string $s
 * @return string
 */
function t2h($s) {
    return htmlentities(trim($s), ENT_QUOTES, "UTF-8");
}

//prints

/**
 * This prints out the string wrapped in a html paragraph.
 * The optional argument $sClass is a style class that is applied to the paragraph.
 * @param string $s
 * @param string $sClass
 */
function para($s, $sClass='') {
    if ($sClass == '') {
        echo '<p>' . t2h($s) . '</p>';
    } else {
        echo '<p class="' . $sClass . '">' . t2h($s) . '</p>';
    }
}

/**
 * Returns a random password consisting of lowercase, uppercase, and numeric characters.
 * The password has as many characters as the specified length. This is currently used for building the password salt.
 * @param int $len
 * @return string
 */
function getRandomPassword($len) {

    $chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz0123456789";
    srand((double) microtime() * 1000000);
    $i = 0;
    $pass = '';

    while ($i <= $len) {
        $num = rand() % strlen($chars);
        $tmp = substr($chars, $num, 1);
        $pass = $pass . $tmp;
        $i++;
    }

    return $pass;
}

/**
 * Calculates a string representation of the difference in times
 * @param <type> $start
 * @param <type> $end
 * @return string
 */
function date_diff($start, $end="NOW") {
    $sdate = strtotime($start);
    $edate = strtotime($end);

    $time = $edate - $sdate;
    if ($time >= 0 && $time <= 59) {
        // Seconds
        $timeshift = $time . ' seconds ';
    } elseif ($time >= 60 && $time <= 3599) {
        // Minutes + Seconds
        $pmin = ($edate - $sdate) / 60;
        $premin = explode('.', $pmin);

        $presec = $pmin - $premin[0];
        $sec = $presec * 60;

        $timeshift = $premin[0] . ' min ' . round($sec, 0) . ' sec ';
    } elseif ($time >= 3600 && $time <= 86399) {
        // Hours + Minutes
        $phour = ($edate - $sdate) / 3600;
        $prehour = explode('.', $phour);

        $premin = $phour - $prehour[0];
        $min = explode('.', $premin * 60);

        $presec = '0.' . $min[1];
        $sec = $presec * 60;

        $timeshift = $prehour[0] . ' hrs ' . $min[0] . ' min ' . round($sec, 0) . ' sec ';
    } elseif ($time >= 86400) {
        // Days + Hours + Minutes
        $pday = ($edate - $sdate) / 86400;
        $preday = explode('.', $pday);

        $phour = $pday - $preday[0];
        $prehour = explode('.', $phour * 24);

        $premin = ($phour * 24) - $prehour[0];
        $min = explode('.', $premin * 60);

        $presec = '0.' . $min[1];
        $sec = $presec * 60;

        $timeshift = $preday[0] . ' days ' . $prehour[0] . ' hrs ' . $min[0] . ' min ' . round($sec, 0) . ' sec ';
    }
    return $timeshift;
}
?>

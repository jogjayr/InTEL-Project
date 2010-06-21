<?php

require_once('util_email.php');
require_once('util_ftp.php');

//redirects a webpage based on a relative address
function redirect($address, $relative = true) {

    global $base_address;

    if ($relative) {
        header("Location: " . $base_address . $address); /* Redirect browser */
    } else {
        header("Location: " . $address); /* Redirect browser */
    }
    exit;
}

//encodes special characters from stirng so that is safe to pass in as an SQL statement
function t2sql($s) {

    return mysql_real_escape_string($s);
}

//formats text for html
function t2h($s) {
    return htmlentities($s, ENT_QUOTES, "UTF-8");
}

//formats html special characters back into regular text characters
function h2t($s) {
    return html_entity_decode($s, ENT_QUOTES, "UTF-8");
}

//prints the string wrapped in a html paragraph. you may supply an optional class name for the text
function para($s, $sClass='') {
    if ($sClass == '') {
        echo '<p>' . t2h($s) . '</p>';
    } else {
        echo '<p><span class="' . $sClass . '">' . t2h($s) . '</span></p>';
    }
}

//prints an error string in a paragraph if the error is nonempty.  you may supply an optional class name for the text
function paraErr($err, $errClass='errorMessage') {
    $err = trim($err);
    if ($err != '') {
        para($err, $errClass);
    }
}

function getRandomInt($maxn) {
    return rand(0, $maxn);
}

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

function isAlphaNumeric($text) {

    if (ereg('[^A-Za-z0-9]', $text)) {
        return false;
    } else {
        return true;
    }
}

function isInteger($text) {

    if (ereg('[^0-9]', $text)) {
        return false;
    } else {
        return true;
    }
}

function formatDate($s) {
    $f = "m.d.y g:i a";
    return date($f, strtotime($s));
}

function isProfanity($s) {
    //determines if a string has a profane word in it
    //returns true if string has profane word in it, else returns false

    $badWords = array('fuck', 'shit', 'cunt', 'pussy', 'nigger', 'faggot', 'asshole');
    foreach ($badWords as $badWord) {
        if (strstr(strtolower(trim($s)), strtolower(trim($badWord)))) {
            return true;
        }
    }

    return false;
}
?>

<?php

//borrowed from http://www.php5script.com/isemail
function isEmailAddress($s) {

function secureEmail($to, $from, $subject, $message) {

	if ((eregi("(\r|\n)", $from)) || (eregi("(\r|\n)", $to)) || (eregi("(\r|\n)", $subject)) ) {
     	die("Email injection");
	}
	
	$headers = 'From:' .  $from . "\r\n" .
   'Reply-To:' . $from . "\r\n" .
   'X-Mailer: PHP/' . phpversion();

	//mail($to, $subject, $message, $headers);
	//para($to .' ' . $subject . ' ' . $message . ' ' . $headers);
	smtp($to, $subject, $message, $headers);
}


    
    //para('mail factory: ' . $smtp_server . ' ' . $smtp_username . ' ' . $smtp_password);
    //para('smtp sending: ' . $to . ' ' . $headers . ' ' . $message);
    
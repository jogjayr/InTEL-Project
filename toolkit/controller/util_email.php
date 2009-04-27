<?php

//borrowed from http://www.php5script.com/isemail
function isEmailAddress($s) {    return preg_match('|^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]{2,})+$|i', $s);}

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

/***********************************************************************This is a Pear::Mail implementation of the php mail() function to allowscripts that use mail() to work on WebFaction with minimal edits. Placethe line:require_once "pearmail.php";at the head of any file that uses the mail() function. Because PHP doesnot support function overriding, it is not possible to actually replacemail(). Therefore, the package includes a function called smtp() whichis semantically close to mail(). All instances of the mail() functionhave to be replaced with smtp (just edit mail to read smtp, leave theparameter list alone).Note that the $additional_parameters parameter is not supported bysmtp(). No placeholder is provided, so that the unavailability ofany behaviour depending on this parameter is flagged.Place your SMTP server info at the top of the smtp() function in thisfile. These parameters are kept here to avoid contaminating the namespaceof the including file.Ensure that the From header is correctly formed if it is provided. TheSMTP server may refuse to send mail if it doesn't like the From headeror is no From header is provided. If smtp() is called without a Fromheader provided, a simple one will be added; ensure that the SMTPserver will accept the address given for $smtp_default_from.Troubleshooting: A simple way to troubleshoot is to try uncommentingline 80. The error message attribute of any error object returned bythe $smtp->send() method will be echoed.************************************************************************///require_once "Mail.php";function smtp($to, $subject, $message, $additional_headers="") {        # Declare SMTP Server Settings    global $smtp_server; // "myserver.webfaction.com";    global $smtp_username;// = "username";    global $smtp_password; // = "password";    global $smtp_default_from; // = "nottotallybogus@example.com";
        # Cast inputs to strings    $to = (string) $to;    $subject = (string) $subject;    $message = (string) $message;    $additional_headers = (string) $additional_headers;        # Re-construct the headers into an array as expected by Pear    $raw_headers = explode("\n", $additional_headers);    $headers = Array("To"=>$to, "Subject"=>$subject);    foreach($raw_headers as $raw_header) {        $header = explode(":",$raw_header,2);        if (count($header) != 2) {            continue;     # Here the behaviour may differ somewhat with mail()...                         # malformed headers will be discarded silently.        }        if (trim(strtolower($header[0])) == "to" || trim(strtolower($header[0])) == "subject") {            continue;     # No overriding To and Subject        }        $headers[ucfirst(trim($header[0]))] = trim($header[1]); # Key will start uppercase    }        # Set a default From header if none was provided    if (!array_key_exists("From", $headers)) {        $headers["From"] = $smtp_default_from;    }        # Create the smtp object and send mail. Must return true on success,    # false on failure.        $smtp = Mail::factory("smtp", Array("host"=>$smtp_server, "auth"=>true,                                        "username"=>$smtp_username, "password"=>$smtp_password));    
    //para('mail factory: ' . $smtp_server . ' ' . $smtp_username . ' ' . $smtp_password);
    //para('smtp sending: ' . $to . ' ' . $headers . ' ' . $message);
        $result = $smtp->send($to, $headers, $message);        if (PEAR::IsError($result)) {        #para($result->getMessage());        return false;    } else {        return true;    }}?>
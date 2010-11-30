<?php
$headExtra = '<script type="text/javascript" src="swfobject.js"></script>
        <script type="text/javascript">
            swfobject.registerObject("csSWF", "9.0.115", "expressInstall.swf");
        </script>
        
        <style type="text/css" media=screen>
        <!--
           #helpnav {
           	clear: both;
           }
           
           #helpnav ul {
           	clear: both;
           	list-style: none;
           	margin: 0px;
           	padding: 0px;
           }
           
           #helpnav li {
           	margin: 0px;
           	padding: 0px;
           }
           
           #faq ul {
           	list-style: none;
           	margin: 0px;
           	padding: 0px;
           }
           
           #faq li {
           	margin: 0px;
           	padding: 0px;
           	margin-bottom: 25px;
           }
           
           #faq .question {
           	color: #666666;
           	font-style: italic;
           }
           
           #faq .answer {
           	margin-bottom: 50px;
           }
        -->
        </style>
        
        ';
$title = 'Instructions';
require_once('header.php');
?>
<div id="content_text">

<div id="helpnav">
	<ul>
		<li><a id="showInstructions" href="#">How To Use this Applet</a></li>
		<li><a id="showCompatability" href="#">Compatability Issues</a></li>
		<li><a id="showAccount" href="#">Logging In and Account Maitenance</a></li>
		<li><a id="showFAQ" href="#">Frequently Asked Questions</a></li>
	</ul>
</div>

<div id="instructions">
    <h3> How to Use this Applet </h3>
    <p>
        If you are new to InTEL, you may want to review <!--<a href="http://intel.gatech.edu/ArmPurse.htm">this video</a>--> this video before you get started.  It will walk you through everything you need to know to use the applet.<br/>
    </p>
    <div id="media">
        <object id="csSWF" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" width="640" height="498" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,115,0">
            <param name="src" value="InTEL video_controller.swf"/>
            <param name="bgcolor" value="#1a1a1a"/>
            <param name="quality" value="best"/>
            <param name="allowScriptAccess" value="always"/>
            <param name="allowFullScreen" value="true"/>
            <param name="scale" value="showall"/>
            <param name="flashVars" value="autostart=false"/>
            <embed name="csSWF" src="InTEL video_controller.swf" width="640" height="498" bgcolor="#1a1a1a" quality="best" allowScriptAccess="always" allowFullScreen="true" scale="showall" flashVars="autostart=false&thumb=FirstFrame.png&thumbscale=45&color=0x000000,0x000000" pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash"></embed>
        </object>
    </div>
</div>

<div id="compatability">
    <h3> Compatability </h3>
    <p>
        Currently the problems are able to run on PCs with the most updated version of Java installed.  We recommend running the applet on Firefox.

        If your computer is experiencing problems loading or running the applet, try using a PC at the Georgia Tech library to complete your work - these computers have been tested and are able to run the program.
        <br><br>
        If you are using a PC but do not have Firefox installed, <a href="http://www.mozilla.com/en-US/firefox/upgrade.html" TARGET="_blank">download it here</a>.
        <br><br>
        If you are using a PC but do not have the most updated version of Java, <a href="http://www.java.com/en/download/manual.jsp" TARGET="_blank">download your upgrade here</a>.
    </p>

</div>

<div id="account">
    <h3> Logging In and Account Maintenance</h3>
    <p>
        Students using InTEL should <a href='register.php'>register</a> and log in to use the applet.  Having an account makes your work accessable by both you and your instructors.
        <br><br>
        Should you need to edit your account information, visit your <a href='account.php'>account page</a>.  There you can change sections or otherwise update your information.
    </p>
</div>

<div id="faq">
	
	<h3>Frequently Asked Questions</h3>

	<ul>
	<li><a href="#1">Why can't I change my free body diagram after I go into the equation mode?</a></li>
	<li><a href="#2">The software says it cannot understand my coefficient 50cos30. This is the correct value, so why is this answer not being accepted?</a></li>
	<li><a href="#3">How do I save my problem?</a></li>
	<li><a href="#4">It won't run on my mac! Or it runs, but looks all weird. Why?!?</a></li>
	</ul>

	<a name="1"><p class="question">Why can't I change my free body diagram after I go into the equation mode?</p></a>
	<p class="answer">
	You are only allowed to enter equation mode if your free body diagram is correct. So, you do not need to change your diagram.
	</p>

	<a name="2"><p class="question">The software says it cannot understand my coefficient 50cos30. This is the correct value, so why is this answer not being accepted?</p></a>
	<p class="answer">You should format your coefficient like "50*cos(30)".</p>

	<a name="3"><p class="question">How do I save my problem?</p></a>
	<p class="answer">It is saved automatically! You should work on this on a computer with a working internet connection.</p>
	
	<a name="4"><p class="question">It won't run on my mac! Or it runs, but looks all weird. Why?!?</p></a>
	<p class="answer">There are some issues with the mac version of the LWJGL graphics library as used on web browsers. 
	</p>


</div>


    <h3> Need More Help? </h3>
    <p>
        For support, please contact <a href='mailto:beth.schechter@gatech.com'>InTEL support</a>.
    </p>


</div>

<script>


$(document).ready(function() {

function hideAll(){
$('div#instructions').hide();
$('div#compatability').hide();
$('div#account').hide();
$('div#faq').hide();

};

hideAll();

	$("a#showInstructions").click(
		function () {
		hideAll();
			$('div#instructions').show();
		}
	);        
	
	$("a#showCompatability").click(
		function () {
		hideAll();
			$('div#compatability').show();
		}
	);                 
    
	$("a#showAccount").click(
		function () {
		hideAll();
			$('div#account').show();
		}
	);    
    
    $("a#showFAQ").click(
    	function () {
    	hideAll();
    		$('div#faq').show();
    	}
    );    
    
    
});


</script>


<?php
require_once('footer.php');
?>

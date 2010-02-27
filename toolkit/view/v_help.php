<?php 

  $headExtra = '<script type="text/javascript" src="swfobject.js"></script>
        <script type="text/javascript">
            swfobject.registerObject("csSWF", "9.0.115", "expressInstall.swf");
        </script>';
	$title = 'Instructions';
	require_once('header.php'); 
?>
<div id="content_text">
<p>
 <h3> How to Use this Applet </h3>
 If you are new to InTEL, you may want to review <!--<a href="http://intel.gatech.edu/ArmPurse.htm">this video</a>--> this video before you get started.  It will walk you through everything you need to know to use the applet.<br/>
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

</p>
<p>
 <h3> Compatability </h3>
Currently the problems are able to run on PCs with the most updated version of Java installed.  We recommend running the applet on Firefox.

If your computer is experiencing problems loading or running the applet, try using a PC at the Georgia Tech library to complete your work - these computers have been tested and are able to run the program.
<br><br>
If you are using a PC but do not have Firefox installed, <a href="http://www.mozilla.com/en-US/firefox/upgrade.html" TARGET="_blank">download it here</a>. 
<br><br>
If you are using a PC but do not have the most updated version of Java, <a href="http://www.java.com/en/download/manual.jsp" TARGET="_blank">download your upgrade here</a>.
</p>
<p>
 <h3> Logging In and Account Maintenance</h3>
Students using InTEL should <a href='register.php'>register</a> and <a href='login.php'>log in</a> to use the applet.  Having an account makes your work accessable by both you and your instructors.
<br><br>
Should you need to edit your account information, visit your <a href='account.php'>account page</a>.  There you can change sections or otherwise update your information.
</p>
<p>
 <h3> Need Help? </h3>
 For support, please contact <a href='mailto:beth.schechter@gatech.com'>InTEL support</a>.
</p>


</div>
<?php 
	require_once('footer.php'); 
?>

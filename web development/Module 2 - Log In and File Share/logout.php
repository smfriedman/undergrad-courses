<!DOCTYPE html>
<head>
	<meta charset="utf-8"/>
	<title>My Web Page</title>
	<link rel="stylesheet" type="text/css" href="style.css">

</head>
<body>
    <div class="navbar">
		<p><a href="login.php">Log In</a></p>
		<p><a href="homepage.php">Home Page</a></p>
		<p><a href="http://classes.engineering.wustl.edu/cse330/index.php/">Course Wiki</a></li>
		<p><a href="whoami.php">Who am I?</a></p>
        <p><a href="logout.php">Log Out</a></p>
	</div>

	<div id="main">
		<?php
			session_start();
			session_destroy();
			echo("You have been logged out. Thank you for using our site.");
		?> 
	</div>
</body>
</html>
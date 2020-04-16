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
			 
			// Get the filename and make sure it is valid
			$filename = basename($_FILES['uploadedfile']['name']);
			if( !preg_match('/^[\w_\.\-]+$/', $filename) ){
				echo "Invalid filename";
				exit;
			}
			 
			// Get the username and make sure it is valid
			$username = $_SESSION['name'];
			if(!($username== "steve" || $username== "matt")){
				echo("Invalid username");
				exit;
			}
			 
			$full_path = sprintf('/home/stevenfriedman/user_files/%s/%s', $username, $filename);
			if( move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $full_path) ){
				header("Location: upload_success.html");
				//echo("success".$full_path);
				//header("Location: homepage.php");
				exit;
			}else{
				//header("Location: upload_failure.html");
				echo($full_path);
				exit;
			}
		 
		?> 
	</div>
</body>
</html>
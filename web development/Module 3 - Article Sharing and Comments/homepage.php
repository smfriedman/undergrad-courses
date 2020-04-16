<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8"/>
		<title>Peddit</title>
		<link rel="stylesheet" type="text/css" href="style.css">
	</head>
	<body>
		<div class="navbar">
			<!--change header if logged in or not-->
			<?php 
				session_start();
				if(!isset($_SESSION['logged_in'])){ 
			?>
				<p><a href="login.php">Log In</a></p>
			<?php } else { ?>
				<p><a href="logout.php">Log Out</a></p>
				<p><a href="Myuploads.php">My Uploads</a></p>
			<?php } ?>			

			<p><a href="homepage.php">Home Page</a></p>
		<p><?php 
				require 'database.php';
				$stmt = $mysqli->prepare('select title from stories order by rand() limit 1');
				if(!$stmt){
					printf('Query Prep Failed: %s\n', $mysqli->error);
					exit;
				}
				$stmt->execute();
				$title=$stmt->get_result();
				while($row=$title->fetch_assoc()){
				printf('<a href="story.php?story_name=%s">', htmlspecialchars($row["title"]));}
			$stmt->close();
			?>Random Story</a></p>
			<p><a href="http://classes.engineering.wustl.edu/cse330/index.php/">Course Wiki</a></p>
		</div>

		<div id="main">
			<?php
				require 'database.php';
				$stmt = $mysqli->prepare("select title, link, body from stories");
				if(!$stmt){
					printf("Query Prep Failed: %s\n", $mysqli->error);
					exit;
				}
				$stmt->execute();
				$stmt->bind_result($title, $link, $body);

				$stories_exist = false;
				while($stmt->fetch()){
					$stories_exist = true;

					//if link doesn't begin with http(s)://, it won't link out
					if(substr($link, 0, 4) != "http") $link = "http://" . $link;

					printf('<h3 class="title"><a href="%s">%s</a></h3>', htmlspecialchars($link), htmlspecialchars($title));
					printf('<p>%s</p>', htmlspecialchars($body));
					printf('<a href="story.php?story_name=%s">Comments</a>', urlencode(htmlspecialchars($title)));
					echo('<br/><br/>');
				}
				if(!$stories_exist) echo("<h3>There are no stories to display</h3>");

				$stmt->close();
			?>
		</div>
	</body>
</html>
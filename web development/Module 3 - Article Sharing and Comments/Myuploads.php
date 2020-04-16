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
				$stmt->bind_result($title);

				printf('<a href="story.php?story_name=%s">', urlencode(htmlspecialchars($title)));
			?>Random Story</a></p>
			<p><a href="http://classes.engineering.wustl.edu/cse330/index.php/">Course Wiki</a></p>
		</div>

      <div id="main" class="uploads">
				
      	<form action="Myuploads.php" method="POST">
			<label for="title1">Title</label>
			<input type="text" id="title1" name="title" required/>
			<label for="URL">Link</label>
			<input type="text" id="URL" name="URL" required/>
			<label for="body">Body</label>
			<textarea id="body" name="body" required></textarea>
			<br/>
			<input type="hidden" name="token" value="<?php echo $_SESSION['token'];?>" />
			<input type="submit" value="Upload"/>
		</form>


		<form action="Myuploads.php" method="POST">
			<label for="dtitle">Title</label>
			<input type="text" id="dtitle" name="dtitle" required/>
			<input type="hidden" name="token" value="<?php echo $_SESSION['token'];?>" />
			<input type="submit" value="Delete"/>
		</form>

		<form action="edit.php" method="Get">
			<label for="type">Story or comment?</label>
			<input type="text" id="type" name="type" required/>
			<label for="title">Title</label>
			<input type="text" id="title" name="title" required/>
			<input type="submit" value="Edit"/>
		</form>



				<?php
					$user = $_SESSION['username'];
					if(isset($user)){
						require 'database.php';

						//delete
						if(isset($_POST['dtitle'])){
							//end if diff token from session
							if($_SESSION['token'] !== $_POST['token']){
								die("Request forgery detected");
							}

							//delete comments
							$dtitle = $_POST['dtitle'];
							$stmt = $mysqli->prepare("delete from comments where title=?");
							if(!$stmt){
								printf("Query Prep Failed: %s\n", $mysqli->error);
								exit;
							}
							$stmt->bind_param('s', $dtitle);
							$stmt->execute();
							$stmt->close();

							//delete stories
							$dtitle = $_POST['dtitle'];
							$stmt = $mysqli->prepare("delete from stories where title=? and uploader=?");
							if(!$stmt){
								printf("Query Prep Failed: %s\n", $mysqli->error);
								exit;
							}
							$stmt->bind_param('ss', $dtitle, $user);
							$stmt->execute();
							$stmt->close();
							header("Location: homepage.php");
						}

						//upload
						if(isset($_POST['URL']) && isset($_POST['title']) && isset($_POST['body'])){
							//end if diff token from session
							if($_SESSION['token'] !== $_POST['token']){
								die("Request forgery detected");
							}

							$body = $_POST['body'];
							$URL = $_POST['URL'];
							$title = $_POST['title'];
							//if(isset($URL) && isset($title)){	
							$stmt = $mysqli->prepare("insert into stories (title, link, uploader, body) values (?, ?, ?, ?)");
							if(!$stmt){
								printf("Query Prep Failed: %s\n", $mysqli->error);
								exit;
							}
							$stmt->bind_param('ssss', $title, $URL, $user, $body);
							$stmt->execute();
							$stmt->close();

						}

						//display stories
						echo('<h2>Your Stories</h2><br/>');
						$stmt = $mysqli->prepare("select title, body, link from stories where uploader=?");
						if(!$stmt){
							printf("Query Prep Failed: %s\n", $mysqli->error);
							exit;
						}
						$stmt->bind_param('s', $user);
						$stmt->execute();
						$stmt->bind_result($title, $body, $link);

						while($stmt->fetch()){
							//if link doesn't begin with http(s)://, it won't link out
							if(substr($link, 0, 4) != "http") $link = "http://" . $link;

							printf('<h1 class="title"><a href="%s">%s</a></h1>', htmlspecialchars($link), htmlspecialchars($title));
							printf('<br/><p>%s</p><br/>', htmlspecialchars($body));
						} 
					}else{
						echo("You are not logged in");
					}
				?>
				<!--comments-->
		</div>


	</body>
</html>
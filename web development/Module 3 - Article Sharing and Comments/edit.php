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

		<div id="main">
			<div class="story">
				<?php
					require 'database.php';

					if(!isset($_GET['type']) || !isset($_GET['title'])){
						echo("<h1>No story or comment to edit</h1>");
					}else if($_GET['type'] == 'story'){
						printf("<h1>Edit story %s</h1>", htmlspecialchars($_GET['title']));	
					} else if($_GET['type'] == 'comments'){
						printf("<h1>Edit comments on %s</h1>", htmlspecialchars($_GET['title']));	
					}


					//handle if change was made and submitted from form below
					if(isset($_POST['title']) && isset($_POST['link'])){

						//end if diff token from session
						if($_SESSION['token'] !== $_POST['token']){
							die("Request forgery detected");
						}

						$stmt = $mysqli->prepare("update stories inner join comments on comments.title=stories.title set comments.title=?, stories.title=?, stories.link=?, stories.body=? where stories.title=? and stories.uploader=?");
						if(!$stmt){
							printf("Query Prep Failed: %s\n", $mysqli->error);
							exit;
						}
						$stmt->bind_param('ssssss', $_POST['title'], $_POST['title'], $_POST['link'], $_POST['body'], $_GET['title'], $_SESSION['username']);
						$stmt->execute();
						$stmt->close();
						header("Location: homepage.php");
					} else if(isset($_POST['comments_true'])){
						//end if diff token from session
						if($_SESSION['token'] !== $_POST['token']){
							die("Request forgery detected");
						}


						foreach($_POST as $key => $value){
						    if (substr($key, 0, 7) == 'comment'){
						        if(substr($key, 7, strlen($key)) != $value){
									$stmt = $mysqli->prepare("update comments set comment=? where title=? and uploader=? and comment=?");
									if(!$stmt){
										printf("Query Prep Failed: %s\n", $mysqli->error);
										exit;
									}
									$stmt->bind_param('ssss', $value, $_GET['title'], $_SESSION['username'], substr($key, 7, strlen($key)));
									$stmt->execute();
									$stmt->close();	
						        }
						    }
						}
						header("Location: homepage.php");
					}
				?>
				<br/>
				<?php printf('<form action="edit.php?type=%s&title=%s" method="POST">', urlencode(htmlspecialchars($_GET['type'])), urlencode(htmlspecialchars($_GET['title'])));?>
					<?php
						$type = $_GET['type'];
						$title = $_GET['title'];
						$username = $_SESSION['username'];

						if($type == "story"){
							$stmt = $mysqli->prepare("select title, link, body from stories where title=?");
							if(!$stmt){
								printf("Query Prep Failed: %s\n", $mysqli->error);
								exit;
							}
							$stmt->bind_param('s', $title);
							$stmt->execute();
							$stmt->bind_result($title, $link, $body);

							if($stmt->fetch()){
								printf('<label for="title">Title:</label><input type="text" id="title" name="title" value="%s" required/><br/>', htmlspecialchars($title));
								printf('<label for="link">Link:</label><input type="text" id="link" name="link" value="%s" required/><br/>', htmlspecialchars($link));	
								printf('<label for="body">Body:</label><textarea id="body" name="body" value="%s" required>%s</textarea><br/>', htmlspecialchars($body), htmlspecialchars($body));								
							} else echo("<h1>There was an error loading this story</h1>");

							$stmt->close();

							header("Location: homepage.php");

						}else{ //editing comments on story
							$stmt = $mysqli->prepare("select title, comment, uploader from comments where title=? and uploader=?");
							if(!$stmt){
								printf("Query Prep Failed: %s\n", $mysqli->error);
								exit;
							}
							$stmt->bind_param('ss', $title, $username);
							$stmt->execute();
							$stmt->bind_result($title, $comment, $uploader);

							printf('<input type="hidden" name="comments_true" value="comments_true"/>');
							while($stmt->fetch()){
								printf('<label for="comment%s">Comment:</label><textarea rows="7" cols="90" class="new_com" id="comment%s" name="comment%s" value="%s" required>%s</textarea><br/>', htmlspecialchars($comment), htmlspecialchars($comment), htmlspecialchars($comment), htmlspecialchars($comment), htmlspecialchars($comment));								
							}

							$stmt->close();
						}						
					?>

					<input type="hidden" name="token" value="<?php echo $_SESSION['token'];?>" />
					<input type="submit" value="save"/>
				</form>
			</div>
		</div>
	</body>
</html>
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
			<div class="story">
				<?php
					$story_name = $_GET['story_name'];
					if(isset($story_name)){
						require 'database.php';
						$stmt = $mysqli->prepare("select title, link, body from stories where title=?");
						if(!$stmt){
							printf("Query Prep Failed: %s\n", $mysqli->error);
							exit;
						}
						$stmt->bind_param('s', $story_name);
						$stmt->execute();
						$stmt->bind_result($title, $link, $body);

						//if link doesn't begin with http(s)://, it won't link out
						if(substr($link, 0, 4) != "http") $link = "http://" . $link;

						if($stmt->fetch()){
							printf('<h1 class="title"><a href="%s">%s</a></h1>', htmlspecialchars($link), htmlspecialchars($title));
							printf('<br/><p>%s</p>', htmlspecialchars($body));
						//change to select votes
						//printf('<p>%u points', htmlspecialchars($votes));
						//echo upvote and downvote forms/hrefs

						} else echo("<h1>There was an error loading this story</h1>");

						$stmt->close();
					} else{
						echo("<h1>No story specified</h1>");
					}
				?>
			</div>

			<div class="comments">
				<h3>Comments</h3>
				<br/>

				<!--give option to comment if logged in-->
				<?php if(isset($_SESSION['logged_in'])): ?>
					<p>Leave a comment</p>
					<?php printf('<form action="story.php?story_name=%s" method="POST">', urlencode(htmlspecialchars($_GET['story_name'])));?>
						<textarea rows="7" cols="90" name="comment" required></textarea>
						<input type="hidden" name="token" value="<?php echo $_SESSION['token'];?>" />
						<input type="submit" value="Comment">
					</form>
					<br>
				<?php endif ?>

				<!--if receive post from form above on reload, add comment to db-->
				<!--show comments to everyone-->
				<?php
					require 'database.php';

					//insert possible comment
					if(isset($_POST['comment'])){
						//end if diff token from session
						if($_SESSION['token'] != $_POST['token']){
							die("Request forgery detected");
						}
						//get id of story
						$stmt = $mysqli->prepare("select story_id from stories where title=?");
						if(!$stmt){
							printf("Query Prep Failed: %s\n", $mysqli->error);
							exit;
						} 
						$stmt->bind_param('s', $_GET['story_name']);
						$stmt->execute();
						$stmt->bind_result($story_id);
						$stmt->close();

						//insert comment
						$stmt = $mysqli->prepare("insert into comments (title, comment, uploader, story_id) values (?, ?, ?, ?)");
						if(!$stmt){
							printf("Query Prep Failed: %s\n", $mysqli->error);
							exit;
						}
						 
						$stmt->bind_param('ssss', $_GET['story_name'], $_POST['comment'], $_SESSION['username'], $story_id);
						$stmt->execute();
						$stmt->close();
					}

					//show all comments
					$stmt = $mysqli->prepare("select uploader, comment from comments where title=?");
					if(!$stmt){
						echo("<h5>There are no comments to display</h5>");
						//printf("Query Prep Failed: %s\n", $mysqli->error);
						//exit;
					}
					$stmt->bind_param('s', $_GET['story_name']);
					$stmt->execute();
					$stmt->bind_result($uploader, $comment);

					$comments_exist = false;
					while($stmt->fetch()){
						$comments_exist = true;
						printf('<h6>%s</h6>', htmlspecialchars($uploader));
						printf('<p>%s</p>', htmlspecialchars($comment));
						echo('<br/>');
					}
					if(!$comments_exist) echo("<h5>There are no comments to display</h5>");

					$stmt->close();

				?>

			</div>
		</div>
	</body>
</html>
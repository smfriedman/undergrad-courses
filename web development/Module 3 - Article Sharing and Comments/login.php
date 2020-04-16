<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8"/>
		<title>Peddit</title>
		<link rel="stylesheet" type="text/css" href="style.css">
	</head>
	<body>
		<div class="navbar">
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

		<form action="login.php" method="POST">
			<label for="username">Username</label>
			<input type="text" id="username" name="username" required/>
			<br/>
			<label for="password">Password</label>
			<input type="password" id="password" name="password" required/>
			<br/>
			<input type="radio" name="newOrExisting" value="new" checked>I'm a new user<br/>			
			<input type="radio" name="newOrExisting" value="existing">I'm an existing user
			<br/>
			<input type="submit" value="Log In"/>
		</form>

		<p id="warnings">
			<?php
				//only run if form was submitted

				if(isset($_POST['newOrExisting'])){
					session_start();
					require 'database.php';

					$username = $_POST['username'];
					$password = $_POST['password'];
					$newOrExisting = $_POST['newOrExisting'];

					if($newOrExisting == 'new'){
						$stmt = $mysqli->prepare("select count(*) from users where username=?");
						if(!$stmt){
							printf("Query Prep Failed: %s\n", $mysqli->error);
							exit;
						}
						$stmt->bind_param('s', $username);
						$stmt->execute();
						$stmt->bind_result($count);
						$stmt->fetch();

						echo($count);
						if($count > 0){
							echo("username already exists");
							$stmt->close();
						} 
						else{
							$stmt->close();
							$stmt = $mysqli->prepare("insert into users (username, password) values (?, ?)");
							if(!$stmt){
								printf("Query Prep Failed: %s\n", $mysqli->error);
								exit;
							}
							 
							$stmt->bind_param('ss', $username, crypt($password));
							$stmt->execute();
							$stmt->close();

							$_SESSION['logged_in'] = true;
							$_SESSION['username'] = $username;
							//genr CSRF token
							$_SESSION['token'] = substr(md5(rand()), 0, 10);
							header("Location: homepage.php");
						}

					}else{
						$stmt = $mysqli->prepare("select COUNT(*), username, password from users where username=?");
						if(!$stmt){
							printf("Query Prep Failed: %s\n", $mysqli->error);
							exit;
						}
						$stmt->bind_param('s', $username);
						$result = $stmt->execute();

						$stmt->bind_result($count, $username, $pass);
						$stmt->fetch();
						echo($count);
						if($count == 1){
							if(crypt($password, $pass) == $pass){
								$_SESSION['logged_in'] = true;
								$_SESSION['username'] = $username;
								//genr CSRF token
								$_SESSION['token'] = substr(md5(rand()), 0, 10);
								header("Location: homepage.php");
							} else echo("Username does not match password");
						}
						else echo("Username does not exist");
						$stmt->close();
					}
				}
			?>
		</p>
	</body>
</html>
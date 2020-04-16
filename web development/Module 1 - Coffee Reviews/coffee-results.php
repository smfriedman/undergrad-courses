<!DOCTYPE html>
<head>
	<meta charset="utf-8"/>
	<title>Coffee Results</title>
	<style type="text/css">
		body{
			width: 760px; /* how wide to make your web page */
			background-color: teal; /* what color to make the background */
			margin: 0 auto;
			padding: 0;
			font:12px/16px Verdana, sans-serif; /* default font */
		}
		div#main{
			background-color: #FFF;
			margin: 0;
			padding: 10px;
		}
	</style>
</head>
<body>
	<div id="main">
		<h1>Coffee Ratings</h1>

		<?php
			//check if post variables present
			if(!isset($_POST['coffee'])){
				printf("Improper form submission");
				exit;
			}

			//save to variables
			$name = $_POST['coffee'];

			//connect to db and query
			require('database.php');

			//get id of coffee
			$stmt = $mysqli->prepare("select id from coffees where name=?");
			if(!$stmt){
				printf("Query Prep Failed: %s\n", $mysqli->error);
				exit;
			}
			 
			$stmt->bind_param('s', $name);
			$stmt->execute();
			$stmt->bind_result($cId);
			$stmt->fetch();
			$stmt->close();

			//find avg rating
			$stmt = $mysqli->prepare("select avg(rating) from ratings where coffeeId=?");
			if(!$stmt){
				printf("Query Prep Failed: %s<br/>", $mysqli->error);
				exit;
			}
			 
			$stmt->bind_param('i', $cId);
			$stmt->execute();
			$stmt->bind_result($avgRating);
			$stmt->fetch();
			$stmt->close();


			//write avg rating out
			printf("Name: %s <br/>", htmlentities($name));
			printf("Average rating (out of 5): %.1f <br/>", $avgRating);
			printf("Comments: <br/>");

			//find and output comments
			$stmt = $mysqli->prepare("select user, comment from ratings where coffeeId=?");
			if(!$stmt){
				printf("Query Prep Failed: %s<br/>", $mysqli->error);
				exit;
			}
			 
			$stmt->bind_param('i', $cId);
			$stmt->execute();
			$stmt->bind_result($user, $comment);
			while($stmt->fetch()){
				printf("%s:\t\"%s\"<br/>", htmlentities($user), htmlentities($comment));
			}
			$stmt->close();
		?>
 
 		<br/>
 		<br/>
 		<a href="coffee-main.html">Submit a New Rating</a>
	</div>
</body>
</html>
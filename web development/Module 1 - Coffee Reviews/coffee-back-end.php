<?php
	//check that post variables were passed properly
	if(!(isset($_POST['user']) && isset($_POST['coffee']) && isset($_POST['userRating']) && isset($_POST['comments']))){
		printf("Improper form submission");
		exit;
	}

	//bind post variables to php ones
	$user = $_POST['user'];
	$coffee = $_POST['coffee'];
	$userRating = floatval($_POST['userRating']);
	$comments = $_POST['comments'];

	//connect and insert into db
	require('database.php');

	//first coffee
	$stmt = $mysqli->prepare("insert into coffees (name) values (?)");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		exit;
	}
	 
	$stmt->bind_param('s', $coffee);
	$stmt->execute();
	$cId = $stmt->insert_id;
	$stmt->close();

	//then rating
	$stmt = $mysqli->prepare("insert into ratings (coffeeId, user, rating, comment) values (?, ?, ?, ?)");
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		exit;
	}
	 
	$stmt->bind_param('isds', $cId, $user, $userRating, $comments);
	$stmt->execute();
	$stmt->close();

	//if fail, exit above. if successful, redirect to coffee-main.html
	header('Location: coffee-main.html');
?>












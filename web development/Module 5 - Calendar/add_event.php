<?php
	ini_set("session.cookie_httponly", 1);
	session_start();
	require('database.php');

	$user = $_POST['username'];
	$token = $_POST['token'];

	if($user != $_SESSION['username'] && $token != $_SESSION['token']){ //check right user and validate token
		echo(json_encode(array("failure" => true)));
		exit;
	}

	$name = $_POST['ev_name'];
	$hour = $_POST['ev_hour'];
	$minute = $_POST['ev_min'];
	$year = $_POST['ev_year'];
	$month = $_POST['ev_month'];
	$day = $_POST['ev_day'];
	$tag = $_POST['ev_tag'];

	$stmt = $mysqli->prepare('insert into events (name, hour, minute, user, month, day, year, tag) values (?,?,?,?,?,?,?,?)');
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		echo json_encode(array(
			"success" => false
		));
		//exit;
	}

	$stmt->bind_param('siisiiis', $name, $hour, $minute, $user, $month, $day, $year, $tag);
	$stmt->execute();
	$stmt->close();

	echo json_encode(array("success" => true));
?>
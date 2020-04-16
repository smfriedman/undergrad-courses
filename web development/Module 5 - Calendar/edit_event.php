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

	$id = $_POST['ev_id'];

	$name = $_POST['ev_name'];
	$hour = $_POST['ev_hour'];
	$minute = $_POST['ev_min'];
	$year = $_POST['ev_year'];
	$month = $_POST['ev_month'];
	$day = $_POST['ev_day'];

	$stmt = $mysqli->prepare('update events set name=?, hour=?, minute=?, month=?, day=?, year=? where id=? and user=?');
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		echo(json_encode(array("failure" => true)));
		exit;
	}

	$stmt->bind_param('siiiiiis', $name, $hour, $minute, $month, $day, $year, $id, $user);
	$stmt->execute();
	$stmt->close();
	echo(json_encode(array("success" => true)));
?>
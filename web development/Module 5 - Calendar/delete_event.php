<?php
	ini_set("session.cookie_httponly", 1);
	session_start();
	require('database.php');

	$user = $_POST['username'];
	$token = $_POST['token'];
	$id = $_POST['ev_id'];

	if($user != $_SESSION['username'] && $token != $_SESSION['token']){ //check right user and validate token
		echo(json_encode(array("failure" => true)));
		exit;
	}

	$stmt = $mysqli->prepare('delete from events where id=? and user=?');
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		echo(json_encode(array("failure" => true)));
		exit;
	}

	$stmt->bind_param('is', $id, $user);
	$stmt->execute();
	$stmt->close();
	echo(json_encode(array("success" => true)));
?>
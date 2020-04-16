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

	//double check order by
	$stmt = $mysqli->prepare('select share_code from users where user=?');
	if(!$stmt){
		printf("Query Prep Failed: %s\n", $mysqli->error);
		echo json_encode(array("failure" => true));
		exit;
	}

	$stmt->bind_param('s', $user);
	$stmt->execute();
	$stmt->bind_result($share_code);
	$stmt->fetch();
	$stmt->close();

	//if don't have one, generate one
	if($share_code === null || $share_code === 'NULL'){
		$share_code = md5(uniqid(rand(), true));
		$stmt = $mysqli->prepare('update users set share_code=? where user=?');
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			echo json_encode(array("failure" => true));
			exit;
		}
		$stmt->bind_param('ss', $share_code, $user);
		$stmt->execute();
	}

	echo json_encode(array(
		"success" => true,
		"share_code" => $share_code
	));
?>
<?php
	ini_set("session.cookie_httponly", 1);
	session_start();
	require('database.php');

	$user = $_POST['username'];
	$month = $_POST['month'];
	$year = $_POST['year'];
	$token = $_POST['token'];
	$tag = $_POST['tag'];
	$share_code = $_POST['share_code'];

	 //check right user (or shared) and validate token
	if(($user != $_SESSION['username'] && $token != $_SESSION['token']) && !isset($share_code)){
		echo(json_encode(array("failure" => true)));
		exit;
	}
	$res_arr = array();

	if(!isset($tag) || $tag == null || $tag='all'){
		$stmt = $mysqli->prepare('select name, hour, minute, day, id, tag from events where month=? and year=? and user=? order by hour');
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			echo json_encode(array("failure" => true));
			exit;
		}

		$stmt->bind_param('iis', $month, $year, $user);
		$stmt->execute();
		$stmt->bind_result($name, $hour, $minute, $day, $id, $tag);
		
		while($stmt->fetch()){
			array_push($res_arr, array(
				"success" => true,
				"eventName" => htmlentities($name),
				"hour" => htmlentities($hour),
				"minute" => htmlentities($minute),
				//"user" => $user,
				//"month" => $month,
				"day" => htmlentities($day),
				"id" => htmlentities($id),
				"tag" => htmlentities($tag)
				//"year" => $year
			));
		}
		$stmt->close();
	} else{
		$stmt = $mysqli->prepare('select name, hour, minute, day, id, tag from events where month=? and year=? and user=? and tag=? order by hour');
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			echo json_encode(array("failure" => true));
			exit;
		}

		$stmt->bind_param('iiss', $month, $year, $user, $tag);
		$stmt->execute();
		$stmt->bind_result($name, $hour, $minute, $day, $id, $tag);

		while($stmt->fetch()){
			array_push($res_arr, array(
				"success" => true,
				"eventName" => htmlentities($name),
				"hour" => htmlentities($hour),
				"minute" => htmlentities($minute),
				//"user" => $user,
				//"month" => $month,
				"day" => htmlentities($day),
				"id" => htmlentities($id),
				"tag" => htmlentities($tag)
				//"year" => $year
			));
		}
		$stmt->close();
	}

	$code_match = true;
	if(isset($share_code)){
		$stmt = $mysqli->prepare('select share_code from users where user=?');
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			echo json_encode(array("failure" => true));
			exit;
		}

		$stmt->bind_param('s', $user);
		$stmt->execute();
		$stmt->bind_result($real_share_code);	
		$stmt->fetch();	

		if($real_share_code != $share_code) $code_match = false;
		$stmt->close();
	}


	if($code_match){
		echo json_encode($res_arr);
	}
?>
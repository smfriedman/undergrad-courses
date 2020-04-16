<?php
	
	//Need to add statement to make sure user is logged in
	ini_set("session.cookie_httponly", 1);
	session_start();
	require 'database.php';
	$user = $_SESSION['username'];
	$name = $_POST['name'] ;
	$time = $_POST['time'];
	$year = $_POST['year'];
	$month = $_POST['month'];
	$day = $_POST['day'];
	$newname = $_POST['new'];
	$newyear = $_POST['nyear'];
	$newmonth = $_POST['nmonth'];
	$newday = $_POST['nday'];
	$newtime = $_POST['ntime'];
	$action = $_POST['action'];
	///////////////////////////////////////////////////////////////
	$currmonth = ///////// make variable for current month
	///////////////////////////////////////////////////////////////
	if($action == "create event"){
		$stmt = $mysqli->prepare('insert into events (name, time, user, month, day, year) values (?,?,?,?,?,?)');
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			echo json_encode(array(
				"success" => false
			));
			//exit;
		}

		$stmt->bind_param('sssiii', $name, $time, $user, $month, $day, $year);
		$stmt->execute();
		$stmt->close();

		//send data back
		echo json_encode();
	}
	else if($action == "edit"){
		$stmt = $mysqli->prepare('update events set name=? time=? month=? day=? year=? where name=? and user=?');
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			exit;
		}

		$stmt->bind_param('ssiiiss', $newname, $newtime, $newmonth, $newday, $newyear, $user);
		$stmt->execute();
		$stmt->close();
	}
	else if($action == "delete"){
		$stmt = $mysqli->prepare('delete from events where name=? and user=?');
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			exit;
		}

		$stmt->bind_param('ss', $name, $user);
		$stmt->execute();
		$stmt->close();
	}



?>
<?php
// login_ajax.php
 
header("Content-Type: application/json"); // Since we are sending a JSON response here (not an HTML document), set the MIME Type to application/json

$username = $_POST['username'];
$password = $_POST['password'];
$newOrExisting = $_POST['newOrExisting'];

if(isset($newOrExisting)){
	require 'database.php';

	if($newOrExisting == 'new'){
		$stmt = $mysqli->prepare("select count(*) from users where user=?");
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			error();
		}
		$stmt->bind_param('s', $username);
		$stmt->execute();
		$stmt->bind_result($count);
		$stmt->fetch();

		if($count > 0){
			invalid();
			$stmt->close();
		} 
		else{
			$stmt->close();
			$stmt = $mysqli->prepare("insert into users (user, password) values (?, ?)");
			if(!$stmt){
				printf("Query Prep Failed: %s\n", $mysqli->error);
				error();
			}
			 
			$stmt->bind_param('ss', $username, crypt($password));
			$stmt->execute();
			$stmt->close();

			valid($username);
		}

	}else{
		$stmt = $mysqli->prepare("select COUNT(*), user, password from users where user=?");
		if(!$stmt){
			printf("Query Prep Failed: %s\n", $mysqli->error);
			error();
		}
		$stmt->bind_param('s', $username);
		$result = $stmt->execute();

		$stmt->bind_result($count, $username, $pass);
		$stmt->fetch();
		if($count == 1){
			if(crypt($password, $pass) == $pass){
				valid($username);
			} else invalid();
		}
		else invalid();
		$stmt->close();
	}
}
 
// Check to see if the username and password are valid.  (You learned how to do this in Module 3.)
 
function valid($username){
	ini_set("session.cookie_httponly", 1);
	session_start();
	$_SESSION['username'] = $username;
	$_SESSION['token'] = substr(md5(rand()), 0, 10);
 
	echo json_encode(array(
		"success" => true,
		"token" => $_SESSION['token']
	));
	exit;
}

function invalid(){
	echo json_encode(array(
		"success" => false,
		"message" => "Incorrect Username or Password"
	));
	exit;	
}

function error(){
	echo json_encode(array(
		"success" => false,
		"message" => "Error checking credentials"
	));
	exit;	
}
?>
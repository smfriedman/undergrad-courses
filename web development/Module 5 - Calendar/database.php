<?php
	 
	$mysqli = new mysqli('localhost', 'root', 'pw', 'calendar');
	 
	if($mysqli->connect_errno) {
		printf("Connection Failed: %s\n", $mysqli->connect_error);
		exit;
	}
?>
<? php
function logout(){
	ini_set("session.cookie_httponly", 1);
	session_start();
	$username = $_SESSION['username']; 
	//$_SESSION['token'] = substr(md5(rand()), 0, 10);
 	session_destroy();
 	$username = $_SESSION['username']; 
	if(!ISSET($username){
		echo json_encode(array(
			"success" => true
		));
		exit;
	}
}
?>
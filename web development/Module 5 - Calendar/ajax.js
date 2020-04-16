var logged_in = false;
var un = null;
var token = null;
var tag = null;

function loginAjax(){
	var username = document.getElementById("username").value; // Get the username from the form
	var password = document.getElementById("password").value; // Get the password from the form
	if(!username || !password || username === "" || password === "") alert("enter your username and password");
	var newOrExisting = $('input[name="newOrExisting"]:checked').val();
 
 	$.post('login_ajax.php', {username: username, password: password, newOrExisting: newOrExisting}, function(res){
 		if(res.success){  // in PHP, this was the "success" key in the associative array; in JavaScript, it's the .success property of jsonData
			alert("You've been Logged In!");
			token = res.token;
			un = username;
			handleLogin();
		}else{
			alert("You were not logged in.  "+res.message);
		}	
 	});
}

function handleLogin(){
	logged_in = true;
	// un = username;
	fillCalendar();

	$('#login').addClass('hidden');
	$('#logout').removeClass('hidden');
	$('#create_or_edit').removeClass('hidden');
	$('#share_or_tag').removeClass('hidden');
}

function handleLogout(){
	logged_in = false;
	un = null;
	token = null;
	fillCalendar();


	$('#login').removeClass('hidden');
	$('#logout').addClass('hidden');
	$('#create_or_edit').addClass('hidden');
	$('#edit_form').addClass('hidden');
	$('#share_or_tag').addClass('hidden');
}

function logoutAjax(){
	$.post('logout.php', {username: un}, function(res){
		if(res.success){
			alert("You've been logged out!");
			handleLogout();
		}
	});
}

$(document).ready(function(){
	$("#login_btn").on("click", function(e){
		e.preventDefault();
		loginAjax(); // Bind the AJAX call to button click
	});
	$('#lougout_btn').on('click', function(e){
		e.preventDefault();
		logoutAjax();
	});
});



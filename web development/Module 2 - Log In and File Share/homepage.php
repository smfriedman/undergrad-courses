<!DOCTYPE html>
<head>
	<meta charset="utf-8"/>
	<title>My Web Page</title>
	<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
	<div class="navbar">
		<p><a href="login.php">Log In</a></p>
		<p><a href="homepage.php">Home Page</a></p>
		<p><a href="http://classes.engineering.wustl.edu/cse330/index.php/">Course Wiki</a></li>
		<p><a href="whoami.php">Who am I?</a></p>
        <p><a href="logout.php">Log Out</a></p>
	</div>
	
	<div id="main">
		<h2>Your Files</h2>
		<br/>
		<ul>
			<?php
				session_start();
				if(!isset($_SESSION['name'])){
					header("Location: login.php");	
				}

				$name = $_SESSION['name'];
				$filename = $_GET['delete_file'];
				if(isset($filename) && isset($_GET['delete'])){
					if($_GET['delete'] == 'delete'){
						$full_path = sprintf('/home/stevenfriedman/user_files/%s/%s', $name, $filename);
						$success = unlink($full_path);
						if(!$success){
							echo("<li>ERROR: Error deleting " . htmlentities($filename) . "</li>");
						}
					}
				}

				$files=scandir("../user_files/" . $_SESSION['name']);
				//start from 2 to get rid of "." and ".."
				for($i = 2; $i < count($files); $i++){
					$li = '<li><a href="view.php?user=' . htmlentities($_SESSION['name']) . '&file=' . htmlentities($files[$i]) . '"> ' . htmlentities($files[$i]) . '</a></li>';
					echo($li);
				}
				$_SESSION['files'] = $files;
			?>
		</ul> 
		<br/>
		<br/>
		<h2>Upload a File</h2>
		<br/>
		<form enctype="multipart/form-data" action="upload.php" method="POST">
	<p>
		<input type="hidden" name="MAX_FILE_SIZE" value="20000000" />
		<label for="uploadfile_input">Choose a file to upload:</label> <input name="uploadedfile" type="file" id="uploadfile_input" />
	</p>
	<p>
		<input type="submit" value="Upload File" />
	</p>
</form>
		<br/>
		<br/>
		<h2>Delete a File</h2>
		<br/>
		<p>Select the file you wish to delete.</p>
		<form action="homepage.php" method="GET">
			<select name="delete_file">
				<?php
					session_start();
					$files = $_SESSION['files'];
					//start from 2 to get rid of "." and ".."
					for($i = 2; $i < count($files); $i++){
						$option = '<option value="' . htmlentities($files[$i]) . '">' . htmlentities($files[$i]) . '</option>';
						echo($option);
					}
				?>
			</select>
			<input type=hidden name="delete" value="delete"/>
			<input type="submit" value="Delete"/>
		</form>
		
	</div>
</body>
</html>
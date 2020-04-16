<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <title>Who are you?</title>
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
            <h1>
                <?php
                    session_start();
                    $name = $_SESSION['name'];
                    if(isset($name)){
                        echo("You are  " . htmlentities($name) . "!");
                    } else{
                        echo("I'm not sure who you are.  Try logging in.");
                    }
                    
                ?>
            </h1>
        </div>
    </body>
</html>
<!DOCTYPE html>
<head>
    <title>Let's calculate some things</title>
</head>
<body>
    <form action="" method="GET">
        <label>Input 1: <input type="number" name="num1" required/></label>
        <br/>
         <label>Operation:
            <br/>
            <input type="radio" name="op" value="x"/> Multiplication
            <br/>
            <input type="radio" name="op" value="/"/> Division
            <br/>
            <input type="radio" name="op" value="+"/> Addition
            <br/>
            <input type="radio" name="op" value="-"/> Subtraction
            <br/>
        </label>
        <br/>
       <label>Input 2: <input type="number" name="num2" required/></label>
        <br/>
        <br/>
        <button type="submit" value="Calculate!">Calculate!</button>
    </form>
        <br/>
        <br/>
        <h1>
    <?php
        $num1 = $_GET['num1'];
        $num2 = $_GET['num2'];
        $op = $_GET['op'];
        $result;
        
        if($op == "x"){
            $result = $num1 * $num2;
        } else if($op == "/"){
            if($num2 == 0) $result = "Please don't divide by 0.";
            else $result = $num1 / $num2;
        } else if($op == "+"){
            $result = $num1 + $num2;
        } else if($op == "-"){
            $result = $num1 - $num2;
        }
        
        printf("The result is: %s", htmlentities($result));
    ?>
    </h1>
</body>
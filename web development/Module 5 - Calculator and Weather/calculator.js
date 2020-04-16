window.onload = function(){
	document.getElementById('num1').addEventListener('keyup', update, false);
	document.getElementById('num2').addEventListener('keyup', update, false);
	document.getElementById('op+').addEventListener('change', update, false);
	document.getElementById('op-').addEventListener('change', update, false);
	document.getElementById('opx').addEventListener('change', update, false);
	document.getElementById('op/').addEventListener('change', update, false);
}

function update(){
	var x = document.getElementById('num1').value;
	var y = document.getElementById('num2').value;

	if(x && y && x != "" && y != "")
	var res = "TBD";
	if(document.getElementById('op+').checked){
		res = x + y;
	} else if(document.getElementById('op-').checked){
		res = x - y;
	} else if(document.getElementById('opx').checked){
		res = x * y;
	} else if(document.getElementById('op/').checked){
		if(y == 0){
			alert("don't divide by 0");
			res = "Don't divide by 0";
		} else{
			res = x / y;
		}
	}
	
	document.getElementById("res").textContent = res;
}

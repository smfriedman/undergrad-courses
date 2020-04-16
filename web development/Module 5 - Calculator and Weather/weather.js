function fetchWeather(){
	var url = "http://classes.engineering.wustl.edu/cse330/content/weather_json.php"
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.open("GET", url, true);
	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xmlHttp.addEventListener("load", cb, false);
	xmlHttp.send(null);
}

function cb(event){
	var data = JSON.parse(event.target.responseText);
	document.getElementsByClassName("weather-loc")[0].innerHTML = '<strong>' + data.location.city + ' </strong> ' + data.location.state;
	document.getElementsByClassName("weather-humidity")[0].innerHTML = data.atmosphere.humidity;
	document.getElementsByClassName("weather-temp")[0].innerHTML = data.current.temp ;
	document.getElementsByClassName("weather-tomorrow")[0].src = "http://us.yimg.com/i/us/nws/weather/gr/" + data.tomorrow.code + "ds.png";
	document.getElementsByClassName("weather-dayaftertomorrow")[0].src = "http://us.yimg.com/i/us/nws/weather/gr/" + data.dayafter.code + "ds.png";
}

document.addEventListener("DOMContentLoaded", fetchWeather, false);
window.onload = function(){
	document.getElementById('fetchWeather').addEventListener("click", fetchWeather, false);	
}
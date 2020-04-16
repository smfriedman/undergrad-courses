//month: 0-11
//day: 0-6
//date: 1-31

var now = new Date();
var curr_date = now;
//keep track of which cell corresponds to which day
var day_to_id = {};
for(var i = 1; i <= 31; i++){
	day_to_id[i] = null;
}

$(document).ready(init);

function init(){

	var urlVars = getUrlVars();
	if(!urlVars.share){
		$('#prev').on('click', function(e){
			//e.preventDefault();
			curr_date.setMonth(curr_date.getMonth() - 1);
			fillCalendar();
		});

		$('#next').on('click', function(e){
			//e.preventDefault();
			curr_date.setMonth(curr_date.getMonth() + 1);
			fillCalendar();
		});

		$('#create_btn').on('click', function(e){
			e.preventDefault();
			addEvent();
		});

		$('#share_btn').on('click', function(e){
			e.preventDefault();
			generateShareLink();
		});

		document.getElementById("all").addEventListener("change", fillCalendar);
		document.getElementById("social").addEventListener("change", fillCalendar);
		document.getElementById("work").addEventListener("change", fillCalendar);
		document.getElementById("family").addEventListener("change", fillCalendar);
		document.getElementById("misc").addEventListener("change", fillCalendar);
	} else{
		un = urlVars.user;
		curr_date.setMonth(urlVars.month);
		curr_date.setFullYear(urlVars.year);
		$('#login').addClass('hidden');
		$('#prev').addClass('hidden');
		$('#next').addClass('hidden');		
	}

	$('.datepicker').datepicker();	

	fillCalendar();
}


function fillCalendar(){
	fillMonths();
	fillDays();
	if(un && logged_in){ //logged in, show own cal
		showMyEvents();		
	} else if(un){	//not logged in, must be friends
		showFriendsEvents();
	}
}


function fillMonths(){
	var thisMonth;
	switch(curr_date.getMonth()){
		case 0:
			thisMonth = "January";
			break;
		case 1:
			thisMonth = "February";
			break;
		case 2:
			thisMonth = "March";
			break;
		case 3:
			thisMonth = "April";
			break;
		case 4:
			thisMonth = "May";
			break;
		case 5:
			thisMonth = "June";
			break;
		case 6:
			thisMonth = "July";
			break;
		case 7:
			thisMonth = "August";
			break;
		case 8:
			thisMonth = "September";
			break;
		case 9:
			thisMonth = "October";
			break;
		case 10:
			thisMonth = "November";
			break;
		case 11:
			thisMonth = "December";
			break;
		default:
			thisMonth = "Error";
	}

	//default to current month:
	$('#thisMonth').html(thisMonth + ', ' + curr_date.getFullYear());
}

function fillDays(){
	//clear all days first
	clearDays();

	curr_date.setDate(1); //go to first day of month
	//now fill in the calendary with dates
	var weekOfMonth = 0;
	do{
		var dayOfWeek = curr_date.getDay();
		day_to_id[curr_date.getDate()] = 'r' + weekOfMonth + 'd' + dayOfWeek;
		$('#' + day_to_id[curr_date.getDate()]).html('<p class="day_num">' + curr_date.getDate() + '</p>');
		if(dayOfWeek == 6) weekOfMonth++;
		curr_date.setDate(curr_date.getDate() + 1);
	} while (curr_date.getDate() != 1);
	curr_date.setMonth(curr_date.getMonth() - 1);
}

function clearDays(){
	$('td').html("");
	for(var i = 1; i <= 31; i++){
		day_to_id[i] = null;
	}
}


function showMyEvents(){
 	console.log("here");
	//query server for this users events for this month
	var tagCheck = null;
	if(document.getElementById("social").checked){
		tagCheck = "social";
	}else if(document.getElementById("work").checked){
		tagCheck = "work";
	}else if(document.getElementById("family").checked){
		tagCheck = "family";
	}else if(document.getElementById("misc").checked){
		tagCheck = "misc";
	}else{
		tagCheck = null;
	}

	if(un){
		var data = {
			token: token,
			username: un,
			tag: tagCheck,
			month: curr_date.getMonth(), //0-11
			year: curr_date.getFullYear()
		};

		$.post("get_events.php", data).done(function(res){
			console.log(res);
			res = JSON.parse(res);
			console.log(res);
			console.log(day_to_id);
			if(res && res.length > 0){
				for(var ev in res){
					ev = res[ev];
					if(tagCheck === null || ev.tag == tagCheck){
						var cell_id = '#' + day_to_id[ev.day];
						$(cell_id).html($(cell_id).html() + generateEventHTML(ev));						
					}
				}
				for(var ev in res){
					ev = res[ev];
					if(tagCheck === null || ev.tag == tagCheck){
						$('#' + ev.id + '>.delete_btn').on('click', function(e){
							e.preventDefault();
							deleteEvent(ev.id);
						});
						$('#' + ev.id + '>.edit_btn').on('click', function(e){
							e.preventDefault();
							allowEditing(ev.id, ev.eventName, ev.hour, ev.minute, data.year, data.month, ev.day);
						});
					}
				}				
			}
		});		
	}
 }

function htmlEntities(str){
	var container = document.createElement('div');
	var text = document.createTextNode(str);
	container.appendChild(text);
	return container.innerHTML;
}

function generateEventHTML(ev){
	if(ev.hour < 10) ev.hour = "0" + ev.hour;
	if(ev.minute < 10) ev.minute = "0" + ev.minute;
	var html = '';
	html 	+= '<div id="' + htmlEntities(ev.id) + '" class="event_div">';
	html 	+= 		'<p>' + htmlEntities(ev.hour) + ':' + htmlEntities(ev.minute) + '</p>';
	html 	+= 		'<p>' + htmlEntities(ev.eventName) + '</p>';
	if(ev.tag) html 	+= 		'<p class="ev_tag">' + htmlEntities(ev.tag) + '</p>';
	if(!getUrlVars().share){
		html 	+= 		'<button class="edit_btn">Edit</button>';
		html 	+= 		'<button class="delete_btn">Delete</button>';		
	}
	html 	+= '</div><br/>';
	return html;
}


function addEvent(){
	var ev_date = $('#ev_date').val();
	var dmy = ev_date.split('/');
	var ev_day = dmy[1];
	var ev_month = dmy[0] - 1;
	var ev_year = dmy[2];
	var ev_name = $('#ev_name').val();
	var ev_min = $('#ev_min').val();
	var ev_hour = $('#ev_hour').val();
	var ev_tag = $('#ev_tag').val();

	if(!ev_date || !ev_name || !ev_min || ! ev_hour){
		alert("Please fill out all fields properly");
		return;
	}

	if(un && token){
		var data = {
			token: token,
			username: un,
			ev_name: ev_name,
			ev_day: ev_day,
			ev_month: ev_month, //0-11
			ev_year: ev_year,
			ev_hour: ev_hour,
			ev_min: ev_min,
			ev_tag: ev_tag
		};

		$.post("add_event.php", data, function(res){
			res = JSON.parse(res);
			if(res.success){
				alert("Event added");
				//clear inputs and refill cal
				var ev_date = $('#ev_date').val("");
				var ev_name = $('#ev_name').val("");
				var ev_min = $('#ev_min').val("");
				var ev_hour = $('#ev_hour').val("");
				var ev_tag = $('#ev_tag').val();
				fillCalendar();
			} else{
				alert("Error adding event");
			}
		});	
	} 
}

function allowEditing(id, name, hour, minute, year, month, day){
	$('#edit_form').removeClass('hidden');
	$('#new_name').val(name);
	month = month + 1;
	if(month < 10) month = "0" + month; //is 0-11
	if(day < 10) day = "0" + day;
	if(hour < 10) hour = "0" + hour;
	if(minute < 10) minute = "0" + minute;
	$('#new_date').val(month + '/' + day + '/' + year);
	$('#new_hour').val(hour);
	$('#new_min').val(minute);

	$('#major_edit_btn').on('click', function(e){
		e.preventDefault();
		var dmy = $('#new_date').val().split('/');
		var data = {
			token: token,
			username: un,
			ev_id: id,
			ev_name: $('#new_name').val(),
			ev_hour: $('#new_hour').val(),
			ev_min: $('#new_min').val(),
			ev_day: dmy[1],
			ev_month: dmy[0] - 1,
			ev_year: dmy[2]
		};
		editEvent(data);
	});
}

function editEvent(data){
	if(un && token){
		$.post("edit_event.php", data, function(res){
			res = JSON.parse(res);
			console.log(res);
			if(res.success){
				alert("Event changed");
				$('#edit_form').addClass('hidden');
				fillCalendar();
			} else{
				alert("Error changing event");
			}
		});		
	}
}

function deleteEvent(id){
	if(un && token){
		var data = {
			username: un,
			token: token,
			ev_id: id
		};

		$.post("delete_event.php", data, function(res){
			res = JSON.parse(res);
			if(res.success){
				alert("Event deleted");
				fillCalendar();
			} else{
				alert("Error deleting event");
			}
		});		
	}
}
//everything below for sharing your calendar
//////////////////////////////////////////////////////////////////////////////////////////////
//for sharing
//src: http://papermashup.com/read-url-get-variables-withjavascript/
function getUrlVars() {
	var vars = {};
	var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
		vars[key] = value;
	});
	return vars;
}

function generateShareLink(){
	$.post("share_code.php", {username: un, token: token}, function(res){
		res = JSON.parse(res);
		console.log(res);
		if(res.success){
			var share_code = res.share_code;
			var url = "http://ec2-52-89-23-219.us-west-2.compute.amazonaws.com/~stevenfriedman/calendar/homepage.html";
			url+="?share=true";
			url+="&user=" + encodeURI(un);
			url+="&month=" + curr_date.getMonth();
			url+="&year=" + curr_date.getFullYear();	
			url+="&share_code=" + encodeURI(share_code);
			$('#share_link').html(htmlEntities(url));
		}else{
			alert("Error retrieving your share code");
		}
	});
}

function showFriendsEvents(){
	//query server for this users events for this month
	if(un){
		var urlVars = getUrlVars();
		var data = {
			share_code: urlVars.share_code,
			username: urlVars.user,
			month: urlVars.month, //0-11
			year: urlVars.year
		};

		$.post("get_events.php", data).done(function(res){
			res = JSON.parse(res);
			console.log(res);
			console.log(day_to_id);
			if(res && res.length > 0){
				for(var ev in res){
					ev = res[ev];
					var cell_id = '#' + day_to_id[ev.day];
					$(cell_id).html($(cell_id).html() + generateEventHTML(ev));
				}				
			}
		});		
	}
}














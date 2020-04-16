// Require the packages we will use:
var http = require("http"),
	socketio = require("socket.io"),
	fs = require("fs");
	_ = require("underscore");

var users = [];
var rooms = {
	"Main Lobby": {
		name: "Main Lobby",
		users: [],
		banned: [],
		creator: null,
	}
};

// Listen for HTTP connections.  This is essentially a miniature static file server that only serves our one file, client.html:
var app = http.createServer(function(req, resp){
	// This callback runs when a new connection is made to our HTTP server.
 
	fs.readFile("client.html", function(err, data){
		// This callback runs when the client.html file has been read from the filesystem.
 
		if(err) return resp.writeHead(500);
		resp.writeHead(200);
		resp.end(data);
	});
});
app.listen(3456);
 
// Do the Socket.IO magic:
var io = socketio.listen(app);
io.sockets.on("connection", function(socket){
	// This callback runs when a new Socket.IO connection is established.
 
	socket.on('message_to_server', function(data) {
		// This callback runs when the server receives a new message from the client.
 
		console.log("message: "+data["message"]); // log it to the Node.JS output
		if(data["priv"] && data["priv"] != "" && data['priv'] != null && !_.contains(users, data['priv'])){
			//if they're trying to send a private message to a nonexistant user
			socket.emit("failed_private_message", {res: "User does not exist"});
		} else{
			io.sockets.emit("message_to_client",{
				message:data["message"], 
				author:data["user"], 
				priv:data["priv"], 
				room:data["room"] 
			}); // broadcast the message to other users		
		}
	});

	socket.on('pic_to_server', function(data){
		io.sockets.emit("pic_to_client",{
				link:data["url"], 
				author:data["user"],  
				room:data["room"] 
			}); 
	});


	socket.on("new_sign_in", function(data) {
		//Callback runs when someone tries to sign in
	
		var user = data["user"];
		var check = false;
		for(i = 0; i < users.length; i++){
			if(users[i] == user){
			 check = true;
			 break;
			}
		}
		var logInResponse = " ";

		if(check == true){
			logInResponse = "Nickname is already taken. Please use a different name";
		}else{
			users.push(user);
			logInResponse = "You have been logged in";
		}
		//console.log(logInResponse);
		socket.emit("log_in_response", {response:logInResponse, loggedIn:check, rooms:rooms});

	});

	socket.on("log_out", function(data) {
		var name = data["user"]; 
		var i = users.indexOf(name);
		users.splice(i, 1);
		socket.emit("log_out_response", {});
	});

	//------------------ Code for chat rooms-----------------
	

	socket.on("new_room", function(data){
		var room = {
			name: data["roomName"],
			users: [],
			banned: [],
			creator: data["creator"]
		};
		if(rooms[room.name]){
			socket.emit("new_room_response", {success: false});
		} else{
			rooms[room.name] = room;
			socket.emit("new_room_response", {success: true, name: data["roomName"]});	
			io.sockets.emit("new_room_created", {name: data["roomName"], rooms: rooms});		
		}

	});

	socket.on("change_room", function(data){
		console.log(data);
		console.log(rooms[data['room']]);
		var old_room = rooms[data['oldRoom']];
		var room = rooms[data['room']];
		var user = data['user'];
		if(_.contains(room.banned, user)){
			socket.emit("change_room_response", {success: false, res: "You've been banned from this room"});
		} else{
			rooms[data['oldRoom']].users.splice(rooms[data['oldRoom']].users.indexOf(user), 1);
			rooms[data['room']]['users'].push(user);
			if(room.creator == user){
				socket.emit("change_room_response", {success: true, creator: true, room: room});
			} else{
				socket.emit("change_room_response", {success: true, creator: false, room: room});				
			}

			io.sockets.emit("someone_changed_rooms", {oldRoomName: old_room, newRoomName: room.name, user: user, room: room, oldRoom: old_room, boot: false, ban: false});
		}
	});

	socket.on("boot", function(data){
		if(data.user != rooms[data['roomName']].creator){
			socket.emit("boot_responose", {success: false, res: "Can't boot someone if you haven't created the room."});
		} else{
			rooms[data['roomName']].users.splice(rooms[data['roomName']].users.indexOf(data['booted_user']), 1);
			rooms['Main Lobby'].users.push(data['booted_user']);
			socket.emit("boot_responose", {success: true});
			io.sockets.emit("someone_changed_rooms", {oldRoomName: data['roomName'], newRoomName: "Main Lobby", user: data['booted_user'], room: rooms['Main Lobby'], oldRoom: rooms[data['roomName']], boot: true, ban: false});
			io.sockets.emit("someone_booted", {roomName: data['roomName'], room: rooms[data['roomName']], booted_user: data['booted_user']});
		}
	});

	socket.on("ban", function(data){
		if(data.user != rooms[data['roomName']].creator){
			socket.emit("boot_responose", {success: false, res: "Can't ban someone if you haven't created the room."});
		} else{
			rooms[data['roomName']].users.splice(rooms[data['roomName']].users.indexOf(data['banned_user']), 1);
			rooms[data['roomName']].banned.push(data['banned_user'])
			rooms['Main Lobby'].users.push(data['banned_user']);
			socket.emit("boot_responose", {success: true});
			io.sockets.emit("someone_changed_rooms", {oldRoomName: data['roomName'], newRoomName: "Main Lobby", user: data['banned_user'], room: rooms['Main Lobby'], oldRoom: rooms[data['roomName']], boot: false, ban: true});
			io.sockets.emit("someone_banned", {roomName: data['roomName'], room: rooms[data['roomName']], banned_user: data['banned_user']});
		}
	});
});
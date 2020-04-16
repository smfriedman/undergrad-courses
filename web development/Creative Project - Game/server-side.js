// Require the packages we will use:
var http = require("http"),
	socketio = require("socket.io");
var express = require('express');
var http = require('http');
var db = require('./db');

var app = express();
var server = http.createServer(app);
var io = socketio.listen(server);


var players = [];
var turn = null;
	if(players.length >= 0){
		turn = players[0];
	}

app.engine('html', require('ejs').renderFile);
app.set('view engine', 'html');


db.connect(function(err){
	if(err){
		console.log("Error connecting to db");
	} else{
		console.log("Connected to db");
		server.listen(3456, function(){
			console.log("Listening on 3456");
		});
	}


	//routing
	app.get('/', function(req, res){
		res.render('./client-side');
	});

	app.get('*', function(req, res){
		res.render('./client-side');
	});

	var myDb = db.get();
	//sockets
	io.sockets.on("connection", function(socket){


		socket.on('message_to_server', function(data) {
			// This callback runs when the server receives a new message from the client.
	 
			console.log("message: "+data.message); // log it to the Node.JS output
			
			
			
				io.sockets.emit("message_to_client",{
					message:data.message, 
					author:data.user
				}); // broadcast the message to other users		
			
		});

		socket.on("sign_in", function(data){
			if(!data.newUser){
				var news = false;
				var succ = null;
				myDb.collection("users").findOne({name: data.name, password:data.password}, function(err, result){
					if(!result){
						succ = false;
						io.sockets.emit("signin_to_client", {name:data.name, succ:succ, news:news});
					}else{
						succ = true;
						io.sockets.emit("signin_to_client", {name:data.name, succ:succ, news:news});
					}
				});
			}else{
				var succ = false;
				var news = true;

				myDb.collection("users").insert({
						name: data.name,
						password: data.password,
						wins: 0,
						losses: 0
					}, function(err, result){
						if(err) console.error(err);
						else succ = true;
						io.sockets.emit("signin_to_client", {name:data.name, succ:succ, news:news});
					});
				
				
			}
			players.push(data.name);
		});

		socket.on("challenge", function(data){
			io.sockets.emit("challenge_response", {challenged:data.challenged, caller:data.caller});
		});

		socket.on("roll", function(data){
			myDb.collection("users").findOne({name: data.name}, function(err, result){
				if(err) console.error(err);
				if(!result){
					io.sockets.emit("roll_to_client", {roll:data.number, name:data.name, wins: 0, losses: 0, opponent:data.opponent});
				} else{
					io.sockets.emit("roll_to_client", {roll:data.number, name:data.name, wins: result.wins, losses: result.losses, opponent:data.opponent});
				}
			});
		});

		socket.on("chanceCard", function(data){
			var count = myDb.collection("cards").count();
			var rand = Math.floor(Math.random() * count);

			myDb.collection("cards").find().limit(-1).skip(rand).next(function(error, result){	
				io.sockets.emit("card_to_client", {name:data.name, card:result, opponent:data.opponent});
			});
			
			
		});

		socket.on("win", function(data){
			//try to find winner in db, if they don't exist, add them
			myDb.collection("users").findOne({name: data.name}, function(err, result){
				if(err) console.error(err);
				if(!result){
					myDb.collection("users").insert({
						name: data.name,
						wins: 1,
						losses: 0
					}, function(err, result){
						if(err) console.error(err);
						else console.log("new user added with 1 win");
					});
				} else{
					result.wins++;
					myDb.collection("users").save(result);
				}
			});
			//let other player know
			io.sockets.emit("win_response", {winner:data.name});
		});

		socket.on("lose", function(data){
			//try to find loser in db, if they don't exist, add them
			myDb.collection("users").findOne({name: data.name}, function(err, result){
				if(err) console.error(err);
				if(!result){
					myDb.collection("users").insert({
						name: data.name,
						wins: 0,
						losses: 1
					}, function(err, result){
						if(err) console.error(err);
						else console.log("new user added with 1 loss");
					});
				} else{
					result.losses++;
					myDb.collection("users").save(result);
				}
			});		
		});
	});

});







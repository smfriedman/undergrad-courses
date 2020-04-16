var MongoClient = require('mongodb').MongoClient;

var myDb = null;

exports.connect = function(callback){
	if(myDb) return callback();

	MongoClient.connect('mongodb://localhost:27017/creativeGame', function(err, db){
		if (err) return callback(err);
		myDb = db;
		callback();
	});

};

exports.get = function(){
	return myDb;
};

exports.close = function(callback){
	myDb.close(function(err, res){
		myDb = null;
		callback(err);
	});
};




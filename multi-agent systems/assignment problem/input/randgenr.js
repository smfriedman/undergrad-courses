/**
*
* randgenr.js - a Node.js script to populate random data files for auctionAlgo.mod
* n = 256, M = 10^(1...7), 100 runs each (0-99)
* assumes # agents = # objects = n
* 
* stored in inputs[..]/m<M>/<run #>.txt
* files generated with:
* for i in 10 100 1000 10000 100000 1000000 10000000; do mkdir m$i; done
* for i in 10 100 1000 10000 100000 1000000 10000000; do for r in {0..99}; do touch m$i/$r.txt; done; done
*
*/
var fs = require('fs');
var async = require('async');

var n = 256;
var numruns = 100;

//create queue to limit number of open file streams to 10
var q = async.queue(function(fileInfo, callback){
	var ws = fs.createWriteStream(fileInfo.fileName);

	ws.once("open", function(fd){
		ws.write("data;\n\n");

		//create sets of agents and objects & create cols of utility matrix
		var setI = "set I := ";
		var setJ = "set J := ";
		var cols = "param v: ";
		for(var i = 0; i < n; i++){
			setI += ("A" + i + " ");
			cols += ("O" + i + " ");
			setJ += ("O" + i + " ");
		}
		setI += ";";
		setJ += ";";
		cols += ":=";

		ws.write(setI + "\n\n" + setJ + "\n\n" + cols);
		//create rows of utility matrix
		for(var i = 0; i < n; i++){
			var row = "A" + i;
			for(var j = 0; j < n; j++){
				var rand = Math.floor(Math.random() * fileInfo.M);
				row += (" " + rand);
			}
			ws.write("\n" + row);
		}
		ws.write(";");
		ws.end("\n\nend;");	
		callback();
	});
}, 10);

//push files onto queue
for(var M = 10; M <= 10000000; M *= 10){
	for(var r = 0; r < numruns; r++){
		//create file and write to file
		var fileName = "m" + M + "\/" + r + ".txt";
		q.push({fileName: fileName, M: M}, function(err){
			if(err) console.error(err);
			//console.log("done with " + fileName);
		});
	}	
}

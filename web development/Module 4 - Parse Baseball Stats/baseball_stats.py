import sys, os
import re
import operator

#check that multiple file arguments
if len(sys.argv) < 2:
	sys.exit("Usage: %s filename" % sys.argv[0])
 
filename = sys.argv[1]
 
if not os.path.exists(filename):
	sys.exit("Error: File '%s' not found" % sys.argv[1])


#define reg exp and match only lines with stats (uniformly formatted)
def check_line(text):
	stats_regex = re.compile(r"\b\w*\b \b\w*\b batted \d times with \d hits and \d runs")
	match = stats_regex.match(text)
	if match is not None:
		return match.group(0)
	else:
		return False

#pull stats out of a matched line
stats = {};
def get_stats(match):
	match = match.split(" ")
	fullName = match[0] + " " + match[1]
	#if player is not in dictionary, add them with these stats to start
	if(not(fullName in stats)):
		stats[fullName] = {
			'firstName': match[0],
			'lastName': match[1],
			'atBats': float(match[3]),
			'hits': float(match[6]),
			'runs': float(match[9]),
			'batAvg': float(match[6])/float(match[3])
		}
	#if player is in dictionary, add these stats to existing ones, recalculate batting avg
	else:
		stats[fullName]['atBats'] += float(match[3])
		stats[fullName]['hits'] += float(match[6])
		stats[fullName]['runs'] += float(match[9])
		stats[fullName]['batAvg'] = stats[fullName]['hits']/stats[fullName]['atBats']


#read in file and print stats
f = open(filename)
for line in f:
	text = line.rstrip()
	match = check_line(text)
	if(match):
		get_stats(match)

#rearrange into smaller dictionary, so it's easier to sort
batAvgs = {}
for player, info in stats.iteritems():
	batAvgs[player] = info['batAvg']

#sort and print
for stat in sorted(batAvgs.items(), key=operator.itemgetter(1), reverse=True):
	(name, batAvg) = stat #this sorted returns tuples, unpack tuple
 	print name + ': ' + '{:.3f}'.format(round(batAvg, 3)) #round and print


f.close()










#! /bin/bash
# assignmentIter.sh - feed assignmentLP.mod randomly generated datafiles
# and output time elapsed for each M: 10^(1...7)

for M in 10 100 1000 10000 100000 1000000 10000000
do
	echo "Starting $M. Total time elapsed: "
	time {
		for r in {0..99}
		do
			glpsol --math -m assignmentLP.mod -d ../input/m$M/$r.txt >> verbosity$M.log
		done
	}
done

# assignmentLP.mod
# Auction algo solver in GMPL
# Run using glpsol with --math flag
# Requires data input

set I;
/* agents */

set J;
/* objects */

param v{i in I, j in J};
/* value of object j to agent i */

var x{i in I, j in J} >= 0;
/* assignment is 0 or 1, non-negative (add "binary"?)*/

maximize value: sum{i in I, j in J} v[i,j] * x[i,j];
/* assign for max total utility */

s.t. c1{j in J}: sum{i in I} x[i,j], <= 1;
s.t. c2{i in I}: sum{j in J} x[i,j], <= 1;
/* one to one assignments */

solve;

#printf{i in I, j in J}: if x[i,j] > 0 then "%s: %s\n" else "", i, j;
#printf "Value: %d\n", sum{i in I, j in J} if x[i,j] > 0 then v[i,j];

end;

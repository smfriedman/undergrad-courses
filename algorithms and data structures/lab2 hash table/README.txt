Steven Friedman
Lab 2

___________________________________________________________________________________________________

PART 1

My original constructor created a hash table of size 2^k, where k is the first int for which
2^k > maxSize*4 in order to keep the load factor below 1/4 and to have an efficient step hash
function (an function which just produces odd numbers is efficient with 2^k slots).  Additionally,
a "deleted" record is created as a placeholder for deleted records.  "0000" will not occur in any 
DNA sequences that are used at input, so it should be unique, and it proved unique in test cases.

The insert method finds the base hash value, then goes through each slot in the table, incrementing
by the step hash value (modulo M as well).  If the slot is null or deleted, set that slot to the
input record and return true.  Also, change the record index variable (which I added to the record 
class to make deleting records easier--I was having trouble storing the index of the record in 
positions) to indicate where it is.  If the slot contains the record already, return false and don't
insert twice.  If the index is equal to the first index that you checked, you've come full circle
and haven't found an open slot, which means the hash table is full or the hash function doesn't 
check all the slots.  Either way, you can't insert, return false.

The delete method takes the records index variable and changes that number slot to the deleted
record.  The index returns to the default of -1 (since this index can't exist in the table).  

The find method operates similarly to the insert method.  It finds the base hash value and cycles
through the table, incrementing by the step hash value until it finds a null slot (in which case
it returns null, since the key is not in the table), a slot with the same key (in which case it
returns that record), or the first slot it searches, i.e. the base hash index (in which case it
returns null, since it checked every possible slot but couldn't find the key).

Hash functions:
base: (int) Math.floor((M * (A * hashKey - Math.floor(A * hashKey))))
step: (int) Math.floor((M * (A * hashKey - Math.floor(A * hashKey)))) [+1 if even]

___________________________________________________________________________________________________

PART 2

HASH VALUES
I added a hashValue variable to the Record class (as well as a getter and a setter) and set the
default value to -1, since toHashValue(String s) produces only positive numbers.  In the constructor,
the "deleted" record has its hashValue set in order to make comparisons.  Each time a record is 
inserted, it has its hashValue set, and when a record is removed, its hashValue is set back to default
(-1).  For all comparisons of records or keys, hashValues are used first, then keys or records themselves 
if the comparison is successful.

DOUBLING
I tabbed out the original array created and a couple steps to get there.  Now the constructor creates 
an array of size 2.  I created a new instance variable called occupiedSlots to help keep track of load
factor.  It's initialized at 0.  Every time a new record is inserted, occupiedSlots increases by 1,
and if the load factor is over 1/4, it calls the sizeDouble(Record r) method and doesn't insert r yet. 
sizeDouble creates a copy of the current table, creates a new table of double the size and doubles M, and 
rehashes the existing elements into the new table using the existing insert method.  Since the instance 
variables,M and table, are now redefined, it rehashes correctly.  The original record input to insert is 
now inserted again as part of the sizeDouble method.  This is necessary because if r were inserted in the 
insert method after doubling the table, it might replace another record already in the table, because its
index was hashed on the old function with what is now M/2.  If it were inserted before doubling, the load
factor would exceed 1/4.  I was on the fence about decreasing the number of occupied slots when a record
was deleted, but I decided not to decrease it, since deleted slots will still slow down searches.
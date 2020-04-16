//
// RECORD.JAVA
// Record type for string hash table
//
// A record associates a certain string (the key value) with 
// a list of sequence positions at which that string occurs.
//

import java.util.*;

public class Record {
    public String key;
    public ArrayList<Integer> positions;
    public int index;
    public int hashValue;
    
    public Record(String s)
    {
	key = s;
	positions = new ArrayList<Integer>(1);
	index = -1;
	hashValue = -1;
    }

	public int getHashValue() {
		return hashValue;
	}

	public void setHashValue(int hashValue) {
		this.hashValue = hashValue;
	}
    
}

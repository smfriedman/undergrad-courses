//
// EVENTLIST.JAVA
// Skeleton code for your EventList collection type.
//
import java.util.*;

class EventList {
    
    Random randseq;
    Pillar head;
    Pillar tail;
    int t;		//necessary for extension 1 (extensible head and tail pointers)
    
    ////////////////////////////////////////////////////////////////////
    // Here's a suitable geometric random number generator for choosing
    // pillar heights.  We use Java's ability to generate random booleans
    // to simulate coin flips.
    ////////////////////////////////////////////////////////////////////
    
    int randomHeight()
    {
	int v = 1;
	while (randseq.nextBoolean()) { v++; }
	return v;
    }

    
    //
    // Constructor
    //
    public EventList()
    {
	randseq = new Random(9989); // You may seed the PRNG however you like.
	
	head = new Pillar(new Event(-100000, "head"), 1);
	tail = new Pillar(new Event(100000, "tail"), 1);
	for(int i = 0; i < head.height; i++){
		head.tower[i].next = tail.tower[i];
	}
    }

    
    //
    // Add an Event to the list.
    //
    public void insert(Event e)
    {
    t = randomHeight();
    if (t >= head.height) doubleEnds();
    Pillar p = new Pillar(e, t);
    int l = head.height - 1;
    Pillar x = head;
    while(l >= 0){
    	Node y = x.tower[l].next;
    	if(y.pillar.event.year < e.year){
    		x = y.pillar;
    	} else {
    		if (l < t){
    			Node z = new Node(p, y);
	    		x.tower[l].next = z;
	    		p.tower[l] = z;
    		} 
    		l--;	
    	}
    }
    }

    
    //
    // Remove all Events in the list with the specified year.
    //
    public void remove(int year)
    {
        int l = head.height - 1;
        Pillar x = head;
        Pillar first = head;	//right before first occurence of year
        boolean yearHappens = true;
        boolean start = false;
        boolean end = false;
        while(l>=0){
        	yearHappens = true;
        	start = false;
        	end = false;
        	while(yearHappens && (!start || !end)){
            	Node y = x.tower[l].next;
            	Node z = first.tower[l].next;
            	if (x.event.year == year && y.pillar.event.year > year){
            		end = true;
            	} else if (y.pillar.event.year <= year){
        			x = y.pillar;
        		} else {
        			yearHappens = false;
        		}
            	
            	if (z.pillar.event.year < year){
            		first = z.pillar;
            	} else if (z.pillar.event.year == year){
            		start = true;
            	} else {
            		yearHappens = false;
            	}
        	}
        	if(yearHappens){
        		first.tower[l].next = x.tower[l].next;
        	}
        	l--;
        }
    }
    
    //
    // Find all events with greatest year <= input year
    //
    public Event [] findMostRecent(int year)
    {
	int l = head.height - 1;
	Pillar x = head;
	Pillar xprev = head;	//pillar before x
	Pillar first = head;	//first occurence of a certain year (to keep track of the last one before year)
	while(l >= 0){
		Node y = x.tower[l].next;
   		if(year == y.pillar.event.year){
   			xprev = x;
   			x = y.pillar;
   			break;
   		} else if (year > y.pillar.event.year){
   			if(y.pillar.event.year != x.event.year){
   				first = x;
   			}	
   			xprev = x;
   			x = y.pillar;	
   		} else {
   			l--;
   		}
	}
	if(xprev.event.year == x.event.year){
		xprev = first;
	}
	while(xprev.event.year < x.event.year){
		xprev = xprev.tower[0].next.pillar;
	}
	x = xprev.tower[0].pillar;
	int count = 0;
	while(xprev.event.year == x.event.year){
		count++;
		xprev = xprev.tower[0].next.pillar;
	}
	Event[] array = new Event[count];
	for(int i = 0; i < count; i++){
		array[i] = x.event;
		x = x.tower[0].next.pillar;
	}
	if(array[0] == head.event) return null;
	return array;

    }
    
    
    //
    // Find all Events within the specific range of years (inclusive).
    //
    public Event [] findRange(int first, int last)
    {
    	int l = head.height - 1;
    	Pillar start = head;
    	Pillar startPrev = head;
    	while(l >= 0){
    		Node y = start.tower[l].next;
       		if(first == y.pillar.event.year){
       			startPrev = start;
       			start = y.pillar;
       			break;
       		} else if (first > y.pillar.event.year){
       			startPrev = start;
       			start = y.pillar;
       		} else {
       			l--;
       		}
    	}

    	while(startPrev.event.year < first){
    		startPrev = startPrev.tower[0].next.pillar;
    	}
    	start = startPrev;
    	int count = 0;
    	while(startPrev.event.year <= last){
    		count++;
    		startPrev = startPrev.tower[0].next.pillar;
    	}
    	Event[] array = new Event[count];
    	for(int i = 0; i < count; i++){
    		array[i] = start.event;
    		start = start.tower[0].next.pillar;
    	}
    	return array;	

    }
    
    /**
     * this method contains the code for the first
     * extension (extensible head and tail pointers)
     * to make insert cleaner
     */
    public void doubleEnds(){
    	int h = head.height;
    	while(h <= t){
    		h = h + h;
    	}
    	Pillar headTemp = new Pillar(new Event(-100000, "head"), h);
    	Pillar tailTemp = new Pillar(new Event(100000, "tail"), h);
    	for(int i=0; i < head.height; i++){
    		headTemp.tower[i].next = head.tower[i].next;
    	}
    	for(int i = head.height; i < h; i++){
    		headTemp.tower[i].next = tailTemp.tower[i];
    	}
    	head = headTemp;
    	tail = tailTemp;
    }
}

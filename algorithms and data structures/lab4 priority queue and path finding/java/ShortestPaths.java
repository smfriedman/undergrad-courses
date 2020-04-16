
//
// SHORTESTPATHS.JAVA
// Compute shortest paths in a graph.
//
// Your constructor should compute the actual shortest paths and
// maintain all the information needed to reconstruct them.  The
// returnPath() function should use this information to return the
// appropriate path of edge ID's from the start to the given end.
//
// Note that the start and end ID's should be mapped to vertices using
// the graph's get() function.
//
// You can ignore the input and startTime arguments to the constructor
// unless you are doing the extra credit.
//
class ShortestPaths {
    
	private PriorityQueue<Vertex> pq;
	private VertexInfo[] vi;
	private int startID;
    //
    // constructor
    //
    public ShortestPaths(Multigraph G, int startId, 
			 Input input, int startTime) 
    {
    	int n = G.nVertices();
    	startID = startId;
    	pq = new PriorityQueue<Vertex>();
    	vi = new VertexInfo[n];
    	
    	for(int i = 0; i < n; i++){
    			vi[i] = new VertexInfo(pq.insert(Integer.MAX_VALUE, G.get(i)), null, -1);
    	}
    	pq.decreaseKey(vi[startID].getHandle(), 0);
    	
    	while(!pq.isEmpty()){
    		int uID = pq.min();
    		if(uID == Integer.MAX_VALUE) break;

    		Vertex u = pq.extractMin();
    		Vertex.EdgeIterator ei = u.adj();
    		while(ei.hasNext()){
    			Edge e = ei.next();
    			Vertex v = e.to();
    			if(pq.decreaseKey(vi[v.id()].getHandle(), uID + e.weight())){
    				vi[v.id()].setParent(u);
    				vi[v.id()].setParentEdgeID(e.id());
    			}
    		}
    		
    	}
    }
    
    //
    // returnPath()
    // Return an array containing a list of edge ID's forming
    // a shortest path from the start vertex to the specified
    // end vertex.
    //
    public int [] returnPath(int endId) 
    { 
    	if(vi[endId].getParent() == null){
    		int empty[] = new int[0];
    		return empty;
    	}
    	
    	int i = 1;
    	Vertex w = vi[endId].getParent();
    	while(w.id() != startID){
    		w = vi[w.id()].getParent();
    		i++;
    	}

    	int[] result = new int[i];
    	int index = endId;
    	for(int j = i-1; j >= 0; j--){
    		result[j] = vi[index].getParentEdgeID();
    		index = vi[index].getParent().id();
    	}
    	
    	return result;
    }
}
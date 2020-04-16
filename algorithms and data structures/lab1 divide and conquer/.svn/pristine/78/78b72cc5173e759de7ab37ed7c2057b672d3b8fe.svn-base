/**
 * 
 * @author stevenfriedman
 * implements the divide and conquer algorithm for finding the two closest points
 */
public class ClosestPairDC {
    
    public final static double INF = java.lang.Double.POSITIVE_INFINITY;
    //create instance variables to store points and distance without losing them in recursion
    private static XYPoint p1 = null;
    private static XYPoint p2 = null;
    private static double minDist = INF;
    //
    // findClosestPair()
    //
    // Given a collection of nPoints points, find and ***print***
    //  * the closest pair of points
    //  * the distance between them
    // in the form "(x1, y1) (x2, y2) distance"
    //
    
    // INPUTS:
    //  - points sorted in nondecreasing order by X coordinate
    //  - points sorted in nondecreasing order by Y coordinate
    //
    
    /**
     * calls other method to find the closest pair and prints
     * @param pointsByX
     * @param pointsByY
     */
    public static void findClosestPair(XYPoint pointsByX[], 
				       XYPoint pointsByY[])
    {
	
    //call helper
    helper(pointsByX, pointsByY);
	
    //check order
    if (p2.isLeftOf(p1)){
		XYPoint temp = p1;
		p1 = p2;
		p2 = temp;
	}
	
    //print
	System.out.println("(" + p1.x + ", " + p1.y + ") " + "(" + p2.x + ", " + p2.y + ") " + minDist);
    }
    
    /**
     * recursive helper method to findClosestPair(pointsByX, pointsByY).
     * splits set of points into two subcases on each side and compares
     * the points in the center.
     * @param pointsByX
     * @param pointsByY
     * @return
     */
    public static double helper(XYPoint pointsByX[], 
			       XYPoint pointsByY[])
    {
	int nPoints = pointsByX.length;
    
	//base cases: 1 point--> can't compare for distance
    if(nPoints == 1) return INF;
    //2 points-->compare distance to current minDist and return distance. if smaller, change p1, p2, minDist
    if(nPoints == 2) {  
    	double distance = pointsByX[0].dist(pointsByX[1]);
    	if (distance < minDist){
    		p1 = pointsByX[0];
    		p2 = pointsByX[1];
    		minDist = distance;
    	}
    	return distance;
    }
	
    //divide in the middle (x) and place into 4 new arrays
    int mid = (int) (Math.ceil(nPoints/2.0) - 1);
    
    XYPoint[] XL = new XYPoint[mid];
    XYPoint[] YL = new XYPoint[mid];			
    XYPoint[] XR = new XYPoint[nPoints - mid];
    XYPoint[] YR = new XYPoint[nPoints - mid];
    
    //fill XL and XR based on order of original pointsByX
    for(int i=0; i<mid; i++){
    	XL[i] = pointsByX[i];
    }
    
    for(int i=mid; i<nPoints; i++){
    	XR[i-mid] = pointsByX[i];
    }
    
    //fill YL with the same points as XL and YR and YL but in Y order
    int a = 0;
    int b = 0;
    while(a < mid || b < nPoints-mid){
    	for(int i=0; i<nPoints; i++){
    		if(pointsByY[i].isLeftOf(XR[0])){
    			YL[a] = pointsByY[i];
    			a++;
    		}
    		else{
    			YR[b] = pointsByY[i];
    			b++;
    		}
    	}
    }

    //2 subcases: repeat on left and right
    double distL = helper(XL, YL);
    double distR = helper(XR, YR);
    double lrDist = Math.min(distL, distR);
    
    //find out how many points in middle should be compared (should be in yStrip)
    int c = 0;
    for(int i=0; i < nPoints; i++){
    	if(Math.abs(pointsByY[i].x - XR[0].x) < lrDist){
    		c++;
    	}
    }
    
    //create array of this size and fill it with the points within lrDist
    XYPoint[] yStrip = new XYPoint[c];

    int d = 0;
    for(int i=0; i < nPoints; i++){
    	if(Math.abs(pointsByY[i].x - XR[0].x) < lrDist){
    		yStrip[d] = pointsByY[i];
    		d++;
    	}
    }

    //create local variables for this recursive level to compare to the instance variables
    double localMinDist = lrDist;
    XYPoint p1local = null;
    XYPoint p2local = null;
    
    //iterate through points in yStrip
    for(int i=0; i<= c-2; i++){
    	int j=i+1; 
    		while(j <= c-1 && yStrip[j].y - yStrip[i].y < lrDist){
    		double dist = yStrip[j].dist(yStrip[i]);
    		if(dist < localMinDist){
    			localMinDist = dist;
    			p1local = yStrip[i];
    			p2local = yStrip[j];
    		}
    		j++;
    	}

    }

    //find minimum of minDist and this minDist
    if(localMinDist < minDist){
    	minDist = localMinDist;
    	p1 = p1local;
    	p2 = p2local;
    }
    
    return localMinDist;

    }
}

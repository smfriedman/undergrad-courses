/**
 * 
 * @author stevenfriedman
 * implements the naive algorithm for finding the two closest points
 */
public class ClosestPairNaive {
    
    public final static double INF = java.lang.Double.POSITIVE_INFINITY;
    
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
     * 
     * @param points
     * prints two closest points and min dist
     */
    public static void findClosestPair(XYPoint points[])
    {
	int nPoints = points.length;
	//create variables to store points and distance
	XYPoint p1 = null;
	XYPoint p2 = null;
	double minDist = INF;
	
	//iterate through each point and compare it to every point indexed after
	//record closest and their distance
	for(int j = 0; j <= nPoints-2; j++){
		for(int k = j+1; k <= nPoints-1; k++){
			double d = points[j].dist(points[k]);
			if(d < minDist){
				minDist = d;
				p1 = points[j];
				p2 = points[k];
			}
		}
	}
	
	//check for proper order
	if(p2.isLeftOf(p1)){
		XYPoint temp = p1;
		p1 = p2;
		p2 = temp;
	}
	
	//print
	System.out.println("(" + p1.x + ", " + p1.y + ") " + "(" + p2.x + ", " + p2.y + ") " + minDist);
    }
}

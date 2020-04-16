package HW3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class InstantRunoff {
	static int VOTERCOUNT = 2487;
	public static void main(String args[]){
		LinkedList<Integer> eliminated = new LinkedList<Integer>();
		int[] candidates = runoffCount(eliminated);
		int runoffCount = 0;
		
		while(winner(candidates) == -1){
			System.out.println("Runnoff # " + ++runoffCount);
			eliminated.push(lastPos(candidates));
			candidates = runoffCount(eliminated);
		}

		for(int i = 0; i < candidates.length; ++i){
			System.out.println(i + ": " + candidates[i]);
		}
		System.out.println("Winner is " + winner(candidates));
	}
	
	public static int[] runoffCount(LinkedList<Integer> eliminated){
		/* extra 0 index candidate to avoid messiness elsewhere */ 
		int[] candidates = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		try{
			File input = new File("/Users/stevenfriedman/Documents/workspace/CSE516/src/HW3/reduced.toi");
			FileReader fr = new FileReader(input);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] prefs = line.split(",");
				int count = Integer.parseInt(prefs[0]);
				for(int i = 1; i < prefs.length; ++i){
					int favorite = Integer.parseInt(prefs[i]);
					if(!eliminated.contains(favorite)){
						candidates[favorite] = candidates[favorite] + count;					
						break;
					}
				}
			}
			fr.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		return candidates;
	}
	
	public static int winner(int[] candidates){
		int winner = -1;
		int winCount = -1;
		for(int i = 0; i < candidates.length; ++i){
			if(winCount < candidates[i]){
				winner = i;
				winCount = candidates[i];
			}
		}
		
		if (winCount < Math.ceil(VOTERCOUNT / 2.0))
			return -1;
		else
			return winner;
	}
	
	public static int lastPos(int[] candidates){
		int lastPos = -1;
		int lastPosCount = VOTERCOUNT;
		for(int i = 0; i < candidates.length; ++i){
			if(candidates[i] < lastPosCount && candidates[i] > 0){
				lastPos = i;
				lastPosCount = candidates[i];
			}
		}
		return lastPos;
	}

}

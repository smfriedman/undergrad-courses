package HW3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Plurality {
	
	public static void main(String args[]){
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
				int favorite = Integer.parseInt(prefs[1]);
				candidates[favorite] = candidates[favorite] + count;
			}
			fr.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		int winner = -1;
		int winCount = -1;
		for(int i = 0; i < candidates.length; ++i){
			if(winCount < candidates[i]){
				winner = i;
				winCount = candidates[i];
			}
			System.out.println(i + ": " + candidates[i]);
		}
		System.out.println("Winner is " + winner);
	}
}

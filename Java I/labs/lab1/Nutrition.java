package lab1;

import cse131.ArgsProcessor;

public class Nutrition {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArgsProcessor ap = new ArgsProcessor (args);
		String name = ap.nextString("What is the name of this food?");
		double carbs = ap.nextDouble("How many grams of carbohydrates?");
		double fat = ap.nextDouble("How many grams of fat?");
		double protein = ap.nextDouble("How many grams of protein?");
		double statedCals = ap.nextDouble("How many calories are on the label?");
		
		double carbsCals = 4 * carbs;
		double proteinCals = 4 * protein;
		double fatCals = 9 * fat;
		
		System.out.println(name + " has");
		System.out.println("	" + carbs + "grams of carbohydrates = " + (Math.round(carbsCals * 10.0) / 10.0) + " Calories");
		System.out.println("	" + protein + "grams of protein = " + (Math.round(proteinCals * 10.0) / 10.0) + " Calories");
		System.out.println("	" + fat + "grams of fat = " + (Math.round(fatCals * 10.0) / 10.0) + " Calories");
		System.out.println("");
		
		
		double totalCals = carbsCals + proteinCals + fatCals;
		double fiber = (totalCals - statedCals) / 4.0;
		
		System.out.println("This food is said to have " + statedCals + " (available) Calories");
		System.out.println("With " + (Math.round((totalCals - statedCals) * 10.0) / 10.0) + " unavailable Calories, this food has " + (Math.round(fiber * 10.0) / 10.0) + " grams of fiber");
		System.out.println("");
		
		double carbPct = (carbsCals / statedCals) * 100.0;
		double fatPct = (fatCals / statedCals) * 100.0;
		double proteinPct = (proteinCals / statedCals) * 100.0;
		
		System.out.println("Approximately");
		System.out.println("	" + (Math.round(carbPct * 10.0) / 10.0) + "% of your food is carbohydrates");
		System.out.println("	" + (Math.round(fatPct * 10.0) / 10.0) + "% of your food is fat");
		System.out.println("	" + (Math.round(proteinPct * 10.0) / 10.0) + "% of your food is protein");
		System.out.println("");
		
		boolean lowCarb = carbPct <= 25.0;
		boolean lowFat = fatPct <= 15.0;
		double r = Math.random();
		boolean heads = r <= 0.5;
		
		System.out.print("Is this food acceptable for a low-carb diet? ");
		System.out.println(lowCarb);
		System.out.print("Is this food acceptable for a low-fat diet? ");
		System.out.println(lowFat);
		System.out.print("By coin flip, should you eat this food? ");
		System.out.println(heads);
		
		
		
	}

}

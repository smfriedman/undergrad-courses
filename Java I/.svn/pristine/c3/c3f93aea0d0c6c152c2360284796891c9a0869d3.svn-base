package biofindframe;

import java.io.File;

import org.biojava3.core.sequence.DNASequence;

import biojava.SequenceLoader;
import cse131.ArgsProcessor;

public class FindTheFrame {
	
	//
	// Do not change any code from here....
	//

	public static void main(String[] args) {
		File file = ArgsProcessor.chooseFile("genomes");
		System.out.println("For " + file + ", best reading frame is " + runsolution(file));
	}
	
	public static int runsolution(File file) {
		//
		// Load the sequence into a DNASequence object
		//
		SequenceLoader sequenceLoader = new SequenceLoader();
		DNASequence dnaSequence = sequenceLoader.loadDNASequence(file);
		
		//
		// Convert the sequence to a string and then a char array
		//
		String dnaAsString = dnaSequence.getSequenceAsString().toUpperCase();
		char[] dnaAsCharArray = dnaAsString.toCharArray();
		
		//
		// Call your solution to compute the result
		//
		int frame = bestReadingFrame(dnaAsCharArray);
		
		return frame;
	}
	
	//
	// ... to here, so we can unit test your solution
	//
	
	/**
	 * 
	 * @param dna an array of char.  Each element is a nucleotide:  one of A, T, C, or G.
	 * @return the index at which the best reading frame occurs.  This would be 0, 1, or 2.
	 */
	public static int bestReadingFrame(char[] dna) {
		

		// Below, define each of the three Stop Codons as a separate array of char,
		//     named ochre, amber, and opal
		//     See http://en.wikipedia.org/wiki/Genetic_code#Start.2Fstop_codons

		
		
		// Below, define the Start Codon (Methionine) as an array of char
		//Start Codon
		
		int ans = -1;  // returned if no appropriate sequences was found
		
		//
		// Follow the instructions in the extension write up
		//
		
		
		return ans;
	}
}

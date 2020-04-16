package lab2;

import java.util.LinkedList;

import lab2.Scanner;
import lab2.autogen.*;
import java_cup.runtime.*;
import common.Listing;

/**

QUESTIONS:

 * Answer to question 1:
 * 
		package lab2.autogen;
		import java_cup.runtime.*;
		import lab2.IntPair;
		import common.Listing;
		import java.util.*;

		terminal Integer  number;
		terminal         plus, minus, sum, product, times, negate, mean;
		terminal         lparen, rparen;
		
		non terminal Integer  				Program, File, Lists, Lists2, List, Expression;
		non terminal Integer  				Operand1, Operand2, Atom, Operand;
		non terminal LinkedList<Integer> 	Operands, Operands2;
		
		start with Program;
		
		Program ::= File
			;
		
		File   
			::= Lists
			;
			
		Lists
			::= List Lists2
			;
		
		Lists2
			::= List Lists2
			|	// lambda 
			;
				
		List
			::= lparen Expression rparen
			;
		
		Expression
			::= plus    Operand1 Operand2
			|   minus   Operand1 Operand2
			|   times   Operand1 Operand2
			|   negate  Operand
			|   sum     Operands
			|   product Operands
			|   mean    Operands
			;
		
		Operand1
			::= Atom
			;
		
		Operand2
			::= Atom
			;
		
		Operand
			::= Atom
			;
		
		Operands
			::= Operand Operands2
			;
		
		Operands2
			::= Operand Operands2
			| // lambda 
			;
		
		Atom
			::= List
			|   number
			;

 * Answer to question 2:
 
 		First(Program) = {lparen}
 		Follow(Program) = {EOF ($)}
 		
 		First(File) = {lparen}
 		Follow(File) = {EOF ($)}
 		
 		First(Lists) = {lparen}
 		Follow(Lists) = {EOF ($)}
		
		First(Lists2) = {lparen, *lambda*}
		Folow(Lists2) = {EOF ($)}
		
		First(List) = {lparen}
		Follow(List) = {rparen, number, lparen, EOF ($)}
		
		First(Expression) = {plus, minus, times, negate, sum, product, mean}
		Follow(Expression) = {rparen}
		
		First(Atom) = {lparen, number}
		Follow(Atom) = {rparen, number, lparen}
		
		First(Operand) = {lparen, number}
		Follow(Operand) = {rparen, number, lparen}
		
		First(Operand1) = {lparen, number}
		Follow(Operand1) = {number, lparen}	
		
		First(Operand2) = {lparen, number}
		Follow(Operand2) = {rparen}
		
		First(Operands) = {number, lparen}
		Follow(Operands) = {rparen}
		
		First(Operands2) = {number, lparen, *lambda*}
		Follow(Operands2) = {rparen}

 * Answer to question 4:
		
		I used a LinkedList to pass up the series of operands that followed sum, product, and mean.
		For mean, I divided the sum of the elements by the size of the list. I treated the result as
		an integer, rounded towards 0 as per int division.

 */

public class RecursiveDescent {

	public RecursiveDescent() {
		Scanner.init();
	
		Program();	
	}

	void Program(){
		int peek = peek();
		
		if (peek == sym.lparen) File();
		else oops("Program encountered unexpected symbol");
	}
	
	void File(){
		int peek = peek();
		
		if (peek == sym.lparen) Lists();	
		else oops("File encountered unexpected symbol");
	}
	
	void Lists(){
		int peek = peek();
		
		if(peek == sym.lparen){
			int result = List();
			Listing.get().EmitMessage("Result: " + result);
			Lists2();
		}
		else oops("Lists encountered unexpected symbol");
		
	}
	
	int List(){
		int peek = peek();
		
		if(peek == sym.lparen){
			expect(sym.lparen);
			int result = Expression();
			expect(sym.rparen);
			return result;
		}
		else oops("List encountered unexpected symbol");	
		
		return 0;
	}
	
	void Lists2(){
		int peek = peek();
		
		if(peek == sym.lparen){
			int result = List();
			Listing.get().EmitMessage("Result: " + result);
			Lists2();
		} else if(peek == sym.EOF){ /*lambda case: follow*/
			return;
		}
		else oops("Lists2 encountered unexpected symbol");	
	}
	
	int Expression(){
		int peek = peek();
		
		if(peek == sym.plus){
			expect(sym.plus);
			return Operand1() + Operand2();
			
		} else if(peek == sym.minus){
			expect(sym.minus);
			return Operand1() - Operand2();
			
		} else if(peek == sym.times){
			expect(sym.times);
			return Operand1() * Operand2();
			
		} else if(peek == sym.negate){
			expect(sym.negate);
			return 0 - Operand();
			
		} else if(peek == sym.sum){
			expect(sym.sum);
			LinkedList<Integer> ops = Operands();
			int count = ops.size();
			int total = 0;
			for(int i = 0; i < count; i++){
				total += ops.pop();
			}
			return total;
			
		} else if(peek == sym.product){
			expect(sym.product);
			LinkedList<Integer> ops = Operands();
			int count = ops.size();
			int total = 0;
			if (count > 0) total = ops.pop();
			for(int i = 1; i < count; i++){
				total *= ops.pop();
			}
			return total;
			
		} else if(peek == sym.mean){	
			expect(sym.mean);
			LinkedList<Integer> ops = Operands();
			int count = ops.size();
			int total = 0;
			for(int i = 0; i < count; i++){
				total += ops.pop();
			}
			if(count > 0) total /= count;
			return total; 
		}
		else oops("Expression encountered unexpected symbol");
		
		return 0;
	}
	
	int Atom(){
		int peek = peek();
		
		if(peek == sym.number){
			return expectNum();
		} else if(peek == sym.lparen){
			return List();
		}
		else oops("Atom encountered unexpected symbol");	
		
		return 0;
	}
	
	int Operand(){
		int peek = peek();
		
		if(peek == sym.number || peek == sym.lparen){
			return Atom();
		}
		else oops("Operand encountered unexpected symbol");	
		
		return 0;
	}
	
	int Operand1(){
		int peek = peek();
		
		if(peek == sym.number || peek == sym.lparen){
			return Atom();
		}
		else oops("Operand1 encountered unexpected symbol");	
		
		return 0;
	}
	
	int Operand2(){
		int peek = peek();
		
		if(peek == sym.number || peek == sym.lparen){
			return Atom();
		}
		else oops("Operand2 encountered unexpected symbol");
		
		return 0;
	}
	
	LinkedList<Integer> Operands(){
		int peek = peek();
		
		if(peek == sym.number || peek == sym.lparen){
			int op = Operand();

			LinkedList<Integer> ops = Operands2();
			ops.add(op);
			
			return ops;
		}
		else oops("Operands encountered unexpected symbol");
		
		return null;
	}
	
	LinkedList<Integer> Operands2(){
		int peek = peek();
		
		if(peek == sym.number || peek == sym.lparen){
			int op = Operand();

			LinkedList<Integer> ops = Operands2();
			ops.add(op);
			
			return ops;
		} else if(peek == sym.rparen){ /*lambda case: follow*/
			return new LinkedList<Integer>();
		}
		else oops("Operands2 encountered unexpected symbol");
		
		return null;
	}
	
	void oops(String s) {
		Listing.get().EmitMessage("Error: " + s);
		System.err.println("Error: " + s);
		System.err.println("Stack trace at error:");
		Error e = new Error();
		e.printStackTrace(System.err);
		System.exit(-1);
	}

	/**
	 * Symbol id of the next symbol, without advancing input
	 * @return
	 */
	protected int peek() {
		return Scanner.peek().sym;
	}

	/**
	 * When we expect a number, let's expect it but
	 * also return its integer value
	 * @return the value of the next symbol if it is a number
	 */
	protected int expectNum() {
		if (Scanner.peek().sym != sym.number) {
			oops("expected number but saw symbol " + Scanner.peek().sym);
			return -1; // doesn't matter
		}
		else {
			//
			// Capture the integer value and then
			//   advance the input through expect
			//
			int ans = (Integer) Scanner.peek().value;
			expect(sym.number);
			return ans;
		}
	}

	protected void expect(int symval) {
		expect(symval, "Expected symbol #" + symval + " (as defined in sym.java)");
	}
	
	protected void expect(int symval, String message) {
		if (Scanner.peek().sym != symval) oops(message);
		else Scanner.advance(); 
	}
}

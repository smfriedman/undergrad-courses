/**
 * 
 * Steven Friedman - steven.friedman - 431474
 * 
 * My implementation involves 15 states, and I tried to indicate the flow among states
 * in the comments to the right of the GOTO table. The ACTIONS table and switch case are
 * fairly straightforward, a bit cluttered by symbol table checks. Keywords (non, terminal, 
 * start, with) are reserved, so they are invalid names for terminals. Because of this, 
 * good4.cup produces errors.
 * 
 */
package lab1;

import java.util.Enumeration;

public class Fsa {

 static int GOTO[][] = {//go to 0 on completion?
/*     B    D    S    O    C    S    T    N    S    W    O */
/*     l    e    e    r    o    t    e    o    t    i    t */
/*     a    f    m         m    r    r    n    a    t    h */
/*     n    i    i         m         m         r    h    e */
/*     k    n              a         i         t         r */
/*          e                        n                     */
/*                                   a                     */
/*                                   l                     */
/*                                                         */
{      0,  -1,   0,  -1,  -1,  11,   4,   7,   1,  -1,  -1} /* 0 - Entry / Following semicolons */,
/* Start With */
{      1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,   2,  -1} /* 1 - Start */,
{      2,  -1,  -1,  -1,  -1,   3,  -1,  -1,  -1,  -1,  -1} /* 2 - + With */,
{      3,  -1,   0,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1} /* 3 - + Str (non-term symbol) */,
/* Terminal Declaration */
{      4,  -1,  -1,  -1,  -1,   5,  -1,  -1,  -1,  -1,  -1} /* 4 - Terminal */,
{      5,  -1,  -1,  -1,  -1,   6,  -1,  -1,  -1,  -1,  -1} /* 5 - + Str (type) + {Str (term symbol), }* */,
{      6,  -1,   0,  -1,   5,  -1,  -1,  -1,  -1,  -1,  -1} /* 6 - + Str (term symbol) */,
/* Non Terminal Declaration */
{      7,  -1,  -1,  -1,  -1,  -1,   8,  -1,  -1,  -1,  -1} /* 7 - Non */,
{      8,  -1,  -1,  -1,  -1,   9,  -1,  -1,  -1,  -1,  -1} /* 8 - + Terminal*/,
{      9,  -1,  -1,  -1,  -1,  10,  -1,  -1,  -1,  -1,  -1} /* 9 - + Str (type) + {Str (non-term symbol), }* */,
{     10,  -1,   0,  -1,   9,  -1,  -1,  -1,  -1,  -1,  -1} /* 10 - + Str (non-term symbol) */,
/* Expression/Definition */
{     11,  12,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1} /* 11 - Str (non-term symbol) */,
{     12,  -1,  -1,  -1,  -1,  13,  -1,  -1,  -1,  -1,  -1} /* 12 - + ::= + (RHS | )* */,
{     13,  -1,   0,  12,  -1,  14,  -1,  -1,  -1,  -1,  -1} /* 13 - + Str (term symbol) */,
{     14,  -1,   0,  12,  -1,  -1,  -1,  -1,  -1,  -1,  -1} /* 14 - + Str (non-term symbol) */
};

 static int ACTION[][] = {
/*     B    D    S    O    C    S    T    N    S    W    O */
/*     l    e    e    r    o    t    e    o    t    i    t */
/*     a    f    m         m    r    r    n    a    t    h */
/*     n    i    i         m         m         r    h    e */
/*     k    n              a         i         t         r */
/*          e                        n                     */
/*                                   a                     */
/*                                   l                     */
/*                                                         */
{      1,   1,   1,   1,   1,   5,   1,   1,   1,   1,   1} /* 0 */,
{      1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1} /* 1 */,
{      1,   1,   1,   1,   1,   3,   1,   1,   1,   1,   1} /* 2 */,
{      1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1} /* 3 */,
{      1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1} /* 4 */,
{      1,   1,   1,   1,   1,   2,   1,   1,   1,   1,   1} /* 5 */,
{      1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1} /* 6 */,
{      1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1} /* 7 */,
{      1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1} /* 8 */,
{      1,   1,   1,   1,   1,   4,   1,   1,   1,   1,   1} /* 9 */,
{      1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1} /* 10 */,
{      1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1} /* 11 */,
{      1,   1,   1,   1,   1,   6,   1,   1,   1,   1,   1} /* 12 */,
{      1,   1,   8,   9,   1,   7,   1,   1,   1,   1,   1} /* 13 */,
{      1,   1,   8,   9,   1,   1,   1,   1,   1,   1,   1} /* 14 */,
};

   public Fsa(Enumeration e) {
      // Uncomment the line below and each token processed will be echoed.
      // ((TokenEnum)e).setDebug(true);

      SymbolTable symboltable = new SymbolTable();

      int state = 0;

      String 
         lhs     = "", 
         term    = "", 
         nonterm = "$FINAL$";

      while (e.hasMoreElements()) {         
         Token t = (Token)e.nextElement();
         String tstr = t.strValue();
         
//         System.out.println("   Read token type " + t.type() + ": " + t);
        	 
         int action = ACTION[state][t.type()];
         int newstate = GOTO[state][t.type()];
         if (newstate == -1) oops("Unexpected Token");

//         System.out.println("State " + state +
//                " Performing action " + action + " and going to " + newstate);

         switch (action) {
            case 1: /* do nothing */
                break;
            case 2: /* declare terminal symbol (keywords reserved) */
            	if(tstr.length() <= 0) {
            		oops("Symbol names cannot be empty strings");
            	}
            	else {
            		symboltable.enterTerminal(tstr);
            	}
            	break;
            case 3: /* start with */
            	if(symboltable.inTable(tstr) && !symboltable.isTerminal(tstr)) {
            		System.out.println("Start " + tstr);
            	} else{
            		System.out.println("Must start with a declare non terminal");
            	}
        		break;
            case 4: /* declare non-terminal symbol */	
            	if(tstr.length() <= 0){
            		oops("Symbol names cannot be empty strings");
            	}
            	else {
            		symboltable.enterNonTerminal(tstr);  
            	}
            	break;
            case 5: /* assignment lhs */
            	if(symboltable.inTable(tstr) && !symboltable.isTerminal(tstr)){
            		lhs = tstr;
            	}
            	else {
            		oops("LHS of assignment must be a declared non terminal");
            	}
            	break;
            case 6: /* assignment term */
            	if(symboltable.inTable(tstr) && symboltable.isTerminal(tstr)){
            		term = tstr;
            	}
            	else {
            		oops("RSH of assignment must begin with a declared terminal");
            	}
            	break;
            case 7: /* assignment non term */
            	if(symboltable.inTable(tstr) && !symboltable.isTerminal(tstr)){
            		nonterm = tstr;
            	}
            	else {
            		oops("Second RHS argument must be a declared non terminal");
            	}
            	break;
            case 8: /* assignment end: ; -- full reset */
            	System.out.println("Edge " + lhs + " " + nonterm + " " + term);
            	lhs = "";
            	term = "";
            	nonterm = "$FINAL$";
            	break;
            case 9: /* assignment end: | -- reset except for lhs */
            	System.out.println("Edge " + lhs + " " + nonterm + " " + term);
            	term = "";
            	nonterm = "$FINAL$";
            	break;
         }

         state = newstate;
      }
      if (state != 0) oops("End in bad state: " + state);
   }

   void oops(String s) {
      System.err.println("Error: " + s);
      System.out.println("ABORT");
      System.exit(-1);
   }
}

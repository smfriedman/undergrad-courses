package lab2;
import lab2.autogen.*;
import common.Listing;
import common.OpenFile;
import java_cup.runtime.*;
import java.io.IOException;

public class Scanner {
   private static Yylex slexer;
   private static Symbol LAtok;
   private static String fname;

   public static void setName(String f) {  fname = f; }

   public static void init() {
      slexer = open(fname);
      try {
         LAtok = slexer.next_token();
      } catch (IOException e) { LAtok = null; }
   }

   public static Symbol peek() {
      return((LAtok==null) ? new Symbol(sym.EOF) : LAtok);
   }

   public static void advance() {
      try {
         if (LAtok != null) LAtok = slexer.next_token();
      } catch (IOException e) { LAtok = null; }
   }

   public static Symbol next() {
      Symbol old = peek();
      advance();
      return(old);
   }

   public static Yylex open(String fname) {
      return new Yylex(new OpenFile(fname));
   }
      
      
   public static void main (String args[]) {
      if (args.length != 1) throw new Error("Usage:  java Scanner file");

      new Listing(System.out);
      setName(args[0]);
      init();
      Symbol t;
      while ((t=next()).sym != sym.EOF) {
            Listing.get().EmitMessage("Token: " + t);
      }
   }

}

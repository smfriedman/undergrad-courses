package project.mains;
import lab7.AbstractNode;
import project.CodeGenVisitor;

import common.Listing;
import hw4.DumpNodeVisitor;

public class ReferenceParser {
   public static void main(String args[]) throws Exception {
      
      new Listing(System.err);
      java_cup.runtime.lr_parser parser = CodeGenVisitor.getParser(args);

      AbstractNode root = (AbstractNode) parser.parse().value;

      System.err.println("\nRaw AST (with node numbers)");
      root.accept(new DumpNodeVisitor(System.err));

      System.err.println("Building Symbol table");
      root.accept(CodeGenVisitor.getSymtabVisitor());

      System.err.println("Type Setting pass");
      root.accept(CodeGenVisitor.getTypeSetVisitor());

      System.err.println("\nRaw AST (with node numbers)");
      root.accept(new DumpNodeVisitor(System.err));

      System.err.println("Code Generation");
      root.accept(CodeGenVisitor.getCodeGenVisitor());
   }


}

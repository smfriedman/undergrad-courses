package project;
import coursesolutions.*;
import coursesolutions.courseparser.CourseParser;
import coursesolutions.courseparser.CourseScanner;
import hw4.*;
import lab7.*;

import java.io.PrintStream;
import java.util.Enumeration;

import project.autogen.*;
import common.OpenFile;

/**
 */

public class CodeGenVisitor extends NodeVisitor {

	public static java_cup.runtime.lr_parser getParser(String[] args) {
		// FIXME
		// If you want to use your parser, then use the following line instead
		//    of the one below it:
		// return new Parser(new Yylex(args.length == 0 ? new OpenFile("") : new OpenFile(args[0])));
		return new CourseParser(new CourseScanner(args.length == 0 ? new OpenFile("") : new OpenFile(args[0])));
	}

	public static ReflectiveVisitor getSymtabVisitor() { 
		// FIXME
		// If you want to use your own symbol table code, then
		//    replace the line below with something suitable
		return new CourseSymtabVisitor(new CourseBuildSymtab());
	}


	/**
	 * Change this method to return the visitor you want for code generation. As
	 * given to you, it runs the course-sponsored solution
	 * (CourseProjectCodeGenVisitor); Change the return to
	 * "new CodeGenVisitor()" to return an instance of this class instead.
	 * 
	 * @return the visitor for performing code generation
	 */

	public static ReflectiveVisitor getCodeGenVisitor() {
		// Use this line instead of the last one if you want
		//   to run the course code generating solution for
		//   help
		 //return new CourseProjectCodeGenVisitor();
		return new CodeGenVisitor();
	}

	public static ReflectiveVisitor getTypeSetVisitor() {
		return new CourseTypeSetVisitor();
	}

	private void emit(String s) {
		PrintStream ps = System.out;
		out(ps, s);
	}

	private void emit(NodeDumpable a, String s) {
		emit("; " + a.dump());
		emit(s);
	}

	private void emitComment(String s) {
		emit("; " + s);
	}

	private void skip(int num) {
		for (int i = 0; i < num; ++i)
			emit("");
	}

	/**
	 * This outputs a standard prelude, with the class extending Object, a dummy
	 * method for main(String[] args) that calls main431 Thus, your test file
	 * must have a static main431 to kick things off
	 */
	public void visit(ClassDeclaring c) {
		emitComment("CSE431 automatically generated code file");
		emitComment("");
		emit(c, ".class public TestClasses/" + c.getName());
		emit(".super java/lang/Object");
		emit("; standard initializer\n\n" + ".method public <init>()V\n"
				+ "   aload_0\n"
				+ "   invokenonvirtual java/lang/Object/<init>()V\n"
				+ "   return\n" + ".end method\n\n");
		emitComment("dummy main to call our main because we don't handle arrays");
		skip(2);
		emit(".method public static main([Ljava/lang/String;)V\n"
				+ "   .limit locals 1\n" + "   .limit stack  3\n"
				+ "   invokestatic " + "TestClasses/" + c.getName() + "/main431()V\n"
				+ "   return\n" + ".end method\n\n");
		visitChildren((AbstractNode) c);
	}

	public void defaultVisit(Object o) {
		AbstractNode n = (AbstractNode) o;
		out("Ignoring " + n.dump());
		visitChildren(n);
	}

	public void visit(MethodDeclaring m) {
		AbstractNode n = (AbstractNode) m;
		SigVisitor sv = new SigVisitor();
		sv.performVisit(n.getChild());
		emit(".method " +
				(m.getMods().isPublic() ? "public " : "private ") +
				(m.getMods().isStatic() ? "static " : "") +
				m.getName()  +
				"(" + sv.getAnswer() +
				")" + 
				m.getType().getTypeString()
				);
		emit(".limit locals 10");
		emit(".limit stack  30");
		int nextReg = (m.getMods().isStatic()) ? 0 : 1;

		// How I would like to use an inner class, but reflection security
		// won't let me
		(new RegisterAssignVisitor(nextReg)).performVisit(n);

		visitChildren(n);
		emit("return");
		emit(".end method\n\n");
	}	

	public void visit(AssignIsh a) {
		AbstractNode n = (AbstractNode) a;
//		emitComment(""+n.dump() +" start of assign");
		n.getChild().getSib().accept(this);
		(new AssignTargVisitor()).performVisit(n.getChild());
//		emitComment(""+n.dump() +" end of assign");
	}
	
	public void visit(BinaryComputeIsh b){
		AbstractNode n = (AbstractNode) b;
		visitChildren(n);
		
		String type = n.getChild().getNodeType().toString().toLowerCase();
		if(type.equals("z")) type = "i";
		
		String code = type + b.getOperation();
		emit(type + b.getOperation());
	}
	
	public void visit(UnaryComputeIsh u){
		AbstractNode n = (AbstractNode) u;
		visitChildren(n);
		String type = n.getNodeType().toString().toLowerCase();
		if(type.equals("z")) type = "i";
		
		emit(type + u.getOperation());
	}
	
	public void visit(CompareIsh c){
		AbstractNode n = (AbstractNode) c;
//		n.getChild().accept(this);
//		n.getChild().getSib().accept(this);		
		visitChildren(n);
		
		//types?
		
		//push 1 to stack if true, 0 if false

		Label trueLabel = new Label();
		Label endLabel = new Label();
		
		emit("if_icmp" + c.getCompare() + " " + trueLabel.use()); 
		emit("bipush 0");
		emit("goto " + endLabel.use());
		emit(trueLabel.def());
		emit("bipush 1");
		emit(endLabel.def());
	}
	
	public void visit(IfIsh i){
		Label falseLabel = new Label();
		Label endLabel = new Label();
		
		i.getPredicate().accept(this);
		emit("ifeq " + falseLabel.use());
		i.getTruePart().accept(this);
		emit("goto " + endLabel.use());
		emit(falseLabel.def());
		i.getFalsePart().accept(this);
		emit(endLabel.def());
	}
	
	public void visit(WhileIsh w){
		Label doneLabel = new Label();
		Label loopLabel = new Label();
		emit(loopLabel.def());
		w.getPredicate().accept(this);
		emit("ifeq " + doneLabel.use());
		w.getBody().accept(this);
		emit("goto " + loopLabel.use());
		emit(doneLabel.def());
	}
	
	public void visit(ConstantInt i){
		emit("ldc " + i.getVal());
	}
	
	public void visit(ConstantFloat f){
		emit("ldc " + f.getVal());
	}
	
	public void visit(ConstantBool b){
		emit("bipush " + b.getVal());
	}
	
	public void visit(ConstantString s){
		emit("ldc \"" + s.getVal() + "\"");
	}
	
	public void visit(LocalReferencing l){
		char type = typeLetter((AbstractNode) l);
		emit(type + "load " + l.getSymInfo().getRegister());
	}
	
	public void visit(ArgIsh a){
		visitChildren((AbstractNode) a);
	}
	
	public void visit(InvokeIsh i){
		AbstractNode n = (AbstractNode) i;
		visitChildren(n);

		SigVisitor sv = new SigVisitor();
		sv.performVisit(i.paramsNode());
		String args = sv.getAnswer();

		InvokeReference ir = i.methodNode();
		String sig = ir.getClassType().toString().replace(';', '/');
		sig = sig.substring(1, sig.length());
		sig += ir.getMethodName();
		sig += "(" + args + ")";
		sig += ir.getReturnType();
		if(i.methodNode().isStaticInvoke()){
			emit("invokestatic " + sig);
		} else {
			emit("invokevirtual " + sig);
		}
		
	}
	
	public void visit(InvokeReference i){
		AbstractNode n = (AbstractNode) i;
		visitChildren(n);
		String parent = n.getParent().whatAmI();
		String name = n.whatAmI();
		
		String className = i.getClassType().toString().replace(';', '/');
		className = className.substring(1, className.length());
		
		if(name.contains("StaticReference") && parent.contains("AssignIsh")){
			emit("getstatic " + className + i.getMethodName() + " " + i.getReturnType());
		} else if(name.contains("FieldReference") && parent.contains("AssignIsh")){	
			emit("getfield " + className + i.getMethodName() + " " + i.getReturnType());
		}
	}
	
	//
	// Helpful methods
	//

	private static String type2class(Type t) {
		return type2class(t.toString());
	}
	private static String type2class(String s) {
		return s.substring(0,s.length()-1).substring(1);
	}

	protected class RegisterAssignVisitor extends NodeVisitor {
		private int reg;

		public RegisterAssignVisitor(int reg) { this.reg = reg; }

		public void visit(LocalDeclaring ld) {
			AbstractNode nld = (AbstractNode) ld;
			ld.getSymInfo().setRegister(reg++);
			// System.err.println("Local Declaring! " + nld.dump() + ld.getSymInfo());
		}
		public void defaultVisit(Object o) {
			this.visitChildren((AbstractNode) o);
		}
		public int getLastRegisterNumber() { return this.reg; }
	}

	private static char typeLetter(AbstractNode n) {
		Type t = n.getNodeType();
		char ans = 'a';
		if (t.toString().equals("I")
				||  t.toString().equals("Z")) ans = 'i';
		return ans;
	}

	protected class AssignTargVisitor extends NodeVisitor {
		public void visit(ThisIsh t) {
			AbstractNode n = (AbstractNode) t;
			//emitComment("Assign target resolved to this:");
			emit(typeLetter(n)+"store_0");
		}
		public void visit(LocalReferencing lr) {
			AbstractNode n = (AbstractNode) lr;
			//emitComment("Assign target resolved to local:");
			emit(typeLetter(n)+"store " + lr.getSymInfo().getRegister());
		}
		public void visit(FieldReferencing fr) {
			AbstractNode n = (AbstractNode) fr;
			//emitComment("Assign target resolved to putfield:");
			n.getChild().accept(CodeGenVisitor.this);
			String cl = type2class(n.getChild().getNodeType().toString());
			emit("swap");
			emit(n, "putfield " + cl+ "/"+fr.getFieldName() + " " + fr.getResultingType());
		}
		public void visit(StaticReferencing sr) {
			AbstractNode n = (AbstractNode) sr;
			//emitComment("Assign target resolved to putstatic:");
			String cl = type2class(sr.getClassName().toString());
			emit(n, "putstatic " +  cl+"/"+sr.getFieldName()+ " " + 
					sr.getResultingType());
		}
	}

	protected class SigVisitor extends NodeVisitor {
		private String answer = "";

		public void visit(ParamIsh args) {
			this.visitChildren((AbstractNode) args);
		}
		public void visit(ArgIsh args) {
			this.visitChildren((AbstractNode) args);
		}
		public void visit(LocalDeclaring ld) {
			answer += ld.getType().toString();
		}
		public void defaultVisit(Object o) {
			AbstractNode n = (AbstractNode) o;
			answer += n.getNodeType().toString();
		}
		public String getAnswer() { return answer; }
	}

	static class Label {
		private static int num = 1000;
		private int mynum;
		public Label() { mynum = ++num; }
		public String use() { return "L"+mynum; }
		public String def() { return "L"+mynum+":"; }
	}

}

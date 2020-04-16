;	Steven Friedman
;	Implementation: series of System.out.print() of strings/ints,
;					recursive fib from Fibs.java
;
.class public Contest
.super java/lang/Object

;
;  Team members:
;
;	Steven Friedman
;
;
;  Your assignment is to fill in the fibs method below
;   so that it creates (exactly) the output you see in
;   fibsgolden.txt
;
;  The contest will be won by those entries that compute the
;   correct output and whose bytecode files are smallest
;
;  For reference, a solution in Java is presented in Fibs.java
;
;   

;
; standard initializer
;
.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method
       
.method public static fib(I)I
.limit locals 5
.limit stack  47
       0: iload_0
       1: ifgt          6
       4: iconst_0
       5: ireturn
       6: iload_0
       7: iconst_1
       8: if_icmpne     13
      11: iconst_1
      12: ireturn
      13: iload_0
      14: iconst_1
      15: isub
      16: invokestatic  Contest/fib(I)I
      19: iload_0
      20: iconst_2
      21: isub
      22: invokestatic  Contest/fib(I)I
      25: iadd
      26: ireturn
   return
.end method

.method public static fibs()V
.limit locals 5
.limit stack  47
       0: iconst_0
       1: istore_0
       2: iload_0
       3: bipush        47
       5: if_icmpge     55
       8: getstatic     java/lang/System/out Ljava/io/PrintStream;
      11: ldc           "The fib of "
      13: invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V
      16: getstatic     java/lang/System/out Ljava/io/PrintStream;
      19: iload_0
      20: invokevirtual java/io/PrintStream/print(I)V
      23: getstatic     java/lang/System/out Ljava/io/PrintStream;
      26: ldc           " is "
      28: invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V
      31: getstatic     java/lang/System/out Ljava/io/PrintStream;
      34: iload_0
      35: invokestatic  Contest/fib(I)I
      38: invokevirtual java/io/PrintStream/print(I)V
      41: getstatic     java/lang/System/out Ljava/io/PrintStream;
      44: ldc           "\n"
      46: invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V
      49: iinc          0 1
      52: goto          2
      55: return
   return
.end method


;
;  Do not change any code below this line
;
.method public static main([Ljava/lang/String;)V
       ; set limits used by this method
       .limit locals 1   ; = parameters + locals
        invokestatic Contest/fibs()V 
        return
.end method

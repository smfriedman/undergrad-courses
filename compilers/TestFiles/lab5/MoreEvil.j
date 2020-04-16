.class public MoreEvil
.super java/lang/Object



;
; standard initializer
;
.method public <init>()V
   aload_0
 
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static main([Ljava/lang/String;)V
       ; set limits used by this method
       .limit locals 1   ; = parameters + locals
       .limit stack  20
        getstatic java/lang/System/out Ljava/io/PrintStream;
        sipush 431    
        invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
       
        return
.end method

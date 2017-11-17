//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"

package base;

import base.SymbolTable;

import base.LexicalAnalizer;

import java.util.*;

//#line 27 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short THEN=258;
public final static short END_IF=259;
public final static short BEGIN=260;
public final static short END=261;
public final static short ELSE=262;
public final static short OUT=263;
public final static short DO=264;
public final static short UNTIL=265;
public final static short LET=266;
public final static short FLOAT=267;
public final static short INT=268;
public final static short I_F=269;
public final static short COMP_IGUAL_IGUAL=270;
public final static short COMP_MAYOR_IGUAL=271;
public final static short COMP_DISTINTO=272;
public final static short COMP_MENOR_IGUAL=273;
public final static short ID=274;
public final static short CTE=275;
public final static short CADENA=276;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    3,    5,    5,    6,    6,
    4,    4,    4,    4,    4,    7,    7,   10,   11,   11,
   13,   14,   12,   17,   17,   17,   17,   17,   17,    8,
    8,   18,   19,   20,    9,    9,   15,   15,   21,   21,
   16,   16,   16,   22,   24,   23,   23,   23,   25,   25,
   25,   25,
};
final static short yylen[] = {                            2,
    1,    1,    2,    1,    1,    4,    3,    1,    1,    1,
    1,    1,    1,    5,    2,    5,    3,    1,    5,    3,
    1,    1,    3,    1,    1,    1,    1,    1,    1,    2,
    3,    1,    6,    1,    4,    4,    3,    3,    1,    2,
    3,    3,    1,    1,    1,    3,    3,    1,    1,    1,
    2,    4,
};
final static short yydefred[] = {                         0,
    0,    0,   32,    0,    0,    0,    0,    2,    4,    5,
    0,   11,   12,   13,    0,    0,    0,    0,    0,   15,
    0,    0,    3,    0,    0,    0,    0,    0,   30,   17,
    0,   49,   50,    0,    0,   18,    0,    0,    0,    0,
   48,    0,    0,    0,   10,    9,    0,    7,   31,    0,
   39,    0,    0,    0,   51,    0,   25,   26,   24,   27,
   28,   29,    0,    0,    0,    0,    0,    0,   36,   35,
    6,   38,   37,   40,    0,    0,    0,   16,    0,    0,
    0,   46,   47,   14,   34,    0,   52,    0,   21,    0,
   20,    0,   33,    0,   22,   19,
};
final static short yydgoto[] = {                          6,
    7,    8,    9,   10,   11,   47,   12,   13,   14,   35,
   78,   36,   88,   94,   28,   37,   63,   15,   29,   86,
   52,   38,   39,   40,   41,
};
final static short yysindex[] = {                      -192,
  -37,   20,    0, -254,  -51,    0, -192,    0,    0,    0,
  -26,    0,    0,    0, -193, -232,  -29, -199,  -51,    0,
  -29,  -29,    0, -226, -194,   35, -220, -182,    0,    0,
   44,    0,    0, -190,   45,    0,  -45,    8,    0,   15,
    0,   46,   42,   43,    0,    0,   47,    0,    0, -171,
    0, -205,   51,  -29,    0, -166,    0,    0,    0,    0,
    0,    0,  -29,  -29,  -29,  -29,  -29,   48,    0,    0,
    0,    0,    0,    0,  -29,   54, -164,    0,    0,    0,
    0,    0,    0,    0,    0,   56,    0, -189,    0,   52,
    0, -164,    0, -160,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  -20,    0,  100,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   23,    0,  -41,    0,
    0,    0,   23,   23,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   23,    0,    0,    4,  -34,
  -12,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   94,    0,  -13,    0,    0,    0,    0,   98,    0,
    0,   28,    0,    0,  -52,    1,    0,    0,    0,    0,
    0,    0,   11,    0,   12,
};
final static int YYTABLESIZE=261;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         43,
   45,   43,   17,   43,   43,   45,   41,   45,   41,   22,
   41,   41,   45,   51,   61,   34,   62,   25,   43,   19,
   43,   43,   44,    8,   89,   41,   30,   41,   42,   45,
   42,   24,   42,   42,   45,   50,    1,    8,   74,   95,
   45,   46,    2,    3,   23,    4,   44,   42,   44,   42,
   64,    1,   65,   19,   76,   73,   66,    2,    3,   18,
    4,   67,   26,   79,    1,   44,   27,   44,   19,   91,
    2,    3,   92,    4,   80,   81,   42,   82,   83,   48,
   49,    5,   53,   54,   55,   56,   68,   69,   70,   72,
   75,   77,   71,   84,   87,   27,   90,   93,   96,    1,
   23,   20,   85,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   21,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   16,    0,
    0,    0,    0,    0,   57,   58,   59,   60,   43,   43,
   43,   43,    0,    0,    0,   41,   41,   41,   41,   31,
    0,    0,    0,    0,   32,   33,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   42,   42,   42,
   42,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   40,   45,   46,   47,   41,   42,   43,   61,
   45,   46,   47,   27,   60,   45,   62,   44,   60,  274,
   62,   21,   22,   44,   77,   60,  259,   62,   41,   42,
   43,   58,   45,   46,   47,  256,  257,   58,   52,   92,
  267,  268,  263,  264,   41,  266,   43,   60,   45,   62,
   43,  257,   45,  274,   54,  261,   42,  263,  264,   40,
  266,   47,  256,   63,  257,   43,  260,   45,  274,  259,
  263,  264,  262,  266,   64,   65,  276,   66,   67,  274,
   46,  274,  265,   40,  275,   41,   41,   46,   46,  261,
   40,  258,   46,   46,   41,  260,   41,   46,  259,    0,
    7,    4,   75,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  256,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  256,   -1,
   -1,   -1,   -1,   -1,  270,  271,  272,  273,  270,  271,
  272,  273,   -1,   -1,   -1,  270,  271,  272,  273,  269,
   -1,   -1,   -1,   -1,  274,  275,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  270,  271,  272,
  273,
};
}
final static short YYFINAL=6;
final static short YYMAXTOKEN=276;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",null,
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"IF","THEN","END_IF","BEGIN","END","ELSE","OUT",
"DO","UNTIL","LET","FLOAT","INT","I_F","COMP_IGUAL_IGUAL","COMP_MAYOR_IGUAL",
"COMP_DISTINTO","COMP_MENOR_IGUAL","ID","CTE","CADENA",
};
final static String yyrule[] = {
"$accept : prog",
"prog : programa",
"programa : sentencia",
"programa : programa sentencia",
"sentencia : declarativa",
"sentencia : ejecutable",
"declarativa : decl ':' tipo '.'",
"decl : decl ',' ID",
"decl : ID",
"tipo : INT",
"tipo : FLOAT",
"ejecutable : seleccion",
"ejecutable : iteracion",
"ejecutable : asignacion",
"ejecutable : OUT '(' CADENA ')' '.'",
"ejecutable : LET asignacion",
"seleccion : IF '(' cond_if ')' cpo_if",
"seleccion : IF error END_IF",
"cond_if : condicion",
"cpo_if : THEN cpo_then ELSE cpo_else END_IF",
"cpo_if : THEN cpo_then END_IF",
"cpo_then : bloque",
"cpo_else : bloque",
"condicion : expresion op expresion",
"op : COMP_DISTINTO",
"op : COMP_IGUAL_IGUAL",
"op : COMP_MAYOR_IGUAL",
"op : COMP_MENOR_IGUAL",
"op : '<'",
"op : '>'",
"iteracion : DO_IND cpo_until",
"iteracion : DO_IND error '.'",
"DO_IND : DO",
"cpo_until : bloque UNTIL '(' cond_until ')' '.'",
"cond_until : condicion",
"asignacion : ID '=' expresion '.'",
"asignacion : ID error expresion '.'",
"bloque : BEGIN grupo_de_sentencias END",
"bloque : BEGIN error END",
"grupo_de_sentencias : ejecutable",
"grupo_de_sentencias : grupo_de_sentencias ejecutable",
"expresion : expresion_a '+' termino",
"expresion : expresion_a '-' termino",
"expresion : termino",
"expresion_a : expresion",
"termino_a : termino",
"termino : termino_a '*' factor",
"termino : termino_a '/' factor",
"termino : factor",
"factor : ID",
"factor : CTE",
"factor : '-' CTE",
"factor : I_F '(' expresion ')'",
};

//#line 281 "gramatica.y"

LexicalAnalizer la;
TercetoGenerator tg;
ArrayList<String> aDeclarar = new ArrayList();

public Parser(String dir) {

  la = new LexicalAnalizer(dir);
  tg = new TercetoGenerator(la.getSymbolTable());
}

public TercetoGenerator getTg(){
    return tg;
}

public static void main(String[] args) {
    Parser parser = new Parser(args[0]);
    int parsedValue = parser.yyparse();
    System.out.println(parsedValue);

    parser.getLa().outputST();
    parser.getLa().getCompilationOutput().closeWriter();

    AssemblerGenerator ag = new AssemblerGenerator(parser.getLa().getSymbolTable(),args[0]);
    ag.setTercetos(parser.getTg().getTercetos());
    ag.generate();
}

private int yylex(){
	int token = la.yylex();
    yylval = new ParserVal(la.val);
    return token;
}

private void yyerror(String msj){
}

private void yynotify(int type, String mensaje){
        System.out.println(mensaje);
        String s = Printer.getMessage(1,type, la.getValues().getCurrentLine(), mensaje); //v.currentLine
        la.getCompilationOutput().write(s);

}

public void addSymbol(Data field){ /*Agrega un numero negativo a la tabla*/
        SymbolTable tab = la.getSymbolTable();
        int value = Integer.parseInt(field.getLexema()) * (-1);
        field.setLexema(String.valueOf(value));
        tab.addSymbol(Integer.valueOf(CTE), String.valueOf(value));
}

public void declarar(String type){
    SymbolTable tab = la.getSymbolTable();
    for (String s : aDeclarar){
        tab.setType(s,type);
    }
    aDeclarar.clear();
}



public LexicalAnalizer getLa() {
    return la;
}
//#line 390 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 65 "gramatica.y"
{tg.showTercetos();}
break;
case 6:
//#line 87 "gramatica.y"
{declarar(((Data)val_peek(1).obj).getLexema());}
break;
case 7:
//#line 93 "gramatica.y"
{aDeclarar.add(((Data)val_peek(0).obj).getLexema());}
break;
case 8:
//#line 95 "gramatica.y"
{aDeclarar.add(((Data)val_peek(0).obj).getLexema());}
break;
case 14:
//#line 115 "gramatica.y"
{tg.tercetoOUT((Data)val_peek(2).obj);}
break;
case 16:
//#line 127 "gramatica.y"
{tg.tercetoDesapilar(0);
                                                                            tg.tercetoLabel();}
break;
case 17:
//#line 130 "gramatica.y"
{yynotify(1, "Estructura IF incorrecta");}
break;
case 18:
//#line 136 "gramatica.y"
{tg.tercetoIncompleto("BF");}
break;
case 19:
//#line 142 "gramatica.y"
{yynotify(2, "Estructura IF correcta.");}
break;
case 20:
//#line 144 "gramatica.y"
{yynotify(2, "Estructura IF correcta.");}
break;
case 21:
//#line 150 "gramatica.y"
{tg.tercetoDesapilar(1);

                                                                            tg.tercetoIncompleto("BI");
                                                                            tg.tercetoLabel();}
break;
case 23:
//#line 165 "gramatica.y"
{tg.createTerceto(((Data)val_peek(1).obj).getLexema(), (Data)val_peek(2).obj, (Data)val_peek(0).obj);}
break;
case 31:
//#line 192 "gramatica.y"
{yynotify(1, "Estructura UNTIL incorrecta.");}
break;
case 32:
//#line 198 "gramatica.y"
{tg.setIndexDO();
                                                                            tg.tercetoLabel();}
break;
case 33:
//#line 205 "gramatica.y"
{tg.tercetoIteration("BF");

                                                                            yynotify(2, "Estructura UNTIL correcta.");}
break;
case 35:
//#line 223 "gramatica.y"
{tg.setAptr(tg.createTerceto("=", (Data)val_peek(3).obj, tg.getEptr()));}
break;
case 36:
//#line 227 "gramatica.y"
{yynotify(1, "Asignacion incorrecta.");}
break;
case 38:
//#line 235 "gramatica.y"
{yynotify(1, "Error en bloque.");}
break;
case 41:
//#line 247 "gramatica.y"
{tg.setEptr(tg.createTerceto("+", tg.pilaGramatica.remove(tg.pilaGramatica.size()-1), tg.getTptr()));}
break;
case 42:
//#line 249 "gramatica.y"
{tg.setEptr(tg.createTerceto("-", tg.pilaGramatica.remove(tg.pilaGramatica.size()-1), tg.getTptr()));}
break;
case 43:
//#line 251 "gramatica.y"
{tg.setEptr(tg.getTptr());}
break;
case 44:
//#line 255 "gramatica.y"
{tg.pilaGramatica.add(tg.getEptr());}
break;
case 45:
//#line 257 "gramatica.y"
{tg.pilaGramatica.add(tg.getTptr());}
break;
case 46:
//#line 260 "gramatica.y"
{tg.setTptr(tg.createTerceto("*", tg.pilaGramatica.remove(tg.pilaGramatica.size()-1), tg.getFptr()));}
break;
case 47:
//#line 262 "gramatica.y"
{tg.setTptr(tg.createTerceto("/", tg.pilaGramatica.remove(tg.pilaGramatica.size()-1), tg.getFptr()));}
break;
case 48:
//#line 264 "gramatica.y"
{tg.setTptr(tg.getFptr());}
break;
case 49:
//#line 268 "gramatica.y"
{tg.setFptr(tg.lastDeclaration((Data)val_peek(0).obj));}
break;
case 50:
//#line 270 "gramatica.y"
{tg.setFptr((Data)val_peek(0).obj);}
break;
case 51:
//#line 272 "gramatica.y"
{addSymbol((Data)val_peek(0).obj);

                                                                            tg.setFptr((Data)val_peek(0).obj);}
break;
case 52:
//#line 276 "gramatica.y"
{tg.setFptr(tg.tercetoI_F((Data)val_peek(1).obj));}
break;
//#line 668 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################

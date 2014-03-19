package supersql;

import beaver.Symbol;
import beaver.Scanner;

import supersql.Terminals;

import supersql.ast.*;
%%

%class SupersqlScanner
%extends Scanner
%function nextToken
%type Symbol
%yylexthrow Scanner.Exception
%eofval{
	return newToken(Terminals.EOF, "end-of-file");
%eofval}
%unicode

%line
%column

%{
  StringBuffer string = new StringBuffer(128);

  private Symbol newToken(short id)
  {
	return new Symbol(id, yyline + 1, yycolumn + 1, yylength(), yytext());
  }

  private Symbol newToken(short id, Object value)
  {
	return new Symbol(id, yyline + 1, yycolumn + 1, yylength(), value);
  }

%}
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]
InputCharacter = [^\r\n]

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*
FloatLiteral  = ({FLit1}|{FLit2}|{FLit3})

FLit1    = [0-9]+ \. [0-9]*
FLit2    = \. [0-9]+
FLit3    = [0-9]+

Identifier = [:jletter:][:jletterdigit:]*
EndOfLineComment = "--" {InputCharacter}* {LineTerminator}?
StringCharacter = [^\r\n\'\\]

%state STRING

%%

{WhiteSpace}+   { /* ignore */ }


<YYINITIAL> {
	("create"|"CREATE")       { return newToken(Terminals.CREATE);  }
	("alter"|"ALTER")       { return newToken(Terminals.ALTER);  }
    ("modify"|"MODIFY")       { return newToken(Terminals.MODIFY);  }
	("table"|"TABLE")       { return newToken(Terminals.TABLE);  }
	("add"|"ADD")       { return newToken(Terminals.ADD);  }
	("drop"|"DROP")       { return newToken(Terminals.DROP);  }
	("column"|"COLUMN")       { return newToken(Terminals.COLUMN);  }
    "("         { return newToken(Terminals.LPAREN); }
    ")"         { return newToken(Terminals.RPAREN); }
    "<="         { return newToken(Terminals.LE); }
    ">="         { return newToken(Terminals.GE); }
    ">"         { return newToken(Terminals.GT); }
    "<"         { return newToken(Terminals.LT); }
    "+"         { return newToken(Terminals.PLUS); }
    "-"         { return newToken(Terminals.MINUS); }
    "/"         { return newToken(Terminals.DIV); }
    "*"         { return newToken(Terminals.MULT); }
    ("CLOB"|"clob")       { return newToken(Terminals.CLOB); }
    ("is"|"IS")       { return newToken(Terminals.IS); }
    ("between"|"BETWEEN")       { return newToken(Terminals.BETWEEN); }
    ("CHAR"|"char")    { return newToken(Terminals.CHAR); }
    ("DATE"|"date")    { return newToken(Terminals.DATE); }
    ("TIMESTAMP")    { return newToken(Terminals.TIMESTAMP); }
    ("NUMBER"|"number")    { return newToken(Terminals.NUMBER); }
    ("int"|"INT")       { return newToken(Terminals.INT); }
    ("INTEGER"|"integer")     { return newToken(Terminals.INT); }
    ("LONG"|"long")    { return newToken(Terminals.LONG); }
    ("FLOAT"|"float")       { return newToken(Terminals.FLOAT); }
    ("PRIMARY"|"primary")    { return newToken(Terminals.PRIMARY); }
    ("CONSTRAINT"|"constraint")   { return newToken(Terminals.CONSTRAINT); }
    ("KEY"|"key")   { return newToken(Terminals.KEY); }
    ("NULL"|"null")   { return newToken(Terminals.NULL); }
    ("DEFAULT"|"default")  { return newToken(Terminals.DEFAULT); }
    ("CHECK"|"check")   { return newToken(Terminals.CHECK); }
    ("IN"|"in")   { return newToken(Terminals.IN); }
    ("AND"|"and")   { return newToken(Terminals.AND); }
    ("OR"|"or")   { return newToken(Terminals.OR); }
    ("NOT"|"not")   { return newToken(Terminals.NOT); }
    ("VARCHAR2"|"varchar2")       { return newToken(Terminals.VARCHAR); }
    ("VARCHAR"|"varchar")       { return newToken(Terminals.VARCHAR); }
    ("NVARCHAR2"|"nvarchar2")       { return newToken(Terminals.NVARCHAR); }
    ("NVARCHAR"|"nvarchar")       { return newToken(Terminals.NVARCHAR); }
    ","         { return newToken(Terminals.COMMA); }
    \'                             { yybegin(STRING); string.setLength(0); string.append("'");}
    {DecIntegerLiteral}    { return newToken(Terminals.INTEGER_LITERAL, new Integer(yytext())); }
    {FloatLiteral}            { return newToken(Terminals.FLOATING_POINT_LITERAL, Float.valueOf(yytext())); }
    {EndOfLineComment}      { /* ignore */ }
    {Identifier} {return newToken(Terminals.IDENT,new String(yytext())); }

}


<STRING> {
  \'                             { yybegin(YYINITIAL); string.append("'"); return newToken(Terminals.STRING_LITERAL, string.toString()); }

  {StringCharacter}+             { string.append( yytext() ); }


  /* error cases */
  \\.                            { throw new Scanner.Exception(yyline + 1, yycolumn + 1, "Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { throw new Scanner.Exception(yyline + 1, yycolumn + 1, "Unterminated string at end of line"); }
}

.|\n            { throw new Scanner.Exception("unexpected character '" + yytext() + "'"); }

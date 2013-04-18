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
	"create"       { return newToken(Terminals.CREATE);  }
	"alter"       { return newToken(Terminals.ALTER);  }
	"table"       { return newToken(Terminals.TABLE);  }
	"add"       { return newToken(Terminals.ADD);  }
	"drop"       { return newToken(Terminals.DROP);  }
	"column"       { return newToken(Terminals.COLUMN);  }
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
    "CLOB"       { return newToken(Terminals.CLOB); }
    "clob"       { return newToken(Terminals.CLOB); }
    "is"       { return newToken(Terminals.IS); }
    "between"       { return newToken(Terminals.BETWEEN); }
    "CHAR"    { return newToken(Terminals.CHAR); }
    "char"    { return newToken(Terminals.CHAR); }
    "DATE"    { return newToken(Terminals.DATE); }
    "TIMESTAMP"    { return newToken(Terminals.TIMESTAMP); }
    "NUMBER"    { return newToken(Terminals.NUMBER); }
    "number"    { return newToken(Terminals.NUMBER); }
    "int"       { return newToken(Terminals.INT); }
    "INT"       { return newToken(Terminals.INT); }
    "INTEGER"    { return newToken(Terminals.INT); }
    "integer"    { return newToken(Terminals.INT); }
    "LONG"    { return newToken(Terminals.LONG); }
    "long"    { return newToken(Terminals.LONG); }
    "float"       { return newToken(Terminals.FLOAT); }
    "FLOAT"       { return newToken(Terminals.FLOAT); }
    "primary"   { return newToken(Terminals.PRIMARY); }
    "PRIMARY"   { return newToken(Terminals.PRIMARY); }
    "constraint"   { return newToken(Terminals.CONSTRAINT); }
    "CONSTRAINT"   { return newToken(Terminals.CONSTRAINT); }
    "key"   { return newToken(Terminals.KEY); }
    "null"   { return newToken(Terminals.NULL); }
    "default"  { return newToken(Terminals.DEFAULT); }
    "check"   { return newToken(Terminals.CHECK); }
    "in"   { return newToken(Terminals.IN); }
    "and"   { return newToken(Terminals.AND); }
    "or"   { return newToken(Terminals.OR); }
    "not"   { return newToken(Terminals.NOT); }
    "VARCHAR2"       { return newToken(Terminals.VARCHAR); }
    "varchar2"       { return newToken(Terminals.VARCHAR); }
    "varchar"       { return newToken(Terminals.VARCHAR); }
    "VARCHAR"       { return newToken(Terminals.VARCHAR); }
    ","         { return newToken(Terminals.COMMA); }
    \'                             { yybegin(STRING); string.setLength(0); }
    {DecIntegerLiteral}    { return newToken(Terminals.INTEGER_LITERAL, new Integer(yytext())); }
    {FloatLiteral}            { return newToken(Terminals.FLOATING_POINT_LITERAL, Float.valueOf(yytext())); }
    {EndOfLineComment}      { /* ignore */ }
    {Identifier} {return newToken(Terminals.IDENT,new String(yytext())); }

}


<STRING> {
  \'                             { yybegin(YYINITIAL); return newToken(Terminals.STRING_LITERAL, string.toString()); }

  {StringCharacter}+             { string.append( yytext() ); }


  /* error cases */
  \\.                            { throw new Scanner.Exception(yyline + 1, yycolumn + 1, "Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { throw new Scanner.Exception(yyline + 1, yycolumn + 1, "Unterminated string at end of line"); }
}

.|\n            { throw new Scanner.Exception("unexpected character '" + yytext() + "'"); }
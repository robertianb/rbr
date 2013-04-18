package productname;

import beaver.Symbol;
import beaver.Scanner;

import productname.Terminals;

%%

%class ProductNamePatternScanner
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
Separator = [_ ]

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*
FloatLiteral  = ({FLit1}|{FLit2}|{FLit3})

FLit1    = [0-9]+ \. [0-9]*
FLit2    = \. [0-9]+
FLit3    = [0-9]+

Identifier = [:jletter:][:jletterdigit:]*
EndOfLineComment = "--" {InputCharacter}* {LineTerminator}?
StringCharacter = [^\r\n\'\\]

%state MATURITY

%%

{WhiteSpace}+   { /* ignore */ }


<YYINITIAL> {
	"MUL"       { return newToken(Terminals.MUL);  }
	"UL"       { return newToken(Terminals.UL);  }
	"ID"       { return newToken(Terminals.ID);  }
	"K"       { return newToken(Terminals.ADD);  }
	Separator         { return newToken(Terminals.SEPARATOR); }
    "MAT"                             { yybegin(MATURITY); string.setLength(0); }
    

}


<MATURITY> {
   "("                           { }
  ")"                            { yybegin(YYINITIAL); return newToken(Terminals.DATE, string.toString()); }
  {StringCharacter}+             { string.append( yytext() ); }
  \\.                            { throw new Scanner.Exception(yyline + 1, yycolumn + 1, "Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { throw new Scanner.Exception(yyline + 1, yycolumn + 1, "Unterminated string at end of line"); }
}

.|\n            { throw new Scanner.Exception("unexpected character '" + yytext() + "'"); }
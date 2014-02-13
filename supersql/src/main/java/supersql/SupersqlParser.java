package supersql;

import java.util.ArrayList;
import supersql.ast.*;
import supersql.ast.types.*;
import beaver.*;
import supersql.ast.actions.*;
import supersql.ast.changes.*;
import supersql.ast.entities.*;

/**
 * This class is a LALR parser generated by
 * <a href="http://beaver.sourceforge.net">Beaver</a> v0.9.6.1
 * from the grammar specification "supersql.grammar".
 */
public class SupersqlParser extends Parser {

	static final ParsingTables PARSING_TABLES = new ParsingTables(
		"U9pDbcTm5LKOXj#zcoBfG488gK12CEJUDBn980a9JHJi1hiGi8681LGK8qr4AMANSSOPomz" +
		"d$E4C$d36KKDGn88D4pge808gDOeDuVYUa#zkTczkvAzdv$c#xxodxvxTiu0r0hDmD#x50t" +
		"WOYt4UAb29Qip4xRWBpQXLfvLor2YKeG5ZC0xdXpHr1jUxL1i#nD568mQnE4TiBPQfTvrUB" +
		"e0VYQZ3A1CbE56y4wMQg1uZyPY9UZbjUzAoZFQeHqlIclhDemqmzR8yMbzdj3uc6kGflSdq" +
		"ij#ZNM7gzVPeQKPBSRGC9EDYN8rhS26cubhSY4PSogW9bsEIQT7aZ9jXeg7EFVqQwUeh3CE" +
		"vA44USasawMVipRW5sPYEsr18Enr5ACQjA5Nx433ZDU8gF879M86diGpzyIIUGuRQXKprAr" +
		"RZAUHW85PobODHW7mCGJcUmN04KOKHgC5eZCLuJC14plCYN8BBSANwGUt5TRW1Kp2DzsC6x" +
		"i1it8bxC0TpSH$knpmyYFbu20km48km18zWAPRpBgyYMRnFptA5pmDu0PtfSQufZxFGSyYN" +
		"FTABAolY#egvUtGAgJ5STJfNaS5d5zB0lTAb3kTzwsriDLSmKgpDkz7WjB2uBuFSlz4cyZc" +
		"HvKJPJfHYehwUjf6qphQsHulhKRCYjDLHeeaIFQMfFVOIy6WvtUhLeTHe1TnzXTnHbNogoT" +
		"oRINookaLQsCgxsjfiQVE#9j2MSn$gIECpLjUjvPivYZlIpwk4LnAzJQ$Bz3XwZfbytW6gj" +
		"XcXiyyWzJBss8UhjBZVwbdcPyxVxVAnJzjSDSPghOppJ#MCz3Epps8jNW6NBUMtorOcSOS0" +
		"QnPsxhywaaAIoGIIg$KGJEba3AabJMGQcKTUSzLPG8h9Kj96MibXSfpC8YT81mVUI$ygcI$" +
		"jZee$HRP9V9fKalMIho4RIGFvX8mdcwGidamaaqWZcKocaAba1fb9vf2vf9a4t6kJFXQJ9M" +
		"GbMKL2x#RBv1Mo0h3qM$O6VLnd6ojUV8BqiPgSOJw9Ngzn7#DarnZla3JIGjQHrxlAh5Yfe" +
		"rExTswM7PRdix3waVukRG3989cIpoPP94VoUIIN37Ir6Km6aNp93o45f31ilA6aYC5cSeZn" +
		"CD7zD4Sa3j0Seo#LV1af3$N1PzxXYa#w#m#7AKhyxBFKUp4ilq7yI#HZxIM$qLMdnHM$wOh" +
		"NYdzB$DjXVRU9to9#g$ZGVjqkVaSFSzqfVfVutUBtXDLhZjJULTxkYjU6bHqGVr3y$l$gAx" +
		"pT$mKc9RwrIxDytZfTxmJJ6ZRvopIaLV#eK#flU9BgKCSGCQdTwe0wIewOt45HV$JK#T9OV" +
		"dNKX#G3ifwq6dMTQb7lg$SOSSSfVg7KHsI3Lapv5#HpiidJu$VgEua#umm#LTyoscFoFwkV" +
		"r25rMFsYTgeTKcUxsYRHLl5RnBUhDec#4RzPdL37NIFzQUmVallTs9CqO$abjVX$O1EBVmf" +
		"kmB#561BBVuOO4iipopBdLZNFnMfgGUQ3vdyYYkXpq28#SoQ5g61v1VkgPnrzqkhpgug#Yh" +
		"wINbDcy15z0iNoBBM4A9UFwfOlCQTiYEWmx8Zu8s8vZFuN#nUXNm==");

	static final Action RETURN2 = new Action() {
		public Symbol reduce(Symbol[] _symbols, int offset) {
			return _symbols[offset + 2];
		}
	};

	static final Action RETURN4 = new Action() {
		public Symbol reduce(Symbol[] _symbols, int offset) {
			return _symbols[offset + 4];
		}
	};

	static final Action RETURN3 = new Action() {
		public Symbol reduce(Symbol[] _symbols, int offset) {
			return _symbols[offset + 3];
		}
	};

    ScriptSemantics sema;

    protected void recoverFromError(beaver.Symbol token, beaver.Parser.TokenStream in) throws java.io.IOException, beaver.Parser.Exception
    {
         try {
          super.recoverFromError(token,in);
         } catch (beaver.Parser.Exception e) {
             SuperSqlEvents.getInstance().recoverFailed(e);
             throw e;
         }
    }

	private final Action[] actions;

	public SupersqlParser() {
		super(PARSING_TABLES);
		actions = new Action[] {
			new Action() {	// [0] script = command_seq.s
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_s = _symbols[offset + 1];
					final ScriptActions s = (ScriptActions) _symbol_s.value;
					 sema.addActions(s); return sema;
				}
			},
			new Action() {	// [1] command_seq = command.c DIV command_seq.s
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_c = _symbols[offset + 1];
					final ScriptAction c = (ScriptAction) _symbol_c.value;
					final Symbol _symbol_s = _symbols[offset + 3];
					final ScriptActions s = (ScriptActions) _symbol_s.value;
					 ScriptActions res = ScriptActions.create(c); res.addScriptActions(s); return res;
				}
			},
			new Action() {	// [2] command_seq = command.c
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_c = _symbols[offset + 1];
					final ScriptAction c = (ScriptAction) _symbol_c.value;
					  return ScriptActions.create(c);
				}
			},
			new Action() {	// [3] command = create_table.c
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_c = _symbols[offset + 1];
					final ScriptAction c = (ScriptAction) _symbol_c.value;
					  return c;
				}
			},
			Action.RETURN,	// [4] command = add_column
			Action.RETURN,	// [5] command = drop_column
			new Action() {	// [6] command = 
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new VoidAction();
				}
			},
			new Action() {	// [7] add_column = ALTER TABLE IDENT.t ADD COLUMN column_def.c
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_t = _symbols[offset + 3];
					final String t = (String) _symbol_t.value;
					final Symbol _symbol_c = _symbols[offset + 6];
					final ColumnDefinition c = (ColumnDefinition) _symbol_c.value;
					 return new AddColumnsAction(t, ColumnDefinitions.create(c).getColumnDefinitions());
				}
			},
			new Action() {	// [8] add_column = ALTER TABLE IDENT.t ADD LPAREN columns_def.d RPAREN
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_t = _symbols[offset + 3];
					final String t = (String) _symbol_t.value;
					final Symbol _symbol_d = _symbols[offset + 6];
					final ColumnDefinitions d = (ColumnDefinitions) _symbol_d.value;
					 return new AddColumnsAction(t, d.getColumnDefinitions());
				}
			},
			new Action() {	// [9] drop_column = ALTER TABLE IDENT.t DROP COLUMN IDENT.c
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_t = _symbols[offset + 3];
					final String t = (String) _symbol_t.value;
					final Symbol _symbol_c = _symbols[offset + 6];
					final String c = (String) _symbol_c.value;
					 return new DeleteColumnsAction(t, Columns.create(c).getColumns());
				}
			},
			new Action() {	// [10] drop_column = ALTER TABLE IDENT.t DROP LPAREN columns_list.d RPAREN
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_t = _symbols[offset + 3];
					final String t = (String) _symbol_t.value;
					final Symbol _symbol_d = _symbols[offset + 6];
					final Columns d = (Columns) _symbol_d.value;
					 return new DeleteColumnsAction(t, d.getColumns());
				}
			},
			new Action() {	// [11] columns_list = IDENT.c
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_c = _symbols[offset + 1];
					final String c = (String) _symbol_c.value;
					 return Columns.create(new Column(c));
				}
			},
			new Action() {	// [12] columns_list = IDENT.d COMMA columns_list.l
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_d = _symbols[offset + 1];
					final String d = (String) _symbol_d.value;
					final Symbol _symbol_l = _symbols[offset + 3];
					final Columns l = (Columns) _symbol_l.value;
					 Columns s = Columns.create(new Column(d)); s.addColumns(l); return s;
				}
			},
			new Action() {	// [13] create_table = CREATE TABLE IDENT.i LPAREN columns_def.d COMMA constraint_def.c RPAREN
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_i = _symbols[offset + 3];
					final String i = (String) _symbol_i.value;
					final Symbol _symbol_d = _symbols[offset + 5];
					final ColumnDefinitions d = (ColumnDefinitions) _symbol_d.value;
					final Symbol _symbol_c = _symbols[offset + 7];
					final PrimaryKeyConstraint c = (PrimaryKeyConstraint) _symbol_c.value;
					 return new CreateTableAction(i,d.getColumnDefinitions(), c);
				}
			},
			new Action() {	// [14] create_table = CREATE TABLE IDENT.i LPAREN columns_def.d RPAREN
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_i = _symbols[offset + 3];
					final String i = (String) _symbol_i.value;
					final Symbol _symbol_d = _symbols[offset + 5];
					final ColumnDefinitions d = (ColumnDefinitions) _symbol_d.value;
					 return new CreateTableAction(i,d.getColumnDefinitions());
				}
			},
			new Action() {	// [15] columns_def = column_def.c
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_c = _symbols[offset + 1];
					final ColumnDefinition c = (ColumnDefinition) _symbol_c.value;
					 return ColumnDefinitions.create(c);
				}
			},
			new Action() {	// [16] columns_def = columns_def.d COMMA column_def.c
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_d = _symbols[offset + 1];
					final ColumnDefinitions d = (ColumnDefinitions) _symbol_d.value;
					final Symbol _symbol_c = _symbols[offset + 3];
					final ColumnDefinition c = (ColumnDefinition) _symbol_c.value;
					 d.addColumnDef(c); return d;
				}
			},
			new Action() {	// [17] column_def = IDENT.i type_and_default_value.t null_value.m column_constraint_def
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_i = _symbols[offset + 1];
					final String i = (String) _symbol_i.value;
					final Symbol _symbol_t = _symbols[offset + 2];
					final TypeAndDefaultValue t = (TypeAndDefaultValue) _symbol_t.value;
					final Symbol _symbol_m = _symbols[offset + 3];
					final MandatorySymbol m = (MandatorySymbol) _symbol_m.value;
					 return new ColumnDefinition(i, t.getTypeDefinition(), false, m.isMandatory()) ;
				}
			},
			new Action() {	// [18] column_def = IDENT.i type_and_default_value.t null_value.m default_value column_constraint_def
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_i = _symbols[offset + 1];
					final String i = (String) _symbol_i.value;
					final Symbol _symbol_t = _symbols[offset + 2];
					final TypeAndDefaultValue t = (TypeAndDefaultValue) _symbol_t.value;
					final Symbol _symbol_m = _symbols[offset + 3];
					final MandatorySymbol m = (MandatorySymbol) _symbol_m.value;
					 return new ColumnDefinition(i, t.getTypeDefinition(), false, m.isMandatory()) ;
				}
			},
			new Action() {	// [19] type_and_default_value = type.t
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_t = _symbols[offset + 1];
					final TypeDefinition t = (TypeDefinition) _symbol_t.value;
					 return new TypeAndDefaultValue(t);
				}
			},
			new Action() {	// [20] type_and_default_value = type.t default_value.d
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_t = _symbols[offset + 1];
					final TypeDefinition t = (TypeDefinition) _symbol_t.value;
					final Symbol _symbol_d = _symbols[offset + 2];
					final Object d = (Object) _symbol_d.value;
					 return new TypeAndDefaultValue(t,d);
				}
			},
			RETURN2,	// [21] default_value = DEFAULT INTEGER_LITERAL; returns 'INTEGER_LITERAL' although none is marked
			RETURN2,	// [22] default_value = DEFAULT FLOATING_POINT_LITERAL; returns 'FLOATING_POINT_LITERAL' although none is marked
			RETURN2,	// [23] default_value = DEFAULT STRING_LITERAL; returns 'STRING_LITERAL' although none is marked
			RETURN2,	// [24] column_constraint_def = CONSTRAINT IDENT.c column_constraint_body
			Action.NONE,  	// [25] column_constraint_def = 
			RETURN4,	// [26] column_constraint_body = CHECK LPAREN expression RPAREN; returns 'RPAREN' although none is marked
			Action.RETURN,	// [27] string_list = STRING_LITERAL
			RETURN3,	// [28] string_list = STRING_LITERAL COMMA string_list.l
			Action.RETURN,	// [29] value_list = value
			RETURN3,	// [30] value_list = value COMMA value_list; returns 'value_list' although none is marked
			Action.RETURN,	// [31] value = INTEGER_LITERAL
			Action.RETURN,	// [32] value = FLOATING_POINT_LITERAL
			new Action() {	// [33] null_value = NULL
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new MandatorySymbol(false);
				}
			},
			new Action() {	// [34] null_value = NOT NULL
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new MandatorySymbol(true);
				}
			},
			new Action() {	// [35] null_value = 
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new MandatorySymbol(false);
				}
			},
			new Action() {	// [36] constraint_def = CONSTRAINT IDENT.c PRIMARY KEY LPAREN columns_list.l RPAREN
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_c = _symbols[offset + 2];
					final String c = (String) _symbol_c.value;
					final Symbol _symbol_l = _symbols[offset + 6];
					final Columns l = (Columns) _symbol_l.value;
					 return new PrimaryKeyConstraint(c,l);
				}
			},
			new Action() {	// [37] type = INT
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new TypeDefinition(Type.INT);
				}
			},
			new Action() {	// [38] type = CLOB
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new TypeDefinition(Type.CLOB);
				}
			},
			new Action() {	// [39] type = TIMESTAMP LPAREN INTEGER_LITERAL.i RPAREN
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_i = _symbols[offset + 3];
					final Integer i = (Integer) _symbol_i.value;
					 return new TimestampTypeDefinition(i);
				}
			},
			new Action() {	// [40] type = DATE
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new DateTypeDefinition();
				}
			},
			new Action() {	// [41] type = CHAR LPAREN INTEGER_LITERAL RPAREN
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new TypeDefinition(Type.CHAR);
				}
			},
			new Action() {	// [42] type = NUMBER LPAREN INTEGER_LITERAL.i RPAREN
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_i = _symbols[offset + 3];
					final Integer i = (Integer) _symbol_i.value;
					 return new NumberTypeDefinition(i);
				}
			},
			new Action() {	// [43] type = LONG LPAREN INTEGER_LITERAL.n RPAREN
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_n = _symbols[offset + 3];
					final Integer n = (Integer) _symbol_n.value;
					 return new VarcharTypeDefinition(n);
				}
			},
			new Action() {	// [44] type = FLOAT
				public Symbol reduce(Symbol[] _symbols, int offset) {
					 return new TypeDefinition(Type.FLOAT);
				}
			},
			new Action() {	// [45] type = VARCHAR LPAREN INTEGER_LITERAL.n RPAREN
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_n = _symbols[offset + 3];
					final Integer n = (Integer) _symbol_n.value;
					 return new VarcharTypeDefinition(n);
				}
			},
			new Action() {	// [46] type = NVARCHAR LPAREN INTEGER_LITERAL.n RPAREN
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_n = _symbols[offset + 3];
					final Integer n = (Integer) _symbol_n.value;
					 return new NVarcharTypeDefinition(n);
				}
			},
			RETURN3,	// [47] expression = expression OR expression; returns 'expression' although none is marked
			RETURN3,	// [48] expression = expression AND expression; returns 'expression' although none is marked
			RETURN3,	// [49] expression = LPAREN expression RPAREN; returns 'RPAREN' although none is marked
			Action.RETURN,	// [50] expression = IDENT.i IN LPAREN string_list RPAREN
			Action.RETURN,	// [51] expression = IDENT.i IN LPAREN columns_list RPAREN
			Action.RETURN,	// [52] expression = IDENT.i IN LPAREN value_list RPAREN
			Action.RETURN,	// [53] expression = IDENT.i BETWEEN expr AND expr
			Action.RETURN,	// [54] expression = IDENT.i IS null_value
			RETURN3,	// [55] expression = expr LT expr; returns 'expr' although none is marked
			RETURN3,	// [56] expression = expr GT expr; returns 'expr' although none is marked
			RETURN3,	// [57] expression = expr GE expr; returns 'expr' although none is marked
			RETURN3,	// [58] expression = expr LE expr; returns 'expr' although none is marked
			RETURN3,	// [59] expression = expr EQ expr; returns 'expr' although none is marked
			new Action() {	// [60] expr = expr.a MULT expr.b
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_a = _symbols[offset + 1];
					final Expr a = (Expr) _symbol_a.value;
					final Symbol _symbol_b = _symbols[offset + 3];
					final Expr b = (Expr) _symbol_b.value;
					 return new Expr(a.val * b.val);
				}
			},
			new Action() {	// [61] expr = expr.a DIV expr.b
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_a = _symbols[offset + 1];
					final Expr a = (Expr) _symbol_a.value;
					final Symbol _symbol_b = _symbols[offset + 3];
					final Expr b = (Expr) _symbol_b.value;
					 return new Expr(a.val / b.val);
				}
			},
			new Action() {	// [62] expr = expr.a PLUS expr.b
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_a = _symbols[offset + 1];
					final Expr a = (Expr) _symbol_a.value;
					final Symbol _symbol_b = _symbols[offset + 3];
					final Expr b = (Expr) _symbol_b.value;
					 return new Expr(a.val + b.val);
				}
			},
			new Action() {	// [63] expr = expr.a MINUS expr.b
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_a = _symbols[offset + 1];
					final Expr a = (Expr) _symbol_a.value;
					final Symbol _symbol_b = _symbols[offset + 3];
					final Expr b = (Expr) _symbol_b.value;
					 return new Expr(a.val - b.val);
				}
			},
			new Action() {	// [64] expr = LPAREN expr.e RPAREN
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_e = _symbols[offset + 2];
					final Expr e = (Expr) _symbol_e.value;
					 return e;
				}
			},
			new Action() {	// [65] expr = INTEGER_LITERAL.n
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_n = _symbols[offset + 1];
					final Integer n = (Integer) _symbol_n.value;
					 return new Expr(n);
				}
			},
			new Action() {	// [66] expr = FLOATING_POINT_LITERAL.n
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_n = _symbols[offset + 1];
					final Float n = (Float) _symbol_n.value;
					 return new Expr(n);
				}
			},
			new Action() {	// [67] expr = IDENT.i
				public Symbol reduce(Symbol[] _symbols, int offset) {
					final Symbol _symbol_i = _symbols[offset + 1];
					final String i = (String) _symbol_i.value;
					 return new Expr(0);
				}
			}
		};


    this.sema = new ScriptSemantics();
    this.report = SuperSqlEvents.getInstance();
	}

	protected Symbol invokeReduceAction(int rule_num, int offset) {
		return actions[rule_num].reduce(_symbols, offset);
	}
}

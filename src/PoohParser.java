import lib.Parser;
import lib.Token;
import lib.ast.ASTNode;

import java.util.List;

public class PoohParser {
  /**
   * 从一系列token中解析一个Pooh程序，获得一棵抽象语法树
   *
   * @param tokens 从词法解析器得到的tokens
   * @return 解析出的抽象语法树AST
   */
  public ASTNode parse(List<Token> tokens) {
    var program = new Parser("<program>");
    var statement = new Parser("<statement>");
    var printStatement = new Parser("<print-statement>");
    var assignStatement = new Parser("<assign-statement>");
    var ifStatement = new Parser("<if-statement>");
    var block = new Parser("<block>");
    var expr = new Parser("<expr>");
    var term = new Parser("<term>");
    var moreTerms = new Parser("<more-terms>");
    var whilestatement = new Parser("<while-statement>");
    var functiondef = new Parser("<function-def>");
    var functioncall = new Parser("<function-call>");
    var paramlist = new Parser("<param-list>");
    var functionstatements = new Parser("<function-statements>");
    var returnstatement = new Parser("<return-statement>");
    var moreparams = new Parser("<more-params>");
    var arglist = new Parser("<arg-list>");
    var moreargs = new Parser("<more-args>");
    var assignprefix = new Parser("<assign-prefix>");
    var assignsuffix = new Parser("<assign-suffix>");
    var closure = new Parser("<closure>");
    program.nonTerminal(statement).nonTerminal(program).orEpsilon();
    functiondef.terminal( "KEYWORD_FUNC").terminal("ID").terminal("LEFT_PAR").nonTerminal(paramlist).terminal("RIGHT_PAR").terminal("LEFT_BRACE").nonTerminal(functionstatements).terminal("RIGHT_BRACE");
    functionstatements.nonTerminal(returnstatement).or().nonTerminal(program).nonTerminal(returnstatement);
    returnstatement.terminal("KEYWORD_RETURN").nonTerminal(expr);
    paramlist.terminal("ID").nonTerminal(moreparams).orEpsilon();
    statement.nonTerminal(printStatement).or().nonTerminal(assignStatement).or().nonTerminal(ifStatement).or().nonTerminal(whilestatement).or().nonTerminal(functiondef).or().nonTerminal(functioncall);
    whilestatement.terminal("KEYWORD_WHILE").terminal("LEFT_PAR").nonTerminal(expr).terminal("RIGHT_PAR").nonTerminal(block);
    expr.nonTerminal(term).nonTerminal(moreTerms);
    moreparams.terminal("COMMA").nonTerminal(paramlist).orEpsilon();
    functioncall.terminal("KEYWORD_CALL").terminal("ID").terminal("LEFT_PAR").nonTerminal(arglist).terminal("RIGHT_PAR");
    arglist.nonTerminal(expr).nonTerminal(moreargs).orEpsilon();
    moreargs.terminal("COMMA").nonTerminal(arglist).orEpsilon();
    moreTerms.terminal("PLUS").nonTerminal(expr).or().terminal("LESS_THAN").nonTerminal(term).or().terminal("EQUAL_TEST").nonTerminal(term).orEpsilon();
    term.terminal("NUMBER").or().terminal("ID").or().nonTerminal(functioncall);
    printStatement.terminal("KEYWORD_PRINT").nonTerminal(expr);
    assignStatement.nonTerminal(assignprefix).nonTerminal(assignsuffix);
    assignprefix.terminal("ID").terminal("EQUAL");
    assignsuffix.nonTerminal(expr).or().nonTerminal(closure);
    closure.terminal("KEYWORD_FUNC").terminal("LEFT_PAR").nonTerminal(paramlist).terminal("RIGHT_PAR").terminal("LEFT_BRACE").nonTerminal(functionstatements).terminal("RIGHT_BRACE");
    ifStatement.terminal("KEYWORD_IF").terminal("LEFT_PAR").nonTerminal(expr).terminal("RIGHT_PAR").nonTerminal(block).terminal("KEYWORD_ELSE").nonTerminal(block);
    block.terminal("LEFT_BRACE").nonTerminal(program).terminal("RIGHT_BRACE");
    var root = program.parse(tokens);
    return root;
  }
}

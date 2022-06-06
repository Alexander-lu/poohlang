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
    program.nonTerminal(statement).nonTerminal(program).orEpsilon();
    statement.nonTerminal(printStatement).or().nonTerminal(assignStatement).or().nonTerminal(ifStatement);
    expr.nonTerminal(term).nonTerminal(moreTerms);
    moreTerms.terminal("PLUS").nonTerminal(expr).or().terminal("LESS_THAN").nonTerminal(term).or().terminal("EQUAL_TEST").nonTerminal(term).orEpsilon();
    term.terminal("NUMBER").or().terminal("ID");
    printStatement.terminal("KEYWORD_PRINT").nonTerminal(expr);
    assignStatement.terminal("ID").terminal("EQUAL").nonTerminal(expr);
    ifStatement.terminal("KEYWORD_IF").terminal("LEFT_PAR").nonTerminal(expr).terminal("RIGHT_PAR").nonTerminal(block).terminal("KEYWORD_ELSE").nonTerminal(block);
    block.terminal("LEFT_BRACE").nonTerminal(program).terminal("RIGHT_BRACE");
    var root = program.parse(tokens);
    return root;
  }
}

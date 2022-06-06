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
    program.nonTerminal(statement).nonTerminal(program).orEpsilon();
    statement.nonTerminal(printStatement).or().nonTerminal(assignStatement).or().nonTerminal(ifStatement);
    printStatement.terminal("KEYWORD_PRINT").terminal("ID");
    assignStatement.terminal("ID").terminal("EQUAL").terminal("NUMBER");
    ifStatement.terminal("KEYWORD_IF").terminal("LEFT_PAR").terminal("ID").terminal("LESS_THAN").terminal("ID").terminal("RIGHT_PAR").nonTerminal(block).terminal("KEYWORD_ELSE").nonTerminal(block);
    block.terminal("LEFT_BRACE").nonTerminal(program).terminal("RIGHT_BRACE");
    var root = program.parse(tokens);
    return root;
  }
}

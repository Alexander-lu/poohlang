import lib.Parser;
import lib.PrintHelper;
import lib.Token;
import lib.ast.ASTNode;
import lib.ast.InnerNode;

import java.util.List;

public class EnglishParser {
  public ASTNode parse(List<Token> tokens) {
    var englishParser = new Parser("<sentence>");
    var object = new Parser("<object>");
    englishParser.terminal("I").terminal("VERB").nonTerminal(object);
    object.terminal("NOUN").or().terminal("PRONOUN").orEpsilon();
    var root = englishParser.parse(tokens);
    return root;
  }

  public ASTNode parseMatchingParenthesis(List<Token> tokens) {
    return null;
  }
}

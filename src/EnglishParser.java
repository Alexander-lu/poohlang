import lib.Parser;
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
    var englishParser = new Parser("<matching-parenthesis>");
    englishParser.terminal("LEFT_PAR");
    englishParser.terminal("RIGHT_PAR");
    englishParser.orEpsilon();
    InnerNode parse = (InnerNode)englishParser.parse(tokens);
//    if (parseMatchingParenthesis(tokens)) {
//    }
    parse.addChild(parseMatchingParenthesis(tokens));
    parse.addChild(parseMatchingParenthesis(tokens));
    return parse;
  }
}

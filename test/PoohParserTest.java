import lib.PrintHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PoohParserTest {

  @Test
  void testIfGrammar1() {
    var expected = TestHelper.readFromResourceFile("IfGrammar1.txt").trim();
    var parser = new PoohParser();
    var tokens = new PoohLexer().lex("IfGrammar1.pooh");
    var root = parser.parse(tokens);
    var actual = PrintHelper.astToString(root).replaceAll("\\r\\n?", "\n").trim();
    assertEquals(expected, actual);
  }

  @Test
  void testIfGrammar2() {
    var expected = TestHelper.readFromResourceFile("IfGrammar2.txt").trim();
    var parser = new PoohParser();
    var tokens = new PoohLexer().lex("IfGrammar2.pooh");
    var root = parser.parse(tokens);
    var actual = PrintHelper.astToString(root).replaceAll("\\r\\n?", "\n").trim();
    assertEquals(expected, actual);
  }
  @Test
  void showASTNode() {
    var expected = TestHelper.readFromResourceFile("test1.txt").trim();
    var parser = new PoohParser();
    var tokens = new PoohLexer().lex("ClosureSample3.pooh");
    var root = parser.parse(tokens);
    var actual = PrintHelper.astToString(root).replaceAll("\\r\\n?", "\n").trim();
    assertEquals(expected, actual);
  }
}
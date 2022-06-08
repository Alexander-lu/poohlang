import lib.PrintHelper;
import lib.Token;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnglishParserTest {

  @Test
  void testIVerbNoun() {
    var englishParser = new EnglishParser();
    var expected = TestHelper.readFromResourceFile("IVerbNoun1.txt").trim();
    var tokens = TestHelper.tokensOf(new String[]{"I", "VERB", "NOUN"}, new String[]{"I", "love", "Java"});
    var root = englishParser.parse(tokens);
    var actual = PrintHelper.astToString(root).replaceAll("\\r\\n?", "\n").trim();
    assertEquals(expected, actual);

    expected = TestHelper.readFromResourceFile("IVerbNoun2.txt").trim();
    tokens = TestHelper.tokensOf(new String[]{"I", "VERB", "NOUN"}, new String[]{"I", "hate", "Java"});
    root = englishParser.parse(tokens);
    actual = PrintHelper.astToString(root).replaceAll("\\r\\n?", "\n").trim();
    assertEquals(expected, actual);

    tokens = TestHelper.tokensOf(new String[]{"I", "VERB", "VERB"}, new String[]{"I", "hate", "cry"});
    List<Token> finalTokens = tokens;
    assertThrows(RuntimeException.class, () -> englishParser.parse(finalTokens));
  }

  @Test
  void testIVerbObject() {
    var englishParser = new EnglishParser();
    var expected = TestHelper.readFromResourceFile("IVerbObject1.txt").trim();
    var tokens = TestHelper.tokensOf(new String[]{"I", "VERB", "NOUN"}, new String[]{"I", "love", "object"});
    var root = englishParser.parse(tokens);
    var actual = PrintHelper.astToString(root).replaceAll("\\r\\n?", "\n").trim();
    assertEquals(expected, actual);

    expected = TestHelper.readFromResourceFile("IVerbObject2.txt").trim();
    tokens = TestHelper.tokensOf(new String[]{"I", "VERB", "PRONOUN"}, new String[]{"I", "love", "her"});
    root = englishParser.parse(tokens);
    actual = PrintHelper.astToString(root).replaceAll("\\r\\n?", "\n").trim();
    assertEquals(expected, actual);

    tokens = TestHelper.tokensOf(new String[]{"I", "VERB"}, new String[]{"I", "cry"});
    List<Token> finalTokens = tokens;
    assertThrows(RuntimeException.class, () -> englishParser.parse(finalTokens));
  }

  @Test
  void testEpsilon() {
    var englishParser = new EnglishParser();
    var expected = TestHelper.readFromResourceFile("IVerbObject1.txt").trim();
    var tokens = TestHelper.tokensOf(new String[]{"I", "VERB", "NOUN"}, new String[]{"I", "love", "object"});
    var root = englishParser.parse(tokens);
    var actual = PrintHelper.astToString(root).replaceAll("\\r\\n?", "\n").trim();
    assertEquals(expected, actual);

    expected = TestHelper.readFromResourceFile("IVerbEpsilon.txt").trim();
    tokens = TestHelper.tokensOf(new String[]{"I", "VERB"}, new String[]{"I", "cry"});
    root = englishParser.parse(tokens);
    actual = PrintHelper.astToString(root).replaceAll("\\r\\n?", "\n").trim();
    assertEquals(expected, actual);
  }

  @Test
  void testMatchingParenthesis() {
    var englishParser = new EnglishParser();
    var expected = TestHelper.readFromResourceFile("MatchingParenthesis1.txt").trim();
    var tokens = TestHelper.tokensOf(new String[]{"LEFT_PAR", "RIGHT_PAR"}, new String[]{"(", ")"});
    var root = englishParser.parseMatchingParenthesis(tokens);
    var actual = PrintHelper.astToString(root).replaceAll("\\r\\n?", "\n").trim();
    assertEquals(expected, actual);

    expected = TestHelper.readFromResourceFile("MatchingParenthesis2.txt").trim();
    tokens = TestHelper.tokensOf(
        new String[]{"LEFT_PAR", "RIGHT_PAR", "LEFT_PAR", "RIGHT_PAR"},
        new String[]{"(", ")", "(", ")"}
    );
    root = englishParser.parseMatchingParenthesis(tokens);
    actual = PrintHelper.astToString(root).replaceAll("\\r\\n?", "\n").trim();
    assertEquals(expected, actual);

    expected = TestHelper.readFromResourceFile("MatchingParenthesis3.txt").trim();
    tokens = TestHelper.tokensOf(
        new String[]{"LEFT_PAR", "LEFT_PAR", "RIGHT_PAR", "RIGHT_PAR", "LEFT_PAR", "RIGHT_PAR"},
        new String[]{"(", "(", ")", ")", "(", ")"}
    );
    root = englishParser.parseMatchingParenthesis(tokens);
    actual = PrintHelper.astToString(root).replaceAll("\\r\\n?", "\n").trim();
    assertEquals(expected, actual);
  }
}
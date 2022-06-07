import lib.Parser;
import lib.PrintHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserExamples {

  @Test
  void testICry() {
    var englishParser = new Parser("<sentence>");
    englishParser.terminal("I").terminal("VERB").terminal("NOUN");
    var tokens = TestHelper.tokensOf(new String[]{"I", "VERB"}, new String[]{"I", "cry"});
    var root = englishParser.parse(tokens);
    PrintHelper.printAST(root);
  }

  @Test
  void testIQuit() {
    var englishParser = new Parser("<sentence>");
    englishParser.terminal("I").terminal("VERB");
    var tokens = TestHelper.tokensOf(new String[]{"I", "VERB"}, new String[]{"I", "quit"});
    var root = englishParser.parse(tokens);
    PrintHelper.printAST(root);
  }

  @Test
  void testParsingError() {
    var englishParser = new Parser("<sentence>");
    englishParser.terminal("I").terminal("VERB");
    var tokens = TestHelper.tokensOf(new String[]{"I", "I"}, new String[]{"I", "I"});
    assertThrows(RuntimeException.class, () -> englishParser.parse(tokens));
  }

  /**
   * <pre>{@code
   *
   * <today-message>  := TODAY IS <day-info>
   * <day-info>       := <date>
   *                   | WEEKDAY
   * <date>           := MONTH DAY
   *
   * }
   * </pre>
   */
  @Test
  void testTodayMessage() {
    var todayMessage = new Parser("<today-message>");
    var dayInfo = new Parser("<day-info>");
    var date = new Parser("<date>");

    todayMessage.terminal("TODAY").terminal("IS").nonTerminal(dayInfo);
    dayInfo.nonTerminal(date).or().terminal("WEEKDAY");
    date.terminal("MONTH").terminal("DAY");

    var tokens = TestHelper.tokensOf(
        new String[]{"TODAY", "IS", "MONTH", "DAY"},
        new String[]{"Today", "is", "January", "1st"}
    );
    var ast = todayMessage.parse(tokens);
    PrintHelper.printAST(ast);

    tokens = TestHelper.tokensOf(
        new String[]{"TODAY", "IS", "WEEKDAY"},
        new String[]{"Today", "is", "Monday"}
    );
    ast = todayMessage.parse(tokens);
    PrintHelper.printAST(ast);
  }

}
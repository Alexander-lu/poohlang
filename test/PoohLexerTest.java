import lib.Token;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PoohLexerTest {

  @Test
  void test1() {
//    runTest("LexingSample1.pooh", "LexingSample1.txt");
    runTest("ExprSample1.pooh", "LexingSample1.txt");
  }

  @Test
  void test2() {
    runTest("LexingSample2.pooh", "LexingSample2.txt");
  }

  private void runTest(String inputFilename, String outputFilename) {
    var lexer = new PoohLexer();
    var actual = lexer.lex(inputFilename);
    var expected = readTokensFromResourceFile(outputFilename);
    assertEquals(expected, actual);
  }

  /**
   * 从资源目录读取token
   *
   * @param filename 要读取的文件名
   * @return 读取到的token序列
   */
  private List<Token> readTokensFromResourceFile(String filename) {
    return Arrays.stream(TestHelper.readFromResourceFile(filename).trim().split("\\R"))
        .map(line -> {
              var pair = line.split("\\s+");
              return new Token(pair[0], pair[1]);
            }
        ).collect(Collectors.toList());
  }
}
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PoohInterpreterTest {

  @Test
  void testIf() {
    runTest("IfGrammar1.pooh", "IfGrammar1.out");
  }

  @Test
  void testExpr() {
    runTest("ExprSample1.pooh", "ExprSample1.out");
  }

  @Test
  void testWhile() {
    runTest("WhileSample1.pooh", "WhileSample1.out");
  }

  @Test
  void testFunc() {
    runTest("FuncSample1.pooh", "FuncSample1.out");
  }

  @Test
  void testNestedScope() {
    runTest("NestedScopeSample1.pooh", "NestedScopeSample1.out");
//    runTest("NestedScopeSample2.pooh", "NestedScopeSample2.out");
  }

  @Test
  void testClosure() {
//    runTest("ClosureSample1.pooh", "ClosureSample1.out");
    runTest("ClosureSample2.pooh", "ClosureSample2.out");
  }

  @Test
  void testOOP() {
    runTest("OOPSample1.pooh", "OOPSample1.out");
    runTest("OOPSample2.pooh", "OOPSample2.out");
  }

  private void runTest(String sourceFilename, String expectedOutputFilename) {
    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    var stdOut = System.out;
    System.setOut(new PrintStream(outputStreamCaptor));

    var expected = TestHelper.readFromResourceFile(expectedOutputFilename).trim();
    runProgram(sourceFilename);
    var actual = outputStreamCaptor.toString().replaceAll("\\r\\n?", "\n").trim();
    assertEquals(expected, actual);

    System.setOut(stdOut);
  }

  private void runProgram(String filename) {
    var lexer = new PoohLexer();
    var tokens = lexer.lex(filename);
    var parser = new PoohParser();
    var ast = parser.parse(tokens);
    var interpreter = new PoohInterpreter();
    interpreter.run(ast);
  }
}
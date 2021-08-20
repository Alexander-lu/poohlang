package lib;

import lib.ast.ASTNode;
import lib.ast.EpsilonNode;
import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.ArrayList;
import java.util.List;

public class Parser implements ParserInterface {
  /**
   * 本parser的名字，即BNF记法中等号的左边
   */
  private final String name;

  /**
   * 本parser对应的所有句子
   */
  private final List<List<ParserInterface>> sentences = new ArrayList<>();

  /**
   * 当前句子的下标。“当前”是指在根据BNF语法写parser时，等号右边某个正在生成的句子。
   */
  private int currIdx = -1;

  /**
   * 用于解析终结符的parser
   */
  private static class TerminalParser implements ParserInterface {

    /**
     * 终结符的token标签
     */
    String tokenTag;

    /**
     * @param tokenTag 终结符的类型
     */
    public TerminalParser(String tokenTag) {
      this.tokenTag = tokenTag;
    }

    /**
     * 检查下一个token是否是一个能够被当前parser解析的终结符token
     *
     * @param tokens token序列
     * @return 能够解析返回true，否则返回false
     */
    @Override
    public boolean match(List<Token> tokens) {
      if (tokens.size() == 0) {
        return false;
      } else {
        return tokenTag.equals(tokens.get(0).getTag());
      }
    }

    /**
     * 从token序列中解析出一个AST终结符节点
     *
     * @param tokens token序列
     * @return 解析出的AST终结符节点
     */
    @Override
    public ASTNode parse(List<Token> tokens) {
      var token = tokens.get(0);
      return new LeafNode(token);
    }
  }

  /**
   * 用于解析空句子的parser
   */
  private static class EpsilonParser implements ParserInterface {

    /**
     * @param tokens token序列
     * @return 总是true，因为空句子可以从任何token序列中解析到
     */
    @Override
    public boolean match(List<Token> tokens) {
      return true;
    }

    /**
     * 从token序列中解析出一个AST空句子节点。注意，空句子的{@code tokenCount}为0。
     *
     * @param tokens token序列
     * @return 解析出的AST空句子节点
     */
    @Override
    public ASTNode parse(List<Token> tokens) {
      return new EpsilonNode();
    }
  }

  /**
   * @param name 本parser所对应的BNF语法规则的名字，一般为等号左边的符号，是一个非终结符
   */
  public Parser(String name) {
    this.name = name;
  }

  /**
   * 向当前parser添加一个终结符
   *
   * @param tokenTag 终结符的类型
   * @return 更新之后的当前parser
   */
  public Parser terminal(String tokenTag) {
    if (sentences.size() == 0) {
      sentences.add(new ArrayList<>());
      currIdx = 0;
    }
    sentences.get(currIdx).add(new TerminalParser(tokenTag));
    return this;
  }

  /**
   * 向当前parser添加一个非终结符
   *
   * @param parser 非终结符对应的parser
   * @return 更新之后的当前parser
   */
  public Parser nonTerminal(ParserInterface parser) {
    if (sentences.size() == 0) {
      sentences.add(new ArrayList<>());
      currIdx = 0;
    }
    sentences.get(currIdx).add(parser);
    return this;
  }

  /**
   * 向当前parser添加一个{@code |}，即或
   *
   * @return 更新之后的当前parser
   */
  public Parser or() {
    sentences.add(new ArrayList<>());
    currIdx++;
    return this;
  }

  /**
   * 向当前parser添加一个句子，为一个空句子
   */
  public void orEpsilon() {
    or();
    sentences.get(currIdx).add(new EpsilonParser());
  }

  /**
   * 判断下一个token是否能够被当前parser解析
   *
   * @param tokens token序列
   * @return 能够解析返回true，否则返回false
   */
  @Override
  public boolean match(List<Token> tokens) {
    for (var sentence : sentences) {
      var parser = sentence.get(0);
      if (parser.match(tokens)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 从token序列中解析出一个抽象语法树（AST）节点
   *
   * @param tokens token序列
   * @return 解析出的AST节点
   */
  @Override
  public ASTNode parse(List<Token> tokens) {
    InnerNode node = new InnerNode(name);
    for (var sentence : sentences) {
      if (sentence.get(0).match(tokens)) {
        for (var parser : sentence) {
          var subList = tokens.subList(node.getTokenCount(), tokens.size());
          if (!parser.match((subList))) {
            throw new RuntimeException("解析错误：无法处理token：" + subList.get(0).toString());
          }
          node.addChild(parser.parse(subList));
        }
        break;
      }
    }
    return node;
  }
}
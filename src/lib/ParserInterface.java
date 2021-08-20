package lib;

import lib.ast.ASTNode;

import java.util.List;

interface ParserInterface {
  /**
   * 判断下一个token是否能够被当前parser解析
   *
   * @param tokens token序列
   * @return 能够解析返回true，否则返回false
   */
  boolean match(List<Token> tokens);

  /**
   * 从token序列中解析出一个抽象语法树（AST）节点
   *
   * @param tokens token序列
   * @return 解析出的AST节点
   */
  ASTNode parse(List<Token> tokens);
}

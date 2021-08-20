package lib.ast;

import lib.Token;

/**
 * AST中的叶子节点，直接包含了一个Token
 */
public final class LeafNode implements ASTNode {
  private final Token token;

  public LeafNode(Token token) {
    this.token = token;
  }

  public String getTokenTag() {
    return token.getTag();
  }

  public String getTokenText() {
    return token.getText();
  }
}

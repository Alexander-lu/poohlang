package lib.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * AST中的内部节点
 */
public final class InnerNode implements ASTNode {
  /**
   * BNF等号左边的名字
   */
  private final String astName;

  /**
   * 本AST节点包含的token数量。
   */
  private int tokenCount;

  /**
   * 本AST节点的所有子节点
   */
  List<ASTNode> children = new ArrayList<>();

  public InnerNode(String astName) {
    this.astName = astName;
  }

  /**
   * 向本AST节点添加一个子节点
   *
   * @param child 要添加的子节点
   */
  public void addChild(ASTNode child) {
    children.add(child);
    if (child instanceof LeafNode) {
      tokenCount += 1;
    } else if (child instanceof InnerNode) {
      tokenCount += ((InnerNode) child).tokenCount;
    }
  }

  public String getAstName() {
    return astName;
  }

  public List<ASTNode> getChildren() {
    return children;
  }

  public int getTokenCount() {
    return tokenCount;
  }

  @Override
  public ASTNode getChild(int index) {
    if (children.size() <= index) {
      throw new RuntimeException("越界：请求下标为" + index + "的叶子节点，但该节点仅有" + children.size() + "个叶子节点");
    }
    return children.get(index);
  }
}

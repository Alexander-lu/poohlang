package lib.ast;

public interface ASTNode {
  default ASTNode getChild(int index) {
    throw new RuntimeException("无法获取子节点");
  }
}

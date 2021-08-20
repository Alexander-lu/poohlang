package lib;

import lib.ast.ASTNode;
import lib.ast.EpsilonNode;
import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 一个专门用来打印的辅助类
 */
public final class PrintHelper {
  /**
   * 私有构造函数，使得外界不可以创建这个类的实例
   */
  private PrintHelper() {
  }

  /**
   * 用于在控制台格式化地输出一棵树
   * Credit: https://stackoverflow.com/a/8948691
   */
  private static class PrinterTreeNode {

    final String name;
    final List<PrinterTreeNode> children;

    public PrinterTreeNode(String name, List<PrinterTreeNode> children) {
      this.name = name;
      this.children = children;
    }

    @Override
    public String toString() {
      StringBuilder buffer = new StringBuilder(50);
      toStringHelper(buffer, "", "");
      return buffer.toString();
    }

    private void toStringHelper(StringBuilder buffer, String prefix, String childrenPrefix) {
      buffer.append(prefix);
      buffer.append(name);
      buffer.append(System.lineSeparator());
      for (Iterator<PrinterTreeNode> it = children.iterator(); it.hasNext(); ) {
        PrinterTreeNode next = it.next();
        if (it.hasNext()) {
          next.toStringHelper(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
        } else {
          next.toStringHelper(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
        }
      }
    }
  }

  /**
   * 将一棵AST转化为一棵{@link PrinterTreeNode}树，从而可以格式化打印
   *
   * @param node AST的根节点
   * @return 转换之后的根节点
   */
  private static PrinterTreeNode nodeToTreeNode(ASTNode node) {
    if (node instanceof EpsilonNode) {
      return new PrinterTreeNode("EPSILON", new ArrayList<>());
    } else if (node instanceof LeafNode) {
      var leafNode = (LeafNode) node;
      return new PrinterTreeNode(String.format("%s: %s", leafNode.getTokenTag(), leafNode.getTokenText()), new ArrayList<>());
    } else {
      var innerNode = (InnerNode) node;
      var children = new ArrayList<PrinterTreeNode>();
      for (var child : innerNode.getChildren()) {
        children.add(nodeToTreeNode(child));
      }
      return new PrinterTreeNode(innerNode.getAstName(), children);
    }
  }

  /**
   * 将一棵AST转成字符串标示
   *
   * @param root AST的根节点
   * @return 转换后的字符串
   */
  public static String astToString(ASTNode root) {
    return nodeToTreeNode(root).toString();
  }

  /**
   * 打印一棵AST
   *
   * @param root AST的根节点
   */
  public static void printAST(ASTNode root) {
    System.out.println(astToString(root));
  }

  /**
   * 逐行打印tokens
   *
   * @param tokens 想要打印的token列表
   */
  public static void printTokens(List<Token> tokens) {
    tokens.forEach(token -> {
      System.out.printf("%-20s%s%n", token.getTag(), token.getTag());
    });
  }
}
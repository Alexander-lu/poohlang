import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.HashMap;
import java.util.Map;

public class IfStatement {
    public static void ifStatement(InnerNode statementChoices, Node1 classNode, Map<String, Object> tempScope, String className, String closureName){
        InnerNode expr = (InnerNode) statementChoices.getChild(2);
        if (ifExpr(expr,classNode,tempScope,className)) {
            Class1.readASTNode(statementChoices.getChild(4).getChild(1),classNode,tempScope,className,closureName);
        } else {
            Class1.readASTNode(statementChoices.getChild(6).getChild(1),classNode,tempScope,className,closureName);
        }
    }
    public static boolean ifExpr(InnerNode expr,Node1 classNode,Map<String, Object> tempScope,String className){
        int left = 0;
        int right = 0;
        //还需要考虑numberorid是<function-call>的情况
        LeafNode numberorid = (LeafNode) expr.getChild(0).getChild(0);
        if (numberorid.getTokenTag().equals("NUMBER")) {
            left = Integer.parseInt(numberorid.getTokenText());
        }
        if (numberorid.getTokenTag().equals("ID")) {
            if(classNode.get变量集合().containsKey(numberorid.getTokenText())){
                left = classNode.获取成员变量值(numberorid.getTokenText());
            }else {
                left = (int) tempScope.get(numberorid.getTokenText());
            }
        }
        LeafNode rightTerm = (LeafNode) expr.getChild(1).getChild(1).getChild(0);
        if (rightTerm.getTokenTag().equals("ID")) {
            if(classNode.get变量集合().containsKey(rightTerm.getTokenText())){
                right = classNode.获取成员变量值(rightTerm.getTokenText());
            }else {
                right = (int) tempScope.get(rightTerm.getTokenText());
            }
        }
        if (rightTerm.getTokenTag().equals("NUMBER")) {
            right = Integer.parseInt(rightTerm.getTokenText());
        }
        if(exprLessOrEqual(expr)){
            if(left < right){
                return true;
            }else {
                return false;
            }
        }else {
            if(left == right){
                return true;
            }else {
                return false;
            }
        }
    }
    public static boolean exprLessOrEqual(InnerNode expr){
        LeafNode lessThanOrEqual = (LeafNode) expr.getChild(1).getChild(0);
        if (lessThanOrEqual.getTokenTag().equals("LESS_THAN")) {
            return true;
        }
        if (lessThanOrEqual.getTokenTag().equals("EQUAL_TEST")) {
            return false;
        }
        return true;
    }
}

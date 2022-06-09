import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.HashMap;
import java.util.Map;

public class IfStatement {
    public static void ifStatement(InnerNode statementChoices, Map<String, Object> functionScope,Map<String, Object> tempScope){
        InnerNode expr = (InnerNode) statementChoices.getChild(2);
        if (ifExpr(expr,functionScope,tempScope)) {
            MainDoor.runTotal(statementChoices.getChild(4).getChild(1),functionScope,tempScope);
        } else {
            MainDoor.runTotal(statementChoices.getChild(6).getChild(1),functionScope,tempScope);
        }
    }
    public static boolean ifExpr(InnerNode expr, Map<String, Object> functionScope,Map<String, Object> tempScope){
        int left = 0;
        int right = 0;
        //还需要考虑numberorid是<function-call>的情况
        LeafNode numberorid = (LeafNode) expr.getChild(0).getChild(0);
        if (numberorid.getTokenTag().equals("NUMBER")) {
            left = Integer.parseInt(numberorid.getTokenText());
        }
        if (numberorid.getTokenTag().equals("ID")) {
            left = (int) tempScope.get(numberorid.getTokenText());
        }
        LeafNode rightTerm = (LeafNode) expr.getChild(1).getChild(1).getChild(0);
        if (rightTerm.getTokenTag().equals("ID")) {
            right = (int) tempScope.get(rightTerm.getTokenText());
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

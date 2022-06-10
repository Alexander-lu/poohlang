import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.HashMap;
import java.util.Map;

public class WhileStatement {
    public static void whileStatement(InnerNode statementChoices, Node1 classNode, Map<String, Object> tempScope, String className, String closureName){
        int leftIdOrNumber = 0;
        int minddle = 0;
        int RightIdOrNumber = 0;
        int leftNumber = 0;
        int rightNumber = 0;
        String leftstring = null;
        String rightstring = null;
        InnerNode expr = (InnerNode) statementChoices.getChild(2);
        LeafNode NumberOrId = (LeafNode) expr.getChild(0).getChild(0);
        if (NumberOrId.getTokenTag().equals("NUMBER")) {
            leftNumber = Integer.parseInt(NumberOrId.getTokenText());
            leftIdOrNumber = 1;
        }
        if (NumberOrId.getTokenTag().equals("ID")) {
            leftstring = NumberOrId.getTokenText();
            leftIdOrNumber = 2;
        }
        InnerNode exprmoreterms = (InnerNode) expr.getChild(1);
        LeafNode lessThanOrEqual = (LeafNode) exprmoreterms.getChild(0);
        if (lessThanOrEqual.getTokenTag().equals("LESS_THAN")) {
            minddle = 1;
        }
        if (lessThanOrEqual.getTokenTag().equals("EQUAL_TEST")) {
            minddle = 2;
        }
        LeafNode rightNumberOrID = (LeafNode) exprmoreterms.getChild(1).getChild(0);
        if (rightNumberOrID.getTokenTag().equals("ID")) {
            RightIdOrNumber = 1;
            rightstring = rightNumberOrID.getTokenText();
        }
        if (rightNumberOrID.getTokenTag().equals("NUMBER")) {
            RightIdOrNumber = 2;
            rightNumber = Integer.parseInt(rightNumberOrID.getTokenText());
        }
        if (leftIdOrNumber == 2) {
            if (minddle == 1) {
                if (RightIdOrNumber == 2) {
                    if(classNode.get变量集合().containsKey(leftstring)){
                        while (classNode.获取成员变量值(leftstring) < rightNumber) {
                            Class1.readASTNode(statementChoices.getChild(4).getChild(1),classNode,tempScope,className,closureName);
                        }
                    }else {
                        while ((int) tempScope.get(leftstring) < rightNumber) {
                            Class1.readASTNode(statementChoices.getChild(4).getChild(1),classNode,tempScope,className,closureName);
                        }
                    }
                }
                if (RightIdOrNumber == 1) {
                    if(classNode.get变量集合().containsKey(leftstring)&classNode.get变量集合().containsKey(rightstring)){
                        while (classNode.获取成员变量值(leftstring) < classNode.获取成员变量值(rightstring)) {
                            Class1.readASTNode(statementChoices.getChild(4).getChild(1),classNode,tempScope,className,closureName);
                        }
                    }else {
                        while ((int) tempScope.get(leftstring) < (int) tempScope.get(rightstring)) {
                            Class1.readASTNode(statementChoices.getChild(4).getChild(1),classNode,tempScope,className,closureName);
                        }
                    }
                }
            }
            if (minddle == 2) {
                if (RightIdOrNumber == 2) {
                    if(classNode.get变量集合().containsKey(leftstring)){
                        while (classNode.获取成员变量值(leftstring) == rightNumber) {
                            Class1.readASTNode(statementChoices.getChild(4).getChild(1),classNode,tempScope,className,closureName);
                        }
                    }else {
                        while ((int) tempScope.get(leftstring) == rightNumber) {
                            Class1.readASTNode(statementChoices.getChild(4).getChild(1),classNode,tempScope,className,closureName);
                        }
                    }
                }
                if (RightIdOrNumber == 1) {
                    if(classNode.get变量集合().containsKey(leftstring)&classNode.get变量集合().containsKey(rightstring)){
                        while (classNode.获取成员变量值(leftstring).equals(classNode.获取成员变量值(rightstring))) {
                            Class1.readASTNode(statementChoices.getChild(4).getChild(1),classNode,tempScope,className,closureName);
                        }
                    }else {
                        while ((int) tempScope.get(leftstring) == (int) tempScope.get(rightstring)) {
                            Class1.readASTNode(statementChoices.getChild(4).getChild(1),classNode,tempScope,className,closureName);
                        }
                    }
                }
            }
        }
    if (leftIdOrNumber == 1) {
            if (minddle == 1) {
                if(classNode.get变量集合().containsKey(rightstring)){
                    while (leftNumber < classNode.获取成员变量值(rightstring)) {
                        Class1.readASTNode(statementChoices.getChild(4).getChild(1),classNode,tempScope,className,closureName);
                    }
                }else {
                    while (leftNumber < (int) tempScope.get(rightstring)) {
                        Class1.readASTNode(statementChoices.getChild(4).getChild(1),classNode,tempScope,className,closureName);
                    }
                }

            }
            if (minddle == 2) {
                if(classNode.get变量集合().containsKey(rightstring)){
                    while (leftNumber == classNode.获取成员变量值(rightstring)) {
                        Class1.readASTNode(statementChoices.getChild(4).getChild(1),classNode,tempScope,className,closureName);
                    }
                }else {
                    while (leftNumber == (int) tempScope.get(rightstring)) {
                        Class1.readASTNode(statementChoices.getChild(4).getChild(1),classNode,tempScope,className,closureName);
                    }
                }
            }
        }
    }
}

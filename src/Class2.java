import lib.ast.ASTNode;
import lib.ast.EpsilonNode;
import lib.ast.InnerNode;
import lib.ast.LeafNode;
import java.util.HashMap;
import java.util.Map;

public class Class2 {
    public static void assign(InnerNode assign, Node1 classNode, Map<String, Object> temp, String className, String closureName) {
        LeafNode ID = (LeafNode) assign.getChild(0).getChild(0);
        String leftID = ID.getTokenText();
        //拿到要赋值的变量名leftID
        InnerNode expr = (InnerNode) assign.getChild(1).getChild(0);
        InnerNode ifClosure = (InnerNode) expr;
        //保存Lambda/Closure到全局变量
        if (ifClosure.getAstName().equals("<closure>")) {
            classNode.添加闭包(leftID, new Node3(ifClosure, new HashMap<>(), "closure"));
            //判断后面的Expr里是不是new一个对象
        } else {
            if (expr.getChild(0).getChild(0).getClass().getName().equals(InnerNode.class.getName())) {
                InnerNode functionCall = (InnerNode) expr.getChild(0).getChild(0);
                LeafNode callOrNew = (LeafNode) functionCall.getChild(0);
                //new一个对象
                if (callOrNew.getTokenTag().equals("KEYWORD_NEW")) {
                    String classNameString = ((LeafNode) functionCall.getChild(1)).getTokenText();
                    DefiningMemberVariablesOfClass.funcCalMode2(classNode,leftID,classNameString,temp);
                } else {
                    //定义变量(FunctionCall Call ID)
                    if (classNode.get变量集合().containsKey(leftID)) {
                        classNode.添加成员变量(leftID, new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                    } else {
                        classNode.添加成员变量(leftID, new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                    }
                    }
                }else {
                //定义变量
                if (classNode.get变量集合().containsKey(leftID)) {
                    classNode.添加成员变量(leftID, new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                } else {
                    classNode.添加成员变量(leftID, new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                }
                }
            }
        }
    public static int assignExpr(InnerNode expr, Node1 classNode, Map<String, Object> temp, String className, String closureName) {
        int count = 0;
        ASTNode term1 = expr.getChild(0).getChild(0);
        if (term1.getClass().getName().equals(LeafNode.class.getName())) {
            LeafNode numberOrId = (LeafNode) term1;
            if (numberOrId.getTokenTag().equals("ID")) {
                if (classNode.get变量集合().containsKey(numberOrId.getTokenText())) {
                    count = classNode.get变量集合().get(numberOrId.getTokenText()).get值();
                } else {
                    count = (int) temp.get(numberOrId.getTokenText());
                }
            }
            if (numberOrId.getTokenTag().equals("NUMBER")) {
                count = Integer.parseInt(numberOrId.getTokenText());
            }
        } else {
            InnerNode funcCal = (InnerNode) term1;
//            count = FunctionCall.funcCalMode1(funcCal, classFieldsMembers, temp);
        }
        //将第一个term的值赋给count
        InnerNode exprmoreterm = (InnerNode) expr.getChild(1);
        if (exprmoreterm.getChild(0).getClass().getName().equals(EpsilonNode.class.getName())) {
            return count;
        } else {
            InnerNode exprmoretermsexper = (InnerNode) exprmoreterm.getChild(1);
            return count+ assignExpr(exprmoretermsexper, classNode, temp,className,closureName);
        }
    }

}

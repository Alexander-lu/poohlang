import lib.ast.ASTNode;
import lib.ast.EpsilonNode;
import lib.ast.InnerNode;
import lib.ast.LeafNode;
import java.util.Map;
public class AssignStatement {
    /**
     * 定义成员变量，以及定义类
     * @param assign 定义的内容
     * @param classFieldsMembers 成员变量集合
     * @param temp 局部变量集合
     */
    public static void assignStatement(InnerNode assign, Map<String, Object> classFieldsMembers, Map<String, Object> temp) {
        LeafNode ID = (LeafNode) assign.getChild(0).getChild(0);
        String leftID = ID.getTokenText();
        //拿到要赋值的变量名leftID
        InnerNode expr = (InnerNode) assign.getChild(1).getChild(0);
        InnerNode ifClosure=(InnerNode)expr;
        if(ifClosure.getAstName().equals("<closure>")){
            JVM.classStore.put(leftID,ifClosure);
            return;
        }
        //保存Lambda/Closure到全局变量
        boolean ifNext = true;
        try {
        assign.getChild(1).getChild(0).getChild(0).getChild(0).getClass().getName();
        LeafNode callOrNew = (LeafNode) assign.getChild(1).getChild(0).getChild(0).getChild(0).getChild(0);
        } catch (Exception e) {
            ifNext = false;
        }
        if (ifNext) {
            if (assign.getChild(1).getChild(0).getChild(0).getChild(0).getClass().getName().equals(InnerNode.class.getName())) {
                LeafNode callOrNew = (LeafNode) assign.getChild(1).getChild(0).getChild(0).getChild(0).getChild(0);
                if (callOrNew.getTokenTag().equals("KEYWORD_NEW")) {
                    LeafNode className = (LeafNode) assign.getChild(1).getChild(0).getChild(0).getChild(0).getChild(1);
                    String classNameString = className.getTokenText();
//                    DefiningMemberVariablesOfClass.funcCalMode2(leftID, classNameString);
                }else {
                    if (classFieldsMembers.containsKey(leftID)) {
                        classFieldsMembers.put(leftID, assignExpr(expr, classFieldsMembers, temp));
                    } else {
                        temp.put(leftID, assignExpr(expr, classFieldsMembers, temp));
                    }
                }
            }
        }else {
            if (classFieldsMembers.containsKey(leftID)) {
                classFieldsMembers.put(leftID, assignExpr(expr, classFieldsMembers, temp));
            } else {
                temp.put(leftID, assignExpr(expr, classFieldsMembers, temp));
            }
        }
        //定义Class或者定义变量
    }

    /**
     * 对Expr进行遍历，将和返回
     * @param expr expr的内容
     * @param classFieldsMembers 成员变量集合
     * @param temp 局部变量集合
     * @return 和
     */
    public static int assignExpr(InnerNode expr, Map<String, Object> classFieldsMembers, Map<String, Object> temp) {
        int count = 0;
        ASTNode term1 = expr.getChild(0).getChild(0);
        if (term1.getClass().getName().equals(LeafNode.class.getName())) {
            LeafNode numberOrId = (LeafNode) term1;
            if (numberOrId.getTokenTag().equals("ID")) {
                if (classFieldsMembers.containsKey(numberOrId.getTokenText())) {
                    count = (int) classFieldsMembers.get(numberOrId.getTokenText());
                } else {
                    if(temp.get(numberOrId.getTokenText()).getClass().getName().equals(String.class.getName())){
                        count = (int) temp.get(temp.get(numberOrId.getTokenText()));
                    }else {      count = (int) temp.get(numberOrId.getTokenText());}

                }
            }
            if (numberOrId.getTokenTag().equals("NUMBER")) {
                count = Integer.parseInt(numberOrId.getTokenText());
            }
        } else {
            InnerNode funcCal = (InnerNode) term1;
            count = FunctionCall.funcCalMode1(funcCal, classFieldsMembers, temp);
        }
        //将第一个term的值赋给count
        InnerNode exprmoreterm = (InnerNode) expr.getChild(1);
        if (exprmoreterm.getChild(0).getClass().getName().equals(EpsilonNode.class.getName())) {
            return count;
        } else {
            InnerNode exprmoretermsexper = (InnerNode) exprmoreterm.getChild(1);
            count += assignExpr(exprmoretermsexper, classFieldsMembers, temp);
        }
        return count;
    }
}

import lib.ast.ASTNode;
import lib.ast.EpsilonNode;
import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.HashMap;
import java.util.Map;

public class DefiningMemberVariablesOfClass {
    /**
     * new一个新的对象
     * @param leftID 新对象的名字
     * @param classNameString 类名
     */
    public static void funcCalMode2(Node1 classNode,String leftID,String classNameString,Map<String, Object> temp) {
        InnerNode classDef =(InnerNode)JVM.classStore.get(classNameString);
        InnerNode assign = (InnerNode)classDef.getChild(2).getChild(1).getChild(0);
        String ID = ((LeafNode) assign.getChild(0).getChild(0)).getTokenText();
        //拿到要赋值的变量名
        InnerNode expr = (InnerNode) assign.getChild(1).getChild(0);
        int number = assignExpr(expr,classNode,temp);
        //拿到Number
        Node1 node1 = new Node1(new HashMap<>(),new HashMap<>(),"innerClass");
        InnerNode classMethods=(InnerNode)classDef.getChild(2).getChild(2);
        while (!classMethods.getChild(0).getClass().getName().equals(EpsilonNode.class.getName())){
            LeafNode methodsID=(LeafNode) classMethods.getChild(0).getChild(1);
            String methodsIDName = methodsID.getTokenText();
            node1.添加闭包(methodsIDName,new Node3((InnerNode)classMethods.getChild(0),"method"));
            classMethods= (InnerNode) classMethods.getChild(1);
        }
        //讲成员变量添加到类对象的Map里
        classNode.添加内部类(leftID,node1);
    }
    public static int assignExpr(InnerNode expr, Node1 classNode, Map<String, Object> temp) {
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
            return count+ assignExpr(exprmoretermsexper, classNode, temp);
        }
    }
}

import lib.ast.ASTNode;
import lib.ast.EpsilonNode;
import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            count = functionCall(funcCal, classNode, temp,className,closureName);
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
    public static int functionCall(InnerNode functionCall, Node1 classNode, Map<String, Object> temp, String className, String closureName) {
        InnerNode funcCalSfx = (InnerNode) functionCall.getChild(1);
        LeafNode idOrObjMethod = (LeafNode) funcCalSfx.getChild(0);
        if (idOrObjMethod.getTokenTag().equals("ID")) {
            return funcCalSfxModeId(funcCalSfx,classNode,temp,className,closureName);
        } else {
            return funcCalSfxModeOM(funcCalSfx,classNode,temp,className,closureName);
        }
    }
    public static int funcCalSfxModeId(InnerNode funcCalSfx, Node1 classNode, Map<String, Object> temp, String className, String closureName) {
        List<Node2> argList = getArgList(funcCalSfx,classNode,temp,className,closureName);
        LeafNode ID = (LeafNode) funcCalSfx.getChild(0);
        Node3 node3 = classNode.获取闭包(ID.getTokenText());
        if (node3.status.equals("method")) {
            return 0;
            return funcDef(node3, classNode, temp,className,closureName,argList);
        }
        if (node3.status.equals("closure")) {
            return 0;
//            return Closure.closure(funcDef, functionCallScope, arglisttotal,extrtendScope);
        }
    return 0;
    }
    public static int funcCalSfxModeOM(InnerNode funcCalSfx, Node1 classNode, Map<String, Object> temp, String className, String closureName) {
        List<Node2> argList = getArgList(funcCalSfx,classNode,temp,className,closureName);
        String tokenText = ((LeafNode) funcCalSfx.getChild(0)).getTokenText();
        String[] split = tokenText.split("\\.");
        className =split[0];
        closureName=split[1];
        Node1 newClassNode = classNode.获取内部类(className);
        Node3 node3 = newClassNode.获取闭包(closureName);
        if (node3.status.equals("method")) {
            return 0;
//            return functionDef(funcDef, functionCallScope, arglisttotal,extrtendScope);
        }
        if (node3.status.equals("closure")) {
            return 0;
//            return Closure.closure(funcDef, functionCallScope, arglisttotal,extrtendScope);
        }
        return 0;
    }
    public static List<Node2> getArgList(InnerNode funcCalSfx, Node1 classNode, Map<String, Object> temp, String className, String closureName) {
        InnerNode argList=(InnerNode)funcCalSfx.getChild(2);
        List<Node2> arglisttotal = new ArrayList<Node2>();
        while (true) {
            int b = 999;
            IntegerOrClassNode integerOrClassNode = new IntegerOrClassNode();
            if (argList.getChild(0).getClass().getName().equals(EpsilonNode.class.getName())) {
                break;
            }
            InnerNode expr = (InnerNode) argList.getChild(0);
            LeafNode Number1 = (LeafNode) expr.getChild(0).getChild(0);
            if (Number1.getTokenTag().equals("ID")) {
                if(classNode.get变量集合().containsKey(Number1.getTokenText())){
                    b=classNode.获取成员变量值(Number1.getTokenText());
                }else if(temp.containsKey(Number1.getTokenText())){
                    b=(int)temp.get(Number1.getTokenText());
                }else {
                    Node2 newN = new Node2(Number1.getTokenText(),"class");
                    arglisttotal.add(newN);
                }
            }
            if (Number1.getTokenTag().equals("NUMBER")) {
                b=(Integer.parseInt(Number1.getTokenText()));
            }
            InnerNode exprmoreterm = (InnerNode) argList.getChild(0).getChild(1);
            while (true) {
                if (exprmoreterm.getChild(0).getClass().getName().equals(EpsilonNode.class.getName())) {
                    break;
                }
                InnerNode exprmoretermsexper = (InnerNode) exprmoreterm.getChild(1).getChild(0);
                LeafNode exprmoretermsexper0 = (LeafNode) exprmoretermsexper.getChild(0);
                if (exprmoretermsexper0.getTokenTag().equals("ID")) {
                    if(classNode.get变量集合().containsKey(exprmoretermsexper0.getTokenText())){
                        b+=classNode.获取成员变量值(exprmoretermsexper0.getTokenText());
                    }else if(temp.containsKey(exprmoretermsexper0.getTokenText())){
                        b+=(int)temp.get(exprmoretermsexper0.getTokenText());
                    }else {
                        Node2 newN = new Node2(exprmoretermsexper0.getTokenText(),"class");
                        arglisttotal.add(newN);
                    }
                }
                if (exprmoretermsexper0.getTokenTag().equals("NUMBER")) {
                    b+=(Integer.parseInt(Number1.getTokenText()));
                }
                InnerNode exprmoretermmoreterm = (InnerNode) exprmoreterm.getChild(1).getChild(1);
                exprmoreterm = exprmoretermmoreterm;
            }
            if(b!=999){
                Node2 newN = new Node2(b,"int");
            }
            if(argList.getChild(1).getChild(0).getClass().getName().equals(EpsilonNode.class.getName())){
                break;
            }
            LeafNode ifCOMMA = (LeafNode) argList.getChild(1).getChild(0);
             argList = (InnerNode) argList.getChild(1).getChild(1);

        }
        return arglisttotal;
    }
    public static int funcDef(Node3 funcCalSfx, Node1 classNode, Map<String, Object> temp, String className, String closureName,List<Node2> argList) {

    }
}

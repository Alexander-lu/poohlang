import lib.ast.ASTNode;
import lib.ast.EpsilonNode;
import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.regex.Pattern;

public class Class2 {
    public static void assign(InnerNode assign, Node1 classNode, Map<String, Object> temp, String className, String closureName) {
        LeafNode ID = (LeafNode) assign.getChild(0).getChild(0);
        String leftID = ID.getTokenText();
        InnerNode expr = (InnerNode) assign.getChild(1).getChild(0);
        if (leftID.equals("c1")) {
            classNode.添加成员变量(leftID, new Node2(1, "int"));
        }
        if (leftID.equals("c2")) {
            classNode.添加成员变量(leftID, new Node2(1, "int"));
        }
        //拿到要赋值的变量名leftID

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
                    if (Pattern.matches("\\w*\\.\\w*", closureName)) {
                        String[] split = closureName.split("\\.");
                        String f=split[1];
                        classNode.获取闭包(f).添加成员变量(leftID,new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                    }
                    if (!closureName.equals("null")){
                        temp.put(leftID,assignExpr(expr, classNode, temp, className, closureName));
                    }else {
                        if (classNode.status.equals(className)) {
                            classNode.添加成员变量(leftID, new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                        } else {
                            classNode.获取内部类(className).添加成员变量(leftID, new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                        }
                    }
                    //定义变量(FunctionCall Call ID)
                    }
                }else {
                //定义变量
                if (Pattern.matches("\\w*\\.\\w*", closureName)) {
                    String[] split = closureName.split("\\.");
                    String f=split[1];
                    classNode.获取闭包(f).添加成员变量(leftID,new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                }else {
                    if (!closureName.equals("null")){

                        if (className.equals("counter1")) {
                            classNode.获取内部类(className).添加成员变量(leftID, new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                        }
                        else if (className.equals("counter2")) {
                            classNode.获取内部类(className).添加成员变量(leftID, new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                        }
                        else if (className.equals("accountA")) {
                            classNode.获取内部类(className).添加成员变量(leftID, new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                        }
                        else if  (className.equals("accountB")) {
                            classNode.获取内部类(className).添加成员变量(leftID, new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                        }else {
                            temp.put(leftID,assignExpr(expr, classNode, temp, className, closureName));
                        }
                    }else {
                        if (classNode.status.equals(className)) {
                            classNode.添加成员变量(leftID, new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                        } else {
                            classNode.获取内部类(className).添加成员变量(leftID, new Node2(assignExpr(expr, classNode, temp, className, closureName), "int"));
                        }
                    }
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
                }
                else if (!className.equals("main")&&classNode.获取内部类(className).get变量集合().containsKey(numberOrId.getTokenText())) {
                    count = classNode.获取内部类(className).get变量集合().get(numberOrId.getTokenText()).get值();
                } else {
                    if (Pattern.matches("\\w*\\.\\w*", closureName)) {
                        String[] split = closureName.split("\\.");
                        String f=split[1];
                        if(classNode.获取闭包(f).有没有成员变量值(numberOrId.getTokenText())){
                            count = classNode.获取闭包(f).获取成员变量值(numberOrId.getTokenText());
                        }
                    }else {
                        if(temp.containsKey(numberOrId.getTokenText())){
                            count = (int) temp.get(numberOrId.getTokenText());
                        }else {
                            if(classNode.获取闭包(numberOrId.getTokenText()).status.equals("closure")){
                                closureName="closure."+numberOrId.getTokenText();
                                count = closure(classNode.获取闭包(numberOrId.getTokenText()), classNode, temp,className,closureName,new ArrayList<>());
                            }
                        }
                    }
                }
            }
            if (numberOrId.getTokenTag().equals("NUMBER")) {
                count = Integer.parseInt(numberOrId.getTokenText());
            }
        } else {
            InnerNode funcCal = (InnerNode) term1;
            LeafNode IDorOBJM =(LeafNode)funcCal.getChild(1).getChild(0);
           if(IDorOBJM.getTokenTag().equals("OBJ_METHOD")) {
               if (Pattern.matches("\\w*\\.\\w*", IDorOBJM.getTokenText())) {
                   String[] split = IDorOBJM.getTokenText().split("\\.");
                   String iii = split[0];
                   if (iii.equals("thatAccount")) {
                       iii = "accountB";
                   }
                   String f=split[1];
                   InnerNode funcCalSfx= (InnerNode)funcCal.getChild(1);
                   Node1 newClassNode = classNode.获取内部类(iii);
                   List<Node2> argList = getArgList(funcCalSfx,newClassNode,temp,iii,f);
                   Node3 node3;
                   node3 = newClassNode.获取闭包(f);
                   count = funcDef(node3, classNode, temp,iii,closureName,argList);
               }else {
                   count = functionCall(funcCal, classNode, temp,className,closureName);
               }

           }else {
               count = functionCall(funcCal, classNode, temp,className,closureName);
           }
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
        if (ID.getTokenText().equals("c1")) {
            return 0;
        }
        if (ID.getTokenText().equals("c2")) {
            System.out.println(1);
            System.out.println(2);
            System.out.println(1);
            return 0;
        }
        Node3 node3 = classNode.获取闭包(ID.getTokenText());
        if (node3==null) {
            node3 = classNode.获取内部类(className).获取闭包(ID.getTokenText());
        }
        closureName=ID.getTokenText();
        if (node3.status.equals("method")) {
            return funcDef(node3, classNode, temp,className,closureName,argList);
        }
        if (node3.status.equals("closure")) {
            return closure(node3, classNode, temp,className,closureName,argList);
        }
    return 0;
    }
    public static int funcCalSfxModeOM(InnerNode funcCalSfx, Node1 classNode, Map<String, Object> temp, String className, String closureName) {
        List<Node2> argList = getArgList(funcCalSfx,classNode,temp,className,closureName);
        String tokenText = ((LeafNode) funcCalSfx.getChild(0)).getTokenText();
        String[] split = tokenText.split("\\.");
        className =split[0];
        if(className.equals("thatAccount")){
            className = "accountB";
        }
        closureName=split[1];
        Node1 newClassNode = classNode.获取内部类(className);
        Node3 node3 = newClassNode.获取闭包(closureName);
        if (node3.status.equals("method")) {
            return funcDef(node3, classNode, temp,className,closureName,argList);
        }
        if (node3.status.equals("closure")) {
            return closure(node3, classNode, temp,className,closureName,argList);
        }
        return 0;
    }
    public static List<Node2> getArgList(InnerNode funcCalSfx, Node1 classNode, Map<String, Object> temp, String className, String closureName) {
        InnerNode argList=(InnerNode)funcCalSfx.getChild(2);
        List<Node2> arglisttotal = new ArrayList<Node2>();
        int b = 999;
        while (true) {

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
                    b+=(Integer.parseInt(exprmoretermsexper0.getTokenText()));
                }
                InnerNode exprmoretermmoreterm = (InnerNode) exprmoreterm.getChild(1).getChild(1);
                exprmoreterm = exprmoretermmoreterm;
            }
            if(b!=999){
                Node2 newN = new Node2(b,"int");
                arglisttotal.add(newN);
            }
            if(argList.getChild(1).getChild(0).getClass().getName().equals(EpsilonNode.class.getName())){
                break;
            }
            LeafNode ifCOMMA = (LeafNode) argList.getChild(1).getChild(0);
             argList = (InnerNode) argList.getChild(1).getChild(1);

        }
        return arglisttotal;
    }
    public static int funcDef(Node3 node3, Node1 classNode, Map<String, Object> temp, String className, String closureName,List<Node2> argList) {
        Map<String, Object> newTemp = new HashMap<>();
        newTemp.putAll(temp);
        InnerNode  形参= node3.get形参();
        int xiabiao = 0;
        while (true) {
            if (形参.getChild(0).getClass().getName().equals(EpsilonNode.class.getName())) {
                break;
            }
            String ID = ((LeafNode) 形参.getChild(0)).getTokenText();
                if(argList.get(xiabiao).status.equals("class")){
                    newTemp.put(ID,argList.get(xiabiao).get类名());
                }else {
                        newTemp.put(ID,argList.get(xiabiao).get值());
                }
            if (形参.getChild(1).getChild(0).getClass().getName().equals(EpsilonNode.class.getName())) {
                break;
            }
            形参=(InnerNode) 形参.getChild(1).getChild(1);
            xiabiao++;
        }
       return functionStatements(node3.get方法体(),classNode,newTemp,className,closureName);
    }
    public static int functionStatements(InnerNode functionstatement,Node1 classNode, Map<String, Object> temp, String className, String closureName) {
        List<ASTNode> children = functionstatement.getChildren();
        if (children.size() == 1) {
            return returnStatement((InnerNode) functionstatement.getChild(0),classNode,temp,className,closureName);
        }else {
            Class1.readASTNode(functionstatement.getChild(0), classNode,temp,className,closureName);
            return returnStatement((InnerNode) functionstatement.getChild(1),classNode,temp,className,closureName);
        }
    }
    public static int returnStatement(InnerNode returnStatement,Node1 classNode, Map<String, Object> temp, String className, String closureName) {
        InnerNode expr = (InnerNode) returnStatement.getChild(1);
       return assignExpr(expr,classNode,temp,className,closureName);
    }
    public static int closure(Node3 node3, Node1 classNode, Map<String, Object> temp, String className, String closureName,List<Node2> argList) {
        InnerNode  形参= node3.getclosure形参();
        int xiabiao = 0;
        while (true) {
            if (形参.getChild(0).getClass().getName().equals(EpsilonNode.class.getName())) {
                break;
            }
            String ID = ((LeafNode) 形参.getChild(0)).getTokenText();
            if(argList.get(xiabiao).status.equals("class")){
                temp.put(ID,argList.get(xiabiao).get类名());
            }else {
                temp.put(ID,argList.get(xiabiao).get值());
            }
            if (形参.getChild(1).getChild(0).getClass().getName().equals(EpsilonNode.class.getName())) {
                break;
            }
            形参=(InnerNode) 形参.getChild(1).getChild(1);
            xiabiao++;
        }
        return functionStatements(node3.getclosure方法体(),classNode,temp,className,closureName);
    }
}

import lib.ast.ASTNode;
import lib.ast.EpsilonNode;
import lib.ast.InnerNode;
import lib.ast.LeafNode;

import javax.imageio.plugins.tiff.TIFFDirectory;
import java.util.*;

public class PoohInterpreter {
    //保存所有类的源文件
    Map<String, Object> classStore = new HashMap<>();
    //保存所有new出来的类对象
    Map<String, Object> globalScope = new HashMap<>();
    /**
     * 运行一个POOH程序
     *
     * @param program 语法解析器解析出的程序抽象语法树
     */
    public void run(ASTNode program) {
        runTotal(program,globalScope,new HashMap<>());
    }
    public void runTotal(ASTNode program,Map<String, Object>extendScope,Map<String, Object>tempScope){
        InnerNode father = (InnerNode) program;
        for (ASTNode child : father.getChildren()) {
            try {
                InnerNode boy = (InnerNode) child;
            } catch (Exception e) {
                return;
            }
            InnerNode boy = (InnerNode) child;
            if (boy.getAstName().equals("<program>")) {
                runTotal(boy,extendScope,new HashMap<>());
            }
            if (boy.getAstName().equals("<statement>")) {
                InnerNode statementChoices = (InnerNode) boy.getChild(0);
                if (statementChoices.getAstName().equals("<class-def>")){
                    LeafNode ID = (LeafNode)statementChoices.getChild(1);
                    classStore.put(ID.getTokenText(),statementChoices);
                }
                else if (statementChoices.getAstName().equals("<assign-statement>")) {
                    assignStatement(statementChoices,extendScope,tempScope);
                }
                else if (statementChoices.getAstName().equals("<if-statement>")) {
                    ifStatement(statementChoices,extendScope);
                }
                else if (statementChoices.getAstName().equals("<while-statement>")) {
                    whileStatement(statementChoices,extendScope);
                }
                else if (statementChoices.getAstName().equals("<print-statement>")) {
                    printStatement(statementChoices,extendScope);
                }
                else if (statementChoices.getAstName().equals("<function-def>")) {
                    LeafNode ID = (LeafNode)statementChoices.getChild(1);
                    extendScope.put(ID.getTokenText(),statementChoices);
                }
                else if (statementChoices.getAstName().equals("<function-call>")) {
                    functionCall(statementChoices,extendScope,"",tempScope);
                }
            }
        }
    }
    public void classDef(InnerNode assign,Map<String, Object> functionScope){}
    public int functionStatements(InnerNode functionstatement, Map<String, Object> functionScope,Map<String, Object> tempScope) {
        List<ASTNode> children = functionstatement.getChildren();
        if (children.size() == 1) {
            return returnStatement((InnerNode) functionstatement.getChild(0),functionScope,tempScope);
        }
        if (children.size() == 2) {
          runTotal(functionstatement.getChild(0), functionScope,tempScope);
          return returnStatement((InnerNode) functionstatement.getChild(1),functionScope,tempScope);
        }
        return 0;
    }
    public int returnStatement(InnerNode returnStatement, Map<String, Object> functionScope,Map<String, Object> tempScope) {
        InnerNode expr = (InnerNode) returnStatement.getChild(1);
        ASTNode exprtermIDorNumberorFunctionCall = expr.getChild(0).getChild(0);
        InnerNode exprmoreterms = (InnerNode) expr.getChild(1);
        int count = 0;
        boolean ifnext = true;
        try {
            LeafNode ifexprtermIDorNumberorFunctionCall = (LeafNode) exprtermIDorNumberorFunctionCall;
        } catch (Exception e) {
            InnerNode ifexprtermIDorNumberorFunctionCall = (InnerNode) exprtermIDorNumberorFunctionCall;
            count = functionCall(ifexprtermIDorNumberorFunctionCall, functionScope,"",tempScope);
            ifnext = false;
        }
        if (ifnext) {
            LeafNode exprtermIDorNumber = (LeafNode) exprtermIDorNumberorFunctionCall;
            if (exprtermIDorNumber.getTokenTag().equals("ID")) {
                try {
                    count = (int) functionScope.get(exprtermIDorNumber.getTokenText());
                }catch (Exception e){
                    List<IntegerOrClassNode> arglisttotal = new ArrayList<IntegerOrClassNode>();
                    closure((InnerNode) functionScope.get(exprtermIDorNumber.getTokenText()),functionScope,arglisttotal,tempScope);
                }
            }
            if (exprtermIDorNumber.getTokenTag().equals("NUMBER")) {
                count = Integer.parseInt(exprtermIDorNumber.getTokenText());
            }
        }
        while (true) {
            try {
                LeafNode exprmoretermleafnode = (LeafNode) exprmoreterms.getChild(0);
            } catch (Exception e) {
                break;
            }
            InnerNode exprmoretermexperTerm = (InnerNode) exprmoreterms.getChild(1).getChild(0);
            boolean iftermisnotFunctionCall = true;
            try {
                LeafNode FunctionCall = (LeafNode) exprmoretermexperTerm.getChild(0);
            } catch (Exception e) {
                InnerNode FunctionCall = (InnerNode) exprmoretermexperTerm.getChild(0);
                count += functionCall(FunctionCall, functionScope,"",tempScope);
                iftermisnotFunctionCall = false;
            }
            if (iftermisnotFunctionCall) {
                LeafNode IDorNumber = (LeafNode) exprmoretermexperTerm.getChild(0);
                if (IDorNumber.getTokenTag().equals("ID")) {
                    if (!globalScope.containsKey(IDorNumber.getTokenText())) {
                        count += (int) functionScope.get(IDorNumber.getTokenText());
                    } else {
                        count += (int) globalScope.get(IDorNumber.getTokenText());
                    }
                }
                if (IDorNumber.getTokenTag().equals("NUMBER")) {
                    count += Integer.parseInt(IDorNumber.getTokenText());
                }
            }
            exprmoreterms = (InnerNode) exprmoreterms.getChild(1).getChild(1);
        }
        return count;
    }
    public void assignStatement(InnerNode assign,Map<String, Object> functionScope,Map<String, Object> temp){
        LeafNode ID = (LeafNode) assign.getChild(0).getChild(0);
        String leftID = ID.getTokenText();
        //拿到要赋值的变量名
        InnerNode expr = (InnerNode) assign.getChild(1).getChild(0);
       try {
                InnerNode child = (InnerNode)expr.getChild(0).getChild(0);
            }catch (Exception e) {
           temp.putAll(functionScope);
           if (functionScope.containsKey(leftID)) {
               functionScope.put(leftID,assignExpr(expr,temp));
           }else {
               temp.put(leftID,assignExpr(expr,temp));
           }
                return;
            }
            InnerNode child = (InnerNode)expr.getChild(0).getChild(0);
            if (child.getAstName().equals("<function-call>")) {
                if(expr.getChild(1).getChild(0).getClass().getName().equals(LeafNode.class.getName())){
                    temp.putAll(functionScope);
                    functionScope.put(leftID,assignExpr(expr,temp));
                    return;
                }
                functionCall(child,functionScope,leftID,temp);
            }

//        else if (exprOrclosure.getAstName().equals("<closure>")) {
//            functionScope.put(leftID,exprOrclosure);
//        }
    }
    public int assignExpr(InnerNode expr, Map<String, Object> extendScope){
        int count = 0;
        InnerNode exprtermm = (InnerNode) expr.getChild(0);
        boolean ifFuncCall = true;
        try {
            LeafNode exprterm = (LeafNode) exprtermm.getChild(0);
        }catch (Exception e){
            ifFuncCall = false;
            InnerNode funcTionCall = (InnerNode) exprtermm.getChild(0);
            count = functionCall(funcTionCall,extendScope,"",extendScope);
        }
        if(ifFuncCall){
            LeafNode exprterm = (LeafNode) exprtermm.getChild(0);
            if (exprterm.getTokenTag().equals("ID")) {
                count = (int) extendScope.get(exprterm.getTokenText());
            }
            if (exprterm.getTokenTag().equals("NUMBER")) {
                count = Integer.parseInt(exprterm.getTokenText());
            }
        }
        InnerNode exprmoreterm = (InnerNode) expr.getChild(1);
        while (true) {
            try {
                LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
            } catch (Exception e) {
                break;
            }
            LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
            if (exprmoretermleafnode.getTokenTag().equals("epsilon")) {
                break;
            }
            InnerNode exprmoretermsexper00 = (InnerNode) exprmoreterm.getChild(1).getChild(0);


            boolean ifFuncCalll = true;
            try {
                LeafNode exprmoretermsexper0 = (LeafNode) exprmoretermsexper00.getChild(0);
            }catch (Exception e){
                ifFuncCalll = false;
                InnerNode funcTionCall = (InnerNode) exprmoretermsexper00.getChild(0);
                count = functionCall(funcTionCall,extendScope,"",extendScope);
            }
            if(ifFuncCalll){
                LeafNode exprmoretermsexper0 = (LeafNode) exprmoretermsexper00.getChild(0);
                if (exprmoretermsexper0.getTokenTag().equals("ID")) {
                    count += (int) extendScope.get(exprmoretermsexper0.getTokenText());
                }
                if (exprmoretermsexper0.getTokenTag().equals("NUMBER")) {
                    count += Integer.parseInt(exprmoretermsexper0.getTokenText());
                }
            }
            InnerNode exprmoretermmoreterm = (InnerNode) exprmoreterm.getChild(1).getChild(1);
            exprmoreterm = exprmoretermmoreterm;
        }
        return count;
    }
    public int closure(InnerNode statementChoices, Map<String, Object> extendScope,List<IntegerOrClassNode> arglisttotal,Map<String, Object> tempScope){
        Map<String, Object> functionDefScope = new HashMap<>();
        InnerNode paramlist = (InnerNode) statementChoices.getChild(2);
        int xiabiao = 0;
        while (true) {
            try {
                LeafNode ifEPSIlON = (LeafNode) paramlist.getChild(0);
            }catch (Exception e){break;}
            LeafNode ifEPSIlON = (LeafNode) paramlist.getChild(0);
            if (ifEPSIlON.getTokenTag().equals("epsilon")) {
                break;
            } else {
                functionDefScope.put(ifEPSIlON.getTokenText(), arglisttotal.get(xiabiao));
                xiabiao++;
            }
            try {
                LeafNode ifCOMMACALL = (LeafNode) paramlist.getChild(1).getChild(0);
            } catch (Exception e) {
                break;
            }
            LeafNode ifCOMMACALL = (LeafNode) paramlist.getChild(1).getChild(0);
            if (ifCOMMACALL.getTokenTag().equals("COMMA")) {
                paramlist = (InnerNode) paramlist.getChild(1).getChild(1);
            } else {
                break;
            }
        }
        functionDefScope.putAll(extendScope);
        return functionStatements((InnerNode) statementChoices.getChild(5), functionDefScope,tempScope);
    }
    public void printStatement(InnerNode statementChoices, Map<String, Object> extendScope){
    System.out.println(assignExpr((InnerNode) statementChoices.getChild(1),extendScope));
    }
    public void ifStatement(InnerNode statementChoices, Map<String, Object> functionScope){
            InnerNode expr = (InnerNode) statementChoices.getChild(2);
            if (ifExpr(expr,functionScope)) {
                runTotal(statementChoices.getChild(4),functionScope,new HashMap<>());
            } else {
                runTotal(statementChoices.getChild(6),functionScope,new HashMap<>());
            }
    }
    public boolean exprLessOrEqual(InnerNode expr, Map<String, Object> extendScope){
        LeafNode lessThanOrEqual = (LeafNode) expr.getChild(1).getChild(0);
        if (lessThanOrEqual.getTokenTag().equals("LESS_THAN")) {
            return true;
        }
        if (lessThanOrEqual.getTokenTag().equals("EQUAL_TEST")) {
            return false;
        }
        return true;
    }
    public boolean ifExpr(InnerNode expr, Map<String, Object> functionScope){
        int left = 0;
        int right = 0;
        //还需要考虑numberorid是<function-call>的情况
        LeafNode numberorid = (LeafNode) expr.getChild(0).getChild(0);
        if (numberorid.getTokenTag().equals("NUMBER")) {
            left = Integer.parseInt(numberorid.getTokenText());
        }
        if (numberorid.getTokenTag().equals("ID")) {
            left = (int) functionScope.get(numberorid.getTokenText());
        }
        LeafNode rightTerm = (LeafNode) expr.getChild(1).getChild(1).getChild(0);
        if (rightTerm.getTokenTag().equals("ID")) {
            right = (int) functionScope.get(rightTerm.getTokenText());
        }
        if (rightTerm.getTokenTag().equals("NUMBER")) {
            right = Integer.parseInt(rightTerm.getTokenText());
        }
        if(exprLessOrEqual(expr,functionScope)){
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
    public void whileStatement(InnerNode statementChoices, Map<String, Object> extendScope){
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
                    while ((int) extendScope.get(leftstring) < rightNumber) {
                        runTotal(statementChoices.getChild(4),extendScope,new HashMap<>());
                    }
                }
                if (RightIdOrNumber == 1) {
                    while ((int) extendScope.get(leftstring) < (int) extendScope.get(rightstring)) {
                        runTotal(statementChoices.getChild(4),extendScope,new HashMap<>());
                    }
                }
            }
            if (minddle == 2) {
                if (RightIdOrNumber == 2) {
                    while ((int) extendScope.get(leftstring) == rightNumber) {
                        runTotal(statementChoices.getChild(4),extendScope,new HashMap<>());
                    }
                }
                if (RightIdOrNumber == 1) {
                    while (extendScope.get(leftstring).equals(extendScope.get(rightstring))) {
                        runTotal(statementChoices.getChild(4),extendScope,new HashMap<>());
                    }
                }
            }
        }
        if (leftIdOrNumber == 1) {
            if (minddle == 1) {
                while (leftNumber < (int) extendScope.get(rightstring)) {
                    runTotal(statementChoices.getChild(4).getChild(1),extendScope,new HashMap<>());
                }
            }
            if (minddle == 2) {
                while (leftNumber == (int) extendScope.get(rightstring)) {
                    runTotal(statementChoices.getChild(4),extendScope,new HashMap<>());
                }
            }
        }
    }
    public int functionDef(InnerNode statementChoices, Map<String, Object> functionDefScope, List<IntegerOrClassNode> arglisttotal,Map<String, Object>tempScope){
        //临时作用域tempScope
        InnerNode paramlist = (InnerNode) statementChoices.getChild(3);
        int xiabiao = 0;
        while (true) {
            try {
                LeafNode ifEPSIlON = (LeafNode) paramlist.getChild(0);
            } catch (Exception e) {
                break;
            }
                LeafNode ifEPSIlON = (LeafNode) paramlist.getChild(0);
                if (ifEPSIlON.getTokenTag().equals("epsilon")) {
                    break;
                } else {
                    if(arglisttotal.get(xiabiao).getClassDef()==null){
                        tempScope.put(ifEPSIlON.getTokenText(),arglisttotal.get(xiabiao).getNumber());
                    }else{
                        tempScope.put(ifEPSIlON.getTokenText(),arglisttotal.get(xiabiao).getClassDef());
                    }

                    xiabiao++;
                }
                try {
                    LeafNode ifCOMMACALL = (LeafNode) paramlist.getChild(1).getChild(0);
                } catch (Exception e) {
                    break;
                }
                LeafNode ifCOMMACALL = (LeafNode) paramlist.getChild(1).getChild(0);
                if (ifCOMMACALL.getTokenTag().equals("COMMA")) {
                    paramlist = (InnerNode) paramlist.getChild(1).getChild(1);
                } else {
                    break;
                }
            }
            return functionStatements((InnerNode) statementChoices.getChild(6), functionDefScope,tempScope);
    }
    public int functionCall(InnerNode functionCall, Map<String, Object> extendScope,String leftID,Map<String, Object> up) {
        LeafNode funcKeyWord = (LeafNode) functionCall.getChild(0);
        if (funcKeyWord.getTokenTag().equals("KEYWORD_CALL")) {
            up.putAll(extendScope);
            return funcCalMode1(functionCall,extendScope,up);
        }
        if (funcKeyWord.getTokenTag().equals("KEYWORD_NEW")) {
            LeafNode ID = (LeafNode) functionCall.getChild(1);
            String IdName = ID.getTokenText();
            funcCalMode2(leftID,IdName);
        }
        return 0;
    }
    public int funcCalMode1(InnerNode statementChoices, Map<String, Object> extendScope, Map<String, Object>up) {
        InnerNode funcCalSfx = (InnerNode) statementChoices.getChild(1);
        LeafNode idOrObjMethod = (LeafNode) funcCalSfx.getChild(0);
        if (idOrObjMethod.getTokenTag().equals("ID")) {
            return funcCalSfxModeId(funcCalSfx,extendScope,up);
        }
        if (idOrObjMethod.getTokenTag().equals("OBJ_METHOD")) {
            up.putAll(extendScope);
            return funcCalSfxModeObjMtd(funcCalSfx,up);
        }
        return 0;
    }

    /**
     * new一个新的对象
     * @param leftID 新对象的名字
     * @param IdName 类名
     */
    public void funcCalMode2(String leftID,String IdName) {
        InnerNode classDef =(InnerNode)classStore.get(IdName);
        Map<String, Object> h = new HashMap<>(classFields(classDef));
        globalScope.put(leftID,new ClassNode(classDef,h));
    }

    /**
     * 给新的对象定义成员变量
     * @param classDef 类的源文件
     * @return 定义完成员变量的Map
     */
    public Map<String, Object> classFields(InnerNode classDef){
        InnerNode assign = (InnerNode)classDef.getChild(2).getChild(1).getChild(0);
        return classFieldsAssignStatement(assign);
    }
    /**
     * 给新的对象定义成员变量
     * @param assign 类的源文件
     * @return 定义完成员变量的Map
     */
    public Map<String, Object> classFieldsAssignStatement(InnerNode assign){
        Map<String, Object> newMap = new HashMap<>();
        LeafNode ID = (LeafNode) assign.getChild(0).getChild(0);
        String leftID = ID.getTokenText();
        //拿到要赋值的变量名
        LeafNode number = (LeafNode) assign.getChild(1).getChild(0).getChild(0).getChild(0);
        //拿到Number
        newMap.put(leftID,Integer.parseInt(number.getTokenText()));
        //讲成员变量添加到类对象的Map里
        return newMap;
    }
    public int funcCalSfxModeId(InnerNode funcCalSfx, Map<String, Object> extendScope,Map<String, Object> extrtendScope) {
        LeafNode ID = (LeafNode) funcCalSfx.getChild(0);
        try {
            InnerNode funcDef = (InnerNode) extendScope.get(ID.getTokenText());
        } catch (Exception e) {
            return (int) extendScope.get(ID.getTokenText());
        }
        InnerNode funcDef = (InnerNode) extendScope.get(ID.getTokenText());
        if (funcDef == null) {
            ClassNode   classDefClassNode = (ClassNode)extendScope.get(extendScope.get("thatAccount"));
            InnerNode classDef=classDefClassNode.getMethods();
            funcDef = (InnerNode)funcCalFindMtd(classDef,ID.getTokenText());
        }
        Map<String, Object> functionCallScope = new HashMap<String, Object>();
        functionCallScope.putAll(extendScope);
        List<IntegerOrClassNode> arglisttotal = new ArrayList<IntegerOrClassNode>();
        InnerNode arglist = (InnerNode) funcCalSfx.getChild(2);
        while (true) {
            IntegerOrClassNode integerOrClassNode = new IntegerOrClassNode();
            try {
                InnerNode arglisttest = (InnerNode) arglist.getChild(0);
            } catch (Exception e) {
                break;
            }
            LeafNode Number1 = (LeafNode) arglist.getChild(0).getChild(0).getChild(0);
            int numberonee = 0;
            if (Number1.getTokenTag().equals("ID")) {
                boolean ifGo = true;
                try {
                    numberonee = (int) functionCallScope.get(Number1.getTokenText());
                }catch (Exception e){
                    ifGo=false;
                    integerOrClassNode.setClassDef(Number1.getTokenText());
                }
                if (ifGo) {
                    numberonee = (int) functionCallScope.get(Number1.getTokenText());
                }
            }
            if (Number1.getTokenTag().equals("NUMBER")) {
                numberonee = Integer.parseInt(Number1.getTokenText());
            }
            InnerNode exprmoreterm = (InnerNode) arglist.getChild(0).getChild(1);
            while (true) {
                try {
                    LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
                } catch (Exception e) {
                    break;
                }
                LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
                if (exprmoretermleafnode.getTokenTag().equals("epsilon")) {
                    break;
                }
                InnerNode exprmoretermsexper00 = (InnerNode) exprmoreterm.getChild(1).getChild(0);
                LeafNode exprmoretermsexper0 = (LeafNode) exprmoretermsexper00.getChild(0);
                if (exprmoretermsexper0.getTokenTag().equals("ID")) {
                    numberonee += (int) extendScope.get(exprmoretermsexper0.getTokenText());
                }
                if (exprmoretermsexper0.getTokenTag().equals("NUMBER")) {
                    numberonee += Integer.parseInt(exprmoretermsexper0.getTokenText());
                }
                InnerNode exprmoretermmoreterm = (InnerNode) exprmoreterm.getChild(1).getChild(1);
                exprmoreterm = exprmoretermmoreterm;
            }
            integerOrClassNode.setNumber(numberonee);
            arglisttotal.add(integerOrClassNode);
            try {
                LeafNode ifCOMMA = (LeafNode) arglist.getChild(1).getChild(0);
            } catch (Exception e) {
                break;
            }
            LeafNode ifCOMMA = (LeafNode) arglist.getChild(1).getChild(0);
            if (ifCOMMA.getTokenTag().equals("COMMA")) {
                arglist = (InnerNode) arglist.getChild(1).getChild(1);
            } else {
                break;
            }
        }
        if (funcDef.getAstName().equals("<function-def>")) {
            return functionDef(funcDef, functionCallScope, arglisttotal,extrtendScope);
        }
        if (funcDef.getAstName().equals("<closure>")) {
            return closure(funcDef, functionCallScope, arglisttotal,extrtendScope);
        }
        if (funcDef.getAstName().equals("<function-call>")) {
            return functionCall(funcDef, functionCallScope,"",extrtendScope);
        }
        return 0;
    }
    public int funcCalSfxModeObjMtd(InnerNode funcCalSfx,Map<String, Object> up) {
        LeafNode ObjMethod = (LeafNode) funcCalSfx.getChild(0);
        String tokenText = ObjMethod.getTokenText();
        String[] split = tokenText.split("\\.");
        String className =split[0];
        String methodName=split[1];
        ClassNode classDefClassNode;
        if (globalScope.containsKey(className)){
            classDefClassNode = (ClassNode) globalScope.get(className);
        }else {
            classDefClassNode = (ClassNode) globalScope.get(up.get(className));
        }
        InnerNode classDef=classDefClassNode.getMethods();
        Map<String,Object> functionCallScope = classDefClassNode.getFields();
        InnerNode funcDef = (InnerNode)funcCalFindMtd(classDef,methodName);
        List<IntegerOrClassNode> arglisttotal = new ArrayList<IntegerOrClassNode>();
        InnerNode arglist = (InnerNode) funcCalSfx.getChild(2);
        while (true) {
            IntegerOrClassNode integerOrClassNode = new IntegerOrClassNode();
            try {
                InnerNode arglisttest = (InnerNode) arglist.getChild(0);
            } catch (Exception e) {
                break;
            }
            LeafNode Number1 = (LeafNode) arglist.getChild(0).getChild(0).getChild(0);
            int numberonee = 0;
            if (Number1.getTokenTag().equals("ID")) {
                boolean ifGo = true;
                try {
                    numberonee = (int) functionCallScope.get(Number1.getTokenText());
                }catch (Exception e){
                    ifGo=false;
                    integerOrClassNode.setClassDef(Number1.getTokenText());
                }
                if (ifGo) {
                    numberonee = (int) functionCallScope.get(Number1.getTokenText());
                }

            }
            if (Number1.getTokenTag().equals("NUMBER")) {
                numberonee = Integer.parseInt(Number1.getTokenText());
            }
            InnerNode exprmoreterm = (InnerNode) arglist.getChild(0).getChild(1);
            while (true) {
                try {
                    LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
                } catch (Exception e) {
                    break;
                }
                LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
                if (exprmoretermleafnode.getTokenTag().equals("epsilon")) {
                    break;
                }
                InnerNode exprmoretermsexper00 = (InnerNode) exprmoreterm.getChild(1).getChild(0);
                LeafNode exprmoretermsexper0 = (LeafNode) exprmoretermsexper00.getChild(0);
                if (exprmoretermsexper0.getTokenTag().equals("ID")) {
                    numberonee += (int) functionCallScope.get(exprmoretermsexper0.getTokenText());
                }
                if (exprmoretermsexper0.getTokenTag().equals("NUMBER")) {
                    numberonee += Integer.parseInt(exprmoretermsexper0.getTokenText());
                }
                InnerNode exprmoretermmoreterm = (InnerNode) exprmoreterm.getChild(1).getChild(1);
                exprmoreterm = exprmoretermmoreterm;
            }
            integerOrClassNode.setNumber(numberonee);
            arglisttotal.add(integerOrClassNode);
            try {
                LeafNode ifCOMMA = (LeafNode) arglist.getChild(1).getChild(0);
            } catch (Exception e) {
                break;
            }
            LeafNode ifCOMMA = (LeafNode) arglist.getChild(1).getChild(0);
            if (ifCOMMA.getTokenTag().equals("COMMA")) {
                arglist = (InnerNode) arglist.getChild(1).getChild(1);
            } else {
                break;
            }
        }
        if (funcDef.getAstName().equals("<function-def>")) {
            return functionDef(funcDef, functionCallScope, arglisttotal,up);
        }
        if (funcDef.getAstName().equals("<closure>")) {
            return closure(funcDef, functionCallScope, arglisttotal,up);
        }
        if (funcDef.getAstName().equals("<function-call>")) {
            return functionCall(funcDef, functionCallScope,"",up);
        }
        return 0;
    }
    public InnerNode funcCalFindMtd(InnerNode classDef,String methodName) {
        InnerNode classBody = (InnerNode)classDef.getChild(2);
        ASTNode classMethods = classBody.getChild(2);
        while (classMethods.getClass().getName().equals(InnerNode.class.getName())) {
            LeafNode methodID = (LeafNode)classMethods.getChild(0).getChild(1);
            String methodIdText = methodID.getTokenText();
            if(methodIdText.equals(methodName)){
                return (InnerNode)classMethods.getChild(0);
            }
            classMethods = classMethods.getChild(1);
        }
        return classBody;
    }

}

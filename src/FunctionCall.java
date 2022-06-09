import lib.ast.ASTNode;
import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionCall {
    /**
     * 调用方法
     * @param functionCall 方法的内容
     * @param extendScope 成员变量集合
     * @param leftID 方法的名字
     * @param up 局部变量集合
     * @return 和
     */
    public static int functionCall(InnerNode functionCall, Map<String, Object> extendScope, String leftID, Map<String, Object> up) {
        LeafNode funcKeyWord = (LeafNode) functionCall.getChild(0);
        if (funcKeyWord.getTokenTag().equals("KEYWORD_CALL")) {
            return funcCalMode1(functionCall,extendScope,up);
        }
        return 0;
    }
    public static int funcCalSfxModeId(InnerNode funcCalSfx, Map<String, Object> extendScope,Map<String, Object> extrtendScope) {
        LeafNode ID = (LeafNode) funcCalSfx.getChild(0);
        try {
            InnerNode funcDef = (InnerNode) extendScope.get(ID.getTokenText());
        } catch (Exception e) {
            return (int) extendScope.get(ID.getTokenText());
        }
        InnerNode funcDef = (InnerNode) extendScope.get(ID.getTokenText());
        if (funcDef == null) {
            InnerNode classDef=(InnerNode)JVM.classStore.get("Account");
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
//            return closure(funcDef, functionCallScope, arglisttotal,extrtendScope);
        }
        if (funcDef.getAstName().equals("<function-call>")) {
            return functionCall(funcDef, functionCallScope,"",extrtendScope);
        }
        return 0;
    }
    public static int funcCalSfxModeObjMtd(InnerNode funcCalSfx,Map<String, Object> up) {
        LeafNode ObjMethod = (LeafNode) funcCalSfx.getChild(0);
        String tokenText = ObjMethod.getTokenText();
        String[] split = tokenText.split("\\.");
        String className =split[0];
        String methodName=split[1];
        ClassNode classDefClassNode;
        if (JVM.globalScope.containsKey(className)){
            classDefClassNode = (ClassNode) JVM.globalScope.get(className);
        }else {
            classDefClassNode = (ClassNode) JVM.globalScope.get(up.get(className));
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
//            return closure(funcDef, functionCallScope, arglisttotal,up);
        }
        if (funcDef.getAstName().equals("<function-call>")) {
            return functionCall(funcDef, functionCallScope,"",up);
        }
        return 0;
    }
    public static InnerNode funcCalFindMtd(InnerNode classDef,String methodName) {
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
    public static int funcCalMode1(InnerNode statementChoices, Map<String, Object> extendScope, Map<String, Object>up) {
        InnerNode funcCalSfx = (InnerNode) statementChoices.getChild(1);
        LeafNode idOrObjMethod = (LeafNode) funcCalSfx.getChild(0);
        if (idOrObjMethod.getTokenTag().equals("ID")) {
            return funcCalSfxModeId(funcCalSfx,extendScope,up);
        }
        if (idOrObjMethod.getTokenTag().equals("OBJ_METHOD")) {
            return funcCalSfxModeObjMtd(funcCalSfx,up);
        }
        return 0;
    }
    public static int functionStatements(InnerNode functionstatement, Map<String, Object> functionScope,Map<String, Object> tempScope) {
        List<ASTNode> children = functionstatement.getChildren();
        if (children.size() == 1) {
            return returnStatement((InnerNode) functionstatement.getChild(0),functionScope,tempScope);
        }
        if (children.size() == 2) {
            MainDoor.runTotal(functionstatement.getChild(0), functionScope,tempScope);
            return returnStatement((InnerNode) functionstatement.getChild(1),functionScope,tempScope);
        }
        return 0;
    }
    public static int returnStatement(InnerNode returnStatement, Map<String, Object> functionScope,Map<String, Object> tempScope) {
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
                    count = (int) tempScope.get(exprtermIDorNumber.getTokenText());
//                    List<IntegerOrClassNode> arglisttotal = new ArrayList<IntegerOrClassNode>();
//                    closure((InnerNode) functionScope.get(exprtermIDorNumber.getTokenText()),functionScope,arglisttotal,tempScope);
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
                    if (!JVM.globalScope.containsKey(IDorNumber.getTokenText())) {
                        count += (int) functionScope.get(IDorNumber.getTokenText());
                    } else {
                        count += (int) JVM.globalScope.get(IDorNumber.getTokenText());
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
    public static int functionDef(InnerNode statementChoices, Map<String, Object> functionDefScope, List<IntegerOrClassNode> arglisttotal,Map<String, Object>tempScope){
        //临时作用域tempScope
        InnerNode paramlist = (InnerNode) statementChoices.getChild(3);
        int xiabiao = 0;
        Map<String, Object> newTempScope = new HashMap<>();
        boolean ifffff = true;
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
                    if(tempScope.containsKey(ifEPSIlON.getTokenText())){
                        newTempScope.putAll(tempScope);
                        newTempScope.put(ifEPSIlON.getTokenText(),arglisttotal.get(xiabiao).getClassDef());
                        ifffff=false;
                    }else{
                        tempScope.put(ifEPSIlON.getTokenText(),arglisttotal.get(xiabiao).getClassDef());
                    }
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
        if(ifffff){
            return functionStatements((InnerNode) statementChoices.getChild(6), functionDefScope,tempScope);
        }else {
            return functionStatements((InnerNode) statementChoices.getChild(6), functionDefScope,newTempScope);
        }
    }
}

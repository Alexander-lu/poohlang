import lib.ast.ASTNode;
import lib.ast.EpsilonNode;
import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoohInterpreter {
    Map<String, Object> globalScope = new HashMap<>();
    /**
     * 运行一个POOH程序
     *
     * @param program 语法解析器解析出的程序抽象语法树
     */
    public void run(ASTNode program) {
        runTotal(program,globalScope);
    }
    public void runTotal(ASTNode program,Map<String, Object>extendScope){
        InnerNode father = (InnerNode) program;
        for (ASTNode child : father.getChildren()) {
            try {
                InnerNode boy = (InnerNode) child;
            } catch (Exception e) {
                return;
            }
            InnerNode boy = (InnerNode) child;
            if (boy.getAstName().equals("<program>")) {
                runTotal(boy,extendScope);
            }
            if (boy.getAstName().equals("<statement>")) {
                InnerNode statementChoices = (InnerNode) boy.getChild(0);
                if (statementChoices.getAstName().equals("<assign-statement>")) {
                    assignStatement(statementChoices,extendScope);
                }
                if (statementChoices.getAstName().equals("<if-statement>")) {
                    ifStatement(statementChoices,extendScope);
                }
                if (statementChoices.getAstName().equals("<while-statement>")) {
                    whileStatement(statementChoices,extendScope);
                }
                if (statementChoices.getAstName().equals("<print-statement>")) {
                    printStatement(statementChoices,extendScope);
                }
                if (statementChoices.getAstName().equals("<function-def>")) {
                    functionDef(statementChoices,extendScope);
                }
                if (statementChoices.getAstName().equals("<function-call>")) {
                    functionCall(statementChoices,extendScope);
                }
            }
        }
    }

    public int runFunctionCall(ASTNode functioncall, Map<String, Object> fatherScope) {
        List<Integer> arglisttotal = new ArrayList<Integer>();
        LeafNode ID = (LeafNode) functioncall.getChild(1);
        InnerNode arglist = (InnerNode) functioncall.getChild(3);
        while (true) {
            try {
                InnerNode arglisttest = (InnerNode) arglist.getChild(0);
            } catch (Exception e) {
                break;
            }
            LeafNode Number1 = (LeafNode) arglist.getChild(0).getChild(0).getChild(0);
            int numberonee = 0;
            if (Number1.getTokenTag().equals("ID")) {
                numberonee = (int) fatherScope.get(Number1.getTokenText());
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
                    numberonee += (int) fatherScope.get(exprmoretermsexper0.getTokenText());
                }
                if (exprmoretermsexper0.getTokenTag().equals("NUMBER")) {
                    numberonee += Integer.parseInt(exprmoretermsexper0.getTokenText());
                }
                InnerNode exprmoretermmoreterm = (InnerNode) exprmoreterm.getChild(1).getChild(1);
                exprmoreterm = exprmoretermmoreterm;
            }
            arglisttotal.add(numberonee);
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
        InnerNode functionDef = (InnerNode) globalScope.get(ID.getTokenText());
        if (functionDef.getAstName().equals("<closure>")) {
            Map<String, Object> functionScope = new HashMap<>();
            fatherScope.put("1", functionScope);
            InnerNode paramlist = (InnerNode) functionDef.getChild(2);
            int xiabiao = 0;
            while (true) {
                LeafNode ifEPSIlON = (LeafNode) paramlist.getChild(0);
                if (ifEPSIlON.getTokenTag().equals("epsilon")) {
                    break;
                } else {
                    functionScope.put(ifEPSIlON.getTokenText(), arglisttotal.get(xiabiao));
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
            InnerNode functionstatement = (InnerNode) functionDef.getChild(5);
            return functionStatements(functionstatement, functionScope);
        }
        if (functionDef.getAstName().equals("<function-def>")) {
            Map<String, Object> functionScope = new HashMap<>();
            fatherScope.put("1", functionScope);
            InnerNode paramlist = (InnerNode) functionDef.getChild(3);
            int xiabiao = 0;
            while (true) {
                LeafNode ifEPSIlON = (LeafNode) paramlist.getChild(0);
                if (ifEPSIlON.getTokenTag().equals("epsilon")) {
                    break;
                } else {
                    functionScope.put(ifEPSIlON.getTokenText(), arglisttotal.get(xiabiao));
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
            InnerNode functionstatement = (InnerNode) functionDef.getChild(6);
            return functionStatements(functionstatement, functionScope);
        }
        return 2;
    }
    public int functionStatements(InnerNode functionstatement, Map<String, Object> functionScope) {
        List<ASTNode> children = functionstatement.getChildren();
        if (children.size() == 1) {
            return returnStatement((InnerNode) functionstatement.getChild(0),functionScope);
        }
        if (children.size() == 2) {
            run2(functionstatement.getChild(0), functionScope);
            return returnStatement((InnerNode) functionstatement.getChild(1),functionScope);
        }
        return 0;
    }

    public int returnStatement(InnerNode returnStatement, Map<String, Object> functionScope) {
        InnerNode expr = (InnerNode) returnStatement.getChild(1);
        ASTNode exprtermIDorNumberorFunctionCall = expr.getChild(0).getChild(0);
        InnerNode exprmoreterms = (InnerNode) expr.getChild(1);
        int count = 0;
        boolean ifnext = true;
        try {
            LeafNode ifexprtermIDorNumberorFunctionCall = (LeafNode) exprtermIDorNumberorFunctionCall;
        } catch (Exception e) {
            InnerNode ifexprtermIDorNumberorFunctionCall = (InnerNode) exprtermIDorNumberorFunctionCall;
            count = runFunctionCall(ifexprtermIDorNumberorFunctionCall, functionScope);
        }
        if (ifnext) {
            LeafNode exprtermIDorNumber = (LeafNode) exprtermIDorNumberorFunctionCall;
            if (exprtermIDorNumber.getTokenTag().equals("ID")) {
                count = (int) functionScope.get(exprtermIDorNumber.getTokenText());
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
                count += runFunctionCall(FunctionCall, functionScope);
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

    public void assignStatement(InnerNode assign,Map<String, Object> functionScope){
        LeafNode ID = (LeafNode) assign.getChild(0).getChild(0);
        String leftID = ID.getTokenText();
        InnerNode exprOrclosure = (InnerNode) assign.getChild(1).getChild(0);
        if (exprOrclosure.getAstName().equals("<expr>")) {
            functionScope.remove(leftID);
            functionScope.put(leftID,assignExpr(exprOrclosure,functionScope));
        }else if (exprOrclosure.getAstName().equals("<closure>")) {
            String funcIDScope = leftID + "Scope";
            globalScope.remove(funcIDScope);
            globalScope.put(funcIDScope, new HashMap<String, Object>());
            globalScope.remove(leftID);
            globalScope.put(leftID, exprOrclosure);
        }
    }
    public int assignExpr(InnerNode exprOrclosure, Map<String, Object> functionScope){
        int count = 0;
        InnerNode exprtermm = (InnerNode) exprOrclosure.getChild(0);
        LeafNode exprterm = (LeafNode) exprtermm.getChild(0);
        if (exprterm.getTokenTag().equals("ID")) {
            count = (int) globalScope.get(exprterm.getTokenText());
        }
        if (exprterm.getTokenTag().equals("NUMBER")) {
            count = Integer.parseInt(exprterm.getTokenText());
        }
        InnerNode exprmoreterm = (InnerNode) exprOrclosure.getChild(1);
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
                count += (int) globalScope.get(exprmoretermsexper0.getTokenText());
            }
            if (exprmoretermsexper0.getTokenTag().equals("NUMBER")) {
                count += Integer.parseInt(exprmoretermsexper0.getTokenText());
            }
            InnerNode exprmoretermmoreterm = (InnerNode) exprmoreterm.getChild(1).getChild(1);
            exprmoreterm = exprmoretermmoreterm;
        }
        return count;
    }
    public void closure(){}
    public void printStatement(InnerNode statementChoices, Map<String, Object> functionScope){
        InnerNode expr = (InnerNode) statementChoices.getChild(1);
        try {
            LeafNode term = (LeafNode) expr.getChild(0).getChild(0);
        } catch (Exception e) {
            InnerNode term = (InnerNode) expr.getChild(0).getChild(0);
            LeafNode funcID = (LeafNode) term.getChild(1);
            String funcIDScope = funcID.getTokenText() + "Scope";
            Object funcIDScopeCheck = globalScope.get(funcIDScope);
            Map<String, Object> fatherScope = (Map<String, Object>) funcIDScopeCheck;
            System.out.println(runFunctionCall(term, fatherScope));
            return;
        }
        LeafNode term = (LeafNode) expr.getChild(0).getChild(0);
        if (term.getTokenTag().equals("NUMBER")) {
            System.out.println(Integer.parseInt(term.getTokenText()));
        }
        if (term.getTokenTag().equals("ID")) {
            System.out.println(globalScope.get(term.getTokenText()));
        }
    }
    public void ifStatement(InnerNode statementChoices, Map<String, Object> functionScope){
            InnerNode expr = (InnerNode) statementChoices.getChild(2);
            if (ifExpr(expr,functionScope)) {
                runTotal(statementChoices.getChild(4),functionScope);
            } else {
                runTotal(statementChoices.getChild(6),functionScope);
            }
    }
    public boolean exprLessOrEqual(InnerNode exprOrclosure, Map<String, Object> functionScope){
        int left = 0;
        int right = 0;
        LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
        if (exprmoretermleafnode.getTokenTag().equals("LESS_THAN")) {
            return true;
        }
        if (exprmoretermleafnode.getTokenTag().equals("EQUAL_TEST")) {
            return false;
        }
    }
    public boolean ifExpr(InnerNode expr, Map<String, Object> functionScope){
        int left = 0;
        int right = 0;
        LeafNode numberorid = (LeafNode) expr.getChild(0).getChild(0);
        if (numberorid.getTokenTag().equals("NUMBER")) {
            left = Integer.parseInt(numberorid.getTokenText());
        }
        if (numberorid.getTokenTag().equals("ID")) {
            left = (int) globalScope.get(numberorid.getTokenText());
        }
        LeafNode exprmoretermsexper00 = (LeafNode) expr.getChild(1).getChild(1).getChild(0);
        if (exprmoretermsexper00.getTokenTag().equals("ID")) {
            right = (int) globalScope.get(exprmoretermsexper00.getTokenText());
        }
        if (exprmoretermsexper00.getTokenTag().equals("NUMBER")) {
            right = Integer.parseInt(exprmoretermsexper00.getTokenText());
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
    public void whileStatement(InnerNode statementChoices, Map<String, Object> functionScope){
        int countttttt = 0;
        int lefttgrement = 0;
        int minddle = 0;
        int rightgrement = 0;
        int left = 0;
        String leftstring = null;
        int rightnumber = 0;
        String rightstring = null;
        InnerNode expr = (InnerNode) statementChoices.getChild(2);
        LeafNode numberorid = (LeafNode) expr.getChild(0).getChild(0);
        if (numberorid.getTokenTag().equals("NUMBER")) {
            left = Integer.parseInt(numberorid.getTokenText());
            lefttgrement = 1;
        }
        if (numberorid.getTokenTag().equals("ID")) {
            leftstring = numberorid.getTokenText();
            lefttgrement = 2;
        }
        InnerNode exprmoreterm = (InnerNode) expr.getChild(1);
        try {
            LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
        } catch (Exception e) {
            break;
        }
        LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
        if (exprmoretermleafnode.getTokenTag().equals("LESS_THAN")) {
            minddle = 1;
        }
        if (exprmoretermleafnode.getTokenTag().equals("EQUAL_TEST")) {
            minddle = 2;
        }
        LeafNode exprmoretermsexper00 = (LeafNode) exprmoreterm.getChild(1).getChild(0);
        if (exprmoretermsexper00.getTokenTag().equals("ID")) {
            rightgrement = 1;
            rightstring = exprmoretermsexper00.getTokenText();
        }
        if (exprmoretermsexper00.getTokenTag().equals("NUMBER")) {
            rightgrement = 2;
            rightnumber = Integer.parseInt(exprmoretermsexper00.getTokenText());
        }
        if (lefttgrement == 2) {
            if (minddle == 1) {
                if (rightgrement == 2) {
                    while ((int) globalScope.get(leftstring) < rightnumber) {
                        runTotal(statementChoices.getChild(4));
                    }
                }
                if (rightgrement == 1) {
                    while ((int) globalScope.get(leftstring) < (int) globalScope.get(rightstring)) {
                        runTotal(statementChoices.getChild(4));
                    }
                }
            }
            if (minddle == 2) {
                if (rightgrement == 2) {
                    while ((int) globalScope.get(leftstring) == rightnumber) {
                        runTotal(statementChoices.getChild(4));
                    }
                }
                if (rightgrement == 1) {
                    while (globalScope.get(leftstring).equals(globalScope.get(rightstring))) {
                        runTotal(statementChoices.getChild(4));
                    }
                }
            }
        }
        if (lefttgrement == 1) {
            if (minddle == 1) {
                while (left < (int) globalScope.get(rightstring)) {
                    runTotal(statementChoices.getChild(4));
                }
            }
            if (minddle == 2) {
                while (left == (int) globalScope.get(rightstring)) {
                    runTotal(statementChoices.getChild(4));
                }
            }
        }
    }
    public void functionDef(InnerNode statementChoices, Map<String, Object> functionScope){
        LeafNode funcID = (LeafNode) statementChoices.getChild(1);
        String funcIDScope = funcID.getTokenText() + "Scope";
        globalScope.put(funcID.getTokenText(), (InnerNode) statementChoices);
        globalScope.put(funcIDScope, new HashMap<String, Object>());
    }
    public void functionCall(InnerNode statementChoices, Map<String, Object> functionScope){
        LeafNode funcID = (LeafNode) statementChoices.getChild(1);
        String funcIDScope = funcID.getTokenText() + "Scope";
        Object funcIDScopeCheck = globalScope.get(funcIDScope);
        Map<String, Object> fatherScope = (Map<String, Object>) funcIDScopeCheck;
        runFunctionCall((ASTNode) statementChoices, fatherScope);
    }
}

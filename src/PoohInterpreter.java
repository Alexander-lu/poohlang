import lib.ast.ASTNode;
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
        InnerNode father = (InnerNode) program;
        if (father.getAstName().equals("<block>")) {
            run(father.getChild(1));
        }
        for (ASTNode child : father.getChildren()) {
            try {
                InnerNode boy = (InnerNode) child;
            } catch (Exception e) {
                return;
            }
            InnerNode boy = (InnerNode) child;
            if (boy.getAstName().equals("<program>")) {
                run(boy);
            }
            if (boy.getAstName().equals("<statement>")) {
                InnerNode wwName = (InnerNode) boy.getChild(0);
                if (wwName.getAstName().equals("<assign-statement>")) {
                    LeafNode ID = (LeafNode) wwName.getChild(0).getChild(0);
                    String namename = ID.getTokenText();
                    InnerNode exprOrclosure = (InnerNode) wwName.getChild(1).getChild(0);
                    int countcount = 0;
                    if (exprOrclosure.getAstName().equals("<expr>")) {
                        InnerNode exprtermm = (InnerNode) exprOrclosure.getChild(0);
                        LeafNode exprterm = (LeafNode) exprtermm.getChild(0);
                        if (exprterm.getTokenTag().equals("ID")) {
                            countcount = (int) globalScope.get(exprterm.getTokenText());
                        }
                        if (exprterm.getTokenTag().equals("NUMBER")) {
                            countcount = Integer.parseInt(exprterm.getTokenText());
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
                                countcount += (int) globalScope.get(exprmoretermsexper0.getTokenText());
                            }
                            if (exprmoretermsexper0.getTokenTag().equals("NUMBER")) {
                                countcount += Integer.parseInt(exprmoretermsexper0.getTokenText());
                            }
                            InnerNode exprmoretermmoreterm = (InnerNode) exprmoreterm.getChild(1).getChild(1);
                            exprmoreterm = exprmoretermmoreterm;
                        }
                        globalScope.remove(namename);
                        globalScope.put(namename, countcount);
                    }
                    if (exprOrclosure.getAstName().equals("<closure>")) {
                        String funcIDScope = namename + "Scope";
                        globalScope.remove(funcIDScope);
                        globalScope.put(funcIDScope, new HashMap<String, Object>());
                        globalScope.remove(namename);
                        globalScope.put(namename, exprOrclosure);
                    }

                }
                if (wwName.getAstName().equals("<if-statement>")) {
                    int left = 0;
                    InnerNode expr = (InnerNode) wwName.getChild(2);
                    LeafNode numberorid = (LeafNode) expr.getChild(0).getChild(0);
                    if (numberorid.getTokenTag().equals("NUMBER")) {
                        left = Integer.parseInt(numberorid.getTokenText());
                    }
                    if (numberorid.getTokenTag().equals("ID")) {
                        left = (int) globalScope.get(numberorid.getTokenText());
                    }
                    InnerNode exprmoreterm = (InnerNode) expr.getChild(1);
                    int ringhtg = 0;
                    int ringhtcount = 0;
                    try {
                        LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
                    } catch (Exception e) {
                        break;
                    }
                    LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
                    if (exprmoretermleafnode.getTokenTag().equals("LESS_THAN")) {
                        ringhtg = 1;
                    }
                    if (exprmoretermleafnode.getTokenTag().equals("EQUAL_TEST")) {
                        ringhtg = 2;
                    }
                    LeafNode exprmoretermsexper00 = (LeafNode) exprmoreterm.getChild(1).getChild(0);
                    if (exprmoretermsexper00.getTokenTag().equals("ID")) {
                        ringhtcount = (int) globalScope.get(exprmoretermsexper00.getTokenText());
                    }
                    if (exprmoretermsexper00.getTokenTag().equals("NUMBER")) {
                        ringhtcount = Integer.parseInt(exprmoretermsexper00.getTokenText());
                    }
                    if (ringhtg == 1) {
                        if (left < ringhtcount) {
                            run(wwName.getChild(4));
                        } else {
                            run(wwName.getChild(6));
                        }
                    }
                    if (ringhtg == 2) {
                        if (left == ringhtcount) {
                            run(wwName.getChild(4));
                        } else {
                            run(wwName.getChild(6));
                        }
                    }
                }
                if (wwName.getAstName().equals("<while-statement>")) {
                    int countttttt = 0;
                    int lefttgrement = 0;
                    int minddle = 0;
                    int rightgrement = 0;
                    int left = 0;
                    String leftstring = null;
                    int rightnumber = 0;
                    String rightstring = null;
                    InnerNode expr = (InnerNode) wwName.getChild(2);
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
                                    run(wwName.getChild(4));
                                }
                            }
                            if (rightgrement == 1) {
                                while ((int) globalScope.get(leftstring) < (int) globalScope.get(rightstring)) {
                                    run(wwName.getChild(4));
                                }
                            }
                        }
                        if (minddle == 2) {
                            if (rightgrement == 2) {
                                while ((int) globalScope.get(leftstring) == rightnumber) {
                                    run(wwName.getChild(4));
                                }
                            }
                            if (rightgrement == 1) {
                                while (globalScope.get(leftstring).equals(globalScope.get(rightstring))) {
                                    run(wwName.getChild(4));
                                }
                            }
                        }
                    }
                    if (lefttgrement == 1) {
                        if (minddle == 1) {
                            while (left < (int) globalScope.get(rightstring)) {
                                run(wwName.getChild(4));
                            }
                        }
                        if (minddle == 2) {
                            while (left == (int) globalScope.get(rightstring)) {
                                run(wwName.getChild(4));
                            }
                        }
                    }
                }
                if (wwName.getAstName().equals("<print-statement>")) {
                    InnerNode expr = (InnerNode) wwName.getChild(1);
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
                if (wwName.getAstName().equals("<function-def>")) {
                    LeafNode funcID = (LeafNode) wwName.getChild(1);
                    String funcIDScope = funcID.getTokenText() + "Scope";
                    globalScope.put(funcID.getTokenText(), (InnerNode) wwName);
                    globalScope.put(funcIDScope, new HashMap<String, Object>());
                }
                if (wwName.getAstName().equals("<function-call>")) {
                    LeafNode funcID = (LeafNode) wwName.getChild(1);
                    String funcIDScope = funcID.getTokenText() + "Scope";
                    Object funcIDScopeCheck = globalScope.get(funcIDScope);
                    Map<String, Object> fatherScope = (Map<String, Object>) funcIDScopeCheck;
                    runFunctionCall((ASTNode) wwName, fatherScope);
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

    public void run2(ASTNode program, Map<String, Object> functionScope) {
        InnerNode father = (InnerNode) program;
        if (father.getAstName().equals("<block>")) {
            run2(program.getChild(1), functionScope);
        }
        for (ASTNode child : father.getChildren()) {
            try {
                InnerNode boy = (InnerNode) child;
            } catch (Exception e) {
                return;
            }
            InnerNode boy = (InnerNode) child;
            if (boy.getAstName().equals("<program>")) {
                run2(boy, functionScope);
            }
            if (boy.getAstName().equals("<statement>")) {
                InnerNode wwName = (InnerNode) boy.getChild(0);
                if (wwName.getAstName().equals("<assign-statement>")) {
                    LeafNode ID = (LeafNode) wwName.getChild(0).getChild(0);
                    String namename = ID.getTokenText();
                    InnerNode exprOrclosure = (InnerNode) wwName.getChild(1).getChild(0);
                    if (exprOrclosure.getAstName().equals("expr")) {
                        InnerNode exprtermm = (InnerNode) exprOrclosure.getChild(0);
                        boolean iffunctioncall = true;
                        int countcount = 0;
                        try {
                            LeafNode exprterm = (LeafNode) exprtermm.getChild(0);
                        } catch (Exception e) {
                            InnerNode exprterm = (InnerNode) exprtermm.getChild(0);
                            countcount = runFunctionCall(exprterm, functionScope);
                            iffunctioncall = false;
                        }
                        if (iffunctioncall) {
                            LeafNode exprterm = (LeafNode) exprtermm.getChild(0);
                            if (exprterm.getTokenTag().equals("ID")) {
                                if (!globalScope.containsKey(exprterm.getTokenText())) {
                                    countcount = (int) functionScope.get(exprterm.getTokenText());
                                } else {
                                    countcount = (int) globalScope.get(exprterm.getTokenText());
                                }
                            }
                            if (exprterm.getTokenTag().equals("NUMBER")) {
                                countcount = Integer.parseInt(exprterm.getTokenText());
                            }
                        }
                        InnerNode exprmoreterm = (InnerNode) exprOrclosure.getChild(1);
                        while (true) {
                            try {
                                LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
                            } catch (Exception e) {
                                break;
                            }
                            InnerNode exprmoretermsexper00 = (InnerNode) exprmoreterm.getChild(1).getChild(0);
                            boolean ifnamemm = true;
                            try {
                                LeafNode exprterm = (LeafNode) exprmoretermsexper00.getChild(0);
                            } catch (Exception e) {
                                InnerNode exprterm = (InnerNode) exprmoretermsexper00.getChild(0);
                                countcount += runFunctionCall(exprterm, functionScope);
                                ifnamemm = false;
                            }
                            if (ifnamemm) {
                                LeafNode exprmoretermsexper0 = (LeafNode) exprmoretermsexper00.getChild(0);
                                if (exprmoretermsexper0.getTokenTag().equals("ID")) {
                                    if (!globalScope.containsKey(exprmoretermsexper0.getTokenText())) {
                                        countcount += (int) functionScope.get(exprmoretermsexper0.getTokenText());
                                    } else {
                                        countcount += (int) globalScope.get(exprmoretermsexper0.getTokenText());
                                    }
                                }
                                if (exprmoretermsexper0.getTokenTag().equals("NUMBER")) {
                                    countcount += Integer.parseInt(exprmoretermsexper0.getTokenText());
                                }
                            }
                            InnerNode exprmoretermmoreterm = (InnerNode) exprmoreterm.getChild(1).getChild(1);
                            exprmoreterm = exprmoretermmoreterm;
                        }
                        functionScope.remove(namename);
                        functionScope.put(namename, countcount);
                    }
                    if (exprOrclosure.getAstName().equals("<closure>")) {
                        functionScope.remove(namename);
                        functionScope.put(namename, exprOrclosure);
                    }
                }
                if (wwName.getAstName().equals("<if-statement>")) {
                    int left = 0;
                    InnerNode expr = (InnerNode) wwName.getChild(2);
                    LeafNode numberorid = (LeafNode) expr.getChild(0).getChild(0);
                    if (numberorid.getTokenTag().equals("NUMBER")) {
                        left = Integer.parseInt(numberorid.getTokenText());
                    }
                    if (numberorid.getTokenTag().equals("ID")) {
                        left = (int) functionScope.get(numberorid.getTokenText());
                    }
                    InnerNode exprmoreterm = (InnerNode) expr.getChild(1);
                    int ringhtg = 0;
                    int ringhtcount = 0;
                    try {
                        LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
                    } catch (Exception e) {
                        break;
                    }
                    LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
                    if (exprmoretermleafnode.getTokenTag().equals("LESS_THAN")) {
                        ringhtg = 1;
                    }
                    if (exprmoretermleafnode.getTokenTag().equals("EQUAL_TEST")) {
                        ringhtg = 2;
                    }
                    LeafNode exprmoretermsexper00 = (LeafNode) exprmoreterm.getChild(1).getChild(0);
                    if (exprmoretermsexper00.getTokenTag().equals("ID")) {
                        ringhtcount = (int) globalScope.get(exprmoretermsexper00.getTokenText());
                    }
                    if (exprmoretermsexper00.getTokenTag().equals("NUMBER")) {
                        ringhtcount = Integer.parseInt(exprmoretermsexper00.getTokenText());
                    }
                    if (ringhtg == 1) {
                        if (left < ringhtcount) {
                            run2(wwName.getChild(4), functionScope);
                        } else {
                            run2(wwName.getChild(6), functionScope);
                        }
                    }
                    if (ringhtg == 2) {
                        if (left == ringhtcount) {
                            run2(wwName.getChild(4), functionScope);
                        } else {
                            run2(wwName.getChild(6), functionScope);
                        }
                    }
                }
                if (wwName.getAstName().equals("<while-statement>")) {
                    int countttttt = 0;
                    int lefttgrement = 0;
                    int minddle = 0;
                    int rightgrement = 0;
                    int left = 0;
                    String leftstring = null;
                    int rightnumber = 0;
                    String rightstring = null;
                    InnerNode expr = (InnerNode) wwName.getChild(2);
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
                                    run2(wwName.getChild(4), functionScope);
                                }
                            }
                            if (rightgrement == 1) {
                                while ((int) globalScope.get(leftstring) < (int) globalScope.get(rightstring)) {
                                    run2(wwName.getChild(4), functionScope);
                                }
                            }
                        }
                        if (minddle == 2) {
                            if (rightgrement == 2) {
                                while ((int) globalScope.get(leftstring) == rightnumber) {
                                    run2(wwName.getChild(4), functionScope);
                                }
                            }
                            if (rightgrement == 1) {
                                while (globalScope.get(leftstring).equals(globalScope.get(rightstring))) {
                                    run2(wwName.getChild(4), functionScope);
                                }
                            }
                        }
                    }
                    if (lefttgrement == 1) {
                        if (minddle == 1) {
                            if (!globalScope.containsKey(rightstring)) {
                                while (left < (int) functionScope.get(rightstring)) {
                                    run2(wwName.getChild(4), functionScope);
                                }
                            } else {
                                while (left < (int) globalScope.get(rightstring)) {
                                    run2(wwName.getChild(4), functionScope);
                                }
                            }

                        }
                        if (minddle == 2) {
                            while (left == (int) globalScope.get(rightstring)) {
                                run2(wwName.getChild(4), functionScope);
                            }
                        }
                    }
                }
                if (wwName.getAstName().equals("<print-statement>")) {
                    InnerNode expr = (InnerNode) wwName.getChild(1);
                    try {
                        LeafNode term = (LeafNode) expr.getChild(0).getChild(0);
                    } catch (Exception e) {
                        InnerNode term = (InnerNode) expr.getChild(0).getChild(0);
                        System.out.println(runFunctionCall(term, globalScope));
                        return;
                    }
                    LeafNode term = (LeafNode) expr.getChild(0).getChild(0);
                    if (term.getTokenTag().equals("NUMBER")) {
                        System.out.println(Integer.parseInt(term.getTokenText()));
                    }
                    if (term.getTokenTag().equals("ID")) {
                        System.out.println(functionScope.get(term.getTokenText()));
                    }
//                    if (term.getTokenTag().equals("<function-call>")) {}
                }
                if (wwName.getAstName().equals("<function-def>")) {
                    LeafNode funcID = (LeafNode) wwName.getChild(1);
                    InnerNode functionDef = (InnerNode) wwName;
                    globalScope.put(funcID.getTokenText(), functionDef);
                }
                if (wwName.getAstName().equals("<function-call>")) {
                    runFunctionCall(wwName, globalScope);
                }
            }
        }

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
    public void closure(){}
    public void assignStatement(){}
}

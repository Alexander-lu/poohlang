import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.HashMap;
import java.util.Map;

public class WhileStatement {
    public static void whileStatement(InnerNode statementChoices, Map<String, Object> extendScope,Map<String, Object>tempScope){
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
                    if (extendScope.containsKey(rightstring)) {
                        while ((int) extendScope.get(leftstring) < rightNumber) {
                            MainDoor.runTotal(statementChoices.getChild(4).getChild(1),extendScope,tempScope);
                        }
                    }else {
                        while ((int) tempScope.get(leftstring) < rightNumber) {
                            MainDoor.runTotal(statementChoices.getChild(4).getChild(1),extendScope,tempScope);
                        }
                    }

                }
                if (RightIdOrNumber == 1) {
                    while ((int) extendScope.get(leftstring) < (int) extendScope.get(rightstring)) {
                        MainDoor.runTotal(statementChoices.getChild(4),extendScope,new HashMap<>());
                    }
                }
            }
            if (minddle == 2) {
                if (RightIdOrNumber == 2) {
                    while ((int) extendScope.get(leftstring) == rightNumber) {
                        MainDoor.runTotal(statementChoices.getChild(4).getChild(1),extendScope,tempScope);
                    }
                }
                if (RightIdOrNumber == 1) {
                    while (extendScope.get(leftstring).equals(extendScope.get(rightstring))) {
                        MainDoor.runTotal(statementChoices.getChild(4).getChild(1),extendScope,tempScope);
                    }
                }
            }
        }
        if (leftIdOrNumber == 1) {
            if (minddle == 1) {
                if (extendScope.containsKey(rightstring)) {
                    while (leftNumber < (int) extendScope.get(rightstring)) {
                        MainDoor.runTotal(statementChoices.getChild(4).getChild(1),extendScope,new HashMap<>());
                    }
                }else {
                    while (leftNumber < (int) tempScope.get(rightstring)) {
                        MainDoor.runTotal(statementChoices.getChild(4).getChild(1),extendScope,tempScope);
                    }
                }

            }
            if (minddle == 2) {
                while (leftNumber == (int) extendScope.get(rightstring)) {
                    MainDoor.runTotal(statementChoices.getChild(4).getChild(1),extendScope,tempScope);
                }
            }
        }
    }
}

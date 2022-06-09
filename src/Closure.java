import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Closure {
        public static int closure(InnerNode statementChoices, Map<String, Object> extendScope, List<IntegerOrClassNode> arglisttotal, Map<String, Object>tempScope){
        Map<String,Object> functionDefScope = new HashMap<>();
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
        return FunctionCall.functionStatements((InnerNode) statementChoices.getChild(5), functionDefScope,tempScope);
    }
}

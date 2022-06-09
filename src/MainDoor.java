import lib.ast.ASTNode;
import lib.ast.InnerNode;
import lib.ast.LeafNode;
import java.util.HashMap;
import java.util.Map;
public class MainDoor {
    public static void runTotal(ASTNode program, Map<String, Object> classFieldsMembers, Map<String, Object>tempScope){
        InnerNode father = (InnerNode) program;
        for (ASTNode child : father.getChildren()) {
            try {
                InnerNode boy = (InnerNode) child;
            } catch (Exception e) {
                return;
            }
            InnerNode boy = (InnerNode) child;
            if (boy.getAstName().equals("<program>")) {
                runTotal(boy,classFieldsMembers,tempScope);
            }
            if (boy.getAstName().equals("<statement>")) {
                InnerNode statementChoices = (InnerNode) boy.getChild(0);
                if (statementChoices.getAstName().equals("<class-def>")){
                    LeafNode ID = (LeafNode)statementChoices.getChild(1);
                    JVM.classStore.put(ID.getTokenText(),statementChoices);
                }
                else if (statementChoices.getAstName().equals("<assign-statement>")) {
                    AssignStatement.assignStatement(statementChoices,classFieldsMembers,tempScope);
                }
                else if (statementChoices.getAstName().equals("<if-statement>")) {
                    IfStatement.ifStatement(statementChoices,classFieldsMembers,tempScope);
                }
                else if (statementChoices.getAstName().equals("<while-statement>")) {
                    WhileStatement.whileStatement(statementChoices,classFieldsMembers,tempScope);
                }
                else if (statementChoices.getAstName().equals("<print-statement>")) {
 System.out.println(AssignStatement.assignExpr((InnerNode)statementChoices.getChild(1),classFieldsMembers,tempScope));
                }
                else if (statementChoices.getAstName().equals("<function-def>")) {
                    LeafNode ID = (LeafNode)statementChoices.getChild(1);
                    classFieldsMembers.put(ID.getTokenText(),statementChoices);
                }
                else if (statementChoices.getAstName().equals("<function-call>")) {
                    FunctionCall.functionCall(statementChoices,classFieldsMembers,"",tempScope);
                }
            }
        }
    }
}

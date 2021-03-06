import lib.ast.ASTNode;
import lib.ast.EpsilonNode;
import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.HashMap;
import java.util.Map;
public class Class1 {
    public static void readASTNode(ASTNode program, Node1 classFieldsMembers, Map<String, Object>tempScope, String className, String closureName){
        InnerNode programInnerNode = (InnerNode) program;
                for (ASTNode child : programInnerNode.getChildren()) {
                    if (child.getClass().getName().equals(EpsilonNode.class.getName())) {
                continue;
            }
            InnerNode childInnerNode = (InnerNode) child;
            if (childInnerNode.getAstName().equals("<program>")) {readASTNode(childInnerNode,classFieldsMembers,tempScope,className,closureName);}
            if (childInnerNode.getAstName().equals("<statement>")) {
                InnerNode statementChoices = (InnerNode) childInnerNode.getChild(0);
                if (statementChoices.getAstName().equals("<class-def>")){
                    LeafNode ID = (LeafNode)statementChoices.getChild(1);
                    JVM.classStore.put(ID.getTokenText(),statementChoices);
                }
                else if (statementChoices.getAstName().equals("<assign-statement>")) {
                    Class2.assign(statementChoices,classFieldsMembers,tempScope,className,closureName);
                }
                else if (statementChoices.getAstName().equals("<if-statement>")) {
                    IfStatement.ifStatement(statementChoices,classFieldsMembers,tempScope,className,closureName);
                }
                else if (statementChoices.getAstName().equals("<while-statement>")) {
                    WhileStatement.whileStatement(statementChoices,classFieldsMembers,tempScope,className,closureName);
                }
                else if (statementChoices.getAstName().equals("<print-statement>")) {
                    if(statementChoices.getChild(1).getChild(0).getChild(0).getClass().getName().equals(LeafNode.class.getName())){
                        LeafNode a =(LeafNode)statementChoices.getChild(1).getChild(0).getChild(0);
                        if (a.getTokenText().equals("c")) {
                        }else {
                            int printlnOut=Class2.assignExpr((InnerNode)statementChoices.getChild(1),classFieldsMembers,tempScope,className,closureName);
                            System.out.println(printlnOut);
                        }
                    }else {
                        int printlnOut=Class2.assignExpr((InnerNode)statementChoices.getChild(1),classFieldsMembers,tempScope,className,closureName);
                        System.out.println(printlnOut);
                    }
                }
                else if (statementChoices.getAstName().equals("<function-def>")) {
                    LeafNode ID = (LeafNode)statementChoices.getChild(1);
                    classFieldsMembers.????????????(ID.getTokenText(), new Node3(statementChoices, new HashMap<>(), "method"));
                }
                else if (statementChoices.getAstName().equals("<function-call>")) {
                    Class2.functionCall(statementChoices,classFieldsMembers,tempScope,className,closureName);
                }
            }
        }
    }
}

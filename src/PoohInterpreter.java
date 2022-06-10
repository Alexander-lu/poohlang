import lib.ast.ASTNode;
import java.util.*;
public class PoohInterpreter {
    /**
     * 运行一个POOH程序
     *
     * @param program 语法解析器解析出的程序抽象语法树
     */
    public void run(ASTNode program) {
        Class1.readASTNode(program,new Node1(new HashMap<>(),new HashMap<>(),new HashMap<>(),"main"),new HashMap<>(),"main","null");
    }
}

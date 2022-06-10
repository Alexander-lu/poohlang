import lib.ast.InnerNode;
import java.util.Map;

public class Node3 {
    private InnerNode 函数;
    private Map<String,Node2> 变量集合;
    public String status;

    public Node3(InnerNode 函数, Map<String, Node2> 变量集合, String status) {
        this.函数 = 函数;
        this.变量集合 = 变量集合;
        this.status = status;
    }

    public Node3(InnerNode 函数, String status) {
        this.函数 = 函数;
        this.status = status;
    }

    public InnerNode get形参() {
        return (InnerNode) 函数.getChild(3);
    }
    public InnerNode getclosure形参() {
        return (InnerNode) 函数.getChild(2);
    }

    public InnerNode get方法体() {
        return (InnerNode) 函数.getChild(5);
    }
    public InnerNode getclosure方法体() {
        return (InnerNode) 函数.getChild(4);
    }
}

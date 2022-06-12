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
        return (InnerNode) 函数.getChild(6);
    }
    public InnerNode getclosure方法体() {
        return (InnerNode) 函数.getChild(5);
    }
    public InnerNode get函数(){
        return 函数;
    }
    public void 添加成员变量(String 变量名,Node2 变量值){
        变量集合.put(变量名,变量值);
    }
    public Integer 获取成员变量值(String name){
        return 变量集合.get(name).get值();
    }
    public boolean 有没有成员变量值(String name){
        return 变量集合.containsKey(name);
    }
}

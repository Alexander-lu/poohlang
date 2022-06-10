import lib.ast.InnerNode;

import java.util.Map;

public class Node1 {
    /**
     * 保存类的成员变量和闭包
     */
    private Map<String, Node1> 类集合;
    private Map<String,Node3> 闭包集合;
    private Map<String,Node2> 变量集合;
    private String status;

    public Node1() {
    }

    public Node1(Map<String, Node3> 闭包集合, Map<String, Node2> 变量集合, String status) {
        this.闭包集合 = 闭包集合;
        this.变量集合 = 变量集合;
        this.status = status;
    }

    public Node1(Map<String, Node1> 类集合, Map<String, Node3> 闭包集合, Map<String, Node2> 变量集合, String status) {
        this.类集合 = 类集合;
        this.闭包集合 = 闭包集合;
        this.变量集合 = 变量集合;
        this.status = status;
    }


    public Map<String, Node3> get闭包集合() {
        return 闭包集合;
    }

    public Map<String, Node2> get变量集合() {
        return 变量集合;
    }

    public void set闭包集合(Map<String, Node3> 闭包集合) {
        this.闭包集合 = 闭包集合;
    }

    public void set变量集合(Map<String, Node2> 变量集合) {
        this.变量集合 = 变量集合;
    }
    public void 添加闭包(String 闭包名,Node3 闭包内容){
        闭包集合.put(闭包名,闭包内容);
    }
    public void 添加成员变量(String 变量名,Node2 变量值){
        变量集合.put(变量名,变量值);
    }

    public Map<String, Node1> get类集合() {
        return 类集合;
    }

    public void 添加内部类(String 类名, Node1 类) {
        类集合.put(类名,类);
    }
    public Integer 获取成员变量值(String name){
        return 变量集合.get(name).get值();
    }
    public Node3 获取闭包(String name){
        return 闭包集合.get(name);
    }
    public Node1 获取内部类(String name){
        return 类集合.get(name);
    }
}


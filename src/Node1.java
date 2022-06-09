import lib.ast.InnerNode;

import java.util.Map;

public class Node1 {
    /**
     * 保存类的成员变量和方法
     */
    private InnerNode classDEF;
    private Map<String,IntegerOrClassNode> fields;

    public Node1(InnerNode methods, Map<String,IntegerOrClassNode> fields) {
        this.classDEF = methods;
        this.fields = fields;
    }

    public void setMethods(InnerNode methods) {
        this.classDEF = methods;
    }

    public void setFields(Map<String, IntegerOrClassNode> fields) {
        this.fields = fields;
    }

    public InnerNode getMethods() {
        return classDEF;
    }

    public Map<String, IntegerOrClassNode> getFields() {
        return fields;
    }
}

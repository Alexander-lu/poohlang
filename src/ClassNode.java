import lib.ast.InnerNode;

import java.util.Map;

public class ClassNode {
    /**
     * 保存类的成员变量和方法
     */
    private InnerNode classDEF;
    private Map<String,Object> fields;

    public ClassNode(InnerNode methods, Map<String,Object> fields) {
        this.classDEF = methods;
        this.fields = fields;
    }

    public void setMethods(InnerNode methods) {
        this.classDEF = methods;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public InnerNode getMethods() {
        return classDEF;
    }

    public Map<String, Object> getFields() {
        return fields;
    }
}

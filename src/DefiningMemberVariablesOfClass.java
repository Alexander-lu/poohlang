import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.HashMap;
import java.util.Map;

public class DefiningMemberVariablesOfClass {
    /**
     * new一个新的对象
     * @param leftID 新对象的名字
     * @param IdName 类名
     */
    public static void funcCalMode2(String leftID,String IdName) {
        InnerNode classDef =(InnerNode)JVM.classStore.get(IdName);
        Map<String, Object> h = new HashMap<>(classFields(classDef));
        JVM.globalScope.put(leftID,new ClassNode(classDef,h));
    }

    /**
     * 给新的对象定义成员变量
     * @param classDef 类的源文件
     * @return 定义完成员变量的Map
     */
    public static Map<String, Object> classFields(InnerNode classDef){
        InnerNode assign = (InnerNode)classDef.getChild(2).getChild(1).getChild(0);
        return classFieldsAssignStatement(assign);
    }
    /**
     * 给新的对象定义成员变量
     * @param assign 类的源文件
     * @return 定义完成员变量的Map
     */
    public static Map<String, Object> classFieldsAssignStatement(InnerNode assign){
        Map<String, Object> newMap = new HashMap<>();
        LeafNode ID = (LeafNode) assign.getChild(0).getChild(0);
        String leftID = ID.getTokenText();
        //拿到要赋值的变量名
        LeafNode number = (LeafNode) assign.getChild(1).getChild(0).getChild(0).getChild(0);
        //拿到Number
        newMap.put(leftID,Integer.parseInt(number.getTokenText()));
        //讲成员变量添加到类对象的Map里
        return newMap;
    }
}

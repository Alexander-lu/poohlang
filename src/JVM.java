import java.util.HashMap;
import java.util.Map;

public class JVM {
    public static Map<String, Object> classStore = new HashMap<>();
    //保存所有new出来的类对象
    public static Map<String, Object> globalScope = new HashMap<>();
}

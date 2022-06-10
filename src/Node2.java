public class Node2 {
    private String 类名;
    private Integer 值;
    public String status;
    public Node2() {
    }

    public Node2(String 类名, String status) {
        this.类名 = 类名;
        this.status = status;
    }

    public Node2(Integer 值, String status) {
        this.值 = 值;
        this.status = status;
    }

    public Node2(String 类名, Integer 值, String status) {
        this.类名 = 类名;
        this.值 = 值;
        this.status = status;
    }

    public String get类名() {
        return 类名;
    }

    public Integer get值() {
        return 值;
    }
    public void set类名(String 类名) {
        this.类名 = 类名;
    }

    public void set值(Integer 值) {
        this.值 = 值;
    }

}

public class Node2 {
    private String classDef;
    private Integer number;
    private String status;
    public Node2() {
    }

    public Node2(String classDef, Integer number) {
        this.classDef = classDef;
        this.number = number;
    }

    public String getClassDef() {
        return classDef;
    }

    public int getNumber() {
        return number;
    }

    public void setClassDef(String classDef) {
        this.classDef = classDef;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

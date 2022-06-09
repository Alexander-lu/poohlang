import lib.ast.InnerNode;

import java.util.Map;

public class IntegerOrClassNode {
    private String classDef;
    private int number;

    public IntegerOrClassNode() {
    }

    public IntegerOrClassNode(String classDef, int number) {
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
    public boolean ifa (){
        if (classDef!=null){
            if(number==-1|number==-2){
                return true;
            }
        }
        return false;
    }
}

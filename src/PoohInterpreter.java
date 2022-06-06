import lib.ast.ASTNode;
import lib.ast.InnerNode;
import lib.ast.LeafNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoohInterpreter {
  Map<String,Integer> mapStore = new HashMap<>();
  /**
   * 运行一个POOH程序
   *
   * @param program 语法解析器解析出的程序抽象语法树
   */
  public void run(ASTNode program) {
    InnerNode father = (InnerNode) program;
    if(father.getAstName().equals("<block>")){
      run(father.getChild(1));
    }
    for (ASTNode child : father.getChildren()) {
      try {
        InnerNode boy = (InnerNode) child;
      }catch (Exception e) {
        return;
      }
        InnerNode boy = (InnerNode) child;
      if(boy.getAstName().equals("<program>")){
        run(boy);
      }
      if(boy.getAstName().equals("<statement>")){
        InnerNode wwName = (InnerNode) boy.getChild(0);
        if (wwName.getAstName().equals("<assign-statement>")) {
          LeafNode ID = (LeafNode) wwName.getChild(0);
              String namename = ID.getTokenText();
//          if (ID.getTokenTag().equals("NUMBER")) {
//          }
          InnerNode expr = (InnerNode) wwName.getChild(2);
          InnerNode exprtermm = (InnerNode) expr.getChild(0);
          LeafNode exprterm = (LeafNode) exprtermm.getChild(0);
          int countcount = 0;
          if (exprterm.getTokenTag().equals("ID")) {
            countcount = mapStore.get(exprterm.getTokenText());
          }
          if (exprterm.getTokenTag().equals("NUMBER")) {
            countcount = Integer.parseInt(exprterm.getTokenText());
          }
          InnerNode exprmoreterm = (InnerNode) expr.getChild(1);
          while (true){
            try {
              LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
            }catch (Exception e){
              break;
            }
            LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);

            if(exprmoretermleafnode.getTokenTag().equals("epsilon")){
              break;
            }
            InnerNode exprmoretermsexper00 = (InnerNode) exprmoreterm.getChild(1).getChild(0);
            LeafNode exprmoretermsexper0 = (LeafNode) exprmoretermsexper00.getChild(0);
            if(exprmoretermsexper0.getTokenTag().equals("ID")){
              countcount += mapStore.get(exprmoretermsexper0.getTokenText());
            }
            if(exprmoretermsexper0.getTokenTag().equals("NUMBER")){
              countcount += Integer.parseInt(exprmoretermsexper0.getTokenText());
            }
            InnerNode exprmoretermmoreterm = (InnerNode)   exprmoreterm.getChild(1).getChild(1);
            exprmoreterm=exprmoretermmoreterm;
          }
          mapStore.remove(namename);
          mapStore.put(namename, countcount);
        }
        if (wwName.getAstName().equals("<if-statement>")) {
          int left = 0;
          InnerNode expr = (InnerNode) wwName.getChild(2);
          LeafNode numberorid = (LeafNode)expr.getChild(0).getChild(0);
          if (numberorid.getTokenTag().equals("NUMBER")) {
            left = Integer.parseInt(numberorid.getTokenText());
          }
          if (numberorid.getTokenTag().equals("ID")) {
            left = mapStore.get(numberorid.getTokenText());
          }
          InnerNode exprmoreterm = (InnerNode) expr.getChild(1);
          int ringhtg = 0;
          int ringhtcount = 0;
            try {
              LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
            }catch (Exception e){
              break;
            }
            LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
            if(exprmoretermleafnode.getTokenTag().equals("LESS_THAN")){
              ringhtg=1;
            }
            if(exprmoretermleafnode.getTokenTag().equals("EQUAL_TEST")){
              ringhtg=2;
            }
          LeafNode exprmoretermsexper00 = (LeafNode) exprmoreterm.getChild(1).getChild(0);
            if(exprmoretermsexper00.getTokenTag().equals("ID")){
              ringhtcount= mapStore.get(exprmoretermsexper00.getTokenText());
            }
            if(exprmoretermsexper00.getTokenTag().equals("NUMBER")){
              ringhtcount= Integer.parseInt(exprmoretermsexper00.getTokenText());
            }
            if(ringhtg == 1){
              if(left < ringhtcount){
                run(wwName.getChild(4));
              }else {
                run(wwName.getChild(6));
              }
            }
          if(ringhtg == 2){
            if(left == ringhtcount){
              run(wwName.getChild(4));
            }else {
              run(wwName.getChild(6));
            }
          }
        }
        if (wwName.getAstName().equals("<while-statement>")) {
          int countttttt = 0;
          int lefttgrement =0;
          int minddle = 0;
          int rightgrement =0;
          int left = 0;
          String leftstring = null;
          int rightnumber = 0;
          String rightstring = null;
          InnerNode expr = (InnerNode) wwName.getChild(2);
          LeafNode numberorid = (LeafNode)expr.getChild(0).getChild(0);
          if (numberorid.getTokenTag().equals("NUMBER")) {
            left = Integer.parseInt(numberorid.getTokenText());
            lefttgrement = 1;
          }
          if (numberorid.getTokenTag().equals("ID")) {
            leftstring = numberorid.getTokenText();
            lefttgrement = 2;
          }
          InnerNode exprmoreterm = (InnerNode) expr.getChild(1);
          try {
            LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
          }catch (Exception e){
            break;
          }
          LeafNode exprmoretermleafnode = (LeafNode) exprmoreterm.getChild(0);
          if(exprmoretermleafnode.getTokenTag().equals("LESS_THAN")){
            minddle=1;
          }
          if(exprmoretermleafnode.getTokenTag().equals("EQUAL_TEST")){
            minddle=2;
          }
          LeafNode exprmoretermsexper00 = (LeafNode) exprmoreterm.getChild(1).getChild(0);
          if(exprmoretermsexper00.getTokenTag().equals("ID")){
            rightgrement = 1;
            rightstring = exprmoretermsexper00.getTokenText();
          }
          if(exprmoretermsexper00.getTokenTag().equals("NUMBER")){
            rightgrement =2;
            rightnumber= Integer.parseInt(exprmoretermsexper00.getTokenText());
          }
          if(lefttgrement == 2){
            if (minddle == 1) {
              if (rightgrement == 2) {
                  while( mapStore.get(leftstring)<rightnumber){
                    run(wwName.getChild(4));
                  }
              }
              if (rightgrement == 1) {
                while(mapStore.get(leftstring)<mapStore.get(rightstring)){
                  run(wwName.getChild(4));
                }
              }
            }
            if (minddle == 2) {
              if (rightgrement == 2) {
                while( mapStore.get(leftstring)==rightnumber){
                  run(wwName.getChild(4));
                }
              }
              if (rightgrement == 1) {
                while(mapStore.get(leftstring).equals(mapStore.get(rightstring))){
                  run(wwName.getChild(4));
                }
              }
            }
          }
          if(lefttgrement == 1){
            if(minddle == 1 ){
              while( left<mapStore.get(rightstring)){
                run(wwName.getChild(4));
              }
            }
            if(minddle == 2 ){
              while( left==mapStore.get(rightstring)){
                run(wwName.getChild(4));
              }
            }
          }
        }
        if(wwName.getAstName().equals("<print-statement>")){
          InnerNode expr = (InnerNode) wwName.getChild(1);
          LeafNode term = (LeafNode)expr.getChild(0).getChild(0);
          if (term.getTokenTag().equals("NUMBER")) {
            System.out.println(Integer.parseInt(term.getTokenText()));
          }
          if (term.getTokenTag().equals("ID")) {
            System.out.println(mapStore.get(term.getTokenText()));
          }
        }
      }
    }

  }
}

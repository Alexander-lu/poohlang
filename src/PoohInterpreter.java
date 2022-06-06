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
          LeafNode wweeeeName = (LeafNode) wwName.getChild(0);
          LeafNode wweesssName = (LeafNode) wwName.getChild(2);
          mapStore.remove(wweeeeName.getTokenText());
          mapStore.put(wweeeeName.getTokenText(), Integer.valueOf(wweesssName.getTokenText()));
        }
        if (wwName.getAstName().equals("<if-statement>")) {
          LeafNode wweeeeName = (LeafNode) wwName.getChild(2);
          LeafNode wweesssName = (LeafNode) wwName.getChild(4);
          int aa = mapStore.get(wweeeeName.getTokenText());
          int ww = mapStore.get(wweesssName.getTokenText());
          if(mapStore.get(wweeeeName.getTokenText()) < mapStore.get(wweesssName.getTokenText())){
            run(wwName.getChild(6));
          }else {
            run(wwName.getChild(8));
          }
        }
        if(wwName.getAstName().equals("<print-statement>")){
          LeafNode girlerName = (LeafNode) wwName.getChild(1);
          System.out.println(mapStore.get(girlerName.getTokenText()));
        }
      }
    }

  }
}

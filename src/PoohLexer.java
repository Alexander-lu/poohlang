import lib.Token;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PoohLexer {
  /**
   * 从res目录下读取一个
   *
   * @param filename 要进行词法分析的文件名，如{@code LexingSample1.pooh}
   * @return 词法分析后得到的token序列
   */
  public List<Token> lex(String filename) {
    List<Token> tokenlist = new ArrayList<Token>();
    String a = "res/"+filename;
    try {
      Scanner sc = new Scanner(new File(a));
      while (sc.hasNext()) {
        if(sc.hasNextInt()){
          int scInt = sc.nextInt();
          tokenlist.add(new Token("NUMBER",String.valueOf(scInt)));
        }else {
          String scStr= sc.next();
          if(scStr.equals("(")){
            tokenlist.add(new Token("LEFT_PAR",scStr));
          }else if(scStr.equals(")")){
            tokenlist.add(new Token("RIGHT_PAR",scStr));
          }else if(scStr.equals("{")){
            tokenlist.add(new Token("LEFT_BRACE",scStr));
          }else if(scStr.equals("}")){
            tokenlist.add(new Token("RIGHT_BRACE",scStr));
          }else if(scStr.equals("=")){
            tokenlist.add(new Token("EQUAL",scStr));
          }else if(scStr.equals("+")){
            tokenlist.add(new Token("PLUS",scStr));
          }else if(scStr.equals("<")){
            tokenlist.add(new Token("LESS_THAN",scStr));
          }else if(scStr.equals("==")){
            tokenlist.add(new Token("EQUAL_TEST",scStr));
          }else if(scStr.equals("print")){
            tokenlist.add(new Token("KEYWORD_PRINT",scStr));
          }else if(scStr.equals("while")){
            tokenlist.add(new Token("KEYWORD_WHILE",scStr));
          }else if(scStr.equals("if")){
            tokenlist.add(new Token("KEYWORD_IF",scStr));
          }else if(scStr.equals("else")){
            tokenlist.add(new Token("KEYWORD_ELSE",scStr));
          }else if(scStr.equals("call")){
            tokenlist.add(new Token("KEYWORD_CALL",scStr));
          }else if(scStr.equals(",")){
            tokenlist.add(new Token("COMMA",scStr));
          }else if(scStr.equals("func")){
            tokenlist.add(new Token("KEYWORD_FUNC",scStr));
          }else if(scStr.equals("return")){
            tokenlist.add(new Token("KEYWORD_RETURN",scStr));
          }else if(scStr.equals("new")){
            tokenlist.add(new Token("KEYWORD_NEW",scStr));
          }else if(scStr.equals("class")){
            tokenlist.add(new Token("KEYWORD_CLASS",scStr));
          } else if(Pattern.matches("\\w*\\.\\w*", scStr)){
            tokenlist.add(new Token("OBJ_METHOD",scStr));
          }else {
            tokenlist.add(new Token("ID",scStr));
          }
        }
      }
      return tokenlist;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

  }
}

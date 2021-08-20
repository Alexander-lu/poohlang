package lib;

import java.util.Objects;

public class Token {
  /**
   * token的标签，应该在词法分析阶段确定
   */
  private final String tag;

  /**
   * token的具体内容，由lexer从源代码中解析出来
   */
  private final String text;

  public Token(String tag, String text) {
    this.tag = tag;
    this.text = text;
  }

  public String getTag() {
    return tag;
  }

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return "Token{" +
        "tag='" + tag + '\'' +
        ", text='" + text + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Token token = (Token) o;
    return tag.equals(token.tag) && text.equals(token.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tag, text);
  }
}

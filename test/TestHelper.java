import lib.Token;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public final class TestHelper {
  private TestHelper() {}


  /**
   * 从资源目录读取文本文件（兼容macOS、Windows、Linux），并统一转成Linux风格的换行
   *
   * @param filename 要读取的文件名
   * @return 以字符串保存的文件内容
   */
  @SuppressWarnings("ConstantConditions")
  static String readFromResourceFile(String filename) {
    try {
      var uri = new URI(TestHelper.class.getClassLoader().getResource(filename).toString());
      return Files.readString(new File(uri.getPath()).toPath()).replaceAll("\\r\\n?", "\n");
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException("无法读取从resource目录读取" + filename);
    }
  }

  /**
   * 快速构造一个token序列
   *
   * @param tokenTags 每个token的标签
   * @param texts     每个token的具体内容
   * @return 构建出的token序列
   */
  static List<Token> tokensOf(String[] tokenTags, String[] texts) {
    if (tokenTags.length != texts.length) {
      throw new RuntimeException("tag和text数量不相等");
    }
    List<Token> result = new ArrayList<>();
    for (int i = 0; i < tokenTags.length; i++) {
      result.add(new Token(tokenTags[i], texts[i]));
    }
    return result;
  }
}

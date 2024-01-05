package cc.ayakurayuki.shorten.util.crypto;

import com.google.common.io.BaseEncoding;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:50
 */
public abstract class MD5 {

  /**
   * 获取输入流的 MD5 散列信息
   */
  public static byte[] digest(InputStream inputStream) {
    return Digests.digest(inputStream, DigestAlgorithmEnum.MD5);
  }

  /**
   * 获取输入流的 MD5 散列文本结果
   */
  public static String digestToString(InputStream inputStream) {
    byte[] digest = Digests.digest(inputStream, DigestAlgorithmEnum.MD5);
    if (digest == null) {
      return null;
    }
    return BaseEncoding.base16().encode(digest);
  }

  /**
   * 获取文本内容的 MD5 散列信息
   */
  public static byte[] digest(String plainText) {
    return Digests.digest(plainText, DigestAlgorithmEnum.MD5);
  }

  /**
   * 获取文本内容的 MD5 散列文本结果
   */
  public static String digestToString(String plainText) {
    return Digests.digestToBase16String(plainText, DigestAlgorithmEnum.MD5);
  }

  /**
   * 获取文本内容的 MD5 散列文本结果，可以指定对文本内容使用的编码集
   */
  public static String digestToString(String plainText, Charset charset) {
    return Digests.digestToBase16String(plainText, charset, DigestAlgorithmEnum.MD5);
  }

}

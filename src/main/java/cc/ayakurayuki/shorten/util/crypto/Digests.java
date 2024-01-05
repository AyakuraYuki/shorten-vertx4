package cc.ayakurayuki.shorten.util.crypto;

import com.google.common.io.BaseEncoding;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.annotation.CheckForNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:53
 */
public abstract class Digests {

  private static final Logger log = LoggerFactory.getLogger(Digests.class);

  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  /**
   * 生成随机的 salt
   *
   * @param lengthOfBytes salt 的大小，必须是一个大于 0 的数
   */
  public static byte[] generateSalt(int lengthOfBytes) {
    if (lengthOfBytes <= 0) {
      throw new IllegalArgumentException(String.format("length of bytes must be a positive number and greater than zero, you present %s", lengthOfBytes));
    }
    byte[] bytes = new byte[lengthOfBytes];
    SECURE_RANDOM.nextBytes(bytes);
    return bytes;
  }

  /**
   * 对文本进行散列
   *
   * @param plainText 文本内容
   * @param algorithm 散列算法
   *
   * @return 散列结果的 Base16 字符串
   */
  @CheckForNull
  public static String digestToBase16String(String plainText, DigestAlgorithmEnum algorithm) {
    if (algorithm == null) {
      throw new NullPointerException("illegal algorithm parameter");
    }
    return digestToBase16String(plainText, StandardCharsets.UTF_8, algorithm.getAlgorithm());
  }

  /**
   * 对文本进行散列
   *
   * @param plainText 文本内容
   * @param algorithm 散列算法
   *
   * @return 散列结果的 Base16 字符串
   */
  @CheckForNull
  public static String digestToBase16String(String plainText, String algorithm) {
    return digestToBase16String(plainText, StandardCharsets.UTF_8, algorithm);
  }

  /**
   * 对文本进行散列
   *
   * @param plainText 文本内容
   * @param charset   编码集
   * @param algorithm 散列算法
   *
   * @return 散列结果的 Base16 字符串
   */
  @CheckForNull
  public static String digestToBase16String(String plainText, Charset charset, DigestAlgorithmEnum algorithm) {
    if (algorithm == null) {
      throw new NullPointerException("illegal algorithm parameter");
    }
    return digestToBase16String(plainText, charset, algorithm.getAlgorithm());
  }

  /**
   * 对文本进行散列
   *
   * @param plainText 文本内容
   * @param charset   编码集
   * @param algorithm 散列算法
   *
   * @return 散列结果的 Base16 字符串
   */
  @CheckForNull
  public static String digestToBase16String(String plainText, Charset charset, String algorithm) {
    byte[] res = digest(plainText, charset, algorithm);
    if (res == null) {
      return null;
    }
    return BaseEncoding.base16().encode(res);
  }

  /**
   * 对文本进行散列
   *
   * @param plainText 文本内容
   * @param algorithm 散列算法
   *
   * @return 散列结果的 bytes
   */
  @CheckForNull
  public static byte[] digest(String plainText, DigestAlgorithmEnum algorithm) {
    if (algorithm == null) {
      throw new NullPointerException("illegal algorithm parameter");
    }
    return digest(plainText, null, algorithm.getAlgorithm());
  }

  /**
   * 对文本进行散列
   *
   * @param plainText 文本内容
   * @param algorithm 散列算法
   *
   * @return 散列结果的 bytes
   */
  @CheckForNull
  public static byte[] digest(String plainText, String algorithm) {
    return digest(plainText, null, algorithm);
  }

  /**
   * 对文本进行散列
   *
   * @param plainText 文本内容
   * @param charset   编码集
   * @param algorithm 散列算法
   *
   * @return 散列结果的 bytes
   */
  @CheckForNull
  public static byte[] digest(String plainText, Charset charset, DigestAlgorithmEnum algorithm) {
    if (algorithm == null) {
      throw new NullPointerException("illegal algorithm parameter");
    }
    return digest(plainText, charset, algorithm.getAlgorithm());
  }

  /**
   * 对文本进行散列
   *
   * @param plainText 文本内容
   * @param charset   编码集
   * @param algorithm 散列算法
   *
   * @return 散列结果的 bytes
   */
  @CheckForNull
  public static byte[] digest(String plainText, Charset charset, String algorithm) {
    if (plainText == null) {
      return null;
    }
    if (charset == null) {
      charset = StandardCharsets.UTF_8;
    }
    try {
      MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

      byte[] bytes = plainText.getBytes(charset);

      return messageDigest.digest(bytes);

    } catch (NoSuchAlgorithmException e) {
      log.warn(String.format("no such algorithm of %s, returns null. err: %s", algorithm, e.getMessage()), e);
      return null;
    }
  }

  /**
   * 对 input 进行散列
   *
   * @param input      输入内容的 bytes
   * @param algorithm  散列算法
   * @param salt       （可选的）加盐
   * @param iterations （可选的）迭代散列次数
   *
   * @return 散列结果的 bytes
   */
  @CheckForNull
  public static byte[] digest(byte[] input, DigestAlgorithmEnum algorithm, byte[] salt, int iterations) {
    if (algorithm == null) {
      throw new NullPointerException("illegal algorithm parameter");
    }
    return digest(input, algorithm.getAlgorithm(), salt, iterations);
  }

  /**
   * 对 input 进行散列
   *
   * @param input      输入内容的 bytes
   * @param algorithm  散列算法
   * @param salt       （可选的）加盐
   * @param iterations （可选的）迭代散列次数
   *
   * @return 散列结果的 bytes
   */
  @CheckForNull
  public static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
    if (input == null) {
      return null;
    }
    try {
      MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

      if (salt != null && salt.length > 0) {
        messageDigest.update(salt);
      }

      byte[] result = messageDigest.digest(input);

      if (iterations > 0) {
        for (int i = 0; i < iterations; i++) {
          messageDigest.reset();
          result = messageDigest.digest(result);
        }
      }

      return result;

    } catch (NoSuchAlgorithmException e) {
      log.warn(String.format("no such algorithm of %s, returns null. err: %s", algorithm, e.getMessage()), e);
      return null;
    }
  }

  /**
   * 对输入流进行散列
   *
   * @param inputStream 输入流
   * @param algorithm   散列算法
   *
   * @return 散列结果的 bytes
   */
  @CheckForNull
  public static byte[] digest(InputStream inputStream, DigestAlgorithmEnum algorithm) {
    if (algorithm == null) {
      throw new NullPointerException("illegal algorithm parameter");
    }
    return digest(inputStream, algorithm.getAlgorithm());
  }

  /**
   * 对输入流进行散列
   *
   * @param inputStream 输入流
   * @param algorithm   散列算法
   *
   * @return 散列结果的 bytes
   */
  @CheckForNull
  public static byte[] digest(InputStream inputStream, String algorithm) {
    if (inputStream == null) {
      return null;
    }
    try {
      MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

      int bufferSize = 8 * 1024;
      byte[] buffer = new byte[bufferSize];

      int read = inputStream.read(buffer, 0, bufferSize);
      while (read > -1) {
        messageDigest.update(buffer, 0, read);
        read = inputStream.read(buffer, 0, bufferSize);
      }

      return messageDigest.digest();

    } catch (NoSuchAlgorithmException e) {
      log.warn(String.format("no such algorithm of %s, returns null. err: %s", algorithm, e.getMessage()), e);
      return null;
    } catch (IOException e) {
      log.warn(String.format("got i/o exception while reading InputStream, err: %s", e.getMessage()), e);
      return null;
    }
  }

}

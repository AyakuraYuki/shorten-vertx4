package cc.ayakurayuki.shorten.util;

import java.util.Random;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:46
 */
public abstract class RandomUtils {

  private static final String chars = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";

  public static String generateKey(int length) {
    StringBuilder template = new StringBuilder(chars);
    int range = template.length();
    Random random = new Random();
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      stringBuilder.append(template.charAt(random.nextInt(range)));
    }
    return stringBuilder.toString();
  }

}

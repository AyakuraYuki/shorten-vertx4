package cc.ayakurayuki.shorten.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-18:15
 */
@RunWith(JUnit4.class)
public class TestRandomUtils {

  @Test
  public void generateKey() {
    for (int i = 0; i < 5; i++) {
      System.out.println(RandomUtils.generateKey(16));
      System.out.println(RandomUtils.generateKey(12));
      System.out.println(RandomUtils.generateKey(8));
      System.out.println(RandomUtils.generateKey(6));
      System.out.println(RandomUtils.generateKey(4));
      System.out.println();
    }
  }

}

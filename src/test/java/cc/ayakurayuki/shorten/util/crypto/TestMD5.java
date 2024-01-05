package cc.ayakurayuki.shorten.util.crypto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:55
 */
@RunWith(JUnit4.class)
public class TestMD5 {

  @Test
  public void md5() {
    String raw = "Shanghai";
    String rawMD5 = "5466ee572bcbc75830d044e66ab429bc";
    String md5 = MD5.digestToString(raw).toLowerCase();
    Assert.assertEquals(rawMD5, md5);
  }

}

package cc.ayakurayuki.shorten.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-18:10
 */
@RunWith(JUnit4.class)
public class TestUrlUtils {

  @Test
  public void checkURL() {
    Assert.assertFalse(UrlUtils.checkURL("file:///Users/Shared/index.html"));
    Assert.assertFalse(UrlUtils.checkURL("unix:///system/call"));
    Assert.assertFalse(UrlUtils.checkURL("etcd://myService/shorten"));
    Assert.assertTrue(UrlUtils.checkURL("https://blog.ayakurayuki.cc"));
    Assert.assertTrue(UrlUtils.checkURL("https://docs.oracle.com/javase%2Ftutorial%2F/networking/urls/urlInfo.html"));
    Assert.assertTrue(UrlUtils.checkURL("https://us-east-1.console.aws.amazon.com/codesuite/codecommit/repositories?region=us-east-1#"));
  }

  @Test
  public void checkWhiteList() {
    Assert.assertTrue(UrlUtils.checkWhiteList("blog.ayakurayuki.cc"));
    Assert.assertTrue(UrlUtils.checkWhiteList("ayakurayuki.cc"));
    Assert.assertFalse(UrlUtils.checkWhiteList("google.com"));
  }

  @Test
  public void checkHash() {
    String url = "https://blog.ayakurayuki.cc";
    Assert.assertTrue(UrlUtils.checkHash(url, "2A1B43206B8466139A2FA646F9A020EA"));
  }

}

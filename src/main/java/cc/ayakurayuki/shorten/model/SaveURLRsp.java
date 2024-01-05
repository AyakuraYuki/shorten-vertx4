package cc.ayakurayuki.shorten.model;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:13
 */
public class SaveURLRsp {

  private String key;
  private Long   expireAt; // shorten expire time in unix second, value -1 means no expiry

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Long getExpireAt() {
    return expireAt;
  }

  public void setExpireAt(Long expireAt) {
    this.expireAt = expireAt;
  }

}

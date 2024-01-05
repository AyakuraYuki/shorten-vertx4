package cc.ayakurayuki.shorten.model;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:12
 */
public class SaveURLReq {

  private String url;
  private String key;
  private String hash;
  private Long   ttlSecond;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public Long getTtlSecond() {
    return ttlSecond;
  }

  public void setTtlSecond(Long ttlSecond) {
    this.ttlSecond = ttlSecond;
  }

}

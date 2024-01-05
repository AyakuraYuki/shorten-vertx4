package cc.ayakurayuki.shorten.model;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:09
 */
public class Link {

  private String  url; // original url link
  private Long    createAt; // shorten create time in unix second
  private Long    expireAt; // shorten expire time in unix second, value -1 means no expiry
  private Boolean fromAdmin; // true means url was saved by admin

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Long getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Long createAt) {
    this.createAt = createAt;
  }

  public Long getExpireAt() {
    return expireAt;
  }

  public void setExpireAt(Long expireAt) {
    this.expireAt = expireAt;
  }

  public Boolean getFromAdmin() {
    return fromAdmin;
  }

  public void setFromAdmin(Boolean fromAdmin) {
    this.fromAdmin = fromAdmin;
  }

}

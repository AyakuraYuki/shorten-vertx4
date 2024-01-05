package cc.ayakurayuki.shorten.util.crypto;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:53
 */
public enum DigestAlgorithmEnum {

  SHA_1("SHA-1"),
  SHA_256("SHA-256"),
  SHA_384("SHA-384"),
  SHA_512("SHA-512"),
  MD5("MD5"),
  ;

  public final String algorithm;

  public String getAlgorithm() {
    return algorithm;
  }

  DigestAlgorithmEnum(String algorithm) {
    this.algorithm = algorithm;
  }

}

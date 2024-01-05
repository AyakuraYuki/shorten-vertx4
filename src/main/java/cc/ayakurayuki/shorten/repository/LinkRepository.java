package cc.ayakurayuki.shorten.repository;


import cc.ayakurayuki.shorten.core.Application;
import cc.ayakurayuki.shorten.core.Consts;
import cc.ayakurayuki.shorten.model.Link;
import cc.ayakurayuki.shorten.model.SaveURLRsp;
import cc.ayakurayuki.shorten.util.RandomUtils;
import cc.ayakurayuki.shorten.util.UrlUtils;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.redis.client.RedisAPI;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:14
 */
public class LinkRepository {

  private final RedisAPI redis = Application.redis();

  public Future<Link> load(String key) {
    return redis.get(key)
      .compose(response -> {
        if (response != null) {
          return Future.succeededFuture(response.toString());
        } else {
          return Future.succeededFuture(null);
        }
      })
      .compose(value -> {
        if (value == null || value.isEmpty()) {
          return Future.succeededFuture(null);
        }
        return Future.succeededFuture(Json.decodeValue(value, Link.class));
      });
  }

  public Future<SaveURLRsp> save(String key, String link, Duration ttl, int keyLength, boolean fromAdmin) {
    String host;
    try {
      URL url = new URL(link);
      host = url.getHost();
    } catch (MalformedURLException e) {
      return Future.failedFuture(e);
    }

    if (keyLength == 0) {
      keyLength = Consts.DEFAULT_KEY_LENGTH;
    }

    if (!fromAdmin) {
      key = RandomUtils.generateKey(keyLength);
    }

    String finalKey = key;
    int finalKeyLength = keyLength;
    boolean isWhite = UrlUtils.checkWhiteList(host);
    if (!fromAdmin && !isWhite) {
      ttl = Duration.ofDays(30);
    }
    Duration finalTTL = ttl;

    return load(key)
      .compose(existLink -> {
        if (existLink != null) {
          return save(finalKey, link, finalTTL, finalKeyLength + 1, fromAdmin);
        }

        Link metaLink = new Link();
        metaLink.setUrl(link);
        metaLink.setCreateAt(System.currentTimeMillis() / 1000);
        if (finalTTL != null) {
          metaLink.setExpireAt(System.currentTimeMillis() / 1000 + finalTTL.toSeconds());
        } else {
          metaLink.setExpireAt(-1L);
        }
        metaLink.setFromAdmin(fromAdmin);

        String json = Json.encodePrettily(metaLink);

        SaveURLRsp rsp = new SaveURLRsp();
        rsp.setKey(finalKey);

        if (!fromAdmin && !isWhite) {
          long seconds = finalTTL != null ? finalTTL.toSeconds() : Duration.ofDays(30).toSeconds();
          redis.setex(finalKey, String.valueOf(seconds), json);
          rsp.setExpireAt(System.currentTimeMillis() / 1000 + seconds);
        } else {
          redis.set(List.of(finalKey, json));
          rsp.setExpireAt(-1L);
        }

        return Future.succeededFuture(rsp);
      });
  }

}

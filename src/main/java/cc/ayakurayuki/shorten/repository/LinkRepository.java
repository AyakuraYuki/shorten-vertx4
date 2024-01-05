package cc.ayakurayuki.shorten.repository;


import cc.ayakurayuki.shorten.core.Application;
import cc.ayakurayuki.shorten.model.Link;
import cc.ayakurayuki.shorten.model.SaveURLRsp;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.redis.client.RedisAPI;
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

  public Future<SaveURLRsp> save(String key, String link, Duration ttl, boolean fromAdmin, boolean isWhite) {
    return load(key)
      .compose(existLink -> {
        if (existLink != null) {
          return save(key, link, ttl, fromAdmin, isWhite);
        }

        long nowSeconds = System.currentTimeMillis() / 1000;

        Link metaLink = new Link();
        metaLink.setUrl(link);
        metaLink.setCreateAt(nowSeconds);
        metaLink.setExpireAt(ttl != null ? nowSeconds + ttl.toSeconds() : -1L);
        metaLink.setFromAdmin(fromAdmin);

        String json = Json.encodePrettily(metaLink);

        SaveURLRsp rsp = new SaveURLRsp();
        rsp.setKey(key);

        if (!fromAdmin && !isWhite) {

          long seconds = ttl != null ? ttl.toSeconds() : Duration.ofDays(30).toSeconds();
          redis.setex(key, String.valueOf(seconds), json);
          rsp.setExpireAt(System.currentTimeMillis() / 1000 + seconds);

        } else {

          redis.set(List.of(key, json));
          rsp.setExpireAt(-1L);

        }

        return Future.succeededFuture(rsp);
      });
  }

}

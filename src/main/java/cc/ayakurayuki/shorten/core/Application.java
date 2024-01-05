package cc.ayakurayuki.shorten.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:05
 */
public final class Application {

  private Application() {}

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  private static Vertx    vertx;
  private static Context  vertxContext;
  private static Injector injector;
  private static RedisAPI redis;

  public static Vertx vertx() {
    return vertx;
  }

  public static Context vertxContext() {
    return vertxContext;
  }

  public static Injector injector() {
    return injector;
  }

  public static RedisAPI redis() {
    return redis;
  }

  public static void init(Context context) {
    vertxContext = context;
    vertx = context.owner();

    RedisOptions redisOptions = new RedisOptions()
      .setConnectionString("redis://127.0.0.1:6379/0")
      .setMaxPoolSize(128)
      .setMaxPoolWaiting(512)
      .setMaxWaitingHandlers(512);
    Redis redisClient = Redis.createClient(vertx, redisOptions);
    redis = RedisAPI.api(redisClient);
    redis.ping(Collections.emptyList()).onComplete(rsp -> {
      if (rsp.succeeded()) {
        log.info("redis client started");
      } else {
        log.error(String.format("an error occurred when starting redis client: %s", rsp.cause().getMessage()), rsp.cause());
        System.exit(1);
      }
    });

    injector = Guice.createInjector(new Modules());
  }

  public static void close() {
    redis.close();
  }

}

package cc.ayakurayuki.shorten.handler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:24
 */
public abstract class RouterMiddleware {

  public static Handler<RoutingContext> cors() {
    return CorsHandler.create()
      .addRelativeOrigin(".*")
      .allowedMethod(HttpMethod.GET)
      .allowedMethod(HttpMethod.POST)
      .allowedMethod(HttpMethod.OPTIONS)
      .allowCredentials(true)
      .allowedHeader("Access-Control-Allow-Origin")
      .allowedHeader("Accept")
      .allowedHeader("Authorization")
      .allowedHeader("Content-Length")
      .allowedHeader("Content-Type")
      .allowedHeader("Origin")
      .allowedHeader("X-Requested-With")
      .allowedHeader("X-Forwarded-For")
      .allowedHeader("X-Real-Ip");
  }

}

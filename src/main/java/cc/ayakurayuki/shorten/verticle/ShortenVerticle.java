package cc.ayakurayuki.shorten.verticle;

import cc.ayakurayuki.shorten.core.Application;
import cc.ayakurayuki.shorten.handler.ApiHandler;
import cc.ayakurayuki.shorten.handler.NotFoundWriter;
import cc.ayakurayuki.shorten.handler.RouterMiddleware;
import cc.ayakurayuki.shorten.handler.ViewHandler;
import com.zandero.rest.RestRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShortenVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(ShortenVerticle.class);

  private static final int httpPort = 20055;

  private HttpServer server;
  private Router     router;

  @Override
  public void start(Promise<Void> startPromise) {
    Application.init(context);
    registerRouter();
    startServer(startPromise);
  }

  private void registerRouter() {
    this.router = Router.router(vertx);
    this.router.route().handler(RouterMiddleware.cors());
    this.router.route().handler(BodyHandler.create());
    this.router.route().handler(LoggerHandler.create());

    ViewHandler viewHandler = Application.injector().getInstance(ViewHandler.class);
    this.router.options().handler(viewHandler::options);
    this.router.get("/:key").handler(viewHandler::visit);

    ApiHandler apiHandler = Application.injector().getInstance(ApiHandler.class);
    RestRouter.register(this.router, apiHandler);

    RestRouter.notFound(this.router, NotFoundWriter.class);
  }

  private void startServer(Promise<Void> startPromise) {
    this.server = vertx.createHttpServer();
    this.server.requestHandler(this.router);
    this.server.listen(httpPort)
      .onSuccess(svr -> {
        log.info("http server started on port {}", httpPort);
        startPromise.complete();
      })
      .onFailure(e -> {
        log.error(String.format("an error occurred when starting http server on port %d: %s", httpPort, e.getMessage()), e);
        startPromise.fail(e);
      });
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    Application.close();
    this.server.close()
      .onSuccess(v -> log.info("http server is closed"))
      .onFailure(e -> log.error(String.format("an error occurred when closing http server: %s", e.getMessage()), e));
  }

}

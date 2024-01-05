package cc.ayakurayuki.shorten;

import cc.ayakurayuki.shorten.verticle.ShortenVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-18:20
 */
public class Main extends Launcher {

  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.Log4jLogDelegateFactory");

    VertxOptions vertxOptions = new VertxOptions();
    vertxOptions.setEventLoopPoolSize(32);
    Vertx vertx = Vertx.vertx(vertxOptions);

    DeploymentOptions deploymentOptions = new DeploymentOptions();
    deploymentOptions.setWorkerPoolSize(128);
    deploymentOptions.setMaxWorkerExecuteTime(30);
    deploymentOptions.setMaxWorkerExecuteTimeUnit(TimeUnit.SECONDS);
    deploymentOptions.setInstances(1);

    vertx.deployVerticle(ShortenVerticle.class.getName(), deploymentOptions, res -> {
      if (res.succeeded()) {
        log.info("launched!");
      } else {
        log.error(String.format("an error occurred when launch application: %s", res.cause().getMessage()), res.cause());
      }
    });
  }

}

package cc.ayakurayuki.shorten.handler;

import cc.ayakurayuki.shorten.core.Application;
import cc.ayakurayuki.shorten.repository.LinkRepository;
import io.vertx.ext.web.RoutingContext;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:30
 */
public class ViewHandler {

  private final LinkRepository repo = Application.injector().getInstance(LinkRepository.class);

  public void options(RoutingContext ctx) {
    ctx.response().setStatusCode(204).end();
  }

  public void visit(RoutingContext ctx) {
    String key = ctx.pathParam("key");
    if (key == null || key.isBlank()) {
      ctx.response().setStatusCode(400).end("Bad Request");
      return;
    }

    repo.load(key)
      .onSuccess(link -> {
        String url = link.getUrl();
        if (url == null || url.isBlank()) {
          ctx.response().setStatusCode(404).end("Not Found");
        }
        ctx.redirect(url);
      })
      .onFailure(e -> ctx.response().setStatusCode(404).end("Not Found"));
  }

}

package cc.ayakurayuki.shorten.handler;

import cc.ayakurayuki.shorten.core.Application;
import cc.ayakurayuki.shorten.core.Consts;
import cc.ayakurayuki.shorten.model.SaveURLReq;
import cc.ayakurayuki.shorten.model.SaveURLRsp;
import cc.ayakurayuki.shorten.repository.LinkRepository;
import cc.ayakurayuki.shorten.util.UrlUtils;
import io.vertx.core.Future;
import java.time.Duration;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:30
 */
public class ApiHandler {

  private final LinkRepository repo = Application.injector().getInstance(LinkRepository.class);

  @POST
  @Path("/save-url")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Future<SaveURLRsp> saveURL(SaveURLReq req) {
    if (req.getUrl() == null || req.getUrl().isBlank()) {
      throw new RuntimeException("Bad Request");
    }

    boolean fromAdmin = UrlUtils.checkHash(req.getUrl(), req.getHash());

    Duration ttl = null;
    if (req.getTtlSecond() != null && req.getTtlSecond() > 0) {
      ttl = Duration.ofSeconds(req.getTtlSecond());
    }

    return repo.save(req.getKey(), req.getUrl(), ttl, Consts.DEFAULT_KEY_LENGTH, fromAdmin);
  }

}

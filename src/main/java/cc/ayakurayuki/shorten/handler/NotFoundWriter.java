package cc.ayakurayuki.shorten.handler;

import com.zandero.rest.writer.NotFoundResponseWriter;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:28
 */
public class NotFoundWriter extends NotFoundResponseWriter {

  @Override
  public void write(HttpServerRequest request, HttpServerResponse response) {
    response.setStatusCode(404);
    response.putHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML);
    response.end("404 Not Found");
  }

}

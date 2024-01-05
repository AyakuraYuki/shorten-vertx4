package cc.ayakurayuki.shorten.core;

import cc.ayakurayuki.shorten.handler.ApiHandler;
import cc.ayakurayuki.shorten.handler.ViewHandler;
import cc.ayakurayuki.shorten.repository.LinkRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * @author Ayakura Yuki
 * @date 2024/01/04-17:36
 */
public class Modules extends AbstractModule {

  @Override
  protected void configure() {
    super.configure();

    // repository
    bind(LinkRepository.class).in(Scopes.SINGLETON);

    // handler
    bind(ApiHandler.class).in(Scopes.SINGLETON);
    bind(ViewHandler.class).in(Scopes.SINGLETON);
  }

}

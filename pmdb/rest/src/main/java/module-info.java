module pmdb.rest {
  requires jakarta.ws.rs;

  requires jersey.common;
  requires jersey.server;
  requires jersey.media.json.jackson;

  requires java.net.http;
  requires org.glassfish.hk2.api;
  requires org.slf4j;

  requires transitive pmdb.core;

  exports pmdb.restapi;

  opens pmdb.restapi to jersey.server;
}

module pmdb.core {
  requires transitive com.fasterxml.jackson.core;
  requires transitive com.fasterxml.jackson.databind;

  opens core to com.fasterxml.jackson.databind;

  exports core;
  exports json.moviepersistance;
}

#!/bin/sh
    /bin/sh -ec 'cd ui && mvn javafx:run &'
    /bin/sh -ec 'mvn -pl integrationtests jetty:run -D "jetty.port=8999"'
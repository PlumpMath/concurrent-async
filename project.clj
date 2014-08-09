(defproject concurrent-async "0.1.0-SNAPSHOT"
  :description "An example of how to handle concurrent requests in Vert.x using core.async"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [io.vertx/clojure-api "1.0.2"]
                 [org.clojure/core.async "0.1.319.0-6b1aca-alpha"]]
  :vertx {:main concurrent-async.deploy/init})


(ns concurrent-async.deploy
  (:require [vertx.core :refer [deploy-verticle]]))

(defn init []
  (deploy-verticle "src/concurrent_async/logger.clj")
  (deploy-verticle "src/concurrent_async/verticleII.clj")
  (deploy-verticle "src/concurrent_async/serviceA.clj")
  (deploy-verticle "src/concurrent_async/serviceB.clj")
  (deploy-verticle "src/concurrent_async/serviceC.clj")
  (deploy-verticle "src/concurrent_async/verticleI.clj"))

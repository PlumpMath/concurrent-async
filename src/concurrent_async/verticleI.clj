(ns concurrent-async.asker
  (:require [vertx.eventbus :as eb]
            [clojure.string :refer [join]]
            [vertx.core :as vertx]))

(def address "verticleII.address")
(def log-address "logger")

(defn thread-name []
  (.getName (Thread/currentThread)))

(eb/send log-address (join " " ["Initializing verticleII on thread" (thread-name)]))
(vertx/periodic
  5000
  (eb/send address {:request "Query services"}
           (fn [reply]
             (eb/send log-address (join " " ["VerticleI received reply:" (vec reply) "on thread" (thread-name)])))))

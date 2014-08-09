(ns concurrent-async.serviceA
  (:require [vertx.eventbus :as eb]
            [clojure.string :refer [join]]))

(defn thread-name []
  (.getName (Thread/currentThread)))

(def log-address "logger")

(eb/on-message
  "serviceA"
  (fn [msg]
    (eb/send log-address (join " " ["ServiceA received message:" msg "on thread" (thread-name)]))
    (eb/reply "serviceA-reply")))

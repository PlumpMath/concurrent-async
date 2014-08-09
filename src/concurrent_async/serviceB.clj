(ns concurrent-async.serviceB
  (:require [vertx.eventbus :as eb]
            [clojure.string :refer [join]]))

(defn thread-name []
  (.getName (Thread/currentThread)))

(def log-address "logger")

(eb/on-message
  "serviceB"
  (fn [msg]
    (eb/send log-address (join " " ["ServiceB received message:" msg "on thread" (thread-name)]))
    (eb/reply "serviceB-reply")))

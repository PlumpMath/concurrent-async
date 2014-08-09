(ns concurrent-async.serviceC
  (:require [vertx.eventbus :as eb]
            [clojure.string :refer [join]]))

(defn thread-name []
  (.getName (Thread/currentThread)))

(def log-address "logger")

(eb/on-message
  "serviceC"
  (fn [msg]
    (eb/send log-address (join " " ["ServiceC received message:" msg "on thread" (thread-name)]))
    (eb/reply "serviceC-reply")))

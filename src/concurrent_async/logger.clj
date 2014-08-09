(ns concurrent-async.logger
  (:require [vertx.eventbus :as eb]))

(def address "logger")

(eb/on-message
  address
  (fn [msg]
    (println msg)))


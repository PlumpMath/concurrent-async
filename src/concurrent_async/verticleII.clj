(ns concurrent-async.verticleII
  (:require [vertx.eventbus :as eb]
            [clojure.string :refer [join]]
            [clojure.core.async :refer [>! <! go put! take! chan close!]]))

(def address "verticleII.address")
(def log-address "logger")

(defn thread-name []
  (.getName (Thread/currentThread)))

(defn- pr-msg [msg]
  (eb/send log-address (join " " ["Sending" msg "on thread:" (thread-name)])))

(eb/on-message
  address
  (fn [msg]
    (eb/send log-address (join " " ["VerticleII received request" msg "on thread:" (thread-name)]))
    (let [c (chan 3)]
      (eb/send "serviceA" "msg-A" (fn [res] (pr-msg "msgA") (put! c res)))
      (eb/send "serviceB" "msg-B" (fn [res] (pr-msg "msgB") (put! c res)))
      (eb/send "serviceC" "msg-C" (fn [res] (pr-msg "msgC") (put! c res)))
      (go (let [answers [(<! c) (<! c) (<! c)]]
            (eb/send log-address (join " " ["VerticleII received" answers "on thread" (thread-name)]))
            (eb/reply answers))))))

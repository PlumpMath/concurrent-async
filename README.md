# Handling concurrent requests in [Vert.x](http://vertx.io) using [core.async](https://github.com/clojure/core.async)

## Requirements
- [Vert.x with mod-lang-clojure](https://github.com/vert-x/mod-lang-clojure). (I recommend using [GVM](http://gvmtool.net/))
- [lein-vertx](https://github.com/isaiah/lein-vertx)

## How to run
Run `lein vertx run` from the project root


## Scenario
Verticle-1 sends a request to Verticle-2. Verticle-2, in order to give the correct response needs to combine data from serviceA, serviceB and serviceC.
Verticle-2 could send a request to serviceA and in the callback send a request go serviceB and in that callback send a request to serviceC and
in that callback reply to Verticle-1 and then jump into the lava because you're already in callback hell.

Instead, since we can call serviceA, serviceB and serviceC independently, we just do it concurrently, which is also more performant.

```clojure
(eb/send "serviceA" msgA (fn [res] (do-something)))
(eb/send "serviceB" msgB (fn [res] (do-something-else)))
(eb/send "serviceC" msgC (fn [res] (do-something-else-again)))
```

Now we need to reply to Verticle-1 when the _last_ service-reply arrives. But when is that? We could make a counter, increment it in the 3 reply-handlers and only do something when it reaches 3.
That works, but tends to lead to hard-to-understand code and the explicit mutable state enforced by the single-threadedness is icky.

Instead, we use core.async. Problem solved.

```clojure
(let [c (chan 3)]
      (eb/send "serviceA" msgA (fn [res] (put! c res)))
      (eb/send "serviceB" msgB (fn [res] (put! c res)))
      (eb/send "serviceC" msgC (fn [res] (put! c res)))
      (go (let [answers [(<! c) (<! c) (<! c)]]
           (eb/reply answers))))
```

A request handler in a verticle is _always_ handled by the same Vert.x eventloop thread. But we can send messages (using eb/send and eb/reply) using other threads, like those in core.async and all still goes well as this project demonstrates.

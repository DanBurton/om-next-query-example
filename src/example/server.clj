(ns example.server
  (:require [om.next.server :as om]
            [cognitect.transit :as transit])
  (:import [java.io ByteArrayOutputStream]))


(defn remote-parser-read [env k _params]
  (case k
    :server-time
    {:value (str "Party time! " (java.util.Date.))}

    nil))

(def remote-parser
  (om/parser {:read remote-parser-read}))

(defn transit-write [clj-obj]
  (let [out-stream (ByteArrayOutputStream.)]
    (transit/write (transit/writer out-stream :json) clj-obj)
    (.toString out-stream)))

(defn handle-om-query [req]
  (let [query (transit/read (transit/reader (:body req) :json))
        result (remote-parser nil query)]
    {:status 200
     :body (transit-write result)}));



(def html-response-body
"
<html>
  <body>
    <div id=\"app\">Hello, html</div>
    <script src=\"/js/main.js\"></script>
    <script>example.client.init()</script>
  </body>
</html>
")

(defn ring-handler [req]
  (if (= "/om-query" (:uri req))
    (handle-om-query req)
    {:status 200
     :body html-response-body}))

(ns example.server)

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

(defn ring-handler [_req]
  {:status 200
   :body html-response-body})

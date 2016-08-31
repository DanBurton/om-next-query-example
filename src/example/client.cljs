(ns example.client)

(def foo "foo")

(defn init []
  (js/document.write "Hello, Figwheel!"))

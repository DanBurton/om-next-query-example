(ns example.client
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [sablono.core :as sablono]))

(def foo "foo")

;; https://github.com/omcljs/om/wiki/Quick-Start-(om.next)
(om/defui HelloWorld
  Object
  (render [this]
    (sablono/html
      [:div "Hello, defui"])))

(def hello (om/factory HelloWorld))

(defn init []
  (js/ReactDOM.render (hello) (gdom/getElement "app")))

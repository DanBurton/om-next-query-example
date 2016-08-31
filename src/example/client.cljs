(ns example.client
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [sablono.core :as sablono]))

(def foo "foo")

(def names-list
  ["Rebecca" "Jesse" "Molly"])

(defn parser-read [env k params]
  (when (= k :names)
    (let [limit (:limit params)]
      {:value (take limit names-list)})))

(def parser
  (om/parser
    {:read parser-read}))

;; https://github.com/omcljs/om/wiki/Quick-Start-(om.next)
(om/defui HelloWorld
  static om/IQueryParams
  (params [_]
    {:limit 2})

  static om/IQuery
  (query [_]
    '[(:names {:limit ?limit})])

  Object
  (render [this]
    (let [names (:names (om/props this))]
    (sablono/html
      [:div (str "Hello, "
                (first names)
                (when (second names)
                  (str " and " (second names))))]))))

(def hello (om/factory HelloWorld))

(defn init []
  (js/ReactDOM.render (hello {:names ["Ag" "Dan"]}) (gdom/getElement "app")))

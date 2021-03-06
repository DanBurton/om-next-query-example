(ns example.client
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [sablono.core :as sablono]
            [cognitect.transit :as transit]))


(defn cb-handler [cb e]
  (let [response (transit/read (transit/reader :json)
                               (.. e -currentTarget -responseText))]
    (cb response)))

(defn send [{query :remote} cb]
  (let [request-body (transit/write (transit/writer :json) query)]
    (doto (js/XMLHttpRequest.)
      (.open "POST" "/om-query")
      (.addEventListener "load" #(cb-handler cb %))
      (.send request-body))))


(def foo "foo")

(def names-list
  '("Rebecca" "Jesse" "Molly"))

(defn parser-mutate [env k params]
  (when (= k 'prepend-name)
    (let [state (:state env)]
      {:action #(swap! state update :names conj (:name params))})))

(defn parser-read [env k params]
  (case k
    :names
    (let [limit (:limit params)
          state (:state env)]
      {:value (take limit (:names @state))})

    :server-time
    (let [{:keys [state ast]} env]
      {:value (get @state k)
       :remote ast})

    nil))

(def parser
  (om/parser
    {:read parser-read
     :mutate parser-mutate}))

(def reconciler
  (om/reconciler
    {:state {:names names-list}
     :parser parser
     :send send}))

;; https://github.com/omcljs/om/wiki/Quick-Start-(om.next)
(om/defui HelloWorld
  static om/IQueryParams
  (params [_]
    {:limit 2})

  static om/IQuery
  (query [_]
    '[(:names {:limit ?limit})
      :server-time])

  Object
  (render [this]
    (let [{:keys [names server-time]} (om/props this)]
      (sablono/html
        [:div
         [:div (str "Hello, "
                    (first names)
                    (when (second names)
                      (str " and " (second names))))]
         [:div (str "Server time: " server-time)]]))))

(def hello (om/factory HelloWorld))

(defn init []
  (om/add-root! reconciler HelloWorld (gdom/getElement "app")))

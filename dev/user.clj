(ns user
  (:require [figwheel-sidecar.repl-api :as ra]))

(def figwheel-config
  {:figwheel-options {:server-port 3333
                      :css-dirs ["resources/public/css"]
                      :ring-handler 'example.server/ring-handler}
   :build-ids ["dev"]
   :all-builds
   [{:id "dev"
     :figwheel {:on-jsload "example.client/init"}
     :source-paths ["src"]
     :compiler {:main "example.client"
                :asset-path "/js/out"
                :output-to "resources/public/js/main.js"
                :output-dir "resources/public/js/out"
                :parallel-build true
                :source-map-timestamp true
                :verbose true}}]})

(defn start []
  (ra/start-figwheel! figwheel-config))

(defn stop []
  (ra/stop-figwheel!))

(defn repl [& profile]
  (ra/cljs-repl (or profile "dev")))

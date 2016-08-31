(defproject example "0.1.0-SNAPSHOT"
  :description "An example om.next app"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.225"]
                 [org.omcljs/om "1.0.0-alpha41"]
                 [com.cognitect/transit-clj "0.8.288"]
                 [com.cognitect/transit-cljs "0.8.239"]
                 [sablono "0.7.4"]]
  :profiles {:dev {:source-paths ["dev" "src"]
                   :dependencies [[com.cemerick/piggieback "0.2.1"]
                                  [figwheel-sidecar "0.5.4-7"]]
                   :repl-options {:init-ns user
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})

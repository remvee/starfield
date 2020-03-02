(defproject starfield "0.1.0-SNAPSHOT"
  :description "Yay!  A starfield!"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.597"]
                 [reagent "0.9.1"]]

  :profiles {:dev {:plugins [[lein-cljsbuild "1.1.4"]
                             [lein-figwheel "0.5.6"]]
                   :dependencies [[figwheel-sidecar "0.5.19"]
                                  [com.cemerick/piggieback "0.2.2"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]
                                  :init (do (use 'figwheel-sidecar.repl-api) (start-figwheel!))}}}

  :figwheel {:css-dirs ["resources/public/css"]}

  :cljsbuild {:builds {:dev {:source-paths ["src"]
                             :figwheel true
                             :compiler {:output-dir "resources/public/js-dev"
                                        :output-to "resources/public/js-dev/starfield.js"
                                        :optimizations :none}}
                       :prod {:source-paths ["src"]
                              :compiler {:output-dir "resources/public/js"
                                         :output-to "resources/public/js/starfield.js"
                                         :optimizations :advanced}}}})

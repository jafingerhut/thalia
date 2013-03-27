(defproject clojure.test.generative "0.4.0"
  ;; This file is identical to project.clj from the core.memoize
  ;; github repo, except I have added the following line needed to run
  ;; lein-clojuredocs.
  :eval-in :leiningen
  :description "test.generative 0.4.0"
  :source-paths [ "src/main/clojure" ]
  :dependencies [[org.clojure/tools.namespace "0.1.1"]
                 [org.clojure/data.generators "0.1.2"]
                 [ch.qos.logback/logback-classic "1.0.6"]]
  )

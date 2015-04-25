(defproject thalia "0.2.0-SNAPSHOT"
  :description "Documentation for Clojure functions, macros, protocols, vars"
  :url "http://github.com/jafingerhut/thalia"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :profiles {:dev {:dependencies [[cheshire "5.0.2"]
                                  [me.raynes/fs "1.4.3"]
                                  [org.clojure/core.memoize "0.5.7"]]
                   ;; The following line interferes with creating
                   ;; small JAR files via 'lein install/jar/deploy',
                   ;; so only include it in the dev profile.
                   :main thalia.core}
             :1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}
             :1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}
             :1.7 {:dependencies [[org.clojure/clojure "1.7.0-beta2"]]}}
  :aliases {"test-all" ["with-profile" "dev,1.5:dev,1.6:dev,1.7" "test"]}
  :repl-options {:init-ns user}
  :resource-paths ["resource"])

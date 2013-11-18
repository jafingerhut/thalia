(defproject thalia "0.1.0"
  :description "Documentation for Clojure functions, macros, protocols, vars"
  :url "http://github.com/jafingerhut/thalia"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles {:dev {:dependencies [[cheshire "5.0.2"]
                                  [me.raynes/fs "1.4.3"]]}}
  :resource-paths ["resource"]
  ;; Uncomment the following line to enable 'lein run' commands
  ;; defined in namespace thalia.core
  :main thalia.core
  )

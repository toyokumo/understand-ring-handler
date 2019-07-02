(defproject understand-ring-handler "0.1.0-SNAPSHOT"
  :description "Sample codes to understand what is ring handler"
  :url "https://toyokumo.co.jp"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [bidi "2.1.6"]
                 [metosin/muuntaja "0.6.4"]]
  :main ^:skip-aot understand-ring-handler.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

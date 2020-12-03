(defproject advent-of-code "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/math.combinatorics "0.1.6"]
                 [clj-http "3.11.0"]
                 [cheshire "5.10.0"]
                 [criterium "0.4.4"]]
  :resource-paths ["resources"]
  :test-selectors {:default (complement :slow)
                   :year (fn [{:keys [ns]} & args]
                           (some #(clojure.string/includes? ns (str %)) args))})

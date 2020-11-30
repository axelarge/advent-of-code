(ns advent-of-code.2015.day12
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 12))

(defn solve1 [input]
  (reduce + (find-ints input)))

(defn solve2 [input]
  (->> (str/replace input #":" "")
       (read-string)
       (tree-seq coll?
                 #(when-not (and (map? %)
                                 (some #{"red"} (vals %)))
                    (seq %)))
       (filter number?)
       (reduce +)))

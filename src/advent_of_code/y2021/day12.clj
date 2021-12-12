(ns advent-of-code.y2021.day12
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2021 12))

(defn parse [input]
  (->> (str/split-lines input)
       (map #(str/split % #"-"))
       (reduce (fn [edges [a b]]
                 (-> edges
                     (update a conj b)
                     (update b conj a)))
               {})))

(defn path-count [edges at visited can-visit-twice?]
  (->> (get edges at)
       (remove #{"start"})
       (keep (fn [to]
               (cond (= to "end") 1
                     (or (is-upper? to) (not (visited to)))
                     (path-count edges to (conj visited to) can-visit-twice?)
                     can-visit-twice?
                     (path-count edges to visited false))))
       (reduce +)))

(defn solve [can-visit-twice? input]
  (time (path-count (parse input) "start" #{} can-visit-twice?)))

(def solve1 (partial solve false))
(def solve2 (partial solve true))

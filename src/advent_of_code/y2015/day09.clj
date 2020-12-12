(ns advent-of-code.y2015.day09
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 9))

(defn parse-line [line]
  (let [[from to dist] (str/split line #" to | = ")]
    [(sort [from to]) (parse-int dist)]))

(defn parse [input]
  (let [distances (map parse-line (str/split-lines input))]
    {:distances (into {} distances)
     :cities (into #{} (mapcat first) distances)}))

(defn best-route
  ([rf {:keys [distances cities]}]
   (->> cities
        (map #(best-route rf distances (disj cities %) (list %) 0))
        (reduce rf)))
  ([rf distances cities path cost]
   (let [from (peek path)]
     (if (empty? cities)
       cost
       (->> cities
            (map (fn [to]
                   (best-route rf
                               distances
                               (disj cities to)
                               (conj path to)
                               (+ cost (get distances (sort [from to]))))))
            (reduce rf))))))

(defn solve1 [input]
  (best-route min (parse input)))

(defn solve2 [input]
  (best-route max (parse input)))

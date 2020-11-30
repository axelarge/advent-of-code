(ns advent-of-code.2015.day14
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 14))
(def limit 2503)

(defn parse-line [line]
  (let [name (re-find #"\w+" line)
        [v t r] (find-ints line)
        interval (+ t r)]
    {:name name
     :v v
     :move-t t
     :interval interval}))

(defn parse [input]
  (map parse-line (str/split-lines input)))

(defn dist-after [t {:keys [interval v move-t]}]
  (+ (* (quot t interval) (* v move-t))
     (* (min (rem t interval) move-t)
        v)))

(defn leaders [t racers]
  (->> racers
       (map #(assoc % :total-dist (dist-after t %)))
       (sort-by (comp - :total-dist))
       (partition-by :total-dist)
       (first)))

(defn score-after [t racers]
  (->> (range 1 (inc t))
       (mapcat #(map :name (leaders % racers)))
       (frequencies)))

(defn solve1 [input]
  (->> (parse input)
       (leaders limit)
       (first)
       :total-dist))

(defn solve2 [input]
  (->> (parse input)
       (score-after limit)
       (map val)
       (reduce max)))

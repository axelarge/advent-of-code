(ns advent-of-code.2015.day06
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 06))

(defn parse-line [line]
  (let [action (keyword (re-find #"on|off|toggle" line))
        [x0 y0 x1 y1] (find-ints line)]
    [action x0 y0 x1 y1]))

(defn parse [input]
  (map parse-line (str/split-lines input)))

(defn perform1 [action lights pos]
  (case action
    :on (conj lights pos)
    :off (disj lights pos)
    :toggle ((if (contains? lights pos) disj conj) lights pos)))

(defn perform2 [action lights pos]
  (case action
    :on (update lights pos (fnil inc 0))
    :off (update lights pos #(max (dec (or % 0)) 0))
    :toggle (update lights pos #(+ (or % 0) 2))))

(defn run [perform lights [action x0 y0 x1 y1]]
  (reduce (partial perform action)
          lights
          (for [x (range x0 (inc x1))
                y (range y0 (inc y1))]
            [x y])))

(defn solve1 [input]
  (->> input
       (parse)
       (reduce (partial run perform1) #{})
       (count)))

(defn solve2 [input]
  (->> input
       (parse)
       (reduce (partial run perform2) {})
       (vals)
       (reduce +)))

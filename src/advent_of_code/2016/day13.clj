(ns advent-of-code.2016.day13
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str])
  (:import (java.util PriorityQueue)))

(def input (get-input 2016 13))

(defn wall? [n [x y]]
  (or (neg? x)
      (neg? y)
      (odd? (bit-count (+ (* x (+ x 3 y y))
                          (* y (inc y))
                          n)))))

(defn draw
  ([n path]
   (draw n path (map inc (reduce (partial map max) path))))
  ([n path [x y]]
   (let [path (set path)]
     (->> (for [y (range (inc y))]
            (->> (for [x (range (inc x))]
                   (cond (wall? n [x y]) \#
                         (path [x y]) \.
                         :else \space))
                 (apply str)))
          (str/join "\n")))))

(defn neighbors [[x y]]
  (for [[dx dy] [[-1 0] [1 0] [0 -1] [0 1]]]
    [(+ x dx) (+ y dy)]))

(defn solve [pred n]
  (let [queue (PriorityQueue.)]
    (loop [pos [1 1]
           d 1
           visited #{}
           path [[1 1]]]
      (->> (neighbors pos)
           (remove #(wall? n %))
           (remove visited)
           (reduce (fn [q p]
                     (doto q (.add [d p (conj path p)])))
                   queue))
      (let [[d pos path] (.remove queue)]
        (if (pred d pos)
          [d visited path]
          (recur pos
                 (inc d)
                 (conj visited pos)
                 path))))))

(defn solve1
  ([input] (solve1 input [31 39]))
  ([input target]
   (->> (find-int input)
        (solve (fn [d pos] (= pos target)))
        (first))))

(defn solve2 [input]
  (->> (find-int input)
       (solve (fn [d pos] (> d 50)))
       (second)
       (count)))

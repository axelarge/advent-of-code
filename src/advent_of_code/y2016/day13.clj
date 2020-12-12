(ns advent-of-code.y2016.day13
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
                   (cond (wall? n [x y]) "\u2588"
                         (path [x y]) \.
                         :else \space))
                 (apply str)))
          (str/join "\n")))))

(defn neighbors [[x y]]
  (for [[dx dy] [[-1 0] [1 0] [0 -1] [0 1]]]
    [(+ x dx) (+ y dy)]))

(defn explore
  ([pred n]
   (explore pred [1 1] n))
  ([pred pos n]
   (let [queue (PriorityQueue.)]
     (loop [pos pos
            d 1
            visited #{}
            path [pos]]
       (->> (neighbors pos)
            (remove #(wall? n %))
            (remove visited)
            (reduce (fn [q p]
                      (doto q (.add [d p (conj path p)])))
                    queue))
       (when-let [[d pos path] (.poll queue)]
         (if (pred d pos)
           [d visited path]
           (recur pos
                  (inc d)
                  (conj visited pos)
                  path)))))))

(defn solve1
  ([input] (solve1 input [31 39]))
  ([input target]
   (->> (find-int input)
        (explore (fn [d pos] (= pos target)))
        (first))))

(defn solve2 [input]
  (->> (find-int input)
       (explore (fn [d pos] (> d 50)))
       (second)
       (count)))

(ns advent-of-code.y2016.day01
  (:require [clojure.string :as str]
            [advent-of-code.support :refer :all]))

(def input (str/trim (get-input 2016 1)))

(defn parse-step [step]
  (let [direction (keyword (subs step 0 1))
        distance (parse-int (subs step 1))]
    [direction distance]))

(defn parse-steps [steps]
  (map parse-step (str/split steps #", ")))

(defn turn [heading direction]
  (case [heading direction]
    ([:N :L] [:S :R]) :W
    ([:N :R] [:S :L]) :E
    ([:E :L] [:W :R]) :N
    ([:E :R] [:W :L]) :S))

(defn move [[_ x y] heading distance]
  (case heading
    :N [heading x (+ y distance)]
    :S [heading x (- y distance)]
    :E [heading (+ x distance) y]
    :W [heading (- x distance) y]))

(defn step [[heading x y :as position] [direction distance]]
  (let [new-heading (turn heading direction)]
    (move position new-heading distance)))

(defn distance [[x y]]
  (+ (abs x) (abs y)))

(defn solve1 [input]
  (->> (parse-steps input)
       (reduce step [:N 0 0])
       (rest)
       (distance)))

(defn solve2 [input]
  (->> (parse-steps input)
       (reductions step [:N 0 0])
       (window)
       (mapcat (fn [[[_ x1 y1] [_ x2 y2]]]
                 (if (= x1 x2)
                   (for [y (xrange y1 y2)]
                     [x1 y])
                   (for [x (xrange x1 x2)]
                     [x y1]))))
       (reduce (fn [seen pos]
                 (if (seen pos)
                   (reduced pos)
                   (conj seen pos)))
               #{})
       (distance)))

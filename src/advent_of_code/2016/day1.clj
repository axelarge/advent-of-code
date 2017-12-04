(ns advent-of-code.2016.day1
  (:require [clojure.string :as str]
            [advent-of-code.support :refer :all]))

(def input (str/trim (get-input 2016 1)))

(defn parse-step [step]
  (let [direction (keyword (subs step 0 1))
        distance (Integer/parseInt (subs step 1))]
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

(defn travel [start steps]
  (reduce (fn [[heading x y :as position] [direction distance]]
            (let [new-heading (turn heading direction)]
              (move position new-heading distance)))
          start
          steps))

(defn distance [start steps]
  (let [[_ x y] (travel start steps)]
    (+ (Math/abs x) (Math/abs y))))

(defn solve1
  ([steps-str] (distance [:N 0 0] (parse-steps steps-str)))
  ([] (solve1 input)))

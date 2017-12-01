(ns advent-of-code.2016.day1
  (:require [clojure.string :as str]))

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

(defn solve
  ([steps-str] (distance [:N 0 0] (parse-steps steps-str)))
  ([] (solve "R2, L3, R2, R4, L2, L1, R2, R4, R1, L4, L5, R5, R5, R2, R2, R1, L2, L3, L2, L1, R3, L5, R187, R1, R4, L1, R5, L3, L4, R50, L4, R2, R70, L3, L2, R4, R3, R194, L3, L4, L4, L3, L4, R4, R5, L1, L5, L4, R1, L2, R4, L5, L3, R4, L5, L5, R5, R3, R5, L2, L4, R4, L1, R3, R1, L1, L2, R2, R2, L3, R3, R2, R5, R2, R5, L3, R2, L5, R1, R2, R2, L4, L5, L1, L4, R4, R3, R1, R2, L1, L2, R4, R5, L2, R3, L4, L5, L5, L4, R4, L2, R1, R1, L2, L3, L2, R2, L4, R3, R2, L1, L3, L2, L4, L4, R2, L3, L3, R2, L4, L3, R4, R3, L2, L1, L4, R4, R2, L4, L4, L5, L1, R2, L5, L2, L3, R2, L2")))

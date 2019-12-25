(ns advent-of-code.2018.day10
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input (get-input 2018 10 "test"))
(def input (get-input 2018 10))

(defn parse-line [line]
  (let [[x y vx vy] (->> (re-seq #"-?\d+" line)
                         (map parse-int))]
    {:x x :y y :vx vx :vy vy}))

(defn bounds [points]
  (let [min-x (:x (apply min-key :x points))
        min-y (:y (apply min-key :y points))
        max-x (:x (apply max-key :x points))
        max-y (:y (apply max-key :y points))]
    {:x min-x
     :y min-y
     :w (inc (- max-x min-x))
     :h (inc (- max-y min-y))}))

(defn make-state [points]
  {:points points
   :bounds (bounds points)})

(defn parse [input]
  (->> input
       str/split-lines
       (map parse-line)
       (make-state)))

(defn step-point [{:keys [vx vy] :as p}]
  (-> p
      (update :x + vx)
      (update :y + vy)))

(defn step [{:keys [points]}]
  (make-state (map step-point points)))

(defn draw [points]
  (let [{:keys [x y w h]} (bounds points)
        points (->> points
                    (map (fn [{:keys [x y]}] [x y]))
                    (set))]
    (->> (for [y (range y (+ y h))]
           (->> (for [x (range x (+ x w))]
                  (if (points [x y]) \# \.))
                (apply str)))
         (str/join "\n"))))

(defn getting-closer? [[prev cur]]
  (< (:w (:bounds cur))
     (:w (:bounds prev))))

(defn solve1 [input]
  (->> input
       parse
       (iterate step)
       (partition 2 1)
       (take-while getting-closer?)
       last
       second
       :points
       draw))

(defn solve2 [input]
  (->> input
       parse
       (iterate step)
       (partition 2 1)
       (index-where (complement getting-closer?))))

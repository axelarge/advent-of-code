(ns advent-of-code.y2019.day03
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2019 3))

(defn parse-move [[_ dir d]]
  [(keyword dir) (parse-int d)])

(defn parse-line [line]
  (->> line
       (re-seq #"([RLUD])(\d+)")
       (map parse-move)))

(defn parse [input]
  (let [[a b] (->> input
                   (str/split-lines)
                   (map parse-line))]
    [a b]))

(defn step [[x y] [dir d]]
  (case dir
    :R [(+ x d) y]
    :L [(- x d) y]
    :U [x (- y d)]
    :D [x (+ y d)]))

(defn minus [[x1 y1] [x2 y2]]
  [(- x1 x2) (- y1 y2)])

(defn dist [[x y]]
  (+ (abs x) (abs y)))

(defn segments [moves]
  (:res (reduce (fn [{:keys [pos d res]} [_ dist :as move]]
                  (let [pos' (step pos move)]
                    {:pos pos'
                     :d (+ d dist)
                     :res (conj res {:from pos
                                     :to pos'
                                     :d d})}))
                {:pos [0 0]
                 :d 0
                 :res []}
                moves)))

(defn between? [a x b]
  (or (< a x b)
      (> a x b)))

(defn intersection' [[[ax1 ay1] [ax2 ay2]]
                     [[bx1 by1] [bx2 by2]]]
  (when (and (between? ax1 bx1 ax2)
             (between? by1 ay1 by2))
    [bx1 ay1]))

(defn intersection [sa sb]
  (when-let [[x y] (or (intersection' [(:from sa) (:to sa)] [(:from sb) (:to sb)])
                       (intersection' [(:from sb) (:to sb)] [(:from sa) (:to sa)]))]
    {:pos [x y]
     :d (+ (:d sa)
           (:d sb)
           (dist (minus (:from sa) [x y]))
           (dist (minus (:from sb) [x y])))}))

(defn intersections [a b]
  (for [sa (segments a)
        sb (segments b)
        :let [x (intersection sa sb)]
        :when x]
    x))

(defn solve1 [input]
  (let [[a b] (parse input)]
    (->> (intersections a b)
         (map (comp dist :pos))
         (apply min))))

(defn solve2 [input]
  (let [[a b] (parse input)]
    (->> (intersections a b)
         (map :d)
         (apply min))))

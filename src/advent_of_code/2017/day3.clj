(ns advent-of-code.2017.day3
  (:require [clojure.string :as str]
            [advent-of-code.support :refer :all]))

(def input 325489)

(defn ring [n]
  (-> n
      Math/sqrt
      Math/ceil
      (* 0.5)
      int))

(defn ring-width [r]
  (inc (* r 2)))

(defn max-in-ring [r]
  (let [w (ring-width r)]
    (* w w)))

(defn nth-in-ring [n]
  (- n (max-in-ring (dec (ring n)))))

(defn offset [n]
  (let [r (ring n)]
    (if (zero? r)
      0
      (-> (nth-in-ring n)
          (rem (dec (ring-width r)))
          (- r)
          abs))))

(defn distance [n]
  (+ (ring n)
     (offset n)))

(defn get-neighbors [cells [x0 y0]]
  (->>
    (for [x (range -1 2) y (range -1 2) :when (not= [x y] [0 0])]
      [(+ x x0) (+ y y0)])
    (map #(get cells %))
    (keep identity)))

(defn move [[x y] direction]
  (case direction
    :U [x (inc y)]
    :D [x (dec y)]
    :L [(dec x) y]
    :R [(inc x) y]))

(defn turn-left [direction]
  (case direction
    :U :L
    :L :D
    :D :R
    :R :U))

(defn turn-left-if-possible [cells pos direction]
  (let [turned (turn-left direction)]
    (if (get cells (move pos (turn-left direction)))
      direction
      turned)))

(defn fill [limit]
  (loop [pos [1 0]
         direction :U
         cells {[0 0] 1}]
    (let [val (apply + (get-neighbors cells pos))
          cells (assoc cells pos val)
          pos (move pos direction)
          direction (turn-left-if-possible cells pos direction)]
      (if (> val limit)
        [val pos direction cells]
        (recur pos direction cells)))))

(def solve1 distance)
(def solve2 (comp first fill))

;;; ----- debug -----

(defn draw-table [cells]
  (let [xs (->> cells keys (map first))
        ys (->> cells keys (map second))
        min-x (apply min xs)
        max-x (apply max xs)
        min-y (apply min ys)
        max-y (apply max ys)
        max-val-width (->> cells vals (map (comp count str)) (apply max))
        rows (for [y (range max-y (dec min-y) -1)]
               (->> (range min-x (inc max-x))
                    (map #(get cells [% y]))
                    (map #(format (str "%" max-val-width "s") (str %)))
                    (str/join "  ")))]
    (str/join "\n" rows)))

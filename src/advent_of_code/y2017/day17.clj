(ns advent-of-code.y2017.day17
  (require [advent-of-code.support :refer :all]))

(def test-input 3)
(def input 363)

(defn fill [n step]
  (first
    (reduce (fn [[xs i] x]
              [(insert-at xs (inc i) x)
               (rem (+ i 1 step) (inc x))])
            [[0] 0]
            (range 1 (inc n)))))

(defn after-zero [^long n ^long step]
  (loop [v 0 i 0 x 0]
    (if (= x (inc n))
      v
      (let [x1 (inc x)]
        (recur (if (zero? i) x v)
               (rem (+ i 1 step) x1)
               x1)))))

(defn solve1 [input]
  (let [r (fill 2017 input)]
    (get r (rem (inc (.indexOf r 2017))
                (count r)))))

(defn solve2 [input]
  (after-zero 50000000 input))

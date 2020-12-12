(ns advent-of-code.y2018.day05
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input "dabAcCaCBAcCcaDA")

(def input (str/trim (get-input 2018 5)))

(defn is-pair? [a b]
  (when (and a b)
    (let [a (int a)
          b (int b)]
      (= 32 (- (max a b) (min a b))))))

(defn step [ls x]
  (if (is-pair? (peek ls) x)
    (rest ls)
    (conj ls x)))

(defn react [input]
  (reduce step '() input))

(defn char-range [from to]
  (->> (range (int from) (int to))
       (map char)))

(defn solve1 [input]
  (->> input
       react
       count))

(defn solve2 [input]
  (->> (transpose [(char-range \a \z)
                   (char-range \A \Z)])
       (pmap #(solve1 (remove (set %) input)))
       (apply min)))

(ns advent-of-code.y2017.day13
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input "0: 3\n1: 2\n4: 4\n6: 4")
(def input (get-input 2017 13))

(defn parse [input]
  (->> input
       str/split-lines
       (map find-ints)))

(defn severity [delay [t n]]
  (when (zero? (mod (+ t delay)
                    (* 2 (dec n))))
    (* t n)))

(defn total-severity [parsed]
  (apply + (keep (partial severity 0) parsed)))

(def solve1 (comp total-severity parse))

(defn solve2 [input]
  (let [parsed (parse input)]
    (first-where (fn [delay]
                   (not-any? #(severity delay %) parsed))
                 (next (range)))))

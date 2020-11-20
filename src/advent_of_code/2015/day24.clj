(ns advent-of-code.2015.day24
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]
            [clojure.math.combinatorics :refer [combinations]]))

(def input (get-input 2015 24))

(defn parse [input]
  (map parse-int (str/split-lines input)))

(defn equal-groups
  ([n weights]
   (let [group-sum (/ (reduce + weights) n)]
     (first (equal-groups n
                          weights
                          #(= (reduce + %) group-sum)))))
  ([n weights valid-group?]
   (if (= 1 n)
     [[weights]]
     (for [size (range 1 (- (count weights) (dec n)))
           group (combinations weights size)
           :when (valid-group? group)
           next-group (equal-groups (dec n)
                                    (remove (set group) weights)
                                    valid-group?)]
       (cons group next-group)))))

(defn solve [n weights]
  (reduce * (first (equal-groups n weights))))

(defn solve1 [input]
  (solve 3 (parse input)))

(defn solve2 [input]
  (solve 4 (parse input)))

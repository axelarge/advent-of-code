(ns advent-of-code.2019.day05
  (:require [advent-of-code.support :refer :all]
            [advent-of-code.2019.intcode :as intcode]))

(def input (get-input 2019 5))

(defn solve [state code-input]
  (->> (intcode/set-input state [code-input])
       (intcode/result)
       :output
       last))

(defn solve1 [input]
  (let [state (intcode/parse-state input)]
    (solve state 1)))

(defn solve2 [input]
  (let [state (intcode/parse-state input)]
    (solve state 5)))

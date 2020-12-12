(ns advent-of-code.y2016.day23
  (:require [advent-of-code.support :refer :all]
            [advent-of-code.y2016.asm :as asm]))

(def input (get-input 2016 23))

(defn solve1 [input]
  (asm/solve input {:a 7}))

(defn solve2 [input]
  (asm/solve input {:a 12}))

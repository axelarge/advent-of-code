(ns advent-of-code.y2016.day12
  (:require [advent-of-code.support :refer :all]
            [advent-of-code.y2016.asm :as asm]))

(def input (get-input 2016 12))

(defn solve1 [input]
  (asm/solve input {}))

(defn solve2 [input]
  (asm/solve input {:c 1}))

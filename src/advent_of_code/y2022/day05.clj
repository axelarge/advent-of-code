(ns advent-of-code.y2022.day05
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2022 5))

(defn parse [input]
  (let [[stacks moves] (map str/split-lines (str/split input #"\n\n"))]
    [(->> stacks
          transpose
          (partition-all 4)
          (mapv #(->> % second (drop-while #{\space}) reverse rest vec)))
     (->> moves
          (map find-ints)
          (map (juxtv [nil dec dec])))]))

(defn solve [chunk-f input]
  (let [[stacks moves] (parse input)]
    (->> moves
         (reduce (fn [stacks [n from to]]
                   (-> stacks
                       (update to into (chunk-f (subv (get stacks from) (- n))))
                       (update from #(subv % 0 (- n)))))
                 stacks)
         (map peek)
         (apply str))))

(defn solve1 [input]
  (solve reverse input))

(defn solve2 [input]
  (solve identity input))

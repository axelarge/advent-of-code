(ns advent-of-code.y2015.day17
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 17))

(defn parse [input]
  (->> (find-ints input)
       (sort (comp - compare))))

(defn combos [volume containers]
  (if (zero? volume)
    [[]]
    (->> (iterate rest containers)
         (take-while seq)
         (mapcat (fn [[vol & vols]]
                   (when (<= vol volume)
                     (->> (combos (- volume vol) vols)
                          (map #(cons vol %)))))))))

(defn solve1
  ([input] (solve1 150 input))
  ([n input]
   (->> (parse input)
        (combos n)
        (count))))

(defn solve2
  ([input] (solve2 150 input))
  ([n input]
   (if-let [cs (seq (combos n (parse input)))]
     (let [min-n (reduce min (map count cs))]
       (count-where #(= min-n (count %)) cs))
     0)))

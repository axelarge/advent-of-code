(ns advent-of-code.y2021.day09
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2021 9))

(defn parse [input]
  (into {} (for [[y row] (map vector (range) (str/split-lines input))
                 [x h] (map vector (range) row)]
             [[x y] (parse-char-int h)])))

(defn low-spots [grid]
  (->> grid
       (filter (fn [[pos h]]
                 (->> (neighbors4 pos)
                      (keep grid)
                      (every? #(< h %)))))))

(defn expand [grid pos]
  (loop [q #{pos}
         basin #{pos}]
    (if-some [pos (first q)]
      (let [new (->> pos
                     (neighbors4)
                     (filter grid)
                     (remove (comp #{9} grid))
                     (remove basin))]
        (recur (into (disj q pos) new)
               (into basin new)))
      basin)))

(defn solve1 [input]
  (->> input
       (parse)
       (low-spots)
       (map (comp inc val))
       (reduce +)))

(defn solve2 [input]
  (let [grid (parse input)]
    (->> grid
         (low-spots)
         (keys)
         (map #(count (expand grid %)))
         (sort (comp - compare))
         (take 3)
         (reduce *))))

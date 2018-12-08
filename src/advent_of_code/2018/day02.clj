(ns advent-of-code.2018.day02
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2018 2))

(defn count-line [line]
  (->> line
       frequencies
       (keep (comp #{2 3} val))
       set))

(defn checksum [m]
  (* (get m 2 0)
     (get m 3 0)))

(defn differ-by [strs]
  (->> (transpose strs)
       (count-where (partial apply not=))))

(defn equal-by [strs]
  (->> (transpose strs)
       (remove (partial apply not=))
       (map first)))

(defn tails [coll]
  (->> coll
       (iterate next)
       (take-while some?)))

(defn pairs [coll]
  (for [[a & tail] (tails coll)
        b tail]
    [a b]))

(defn solve1 [input]
  (->> input
       str/split-lines
       (map (comp frequencies count-line))
       (apply merge-with +)
       checksum))

(defn solve2 [input]
  (->> input
       str/split-lines
       pairs
       (find-where #(= 1 (differ-by %)))
       equal-by
       (apply str)))

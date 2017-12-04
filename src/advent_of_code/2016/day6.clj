(ns advent-of-code.2016.day6
  (:require [clojure.string :as str]
            [advent-of-code.support :refer :all]))

(def input (get-input 2016 6))

(defn transpose [m] (apply map list m))

(defn parse-lines [lines]
  (->> (str/split-lines lines)
       (map #(str/split % #""))
       transpose))

(defn most-common-elements [coll]
  (let [sorted (->> coll
                    (group-by identity)
                    vals
                    (sort-by (comp - count)))
        n (count (first sorted))]
    (->> sorted
         (take-while #(= (count %) n))
         (map first))))

(defn most-common-element [coll]
  (first (most-common-elements coll)))

(defn least-common-element [coll]
  (->> (group-by identity coll)
       vals
       (apply min-key count)
       first))

(defn solve
  ([f] (solve f input))
  ([f input]
   (->> input
        parse-lines
        (map f)
        (apply str))))

(def solve1 (partial solve most-common-element))
(def solve2 (partial solve least-common-element))

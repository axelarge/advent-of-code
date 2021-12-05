(ns advent-of-code.y2021.day02
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2021 2))

(defn parse-line [line]
  (let [[command n] (split-ws line)]
    [(keyword command) (parse-int n)]))

(defn parse [input]
  (map parse-line (str/split-lines input)))

(defn solve [rf input]
  (->> input
       (parse)
       (reduce rf {:depth 0 :pos 0 :aim 0})
       ((juxt :depth :pos))
       (apply *)))

(defn solve1 [input]
  (->> input
       (solve (fn [res [command n]]
                (case command
                  :down (update res :depth + n)
                  :up (update res :depth - n)
                  :forward (update res :pos + n))))))

(defn solve2 [input]
  (->> input
       (solve (fn [res [command n]]
                (case command
                  :down (update res :aim + n)
                  :up (update res :aim - n)
                  :forward (-> res
                               (update :pos + n)
                               (update :depth + (* n (:aim res)))))))))

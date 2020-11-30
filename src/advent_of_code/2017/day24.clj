(ns advent-of-code.2017.day24
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2017 24))

(defn parse-line [line]
  (set (find-ints line)))

(defn parse [input]
  (let [pieces (map parse-line (str/split-lines input))]
    {:index (->> pieces
                 (mapcat (fn [pair] (map (fn [x] [x pair]) pair)))
                 (group-map first second #{}))
     :pieces (set pieces)}))

(defn best-bridge
  ([f {:keys [index pieces]}]
   (best-bridge f index pieces '(0) 0))
  ([f index unused stack strength]
   (let [tip (peek stack)]
     (->> (get index tip)
          (filter unused)
          (keep (fn [piece]
                  (let [other (or (first (disj piece tip))
                                  ; handle a = b
                                  tip)]
                    (best-bridge f
                                 index
                                 (disj unused piece)
                                 (conj stack other)
                                 (+ strength tip other)))))
          (apply maximum (f strength stack))))))

(defn solve1 [input]
  (->> input
       (parse)
       (best-bridge (fn [strength stack] strength))))

(defn solve2 [input]
  (->> input
       (parse)
       (best-bridge (fn [strength stack] [(count stack) strength]))
       (second)))

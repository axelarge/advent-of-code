(ns advent-of-code.2018.day06
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input
  (str "1, 1\n"
       "1, 6\n"
       "8, 3\n"
       "3, 4\n"
       "5, 5\n"
       "8, 9"))

(def input (get-input 2018 6))

(defn parse-line [line]
  (->> line
       (re-seq #"\d+")
       (mapv parse-int)))

(defn parse [input]
  (let [points (->> input
                    str/split-lines
                    (map parse-line))
        min-x (apply min (map first points))
        max-x (apply max (map first points))
        min-y (apply min (map second points))
        max-y (apply max (map second points))
        edge? (fn [[x y]]
                (or (= x min-x)
                    (= x max-x)
                    (= y min-y)
                    (= y max-y)))
        coords (for [x (range min-x (inc max-x))
                     y (range min-y (inc max-y))]
                 [x y])]
    {:points points
     :coords coords
     :edge? edge?}))

(defn distance [[x0 y0] [x1 y1]]
  (+ (abs (- x0 x1))
     (abs (- y0 y1))))

(defn closest [points c]
  (let [groups (->> points
                    (map #(vector % (distance % c)))
                    (sort-by second)
                    (partition-by second)
                    (first))]
    (when (= 1 (count groups))
      (ffirst groups))))

(defn all-within? [d points c]
  (->> points
       (map #(distance c %))
       (reduce +)
       (> d)))

(defn solve1 [input]
  (let [{:keys [points coords edge?]} (parse input)
        {:keys [inf res]}
        (reduce (fn [{:keys [inf res]} c]
                  (let [p (closest points c)]
                    {:inf (cond-> inf (edge? c) (conj p))
                     :res (conj res p)}))
                {:inf #{} :res []}
                coords)]
    (->> res
         frequencies
         (remove (comp (some-fn nil? inf) first))
         (map second)
         (apply max))))

(defn solve2
  ([input]
   (solve2 input 10000))
  ([input d]
   (let [{:keys [points coords]} (parse input)]
     (->> coords
          (count-where #(all-within? d points %))))))

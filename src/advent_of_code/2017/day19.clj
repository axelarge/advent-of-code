(ns advent-of-code.2017.day19
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input
  (str
    "     |          \n"
    "     |  +--+    \n"
    "     A  |  C    \n"
    " F---|----E|--+ \n"
    "     |  |  |  D \n"
    "     +B-+  +--+ "))
(def input (get-input 2017 19))

(defn parse [input]
  (vec (str/split-lines input)))

(defn find-entry [maze]
  [-1 (index-where #{\|} (maze 0))])

(defn is-letter? [ch]
  (re-matches #"[A-Z]" (str ch)))

(defn move [pos dir]
  (mapv + pos dir))

(defn valid-pos [maze pos]
  (when-some [ch (get-in maze pos)]
    (when (not= ch \space) ch)))

(defn directions [[y x]]
  [[y x] [x y] [(- x) (- y)]])

(defn step [maze pos dir]
  (->> (directions dir)
       (keep (fn [dir]
               (let [pos (move pos dir)]
                 (when-some [ch (valid-pos maze pos)]
                   [pos dir ch]))))
       first))

(defn solve [maze]
  (loop [pos (find-entry maze)
         dir [1 0]
         letters []
         i 0]
    (if-let [[pos dir ch] (step maze pos dir)]
      (recur pos dir (cond-> letters (is-letter? ch) (conj ch)) (inc i))
      {:pos pos :dir dir :letters (apply str letters) :steps i})))

(def solve1 (comp :letters solve parse))
(def solve2 (comp :steps solve parse))

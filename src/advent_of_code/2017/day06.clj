(ns advent-of-code.2017.day06
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2017 6))

(defn step [banks]
  (let [x (apply max banks)
        i (.indexOf banks x)
        n (count banks)
        q (quot x n)
        r (rem x n)
        to-end (+ i 1 r)
        from-start (- to-end n)]
    (->> (assoc banks i 0)
         (map-indexed
           (fn [idx v] (+ v
                          q
                          (if (or (< idx from-start)
                                  (< i idx to-end))
                            1 0))))
         vec)))

(defn step-until-seen [banks]
  (loop [banks banks
         seen #{banks}]
    (let [banks (step banks)]
      (if (seen banks)
        [(count seen) banks]
        (recur banks (conj seen banks))))))

(defn solve [input]
  (step-until-seen (find-ints input)))

(def solve1 (comp first solve))
(def solve2 (comp first step-until-seen second solve))

;;; ------ faster solution using map instead of set ------

(defn step-until-seen' [banks]
  (loop [banks banks
         seen {banks 0}]
    (let [banks (step banks)]
      (if (seen banks)
        [seen banks]
        (recur banks
               (assoc seen banks (count seen)))))))

(defn solve' [input]
  (step-until-seen' (find-ints input)))

(def solve1' (comp count first solve'))
(def solve2' (comp
               (fn [[seen banks]]
                 (- (count seen) (seen banks)))
               solve'))

(ns advent-of-code.y2015.day11
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input "hepxcrrq")

(defn to-int [c]
  (- (int c) (int \a)))

(defn from-str [input]
  (reverse (map to-int input)))

(defn to-str [pass]
  (str/join (map #(char (+ % (int \a))) (reverse pass))))

(def bad-char? (set (map to-int [\i \o \l])))

(def mxf (comp (partition-by identity)
               (filter #(= 2 (count %)))
               (map first)))

(defn valid? [pass]
  (and (not-any? bad-char? pass)
       (<= 2 (count (into #{} mxf pass)))
       (->> (partition 3 1 pass)
            (some (fn [[c b a]] (= (+ a 2) (+ b 1) c))))))

(def max-c (inc (to-int \z)))

(defn inc-pass [pass]
  (loop [pass pass
         new-pass (transient [])
         carry 1]
    (if-some [c (first pass)]
      (let [new-c (+ c carry)
            carry (quot new-c max-c)
            new-c (rem new-c max-c)]
        (recur (rest pass)
               (if (bad-char? new-c)
                 (conj! (transient (vec (repeat (count new-pass) 0)))
                        (inc new-c))
                 (conj! new-pass new-c))
               carry))
      (persistent! (cond-> new-pass (pos? carry) (conj! 0))))))

(defn next-valid-pass [pass]
  (->> pass
       (iterate inc-pass)
       (next)
       (first-where valid?)))

(defn solve1 [input]
  (->> input
       (from-str)
       (next-valid-pass)
       (to-str)))

(defn solve2 [input]
  (->> input
       (from-str)
       (next-valid-pass)
       (next-valid-pass)
       (to-str)))

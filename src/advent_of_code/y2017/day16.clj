(ns advent-of-code.y2017.day16
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input "s1,x3/4,pe/b")
(def input (get-input 2017 16))

;;; Moves

(defn spin [d x]
  (let [i (- (count d) x)]
    (into (subvec d i)
          (subvec d 0 i))))

(defn exchange [d a b]
  (assoc d
    a (d b)
    b (d a)))

(defn partner [d a b]
  (let [ai (.indexOf d a)
        bi (.indexOf d b)]
    (exchange d ai bi)))

;;; Parsing / formatting

(defn char->int [c]
  (- (int c) (int \a)))

(defn int->char [^long i]
  (char (+ i (int \a))))

(defn format-dancers [d]
  (apply str (map int->char d)))

(defn parse-move [move]
  (condp re-find move
    #"s(\d+)"
    :>> (fn [[_ x]] [spin (parse-int x)])
    #"x(\d+)/(\d+)"
    :>> (fn [[_ a b]] [exchange (parse-int a) (parse-int b)])
    #"p./."
    :>> (fn [[_ a _ b]] [partner (char->int a) (char->int b)])))

(defn parse [input]
  (->> (str/split input #",")
       (map parse-move)))

;;; Part 1

(def dancers (comp vec range))

(defn dance [start steps]
  (reduce (fn [d [f & args]] (apply f d args))
          start
          steps))

;;; Part 2

(defn keep-dancing-slow [start steps]
  (iterate #(dance % steps) start))

(defn compile-dance [start steps]
  (let [[ps is]
        (reduce (fn [[ps is] [f & args]]
                  (if (= f partner)
                    [(apply f ps args) is]
                    [ps (apply f is args)]))
                [start start]
                steps)]
    (fn [[p0 i0]]
      [(mapv ps p0)
       (mapv is i0)])))

(defn keep-dancing [start steps]
  (->> (iterate (compile-dance start steps)
                [start start])
       (map (partial apply mapv))))

;;; Solutions

(defn solve1
  ([input] (solve1 16 input))
  ([s input]
   (->> input
        parse
        (dance (dancers s))
        format-dancers)))

(defn solve2-with [f input]
  (let [initial (dancers 16)
        dances (f initial (parse input))
        period (inc (index-where #(= % initial) (next dances)))]
    (-> dances
        (nth (rem 1000000000 period))
        format-dancers)))

(def solve2 (partial solve2-with keep-dancing))
(def solve2-slow (partial solve2-with keep-dancing-slow))

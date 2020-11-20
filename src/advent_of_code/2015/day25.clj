(ns advent-of-code.2015.day25
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2015 25))

(defn parse [input]
  (map parse-int (re-seq #"\d+" input)))

(defn diag [row col]
  (+ row col -1))

(defn nums-until-diag [n]
  (/ (* n (- n 1)) 2))

(defn pos->n [row col]
  (+ (nums-until-diag (diag row col))
     (dec col)))

(def init 20151125)

(defn step [^long x]
  (-> (* x 252533)
      (rem 33554393)))

(defn nth-code [n]
  (nth-iter n step init))

(defn solve [row col]
  (nth-code (pos->n row col)))

(defn solve1 [input]
  (apply solve (parse input)))

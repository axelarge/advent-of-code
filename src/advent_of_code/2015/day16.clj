(ns advent-of-code.2015.day16
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 16))
(def raw-fingerprint "children: 3\ncats: 7\nsamoyeds: 2\npomeranians: 3\nakitas: 0\nvizslas: 0\ngoldfish: 5\ntrees: 3\ncars: 2\nperfumes: 1")

(defn parse-pairs [s]
  (->> (re-seq #"(\w+)[: ]+(\d+)" s)
       (map (fn [[_ k v]] [k (parse-int v)]))))

(defn parse-line [line]
  (let [[[_ nr] & pairs] (parse-pairs line)]
    {:aunt nr :fingerprint (into {} pairs)}))

(defn parse [input]
  (map parse-line (str/split-lines input)))

(defn matches? [check fingerprint fp]
  (->> fingerprint
       (every? (fn [[k v]]
                 (if-let [fpv (get fp k)]
                   (check k v fpv)
                   true)))))

(def fingerprint (parse-pairs raw-fingerprint))

(defn find-match [check aunts]
  (let [pred (partial matches? check fingerprint)]
    (->> aunts
         (find-where (comp pred :fingerprint))
         :aunt)))

(defn solve1 [input]
  (->> (parse input)
       (find-match (fn [k v fpv] (= fpv v)))))

(defn solve2 [input]
  (->> (parse input)
       (find-match (fn [k v fpv]
                     (case k
                       ("cats" "trees") (> fpv v)
                       ("pomeranians" "goldfish") (< fpv v)
                       (= fpv v))))))

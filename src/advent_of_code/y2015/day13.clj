(ns advent-of-code.y2015.day13
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]
            [clojure.math.combinatorics :refer [permutations]]))

(def input (get-input 2015 13))

(defn parse-line [line]
  (let [[_ a act n b] (re-matches #"(\w).*(gain|lose) (\d+).* (\w)\w+." line)]
    {[(keyword a) (keyword b)]
     (cond-> (parse-int n) (= "lose" act) (-))}))

(defn parse [input]
  (let [table (into {} (map parse-line) (str/split-lines input))]
    {:table table
     :people (into #{} (mapcat first) table)}))

(defn solve [{:keys [table people]}]
  (->> (permutations people)
       (map #(->> (cons (last %) %)
                  (window)
                  (map (fn [[a b]]
                         (+ (get table [a b] 0)
                            (get table [b a] 0))))
                  (reduce +)))
       (reduce max)))

(defn solve1 [input]
  (solve (parse input)))

(defn solve2 [input]
  (-> (parse input)
      (update :people conj nil)
      (solve)))

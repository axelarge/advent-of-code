(ns advent-of-code.y2018.day03
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input
  (str "#1 @ 1,3: 4x4\n"
       "#2 @ 3,1: 4x4\n"
       "#3 @ 5,5: 2x2"))

(def input (get-input 2018 3))

(defn parse-line [line]
  (let [[id x y w h] (find-ints line)]
    {:id id :x x :y y :w w :h h}))

(defn covered [{:keys [x y w h]}]
  (for [xx (range x (+ x w))
        yy (range y (+ y h))]
    [xx yy]))


(defn parse [input]
  (->> input
       str/split-lines
       (map parse-line)
       (map #(assoc % :covered (covered %)))))

(defn solve1 [input]
  (->> input
       parse
       (mapcat :covered)
       frequencies
       (count-where #(> (val %) 1))))

(defn solve2 [input]
  (let [claims (parse input)
        agreed (->> claims
                    (mapcat :covered)
                    frequencies
                    (keep (fn [[k v]]
                            (when (> v 1) k)))
                    set)]
    (->> claims
         (find-where (fn [{:keys [covered]}]
                       (not-any? agreed covered)))
         :id)))

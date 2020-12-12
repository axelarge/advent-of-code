(ns advent-of-code.y2015.day18
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 18))

(defn parse [input]
  (let [lines (->> (str/split-lines input)
                   (map-indexed vector))]
    {:lights (->> lines
                  (mapcat (fn [[y row]]
                            (->> row
                                 (keep-indexed (fn [x cell]
                                                 (when (= cell \#) [x y]))))))
                  (set))
     :size (count lines)}))

(defn neighbors [[x y]]
  (for [dx [-1 0 1]
        dy [-1 0 1]
        :when (not= dx dy 0)]
    [(+ x dx) (+ y dy)]))

(defn step [{:keys [size lights]}]
  {:size size
   :lights (set (for [[pos n] (frequencies (mapcat neighbors lights))
                      :when (and (every? #(and (<= 0 %) (< % size))
                                         pos)
                                 (or (= n 3) (and (= n 2) (lights pos))))]
                  pos))})

(defn turn-on-corners [{:keys [size] :as state}]
  (let [n (dec size)]
    (update state :lights into [[0 0] [0 n] [n 0] [n n]])))

(defn draw [{:keys [size lights]}]
  (->> (for [y (range size)]
         (->> (for [x (range size)]
                (if (lights [x y]) \# \.))
              (str/join "")))
       (str/join "\n")))

(defn solve1
  ([input] (solve1 100 input))
  ([steps input]
   (->> (parse input)
        (nth-iter steps step)
        :lights
        (count))))


;; 821 too low
(defn solve2
  ([input] (solve2 100 input))
  ([steps input]
   (->> (parse input)
        (turn-on-corners)
        (nth-iter steps (comp turn-on-corners step))
        :lights
        (count))))

(ns advent-of-code.y2021.day13
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2021 13))

(defn parse [input]
  (let [[dots folds] (str/split input #"\n\n")]
    {:dots (->> dots (find-ints) (partition 2) (map vec) (set))
     :folds (->> folds
                 (re-seq #"([xy])=(\d+)")
                 (map #(-> % (rest) (vec) (update 1 parse-int))))}))

(defn fold [dots [axis fold-xy]]
  (let [fold' #(if (< % fold-xy) % (- (* 2 fold-xy) %))]
    (set (map #(update % (case axis "x" 0 1) fold') dots))))

(defn steps [{:keys [dots folds]}]
  (reductions fold dots folds))

(def solve (comp steps parse))

(def solve1 (comp count second solve))
(def solve2 (comp display-xy-set last solve))

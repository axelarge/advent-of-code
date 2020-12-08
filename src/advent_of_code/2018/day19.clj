(ns advent-of-code.2018.day19
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]
            [advent-of-code.2018.day16 :refer [all-ops]]))

(def input (get-input 2018 19))

(defn parse-line [line]
  (into [(keyword (subs line 0 4))]
        (find-ints line)))

(defn parse [input]
  (let [[head & lines] (str/split-lines input)]
    {:ip (find-int head)
     :code (mapv parse-line lines)}))

(defn run [regs {:keys [ip code]}]
  (loop [regs regs]
    (if-let [[op a b c] (get code (get regs ip))]
      (recur (-> regs
                 ((get all-ops op) a b c)
                 (update ip inc)))
      regs)))

(defn solve1 [input]
  (first (run [0 0 0 0 0 0] (parse input))))

(defn solve2 [input]
  (first (run [1 0 0 0 0 0] (parse input))))

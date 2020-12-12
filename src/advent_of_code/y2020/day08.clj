(ns advent-of-code.y2020.day08
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2020 8))

(defn parse-line [line]
  [(keyword (subs line 0 3)) (find-int line)])

(defn parse [input]
  {:code (mapv parse-line (str/split-lines input))
   :i 0
   :acc 0
   :seen #{}})

(defn step [{:keys [i code seen] :as state}]
  (cond
    (= i (count code)) (reduced (assoc state :finished? true))
    (seen i) (reduced state)
    :else
    (let [[op x] (get code i)]
      (-> (case op
            :acc (update state :acc + x)
            :jmp (update state :i + (dec x))
            :nop state)
          (update :i inc)
          (update :seen conj i)))))

(defn run [state]
  (->> (iterate step state)
       (find-where reduced?)
       (unreduced)))

(defn solve1 [input]
  (:acc (run (parse input))))

(defn solve2 [input]
  (let [state (parse input)]
    (->> (:code state)
         (keep-indexed (fn [i [op]]
                         (cond
                           (= :jmp op) (assoc-in state [:code i 0] :nop)
                           (= :nop op) (assoc-in state [:code i 0] :jmp))))
         (map run)
         (find-where :finished?)
         :acc)))

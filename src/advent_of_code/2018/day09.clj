(ns advent-of-code.2018.day09
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2018 9))

(defn make-state [players rounds]
  {:marbles [0]
   :score {}
   :players players
   :idx 0
   :max-rounds rounds
   :round 1})

(defn parse [input]
  (apply make-state (find-ints input)))

(defn step [{:keys [marbles idx round players] :as state}]
  (if (zero? (rem round 23))
    (let [idx (mod (- idx 7) (count marbles))
          score (+ round (get marbles idx))]
      (-> state
          (assoc :idx idx
                 :marbles (remove-at marbles idx)
                 :round (inc round))
          (update-in [:score (rem round players)] (fnil + 0) score)))
    (let [idx (rem (+ idx 2) (count marbles))
          marbles (insert-at marbles idx round)]
      (-> state
          (assoc :idx idx
                 :marbles marbles
                 :round (inc round))))))


(defn draw [{:keys [marbles idx]}]
  (->> marbles
       (map-indexed (fn [i m] (if (= i idx)
                                (str "(" m ")")
                                m)))
       (str/join " ")))

(defn solve1 [input]
  (let [state (parse input)]
    (->> (:max-rounds state)
         (nth (iterate step state))
         :score
         (apply max-key second)
         second)))

(defn solve2 [input]
  (->> input
       parse))

(ns advent-of-code.2019.day02
  (:require [advent-of-code.support :refer :all]
            [advent-of-code.2019.intcode :as intcode]))

(def input (get-input 2019 2))

(defn solve [mem]
  (:mem (intcode/result (intcode/make-state mem))))

(defn prep-input [state noun verb]
  (assoc state
    1 noun
    2 verb))

(defn solve1 [input]
  (let [state (-> (intcode/parse input)
                  (prep-input 12 2))]
    (get (solve state) 0)))

(defn solve2 [input]
  (let [state (intcode/parse input)]
    (first (for [noun (range 0 100)
                 verb (range 0 100)
                 :let [result (get (solve (prep-input state noun verb)) 0)]
                 :when (= result 19690720)]
             (+ (* 100 noun) verb)))))

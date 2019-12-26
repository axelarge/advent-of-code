(ns advent-of-code.2019.day07
  (:require [advent-of-code.support :refer :all]
            [advent-of-code.2019.intcode :as intcode]))

(def input (get-input 2019 7))

(defn all-phases [r]
  (for [a r
        b r
        c r
        d r
        e r
        :when (and (not= b a)
                   (not= c b) (not= c a)
                   (not= d c) (not= d b) (not= d a)
                   (not= e d) (not= e c) (not= e b) (not= e a))]
    [a b c d e]))

(defn output [program phases]
  (reduce (fn [v phase]
            (-> (intcode/set-input program [phase v])
                (intcode/result)
                :output
                (first)))
          0
          phases))

(defn output-looped [program phases]
  (loop [v 0
         amps (mapv (partial intcode/append-input program) phases)]
    (if (:halt? (peek amps))
      v
      (let [[v amps] (reduce (fn [[v amps] amp]
                               (let [amp (intcode/await-output (intcode/append-input amp v))
                                     v (peek (:output amp))]
                                 [v (conj amps amp)]))
                             [v []]
                             amps)]
        (recur v amps)))))

(defn best-phase [program r runner]
  (->> (all-phases r)
       (map (juxt identity (partial runner program)))
       (apply max-key second)))

(defn solve1 [input]
  (let [program (intcode/parse-state input)]
    (second (best-phase program (range 0 5) output))))

(defn solve2 [input]
  (let [program (intcode/parse-state input)]
    (second (best-phase program (range 5 10) output-looped))))

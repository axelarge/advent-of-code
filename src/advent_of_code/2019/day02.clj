(ns advent-of-code.2019.day02
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2019 2))

(defn parse [input]
  (mapv parse-int (str/split input #"[^\d]+")))

(defn add [mem pos]
  (let [[_ a b dest] (subvec mem pos (+ pos 4))]
    (assoc mem dest (+ (get mem a) (get mem b)))))

(defn multiply [mem pos]
  (let [[_ a b dest] (subvec mem pos (+ pos 4))]
    (assoc mem dest (* (get mem a) (get mem b)))))

(defn step [{:keys [mem pos]}]
  (let [opcode (get mem pos)]
    (case opcode
      1 {:mem (add mem pos) :pos (+ pos 4)}
      2 {:mem (multiply mem pos) :pos (+ pos 4)}
      99 (reduced mem))))

(defn solve [mem]
  (->> (iterate step {:mem mem :pos 0})
       (find-where reduced?)
       unreduced))

(defn prep-input [state noun verb]
  (assoc state
    1 noun
    2 verb))

(defn solve1 [input]
  (let [state (-> (parse input)
                  (prep-input 12 2))]
    (first (solve state))))

(defn solve2 [input]
  (let [state (parse input)]
    (first (for [noun (range 0 100)
                 verb (range 0 100)
                 :let [result (first (solve (prep-input state noun verb)))]
                 :when (= result 19690720)]
             (+ (* 100 noun) verb)))))

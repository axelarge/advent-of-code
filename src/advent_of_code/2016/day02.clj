(ns advent-of-code.2016.day02
  (:require [clojure.string :as str]
            [advent-of-code.support :refer :all]))

(defn parse-steps-line [steps]
  (map (comp keyword str) steps))

(defn parse-steps [steps]
  (map parse-steps-line (str/split-lines steps)))

(defn find-in [keypad button]
  (first (keep-indexed
           (fn [row-idx row]
             (when-let [col-idx (index-where #{button} row)]
               [row-idx col-idx]))
           keypad)))

(defn move-pos [[row col] direction]
  (case direction
    :U [(dec row) col]
    :D [(inc row) col]
    :L [row (dec col)]
    :R [row (inc col)]))

(defn move-in [keypad button direction]
  (let [position (find-in keypad button)
        position (move-pos position direction)
        new-button (get-in keypad position " ")]
    (if (= new-button " ") button new-button)))

(defn find-button [keypad start steps]
  (reduce (partial move-in keypad) start steps))

(defn find-buttons [keypad step-lines]
  (drop 1 (reductions (partial find-button keypad) "5" step-lines)))

(defn make-pad [pad]
  (->> pad
       (map2 str)
       (mapv vec)))

(def pad1 (make-pad ["123"
                     "456"
                     "789"]))

(def pad2 (make-pad ["  1  "
                     " 234 "
                     "56789"
                     " ABC "
                     "  D  "]))

(def input (get-input 2016 2))

(defn solve [keypad input]
  (->> (parse-steps input)
       (find-buttons keypad)
       (apply str)))

(def solve1 (partial solve pad1))
(def solve2 (partial solve pad2))

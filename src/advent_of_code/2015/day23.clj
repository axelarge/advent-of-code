(ns advent-of-code.2015.day23
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 23))

(defn parse-line [line]
  (->> (read-string (str "["line "]"))
       (map #(if (symbol? %) (keyword %) %))))

(defn parse [input]
  {:code (mapv parse-line (str/split-lines input))
   :a 0
   :b 0
   :i 0})

(defn step [{:keys [code i] :as state}]
  (when-let [[ins x y] (get code i)]
    (case ins
      :hlf (-> state
               (update x / 2)
               (update :i inc))
      :tpl (-> state
               (update x * 3)
               (update :i inc))
      :inc (-> state
               (update x inc)
               (update :i inc))
      :jmp (-> state
               (update :i + x))
      :jie (->> (if (even? (get state x)) y 1)
                (update state :i +))
      :jio (->> (if (= 1 (get state x)) y 1)
                (update state :i +)))))

(defn run [state]
  (->> (iterate step state)
       (take-while some?)
       (last)))

(defn solve1 [input]
  (:b (run (parse input))))

(defn solve2 [input]
  (:b (run (assoc (parse input) :a 1))))

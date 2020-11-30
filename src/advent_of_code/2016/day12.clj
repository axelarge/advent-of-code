(ns advent-of-code.2016.day12
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def input (get-input 2016 12))

(defn parse-line [line]
  (edn/read-string (str "[" (str/replace line #"[a-z]+" #(str ":" %)) "]")))

(defn parse [input]
  {:code (mapv parse-line (str/split-lines input))
   :i 0})

(defn arg [state x]
  (if (number? x)
    x
    (get state x 0)))

(defn step [{:keys [i code] :as state}]
  (when-let [[cmd x y] (get code i)]
    (-> (case cmd
          :cpy (assoc state y (arg state x))
          :inc (update state x (fnil inc 0))
          :dec (update state x (fnil dec 0))
          :jnz (cond-> state
                       (not= 0 (arg state x))
                       (update :i + (dec (arg state y)))))
        (update :i inc))))

(defn run [state]
  (->> (iterate step state)
       (take-while some?)
       (last)
       :a))

(defn solve1 [input]
  (run (parse input)))

(defn solve2 [input]
  (run (assoc (parse input) :c 1)))

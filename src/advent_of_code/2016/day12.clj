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
    (get-in state [:r x] 0)))

(defn step [{:keys [i code] :as state}]
  (when-let [[cmd x y] (get code i)]
    (-> (case cmd
          :cpy (assoc-in state [:r y] (arg state x))
          :inc (update-in state [:r x] (fnil inc 0))
          :dec (update-in state [:r x] (fnil dec 0))
          :jnz (cond-> state
                       (not= 0 (arg state x))
                       (update :i + (dec (arg state y)))))
        (update :i inc))))

(defn run [state]
  (->> (iterate step state)
       (take-while some?)
       (last)
       :r :a))

(defn solve1 [input]
  (run (parse input)))

(defn solve2 [input]
  (run (assoc-in (parse input) [:r :c] 1)))

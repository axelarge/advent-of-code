(ns advent-of-code.2018.day07
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]
            [clojure.set :as set]))

(def test-input
  (str "Step C must be finished before step A can begin.\n"
       "Step C must be finished before step F can begin.\n"
       "Step A must be finished before step B can begin.\n"
       "Step A must be finished before step D can begin.\n"
       "Step B must be finished before step E can begin.\n"
       "Step D must be finished before step E can begin.\n"
       "Step F must be finished before step E can begin."))

(def input (get-input 2018 7))

(defn parse-line [line]
  (->> line
       (re-seq #"[Ss]tep (.)")
       (map (comp first second))))

(defn parse [input]
  (->> input
       str/split-lines
       (map parse-line)
       (map (fn [[from to]]
              {to #{from}
               from #{}})) ; Ensure every node is in map
       (apply merge-with into)
       (sort-by key)))

(defn next-node [{:keys [req seen]}]
  (->> req
       (some (fn [[k rs]]
               (when (and (not (seen k))
                          (set/subset? rs seen))
                 k)))))

(defn step [state]
  (when-let [node (next-node state)]
    (-> state
        (update :path conj node)
        (update :seen conj node))))

(defn solve1 [input]
  (->> {:req (parse input)
        :path []
        :seen #{}}
       (iterate step)
       (take-while some?)
       last
       :path
       (apply str)))

(defn solve2 [input]
  (->> input))

(ns advent-of-code.y2018.day07
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

(defn make-work [extra node]
  [node (+ (- (int node) 64) extra)])

(defn forward-time [{:keys [workers elapsed] :as state}]
  (let [t (some->> workers
                   (keep second)
                   not-empty
                   (apply min))]
    (if-not t
      state
      (let [done (->> workers
                      (filter #(= t (second %)))
                      (map first))]
        (-> state
            (update :seen into done)
            (update :path into done)
            (update :elapsed + t)
            (assoc :workers (->> workers
                                 (mapv (fn [[n r]]
                                         (when (and n (not= r t))
                                           [n (- r t)]))))))))))

(defn step2 [make-work {:keys [workers] :as state}]
  (if-let [node (next-node (update state :req #(remove (comp (set (keep first workers)) first) %)))]
    (if-let [free-worker (index-where nil? workers)]
      (assoc-in state [:workers free-worker] (make-work node))
      (forward-time state))
    (when (some some? workers)
      (forward-time state))))

(defn solve1 [input]
  (->> {:req (parse input)
        :path []
        :seen #{}}
       (iterate step)
       (take-while some?)
       last
       :path
       (apply str)))

(defn solve2
  ([input]
   (solve2 input 5 60))
  ([input n e]
   (->> {:req (parse input)
         :workers (vec (repeat n nil))
         :elapsed 0
         :path []
         :seen #{}}
        (iterate (partial step2 (partial make-work e)))
        (take-while some?)
        last
        :elapsed)))

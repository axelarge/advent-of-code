(ns advent-of-code.y2017.day25
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2017 25))

(defn parse-state [s]
  (keyword (second (re-find #"state ([A-Z])" s))))

(defn parse-val [s]
  (= 1 (find-int s)))

(defn parse-block [block]
  (let [[header & rules] (str/split-lines block)
        from-state (parse-state header)]
    (->> rules
         (partition 4)
         (map (fn [[l1 l2 l3 l4]]
                (let [from-val (parse-val l1)
                      to-val (parse-val l2)
                      delta (if (str/includes? l3 "left") -1 +1)
                      to-state (parse-state l4)]
                  {[from-state from-val]
                   [to-state to-val delta]}))))))

(defn parse [input]
  (let [[header & blocks] (str/split input #"\n\n")]
    {:rules (->> blocks (mapcat parse-block) (into {}))
     :steps (find-int header)
     :machine {:state (parse-state header)
               :cursor 0
               :tape #{}}}))

(defn step [rules {:keys [state cursor tape]}]
  (let [[to-state to-val delta] (get rules [state (contains? tape cursor)])]
    {:tape ((if to-val conj disj) tape cursor)
     :cursor (+ cursor delta)
     :state to-state}))

(defn run [{:keys [rules steps machine]}]
  (-> (iterate (partial step rules) machine)
      (nth steps)))

(defn checksum [{:keys [tape]}]
  (count tape))

(defn solve1 [input]
  (->> input
       (parse)
       (run)
       (checksum)))

(ns advent-of-code.2017.day9
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2017 9))

(def special (set "{<!>}"))

(defmulti step (fn [{:keys [chars stream]}] (chars (first stream))))

(defmethod step \! [state]
  (update state :stream #(drop 2 %)))

(defmethod step \{ [state]
  (-> state
      (update :stream rest)
      (update :stack conj \{)
      (update :level inc)))

(defmethod step \} [state]
  (-> state
      (update :stream rest)
      (update :stack pop)
      (update :groups inc)
      (update :score + (:level state))
      (update :level dec)))

(defmethod step \< [state]
  (-> state
      (update :stream rest)
      (update :stack conj \<)
      (assoc :chars (set "!>"))))

(defmethod step \> [state]
  (-> state
      (update :stream rest)
      (update :stack pop)
      (assoc :chars special)))

(defmethod step :default [{:keys [stream chars stack] :as state}]
  (let [[chunk stream] (split-with (complement chars) stream)]
    (cond-> (assoc state :stream stream)
      (= \< (peek stack)) (update :garbage + (count chunk)))))

(defn solve [stream]
  (loop [{:keys [stream] :as state} {:stream  stream
                                     :level   0
                                     :score   0
                                     :groups  0
                                     :stack   []
                                     :garbage 0
                                     :chars   special}]
    (if (first stream)
      (recur (step state))
      state)))

(def solve1 (comp :score solve))
(def solve2 (comp :garbage solve))

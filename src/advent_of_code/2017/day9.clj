(ns advent-of-code.2017.day9
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2017 9))

(def special (set "{<!>}"))

(defn handle-! [chars state]
  [(rest chars) state])

(defn collecting-garbage? [state]
  (= \< (peek (:stack state))))

(defn handle-group-open [chars state]
  (if (collecting-garbage? state)
    [chars (update state :garbage inc)]
    [chars (-> state
               (update :stack conj \{)
               (update :level inc))]))

(defn handle-group-close [chars state]
  (if (collecting-garbage? state)
    [chars (update state :garbage inc)]
    [chars (-> state
               (update :stack pop)
               (update :groups inc)
               (update :score + (:level state))
               (update :level dec))]))

(defn handle-< [chars state]
  (if (collecting-garbage? state)
    [chars (update state :garbage inc)]
    [chars (-> state
               (update :stack conj \<))]))

(defn handle-> [chars state]
  [chars (-> state
             (update :stack pop))])

(defn handle-rest [chars state]
  (let [[chunk chars] (split-with (complement special) chars)]
    [chars (cond-> state
             (collecting-garbage? state) (update :garbage + (count chunk)))]))

(defn solve [chars]
  (loop [[[ch & tail :as chars] state]
         [chars {:level   0
                 :score   0
                 :groups  0
                 :stack   []
                 :garbage 0}]]
    (case ch
      nil state
      \! (recur (handle-! tail state))
      \> (recur (handle-> tail state))
      \< (recur (handle-< tail state))
      \{ (recur (handle-group-open tail state))
      \} (recur (handle-group-close tail state))
      (recur (handle-rest chars state)))))

(def solve1 (comp :score solve))
(def solve2 (comp :garbage solve))

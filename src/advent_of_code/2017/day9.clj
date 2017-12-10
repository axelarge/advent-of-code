(ns advent-of-code.2017.day9
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2017 9))

(defn solve [stream]
  (loop [state {:stream     stream
                :level      0
                :score      0
                :groups     0
                :in-garbage false
                :garbage    0}]
    (if-let [ch (first (:stream state))]
      (recur (->> (update state :stream next)
                  ((case [ch (:in-garbage state)]
                     [\{ false] #(update % :level inc)
                     [\} false] #(-> %
                                     (update :groups inc)
                                     (update :level dec)
                                     (update :score + (:level %)))
                     [\< false] #(assoc % :in-garbage true)
                     [\> true] #(assoc % :in-garbage false)
                     [\! true] #(update % :stream next)
                     #(cond-> %
                        (:in-garbage %) (update :garbage inc))))))
      state)))

(def solve1 (comp :score solve))
(def solve2 (comp :garbage solve))

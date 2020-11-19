(ns advent-of-code.2015.day22
  (:require [advent-of-code.support :refer :all])
  (:import (java.util PriorityQueue)))

(def input (get-input 2015 22))

(def spells
  {:missile {:mana 53 :dmg 4}
   :drain {:mana 73 :dmg 2 :heal 2}
   :shield {:mana 113 :fx 6}
   :poison {:mana 173 :fx 6}
   :recharge {:mana 229 :fx 5}})

(def spell-priority
  (->> (keys spells)
       (sort-by (comp :mana spells))))

(defn parse [input]
  (->> (map parse-int (re-seq #"\d+" input))
       (map vector [:boss :boss-dmg])
       (into {:hp 50 :mana 500 :armor 0 :spent 0 :tick 0})))

(defn fx-start [state spell]
  (case spell
    :shield (update state :armor + 7)
    state))

(defn fx-end [state spell]
  (case spell
    :shield (update state :armor - 7)
    state))

(defn fx-tick [state spell]
  (-> (case spell
        :poison (update state :boss - 3)
        :recharge (update state :mana + 101)
        :doom (cond-> state
                      (odd? (:tick state))
                      (update :hp dec))
        state)))

(defn cast-spell [state spell]
  (let [{:keys [mana dmg heal fx]} (get spells spell)]
    (when (and (<= mana (:mana state))
               (nil? (get-in state [:fx spell])))
      (-> state
          (update :mana - mana)
          (update :spent + mana)
          (cond-> dmg (update :boss - dmg))
          (cond-> heal (update :hp + heal))
          (cond-> fx (-> (assoc-in [:fx spell] fx)
                         (fx-start spell)))))))

(defn tick [state]
  (reduce-kv (fn [state spell timer]
               (-> (if (= timer 1)
                     (-> state
                         (fx-tick spell)
                         (fx-end spell)
                         (update :fx dissoc spell))
                     (-> state
                         (fx-tick spell)
                         (update-in [:fx spell] dec)))))
             (update state :tick inc)
             (:fx state)))

(defn lose? [state]
  (or (nil? state)
      (not (pos? (:hp state)))))

(defn win? [state]
  (not (pos? (:boss state))))

(defn boss-turn [{:keys [boss-dmg armor] :as state}]
  (update state :hp - (max 1 (- boss-dmg armor))))

(defn turn [state spell]
  (reduce (fn [state f]
            (let [state (f state)]
              (cond
                (lose? state) (reduced nil)
                (win? state) (reduced (assoc state :win? true))
                :else state)))
          state
          [#(cast-spell % spell) tick boss-turn tick]))

(defn cheapest-win [state]
  (let [queue (doto (PriorityQueue. 100 (fn [a b] (- (:spent a) (:spent b))))
                (.add state))]
    (loop []
      (when-let [state (.poll queue)]
        (let [states (keep (partial turn state) spell-priority)]
          (or (find-where :win? states)
              (do
                (doseq [new-s states]
                  (.add queue new-s))
                (recur))))))))

(defn solve1 [input]
  (:spent (cheapest-win (parse input))))

(defn solve2 [input]
  (:spent (cheapest-win (assoc (parse input) :fx {:doom -1}))))

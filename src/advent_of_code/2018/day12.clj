(ns advent-of-code.2018.day12
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2018 12))

(def test-input
  (str "initial state: #..#.#..##......###...###\n\n"
       "...## => #\n"
       "..#.. => #\n"
       ".#... => #\n"
       ".#.#. => #\n"
       ".#.## => #\n"
       ".##.. => #\n"
       ".#### => #\n"
       "#.#.# => #\n"
       "#.### => #\n"
       "##.#. => #\n"
       "##.## => #\n"
       "###.. => #\n"
       "###.# => #\n"
       "####. => #"))

(defn parse-line [line]
  (let [[_ from to] (re-find #"(.....) => (.)" line)]
    (when (= "#" to)
      (vec from))))

(defn parse [input]
  (let [[init _ & rules] (str/split-lines input)]
    {:pots (subs init 15)
     :offset 0
     :rules (set (keep parse-line rules))
     :gen 0
     :seen {}}))

(defn pad [{:keys [pots] :as state}]
  (let [first-on (str/index-of pots \#)
        last-on (str/last-index-of pots \#)]
    (if-not first-on
      (assoc state :pots "....." :offset 0)
      (-> state
          (assoc :pots (str "..." (subs pots first-on (inc last-on)) "..."))
          (update :offset #(+ % -3 first-on))))))

(defn apply-rules [{:keys [pots rules] :as state}]
  (assoc state :pots (->> pots
                          (partition 5 1)
                          (map #(if (rules %) \# \.))
                          (apply str ".."))))

#_(defn snapshot [{:keys [pots gen seen offset] :as state}]
    (if-let [seen-at (get seen pots)]
      (-> state
          (assoc :period {:gen (inc (- gen (:gen seen-at)))
                          :offset (- offset (:offset seen-at))}))
      (update state :seen assoc pots (select-keys state [:gen :offset]))))

(defn as-int [pots]
  (BigInteger. (apply str (map {\# 1 \. 0} pots)) 2))

(defn snapshot [{:keys [pots gen seen offset] :as state}]
  (update state :seen assoc pots (select-keys state [:gen :offset])))

(defn already-seen? [{:keys [pots seen gen]}]
  (get seen pots))

(defn step [state]
  (-> state
      snapshot
      apply-rules
      pad
      (update :gen inc)))

(defn step-fast [{:keys [] :as state}]
  (if-let [prev (already-seen? state)]
    (-> state)
        

    (-> state
        snapshot
        apply-rules
        pad
        (update :gen inc))))

(defn sum [{:keys [pots offset]}]
  (->> pots
       (keep-indexed (fn [i c]
                       (when (= \# c)
                         (+ i offset))))
       (apply +)))

(defn solve1 [input]
  (->> input
       parse
       pad
       (iterate step)
       (#(nth % 20))
       sum))

(defn solve2 [input]
  (let [cyclic (->> input
                    parse
                    pad
                    (iterate step)
                    (find-where already-seen?))
        prev (get-in cyclic [:seen (:pots cyclic)])
        d-offset (- (:offset cyclic) (:offset prev))
        period (- (:gen cyclic) (:gen prev))
        d (- 50000000000)]
    (sum ())
    (-> cyclic
        (assoc :prev (get-in cyclic [:seen (:pots cyclic)]))
        (dissoc :seen))
    {:cyclic (dissoc cyclic :seen :rules)
     :d-offset d-offset
     :period period}))
    ;(dissoc period :seen)))

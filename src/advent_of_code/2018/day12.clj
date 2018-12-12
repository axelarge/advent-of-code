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
     :rules (set (keep parse-line rules))}))

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

(defn step [state]
  (-> state
      pad
      apply-rules))

(defn sum [{:keys [pots offset]}]
  (->> pots
       (keep-indexed (fn [i c]
                       (when (= \# c)
                         (+ i offset))))
       (apply +)))

(defn solve1 [input]
  (->> input
       parse
       (iterate step)
       (#(nth % 20))
       sum))

(defn solve2 [input]
  (->> input))

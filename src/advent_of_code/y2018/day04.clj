(ns advent-of-code.y2018.day04
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input
  (str "[1518-11-01 00:00] Guard #10 begins shift\n"
       "[1518-11-01 00:05] falls asleep\n"
       "[1518-11-01 00:25] wakes up\n"
       "[1518-11-01 00:30] falls asleep\n"
       "[1518-11-01 00:55] wakes up\n"
       "[1518-11-01 23:58] Guard #99 begins shift\n"
       "[1518-11-02 00:40] falls asleep\n"
       "[1518-11-02 00:50] wakes up\n"
       "[1518-11-03 00:05] Guard #10 begins shift\n"
       "[1518-11-03 00:24] falls asleep\n"
       "[1518-11-03 00:29] wakes up\n"
       "[1518-11-04 00:02] Guard #99 begins shift\n"
       "[1518-11-04 00:36] falls asleep\n"
       "[1518-11-04 00:46] wakes up\n"
       "[1518-11-05 00:03] Guard #99 begins shift\n"
       "[1518-11-05 00:45] falls asleep\n"
       "[1518-11-05 00:55] wakes up"))

(def input (get-input 2018 4))

(defn parse-line [line]
  (let [type (-> (nth line 19)
                 str/lower-case
                 keyword)
        i (->> line
               (re-find (if (= :g type)
                          #"Guard #(\d+)"
                          #":(\d+)"))
               last
               parse-int)]
    [type i]))

(defn slept-minutes [[guard & times]]
  {guard (->> times
              (partition 2)
              (mapcat (partial apply range)))})

(defn parse [input]
  (->> input
       str/split-lines
       sort
       (map parse-line)
       (partition-all-by (comp #{:g} first))
       (map2 second)
       (map slept-minutes)
       (apply merge-with concat)))

(defn solve1 [input]
  (let [[id minutes] (->> input
                          parse
                          (apply max-key (comp count val)))
        minute (->> minutes
                    frequencies
                    (apply max-key val)
                    key)]
    (* id minute)))

(defn solve2 [input]
  (->> input
       parse
       (mapcat (fn [[g ms]]
                 (for [m ms] [g m])))
       frequencies
       (apply max-key val)
       key
       (apply *)))

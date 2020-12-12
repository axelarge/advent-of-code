(ns advent-of-code.y2015.day19
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 19))

(defn parse-rule [line]
  (str/split line #" => "))

(defn parse [input]
  (let [lines (str/split-lines input)]
    {:rules (map parse-rule (drop-last 2 lines))
     :start (peek lines)}))

(defn apply-rule [s from to]
  (->> (str-indexes s from)
       (map #(str (subs s 0 %) to (subs s (+ % (count from)))))))

(defn apply-rules [rules start]
  (->> rules
       (mapcat (fn [[from to]]
                 (apply-rule start from to)))))

(defn num-results [rules start]
  (count (set (apply-rules rules start))))

(defn steps-to-build
  ([rules cur]
   (steps-to-build (map reverse rules) cur 0))
  ([rules cur n]
   (if (str/includes? cur "e")
     (when (= "e" cur) n)
     (some->> (apply-rules rules cur)
              (keep #(steps-to-build rules % (inc n)))
              (not-empty)
              (first)))))

(defn solve1 [input]
  (let [{:keys [rules start]} (parse input)]
    (num-results rules start)))

(defn solve2 [input]
  (let [{:keys [rules start]} (parse input)]
    (steps-to-build rules start)))

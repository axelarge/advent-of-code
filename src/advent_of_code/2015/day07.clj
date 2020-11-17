(ns advent-of-code.2015.day07
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 07))

(defn parse-line [line]
  (let [op (keyword (re-find #"[A-Z]+" line))
        params (->> (re-seq #"[a-z\d]+" line)
                    (map #(try (parse-int %)
                               (catch Exception _ (keyword %)))))
        [x y :as inputs] (butlast params)]
    {:op (or op :MOV)
     :x x
     :y y
     :out (last params)
     :deps (set (filter keyword? inputs))}))

(defn parse [input]
  (set (map parse-line (str/split-lines input))))

(defn do-step [wires {:keys [op out x y]}]
  (let [arg (fn [a] (get wires a a))
        res (case op
              :MOV (arg x)
              :NOT (bit-not (arg x))
              :AND (bit-and (arg x) (arg y))
              :OR (bit-or (arg x) (arg y))
              :LSHIFT (bit-shift-left (arg x) (arg y))
              :RSHIFT (bit-shift-right (arg x) (arg y)))]
    (assoc wires out res)))

(defn run [wires steps]
  (loop [steps steps
         wires wires]
    (if-let [step (find-where #(every? wires (:deps %)) steps)]
      (recur (disj steps step)
             (do-step wires step))
      (mapm #(mod % 65536) wires))))

(defn solve1 [input]
  (:a (run {} (parse input))))

(defn solve2 [input]
  (let [steps (parse input)
        a (:a (run {} steps))]
    (:a (run {:b a} steps))))

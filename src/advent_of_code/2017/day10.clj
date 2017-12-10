(ns advent-of-code.2017.day10
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input "227,169,3,166,246,201,0,47,1,255,2,254,96,3,97,144")

(defn parse1 [steps]
  (->> (str/split steps #"[^\d]+")
       (mapv parse-int)))

(defn parse2 [steps]
  (-> (map int steps)
      (concat [17 31 73 47 23])
      vec))

(defn advance [n ls]
  (->> (cycle ls)
       (drop n)
       (take (count ls))
       vec))

(defn take-step [[ls offset skip] n]
  [(->> (concat (reverse (take n ls))
                (drop n ls))
        (advance (+ n skip)))
   (+ skip offset n)
   (inc skip)])

(defn reset-to-front [ls skip]
  (let [size (count ls)]
    (advance (- size (mod skip size)) ls)))

(defn take-steps [size times steps]
  (let [[ls skip n] (-> (iterate #(reduce take-step % steps)
                                 [(range size) 0 0])
                        (nth times))]
    (reset-to-front ls skip)))

(defn condense [ints]
  (->> ints
       (partition 16)
       (map (partial apply bit-xor))))

(defn to-hex [ints]
  (str/join (map #(format "%02x" %) ints)))

(defn solve1
  ([input] (solve1 256 input))
  ([size input]
   (->> input
        parse1
        (take-steps size 1)
        (take 2)
        (apply *))))

(defn solve2 [input]
  (->> input
       parse2
       (take-steps 256 64)
       condense
       to-hex))

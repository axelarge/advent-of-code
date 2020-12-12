(ns advent-of-code.y2017.day10
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input "227,169,3,166,246,201,0,47,1,255,2,254,96,3,97,144")

(defn parse1 [steps]
  (find-ints steps))

(defn parse2 [steps]
  (-> (map int steps)
      (concat [17 31 73 47 23])
      vec))

(defn take-step [[ls offset skip] n]
  (let [is (->> (range offset (+ offset n))
                (map #(mod % (count ls))))]
    [(reduce-kv assoc ls (zipmap is (map ls (reverse is))))
     (+ offset n skip)
     (inc skip)]))

(defn take-steps [size times steps]
  (-> (iterate #(reduce take-step % steps)
               [(vec (range size)) 0 0])
      (nth times)
      first))

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

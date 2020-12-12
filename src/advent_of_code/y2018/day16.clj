(ns advent-of-code.y2018.day16
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2018 16))

(defn parse [input]
  (let [[samples program] (str/split input #"\n\n\n")]
    [(->> samples
          (find-ints)
          (partition 4)
          (map vec)
          (partition 3))
     (->> program
          (find-ints)
          (partition 4)
          (map vec))]))

(def all-ops
  {:addr (fn [r a b c] (assoc r c (+ (r a) (r b))))
   :addi (fn [r a b c] (assoc r c (+ (r a) b)))
   :mulr (fn [r a b c] (assoc r c (* (r a) (r b))))
   :muli (fn [r a b c] (assoc r c (* (r a) b)))
   :banr (fn [r a b c] (assoc r c (bit-and (r a) (r b))))
   :bani (fn [r a b c] (assoc r c (bit-and (r a) b)))
   :borr (fn [r a b c] (assoc r c (bit-or (r a) (r b))))
   :bori (fn [r a b c] (assoc r c (bit-or (r a) b)))
   :setr (fn [r a b c] (assoc r c (r a)))
   :seti (fn [r a b c] (assoc r c a))
   :gtir (fn [r a b c] (assoc r c (if (> a (r b)) 1 0)))
   :gtri (fn [r a b c] (assoc r c (if (> (r a) b) 1 0)))
   :gtrr (fn [r a b c] (assoc r c (if (> (r a) (r b)) 1 0)))
   :eqir (fn [r a b c] (assoc r c (if (== a (r b)) 1 0)))
   :eqri (fn [r a b c] (assoc r c (if (== (r a) b) 1 0)))
   :eqrr (fn [r a b c] (assoc r c (if (== (r a) (r b)) 1 0)))})

(defn matches [[r0 [_ a b c] r1]]
  (->> all-ops
       (vals)
       (filter #(= (% r0 a b c) r1))
       (set)))

(defn solve1 [input]
  (let [[samples] (parse input)]
    (count-where #(>= (count (matches %)) 3) samples)))

(defn solve2 [input]
  (let [[samples program] (parse input)
        candidates (->> samples
                        (map (fn [[_ [opn] :as sample]]
                               {opn (matches sample)}))
                        (apply merge-with clojure.set/intersection))
        ops (loop [ops {}
                   candidates candidates]
              (if-let [[k op] (find-where (comp single val) candidates)]
                (let [op (first op)]
                  (recur (assoc ops k op)
                         (mapm #(disj % op) candidates)))
                ops))]
    (->> program
         (reduce (fn [r [op a b c]]
                   ((get ops op) r a b c))
                 [0 0 0 0])
         (first))))


(ns advent-of-code.2018.day16
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
  [(fn addr [r a b c] (assoc r c (+ (r a) (r b))))
   (fn addi [r a b c] (assoc r c (+ (r a) b)))
   (fn mulr [r a b c] (assoc r c (* (r a) (r b))))
   (fn muli [r a b c] (assoc r c (* (r a) b)))
   (fn banr [r a b c] (assoc r c (bit-and (r a) (r b))))
   (fn bani [r a b c] (assoc r c (bit-and (r a) b)))
   (fn borr [r a b c] (assoc r c (bit-or (r a) (r b))))
   (fn bori [r a b c] (assoc r c (bit-or (r a) b)))
   (fn setr [r a b c] (assoc r c (r a)))
   (fn seti [r a b c] (assoc r c a))
   (fn gtir [r a b c] (assoc r c (if (> a (r b)) 1 0)))
   (fn gtri [r a b c] (assoc r c (if (> (r a) b) 1 0)))
   (fn gtrr [r a b c] (assoc r c (if (> (r a) (r b)) 1 0)))
   (fn eqir [r a b c] (assoc r c (if (== a (r b)) 1 0)))
   (fn eqri [r a b c] (assoc r c (if (== (r a) b) 1 0)))
   (fn eqrr [r a b c] (assoc r c (if (== (r a) (r b)) 1 0)))])

(defn matches [[r0 [_ a b c] r1]]
  (->> all-ops
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


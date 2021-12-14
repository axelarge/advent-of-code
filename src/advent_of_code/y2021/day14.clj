(ns advent-of-code.y2021.day14
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2021 14))

(defn parse [input]
  (let [[template pairs] (str/split input #"\n\n")]
    [template (apply hash-map (re-seq #"[A-Z]+" pairs))]))

(def size-after
  (memoize
    (fn [spacers template n]
      (if (zero? n)
        (frequencies template)
        (->> template
             (window)
             (map (fn [[a b]]
                    (size-after spacers
                                (str a (spacers (str a b)) b)
                                (dec n))))
             (cons (->> template rest butlast frequencies (mapm -)))
             (apply merge-with +))))))

(defn score [freq]
  (- (apply max (vals freq))
     (apply min (vals freq))))

(defn solve [n input]
  (let [[template spacers] (parse input)]
    (score (size-after spacers template n))))

(def solve1 (partial solve 10))
(def solve2 (partial solve 40))

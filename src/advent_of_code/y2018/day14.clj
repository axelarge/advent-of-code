(ns advent-of-code.y2018.day14
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input "556061")

(def init-state
  {:recipes [3 7]
   :a 0
   :b 1})

(defn next-recipes [{:keys [recipes a b] :as state}]
  (let [sum (+ (get recipes a)
               (get recipes b))]
    (assoc state
      :recipes (if (< sum 10)
                 (conj recipes sum)
                 (conj recipes (quot sum 10) (rem sum 10))))))

(defn move-elves [{:keys [recipes a b] :as state}]
  (let [rc (count recipes)]
    (assoc state
      :a (rem (+ 1 a (get recipes a)) rc)
      :b (rem (+ 1 b (get recipes b)) rc))))

(def step (comp next-recipes move-elves))

(defn has? [n {:keys [recipes]}]
  (let [cn (count n)
        cr (count recipes)]
    (and (>= cr cn)
         (or (= n (subvec recipes (- cr cn)))
             (and (> cr cn)
                  (= n (subvec recipes (- cr cn 1) (- cr 1))))))))

(defn draw [{:keys [recipes a b]}]
  (str (str/join " " recipes) "\n"
       (str/join " " (map (fn [i] (condp = i a "-" b "~" " "))
                          (range (count recipes))))))

(defn solve1 [input]
  (let [n (parse-int input)]
    (->> init-state
         (iterate step)
         (first-where #(-> % :recipes count (>= (+ n 10))))
         :recipes
         (drop n)
         (take 10)
         (apply str))))

(defn solve2 [input]
  (let [n (mapv (comp parse-int str) (str input))]
    (->> init-state
         (iterate step)
         (first-where (partial has? n))
         :recipes
         (apply str)
         (#(str/last-index-of % (str input))))))

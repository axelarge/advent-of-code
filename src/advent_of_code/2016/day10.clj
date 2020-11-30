(ns advent-of-code.2016.day10
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2016 10))

(defn parse-line [line]
  (if (str/starts-with? line "value")
    (let [[x bot] (find-ints line)]
      [bot x])
    (let [[bot x y] (find-ints line)
          [_ tx ty] (map keyword (re-seq #"bot|output" line))]
      {bot [tx x ty y]})))

(defn parse [input]
  (let [{bots false gives true} (->> (map parse-line (str/split-lines input))
                                     (group-by map?))]
    {:bots (group-map first second [] (sort bots))
     :gives (into {} gives)}))

(defn insert [vs x]
  ;; slow, but it doesn't matter
  (vec (sort (conj vs x))))

(defn set-val [state t k v]
  (if (= :output t)
    (assoc-in state [:out k] v)
    (update-in state [:bots k] insert v)))

(defn step [state bot]
  (let [vs (get-in state [:bots bot])]
    (if (< (count vs) 2)
      state
      (let [lo (first vs)
            hi (peek vs)
            [tx x ty y] (get-in state [:gives bot])]
        (-> state
            (assoc-in [:bots bot] (subvec vs 1 (dec (count vs))))
            (assoc-in [:comps (set vs)] bot)
            (set-val tx x lo)
            (set-val ty y hi)
            (cond-> (= tx :bot) (step x)
                    (= ty :bot) (step y)))))))

(defn solve [state]
  (->> (keys (:bots state))
       (reduce step state)))

(defn solve1
  ([input] (solve1 input [17 61]))
  ([input comps]
   (get-in (solve (parse input)) [:comps (set comps)])))

(defn solve2 [input]
  (-> (:out (solve (parse input)))
      (map [0 1 2])
      (->> (reduce *))))

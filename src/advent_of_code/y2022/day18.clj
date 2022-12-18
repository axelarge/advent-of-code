(ns advent-of-code.y2022.day18
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2022 18))

(defn parse [input]
  (let [rocks (set (map (comp vec find-ints) (str/split-lines input)))]
    {:rocks rocks
     :outside #{}
     :inside #{}
     :oob? (complement (partial bounds3d-contain? (bounds3d rocks)))}))

(defn outside? [{:keys [rocks outside inside oob?] :as state} pos]
  (cond (outside pos) [true state]
        (inside pos) [false state]
        :else
        (loop [q (queue pos)
               state state
               visited #{}]
          (if-let [pos (peek q)]
            (cond
              (visited pos)
              (recur (pop q) state visited)

              (oob? pos)
              [true (update state :outside into (conj visited pos))]

              :else
              (recur (->> (neighbors3d6 pos)
                          (remove (some-fn rocks visited))
                          (into (pop q)))
                     state
                     (conj visited pos)))
            [false (update state :inside into visited)]))))

(defn solve1 [input]
  (let [{:keys [rocks]} (parse input)]
    (->> rocks
         (mapcat neighbors3d6)
         (remove rocks)
         (count))))

(defn solve2 [input]
  (let [{:keys [rocks] :as state} (parse input)]
    (->> rocks
         (mapcat neighbors3d6)
         (remove rocks)
         (reduce (fn [[n state] pos]
                   (let [[out? state] (outside? state pos)]
                     [(cond-> n out? inc) state]))
                 [0 state])
         (first))))

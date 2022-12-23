(ns advent-of-code.y2022.day23
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2022 23))

(def moves
  [[[0 -1] [[-1 -1] [0 -1] [+1 -1]]]
   [[0 +1] [[-1 +1] [0 +1] [+1 +1]]]
   [[-1 0] [[-1 -1] [-1 0] [-1 +1]]]
   [[+1 0] [[+1 -1] [+1 0] [+1 +1]]]])

(defn step [grid moves pos]
  (or (when (some grid (neighbors8 pos))
        (->> moves
             (some (fn [[move-delta neighbor-deltas]]
                     (when (->> neighbor-deltas
                                (map (partial vec+ pos))
                                (not-any? grid))
                       (vec+ pos move-delta))))))
      pos))

(defn states [grid]
  (->> [grid (cycle moves)]
       (iterate (fn [[grid moves]]
                  [(->> grid
                        (group-by (partial step grid (take 4 moves)))
                        (mapcat (fn [[to from]] (if (== 1 (count from)) [to] from)))
                        set)
                   (rest moves)]))
       (map first)))

(defn solve1 [input]
  (let [grid (-> input lines->set states (nth 10))
        [minx maxx] (apply (juxt min max) (mapv first grid))
        [miny maxy] (apply (juxt min max) (mapv second grid))]
    (- (* (- maxx minx -1)
          (- maxy miny -1))
       (count grid))))

(defn solve2 [input]
  (->> input lines->set states window (take-while (partial apply not=)) count inc))

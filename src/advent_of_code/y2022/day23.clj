(ns advent-of-code.y2022.day23
  (:require [advent-of-code.support :refer :all]))

(def input (get-input 2022 23))

(defn parse [input]
  (set (for [[y line] (indexed (split-lines input))
             [x c] (indexed line)
             :when (= c \#)]
         [x y])))

(def moves
  [[[0 -1] [[-1 -1] [0 -1] [+1 -1]]]
   [[0 +1] [[-1 +1] [0 +1] [+1 +1]]]
   [[-1 0] [[-1 -1] [-1 0] [-1 +1]]]
   [[+1 0] [[+1 -1] [+1 0] [+1 +1]]]])

(defn vec+ [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

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
  (let [res (nth (states (parse input)) 10)
        [minx maxx] (apply (juxt min max) (mapv first res))
        [miny maxy] (apply (juxt min max) (mapv second res))]
    (- (* (- maxx minx -1)
          (- maxy miny -1))
       (count res))))

(defn solve2 [input]
  (->> input parse states window (take-while (partial apply not=)) count inc))

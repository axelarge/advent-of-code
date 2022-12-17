(ns advent-of-code.y2022.day17
  (:require [advent-of-code.support :refer :all]
            [clojure.set :as set]
            [clojure.string :as str]))

(def input (get-input 2022 17))
(def tile-data "..####.\n\n...#...\n..###..\n...#...\n\n....#..\n....#..\n..###..\n\n..#....\n..#....\n..#....\n..#....\n\n..##...\n..##...")
(def tiles
  (->> (str/split tile-data #"\n\n")
       (mapv (fn [tile]
               (set (for [[y line] (indexed (reverse (str/split-lines tile)))
                          [x c] (indexed line)
                          :when (= c \#)]
                      [x y]))))))

(defn get-tile [i top]
  (set (for [[x y] (tiles i)] [x (+ y top 4)])))

(defn move [tile dx dy]
  (set (for [[x y] tile] [(+ x dx) (+ y dy)])))

(defn move-left [tile]
  (if (not-any? (comp #{0} first) tile)
    (move tile -1 0)
    tile))

(defn move-right [tile]
  (if (not-any? (comp #{6} first) tile)
    (move tile 1 0)
    tile))

(defn move-down [tile]
  (move tile 0 -1))

(defn try-move [tile move-fn screen]
  (let [tile' (move-fn tile)]
    (if (intersects? tile' screen)
      tile
      tile')))

(defn debug [screen]
  (doseq [y (take 12 (range (inc (apply max (map second screen))) -1 -1))]
    (println (->> (range 7)
                  (map #(if (contains? screen [% y]) "#" "."))
                  (apply str))))
  (println))

(defn fingerprint [screen top]
  (->> (range 7)
       (mapv (fn [x]
               (->> (range (- top 5) (inc top))
                    (transduce (comp (filter (fn [y] (screen [x y])))
                                     (map #(- top %)))
                               min
                               255))))
       (reduce (fn [res x]
                 (bit-or (bit-shift-left res 8) x))
               0)))

(defn solve [input rounds]
  (let [moves (str/trim input)]
    (loop [i 0
           move-i 0
           top 0
           skipped 0
           screen (set (for [x (range 7)] [x 0]))
           seen {}]
      (if (>= i rounds)
        (+ top skipped)
        (let [tile-i (mod i (count tiles))
              [move-i tile] (loop [move-i move-i
                                   tile (get-tile tile-i top)]
                              (let [move (nth moves move-i)
                                    move-i (mod (+ move-i 1) (count moves))
                                    tile (case move
                                           \< (try-move tile move-left screen)
                                           \> (try-move tile move-right screen))
                                    tile' (move-down tile)]
                                (if (intersects? tile' screen)
                                  [move-i tile]
                                  (recur move-i tile'))))
              top (apply max top (map second tile))
              screen (set/union screen tile)
              sig [tile-i move-i (fingerprint screen top)]]
          (if-let [[prev-i prev-top] (get seen sig)]
            (let [cycle (- i prev-i)
                  n (quot (- rounds i) cycle)
                  skipped (+ skipped (* n (- top prev-top)))
                  new-i (+ i 1 (* n cycle))]
              (recur new-i move-i top skipped screen seen))
            (recur (inc i) move-i top skipped screen (assoc seen sig [i top]))))))))

(defn solve1 [input]
  (solve input 2022))

(defn solve2 [input]
  (solve input 1000000000000))

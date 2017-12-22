(ns advent-of-code.2017.day22
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input "..#\n#..\n...")
(def input (get-input 2017 22))

(defn parse-line [line]
  (->> line))


(defn parse [input]
  (let [lines (str/split-lines input)]
    [(count lines)
     (->> (for [[y line] (map-indexed vector (str/split-lines input))
                [x ch] (map-indexed vector line)
                :when (= \# ch)]
            [[y x] ch])
          (into {}))]))

(defn turn-left [[y x]] [(- x) y])
(defn turn-right [[y x]] [x (- y)])
(defn turn-around [[y x]] [(- y) (- x)])

;(def move (partial mapv +))
(defn move [[a b] [c d]] [(+ a c) (+ b d)])

(defn burst [dir-fn state-fn {:keys [states pos dir steps infected bursts]}]
  (let [state (get states pos \.)
        dir ((dir-fn state) dir)
        state' (state-fn state)]
    {:dir dir
     :states (assoc! states pos state')
     :pos (move dir pos)
     :bursts (inc bursts)
     :infected (cond-> infected (= \# state') inc)}))

(defn center [size]
  (let [c (/ (dec size) 2)]
    [c c]))

(defn draw [states]
  (let [[min-x max-x min-y max-y]
        (reduce (fn [[min-x max-x min-y max-y] [y x]]
                  [(min x min-x) (max x max-x)
                   (min y min-y) (max y max-y)])
                [0 0 0 0]
                (keys states))]
    (for [y (range min-y (inc max-y))]
      (->> (range min-x (inc max-x))
           (map (fn [x] (get states [y x] \.)))
           (apply str)))))

(defn solve [dir-fn state-fn input steps]
  (let [[size states] (parse input)]
    (-> (iterate (partial burst dir-fn state-fn)
                 {:states (transient states)
                  :pos (center size)
                  :dir [-1 0]
                  :bursts 0
                  :infected 0})
        (nth steps)
        (update :states (comp draw persistent!)))))

(def solve1 (partial solve
                     {\. turn-left
                      \# turn-right}
                     {\. \# \# \.}))

(def solve2 (partial solve
                     {\. turn-left
                      \W identity
                      \# turn-right
                      \F turn-around}
                     {\. \W \W \# \# \F \F \.}))

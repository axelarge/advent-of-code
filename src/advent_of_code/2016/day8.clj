(ns advent-of-code.2016.day8
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2016 8))

(defn make-screen [w h]
  (->> (repeat w ".")
       vec
       (repeat h)
       vec))

(defn draw [screen]
  (->> screen
       (map (partial apply str))
       (str/join "\n")))

(defn rect [screen w h]
  (->> (range h)
       (reduce
         (fn [screen y]
           (update screen y (fn [row]
                              (concat
                                (repeat w "#")
                                (drop w row)))))
         screen)))

(defn rotate [row n]
  (let [size (count row)]
    (->> row
         cycle
         (drop (- size n))
         (take size)
         vec)))

(defn rotate-row [screen i n]
  (update screen i rotate n))

(defn rotate-column [screen i n]
  (-> screen
      transpose
      (rotate-row i n)
      transpose))

(defn parse-line [line]
  (condp re-find line
    #"rect (\d+)x(\d+)"
    :>> (fn [[_ w h]]
          [rect (parse-int w) (parse-int h)])
    #"rotate (?:row|column) ([xy])=(\d+) by (\d+)"
    :>> (fn [[_ xy i n]]
          [(if (= xy "y") rotate-row rotate-column)
           (parse-int i)
           (parse-int n)])))

(defn apply-command [screen [f & args]]
  (apply f screen args))

(defn lit-count [screen]
  (->> screen
       (apply concat)
       (keep #{"#"})
       count))

(defn display [screen input]
  (->> input
       str/split-lines
       (map parse-line)
       (reduce apply-command screen)))

(defn solve1
  ([input] (solve1 (make-screen 50 6) input))
  ([screen input]
   (lit-count (display screen input))))

(defn solve2
  ([input] (solve2 (make-screen 50 6) input))
  ([screen input]
   (-> (display screen input)
       draw
       (str/replace "." " "))))

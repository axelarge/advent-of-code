(ns advent-of-code.2019.day08
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2019 8))

(def width 25)
(def height 6)

(defn layers [w h input]
  (partition (* w h) input))

(defn n-digits [digit layer]
  (count-where #(= digit %) layer))

(defn merge-pixels [a b]
  (if (= a \2)
    b
    a))

(defn merge-layers [a b]
  (map merge-pixels a b))

(defn flatten-image [layers]
  (reduce merge-layers layers))

(defn display [w h layer]
  (->> layer
       (map {\0 "." \1 "#" \2 "_"})
       (partition w)
       (map #(apply str %))
       (str/join "\n")))

(defn solve1 [input]
  (let [layer (->> input
                   (layers width height)
                   (apply min-key (partial n-digits \0)))]
    (* (n-digits \1 layer)
       (n-digits \2 layer))))

(defn solve2
  ([input] (solve2 input width height))
  ([input w h]
   (->> input
        (layers w h)
        (flatten-image)
        (display w h))))

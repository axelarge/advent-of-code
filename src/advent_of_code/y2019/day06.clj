(ns advent-of-code.y2019.day06
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2019 6))

(defn parse-line [line]
  (let [[a b] (str/split line #"\)")]
    [b a]))

(defn parse [input]
  (->> (str/split-lines input)
       (map parse-line)))

(defn depth [orbits depth o]
  (if-let [parent (get orbits o)]
    (+ (depth orbits depth parent) 1)
    0))

(defn total [parents]
  (let [depth (memoize depth)]
    (->> (keys parents)
         (map #(depth parents depth %))
         (apply +))))

(defn build-parents [pairs]
  (into {} pairs))

(defn build-direct-children [pairs]
  (->> (map reverse pairs)
       (group-map first second #{})))

(defn add-deep-children [direct-children deep o]
  (let [children (get direct-children o)
        deep (reduce (partial add-deep-children direct-children)
                     deep
                     children)
        descendants (set (into children (mapcat deep) children))]
    (assoc deep o descendants)))

(defn build-deep-children [direct-children]
  (add-deep-children direct-children {} "COM"))

(defn find-path [{:keys [parents direct-children deep-children] :as args} from to]
  (cond
    #_when (get-in direct-children [from to])
    []
    #_when (get-in deep-children [from to])
    (let [child (->> (get direct-children from)
                     (first-where #(get-in deep-children [% to])))]
      (into [child] (find-path args child to)))
    :else
    (let [parent (get parents from)]
      (into [parent] (find-path args parent to)))))

(defn solve-path [pairs]
  (let [parents (build-parents pairs)
        direct-children (build-direct-children pairs)
        deep-children (build-deep-children direct-children)]
       (find-path {:parents parents
                   :direct-children direct-children
                   :deep-children deep-children}
                  "YOU"
                  "SAN")))

(defn solve1 [input]
  (let [parents (into {} (parse input))]
    (total parents)))

(defn solve2 [input]
  (dec (count (solve-path (parse input)))))

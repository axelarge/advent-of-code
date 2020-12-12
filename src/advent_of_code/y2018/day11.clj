(ns advent-of-code.y2018.day11
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input 9424)

(def grid-size 300)

(defn power-level [serial x y]
  (let [rack-id (+ x 10)]
    (-> (* rack-id y)
        (+ serial)
        (* rack-id)
        (quot 100)
        (rem 10)
        (- 5))))

(defn to-key [x y]
  (+ (* 1000 y) x))

(defn g [from x y]
  (get from (to-key x y) 0))

(defn power-grid [serial]
  (into {}
        (for [x (range grid-size)
              y (range grid-size)]
          {(to-key x y) (power-level serial x y)})))

(defn lookup-grid [serial]
  (->> (for [y (range (inc grid-size))
             x (range (inc grid-size))]
         [x y])
       (reduce (fn [lookup [x y]]
                 (assoc lookup
                   (to-key x y) (+ (power-level serial x y)
                                   (g lookup (dec x) y)
                                   (g lookup x (dec y))
                                   (- (g lookup (dec x) (dec y))))))
               {})))

(defn square-power [lookup size x y]
  (let [x (dec x)
        y (dec y)]
    (- (g lookup (+ x size) (+ y size))
       (g lookup x (+ y size))
       (g lookup (+ x size) y)
       (- (g lookup x y)))))

(defn max-grid-power [lookup size]
  (->> (for [x (range (inc (- grid-size size)))
             y (range (inc (- grid-size size)))]
         {:pos [x y]
          :power (square-power lookup size x y)
          :size size})
       (apply max-key :power)))

(defn solve1 [input]
  (-> input
      lookup-grid
      (max-grid-power 3)
      :pos
      (->> (str/join ","))))

(defn solve2 [input]
  (let [{:keys [pos size]}
        (->> (range 1 (inc grid-size))
             (pmap (partial max-grid-power (lookup-grid input)))
             (apply max-key :power))]
    (str/join "," (conj pos size))))


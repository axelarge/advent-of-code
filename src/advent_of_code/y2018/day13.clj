(ns advent-of-code.y2018.day13
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))


(def test-input (get-input 2018 13 "test"))
(def test-input-2 (get-input 2018 13 "test" 2))
(def input (get-input 2018 13))

(defn parse-line [y line]
  (->> line
       (keep-indexed
         (fn [x c]
           (case c
             (\/ \\ \+) {:tracks {[y x] c}}
             (\> \v \< \^) {:carts {[y x] {:pos [y x] :dir c :turn :L}}
                            :tracks {[y x] (case c (\v \^) \| \-)}}
             (\| \-) {:tracks {[y x] c}}
             nil)))
       (apply merge-with merge)))

(defn parse [input]
  (->> input
       str/split-lines
       (map-indexed parse-line)
       (apply merge-with merge)))

(defn move [{:keys [pos dir] :as cart}]
  (let [[y x] pos]
    (assoc cart :pos (case dir
                       \^ [(dec y) x]
                       \> [y (inc x)]
                       \v [(inc y) x]
                       \< [y (dec x)]))))

(defn intersection [{:keys [dir turn] :as cart}]
  (assoc cart
    :dir (if (= :S turn)
           dir
           (case [dir turn]
             ([\^ :L] [\v :R]) \<
             ([\^ :R] [\v :L]) \>
             ([\> :L] [\< :R]) \^
             ([\> :R] [\< :L]) \v))
    :turn (case turn
            :L :S
            :S :R
            :R :L)))

(defn corner [{:keys [dir] :as cart} track]
  (assoc cart :dir (case [dir track]
                     ([\^ \/] [\v \\]) \>
                     ([\v \/] [\^ \\]) \<
                     ([\> \/] [\< \\]) \^
                     ([\< \/] [\> \\]) \v)))

(defn step-cart [cart tracks]
  (let [cart (move cart)
        track (get tracks (:pos cart))]
    (case track
      \+ (intersection cart)
      (\/ \\) (corner cart track)
      cart)))

(defn tick [{:keys [tracks carts throw?] :as state}]
  (assoc state
    :carts
    (reduce (fn [carts [old-pos cart]]
              (if-not (get carts old-pos) ; Removed in collision
                carts
                (let [{:keys [pos] :as cart} (step-cart cart tracks)]
                  (if (get carts pos)
                    (if throw?
                      (throw-ex-data {:pos pos})
                      (dissoc carts old-pos pos))
                    (-> carts
                        (dissoc old-pos)
                        (assoc pos cart))))))
            carts
            (sort-by :pos carts))))

(defn draw [{:keys [tracks carts]}]
  (let [ps (->> (merge tracks carts) keys)
        max-y (->> ps (map first) (apply max 0) inc)
        max-x (->> ps (map second) (apply max 0) inc)]
    (str/join "\n"
      (for [y (range 0 max-y)]
        (apply str (for [x (range 0 max-x)]
                     (if-let [cart (get carts [y x])]
                       (:dir cart)
                       (get tracks [y x] \space))))))))

(defn solve1 [input]
  (->> input
       parse
       (merge {:throw? true})
       (iterate tick)
       doall
       catch-ex-data
       :pos
       reverse
       (str/join ",")))

(defn solve2 [input]
  (->> input
       parse
       (iterate tick)
       (drop-while #(-> % :carts count (> 1)))
       first
       :carts
       ffirst
       reverse
       (str/join ",")))

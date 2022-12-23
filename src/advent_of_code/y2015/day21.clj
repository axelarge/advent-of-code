(ns advent-of-code.y2015.day21
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2015 21))
(def raw-items "Weapons:    Cost  Damage  Armor\nDagger        8     4       0\nShortsword   10     5       0\nWarhammer    25     6       0\nLongsword    40     7       0\nGreataxe     74     8       0\n\nArmor:      Cost  Damage  Armor\nLeather      13     0       1\nChainmail    31     0       2\nSplintmail   53     0       3\nBandedmail   75     0       4\nPlatemail   102     0       5\n\nRings:      Cost  Damage  Armor\nDamage +1    25     1       0\nDamage +2    50     2       0\nDamage +3   100     3       0\nDefense +1   20     0       1\nDefense +2   40     0       2\nDefense +3   80     0       3")

(defn parse-item [item]
  (->> (re-seq #"(?<!\+)\d+" item)
       (map parse-int)
       (map vector [:cost :dmg :armor])
       (into {})))

(defn parse-items [raw-items]
  (->> (str/split raw-items #"\n\n")
       (map #(map parse-item (rest (str/split-lines %))))
       (map vector [:weapons :armor :rings])
       (into {})))

(def items (parse-items raw-items))

(defn parse [input]
  (->> (find-ints input)
       (map vector [:hp :dmg :armor])
       (into {})))

(defn hits-to-kill [attacker defender]
  (Math/ceil
    (/ (:hp defender)
       (max 1 (- (:dmg attacker) (:armor defender))))))

(defn win? [boss player]
  (<= (hits-to-kill player boss)
      (hits-to-kill boss player)))

(defn gear-options [{:keys [rings] :as items}]
  ;; 1 weapon
  ;; 0-1 armor
  ;; 0-2 rings
  (->> (for [weapon (:weapons items)
             armor (cons nil (:armor items))
             ring1 (cons nil rings)
             ring2 (cons nil
                         (cond-> rings
                                 ring1 (nthrest (inc (index-where #{ring1} rings)))))]
         (merge-with + {:hp 100} weapon armor ring1 ring2))
       (sort-by :cost)))

(defn solve1 [input]
  (let [boss (parse input)]
    (->> (gear-options items)
         (first-where (partial win? boss))
         :cost)))

(defn solve2 [input]
  (let [boss (parse input)]
    (->> (gear-options items)
         (reverse)
         (first-where (complement (partial win? boss)))
         :cost)))

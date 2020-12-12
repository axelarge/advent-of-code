(ns advent-of-code.y2020.day04
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2020 4))

(defn parse-passport [line]
  (->> (str/split line #"(?m)[:\s]")
       (partition 2)
       (map (fn [[k v]] [(keyword k) v]))
       (into {})))

(defn parse [input]
  (map parse-passport (str/split input #"\n\n")))

(def required-fields
  (set (keywords "byr iyr eyr hgt hcl ecl pid")))

(def eye-colors
  (set (split-ws "amb blu brn gry grn hzl oth")))

(defn valid-keys? [pw]
  (every? pw required-fields))

(defn valid-year? [s lo hi]
  (when (re-matches #"\d{4}" s)
    (<= lo (parse-int s) hi)))

(defn solve1 [input]
  (->> (parse input)
       (count-where valid-keys?)))

(defn solve2 [input]
  (->> (parse input)
       (count-where
         (fn [{:keys [byr iyr eyr hgt hcl ecl pid] :as pw}]
           (and (valid-keys? pw)
                (valid-year? byr 1920 2002)
                (valid-year? iyr 2010 2020)
                (valid-year? eyr 2020 2030)
                (when-let [[_ h unit] (re-matches #"(\d+)(cm|in)" hgt)]
                  (let [h (parse-int h)]
                    (if (= "cm" unit)
                      (<= 150 h 193)
                      (<= 59 h 76))))
                (re-matches #"#[a-f0-9]{6}" hcl)
                (eye-colors ecl)
                (re-matches #"\d{9}" pid))))))

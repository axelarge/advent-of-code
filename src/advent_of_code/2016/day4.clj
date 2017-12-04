(ns advent-of-code.2016.day4
  (:require [clojure.string :as str]
            [advent-of-code.support :refer :all]))

(def input (get-input 2016 4))

(defn parse-line [line]
  (let [[_ name sector checksum] (re-find #"^((?:[a-z]+-)+)(\d+)\[([a-z]+)\]$" line)
        groups (str/split name #"-")]
    {:name name :groups groups :sector sector :checksum checksum}))

(defn make-checksum [groups]
  (->> groups
       (apply str)
       sort
       (partition-by identity)
       (sort-by (juxt (comp - count) first))
       (map first)
       (take 5)
       (apply str)))

(defn valid? [{:keys [groups checksum]}]
  (= checksum (make-checksum groups)))

(def alphabet (vec (map char (range (int \a) (inc (int \z))))))

(defn rotate [n ch]
  (if (= ch \-)
    \space
    (get alphabet (mod (+ n (.indexOf alphabet ch)) (count alphabet)))))

(defn decrypt [name n]
  (apply str (map (partial rotate n) name)))

(defn decrypted-name [{:keys [name sector]}]
  (decrypt name (Integer/parseInt sector)))

(defn get-rooms [lines]
  (->> lines
       str/split-lines
       (map parse-line)))

(defn solve1 [lines]
  (->> lines
       get-rooms
       (filter valid?)
       (map :sector)
       (map #(Integer/parseInt %))
       (apply +)))

(defn solve2 [lines]
  (->> lines
       get-rooms
       (map #(assoc % :name (decrypted-name %)))
       (filter (comp (partial re-find #"north") :name))))

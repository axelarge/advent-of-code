(ns advent-of-code.support
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn- res [year day]
  (io/resource (str "inputs/" year "/day" day ".txt")))

(defn get-input [year day]
  (slurp (res year day)))

(defn parse-int [s]
  (Integer/parseInt s))

(defn splitter [r]
  #(str/split % r))

(def split-whitespace (splitter #"\s+"))

(defn tokenize-lines
  ([input] (tokenize-lines identity input))
  ([f input]
   (->> input
        str/split-lines
        (map (comp #(map f %)
                   #(keep not-empty %)
                   split-whitespace)))))

(defn transpose [m]
  (vec (apply map vector m)))

(defn map2 [f coll]
  (map (partial map f) coll))

(defn find-where [pred coll]
  (->> coll (filter pred) first))
(ns advent-of-code.support
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn- res [year day]
  (io/resource (format "inputs/%d/day%02d.txt" year day)))

(defn get-input [year day]
  (slurp (res year day)))

(defn parse-int [s]
  (Integer/parseInt s))

(defn abs [x]
  (if (pos? x) x (- x)))

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

(defn left-pad [s len pad]
  (str
    (apply str (take (- len (count s)) (cycle pad)))
    s))

(defn transpose [m]
  (vec (apply map vector m)))

(defn map2 [f coll]
  (map (partial map f) coll))

(defn find-where [pred coll]
  (reduce (fn [_ x] (when (pred x) (reduced x)))
          nil
          coll))

(defn index-where [pred coll]
  (if (associative? coll)
    (reduce-kv (fn [_ k v] (when (pred v) (reduced k)))
               nil
               coll)
    (loop [idx 0 coll (seq coll)]
      (when coll
        (if (pred (first coll))
          idx
          (recur (inc idx) (next coll)))))))

(defn count-where [pred coll]
  (reduce (fn [c x] (if (pred x) (inc c) c))
          0
          coll))

(defn index-by
  ([kf coll]
   (index-by kf identity coll))
  ([kf vf coll]
   (->> coll
        (map (juxt kf vf))
        (into {}))))

(defn partition-all-by [pred coll]
  (let [[gs g] (reduce (fn [[gs g] x]
                         (if (pred x)
                           [(if (= [] g)
                              gs
                              (conj gs g))
                            [x]]
                           [gs (conj g x)]))
                       [[] []]
                       coll)]
    (conj gs g)))

(defn connected-nodes
  ([neighbors from] (connected-nodes neighbors from #{}))
  ([neighbors from seen]
   (when-not (seen from)
     (reduce (fn [seen child]
               (into seen (connected-nodes neighbors child seen)))
             (conj seen from)
             (neighbors from)))))

(defn connected-nodes-fast
  ([neighbors from]
   (persistent!
     (connected-nodes-fast neighbors from (transient #{}))))
  ([neighbors from seen]
   (if (seen from)
     seen
     (reduce (fn [seen child]
               (connected-nodes-fast neighbors child seen))
             (conj! seen from)
             (neighbors from)))))

(defn group-count [nodes neighbors]
  (first
    (reduce (fn [[count visited] c]
              (if (visited c)
                [count visited]
                [(inc count) (into visited (neighbors c))]))
            [0 #{}]
            nodes)))

(defmacro catch-ex-data [& body]
  `(try
     ~@body
     (catch Exception e#
       (ex-data e#))))

(defn prime? [n]
  (->> (range 2 (inc (int (Math/ceil (Math/sqrt n)))))
       (not-any? #(zero? (rem n %)))))

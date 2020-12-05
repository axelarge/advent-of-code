(ns advent-of-code.support
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clj-http.client :as http])
  (:import (java.security MessageDigest)
           (java.time Instant ZoneId)
           (java.time.format DateTimeFormatter)))

(def token (delay (slurp ".token")))

(defn- res [year day suffix]
  (io/resource (format "inputs/%d/day%02d%s.txt" year day (str/join "-" (cons "" suffix)))))

(defn get-input [year day & suffix]
  (slurp (res year day suffix)))

(defn xor [a b]
  (not= (boolean a) (boolean b)))

(defn parse-int
  ([s] (Integer/parseInt s))
  ([s r] (Integer/parseInt s r)))

(defn find-int [s]
  (parse-int (re-find #"-?\d+" s)))

(defn find-pos-int [s]
  (parse-int (re-find #"\d+" s)))

(defn find-ints [s]
  (mapv parse-int (re-seq #"-?\d+" s)))

(defn find-pos-ints [s]
  (mapv parse-int (re-seq #"\d+" s)))

(defn parse-char-int [c]
  (- (int c) 48))

(defn keywords [s]
  (map keyword (str/split s #"\s+")))

(defn xrange [from to]
  (if (>= to from)
    (range from to)
    (range from to -1)))

(defn abs [x]
  (if (pos? x) x (- x)))

(defn bit-count [n]
  (.bitCount (BigInteger/valueOf n)))

(defn maximum
  ([a] a)
  ([a b]
   (if (pos? (compare a b)) a b))
  ([a b & rest]
   (reduce maximum (maximum a b) rest)))

(defn tokenize-lines
  ([input] (tokenize-lines identity input))
  ([f input]
   (->> input
        str/split-lines
        (map #(->> (str/split % #"\s+")
                   (keep not-empty)
                   (map f))))))

(defn left-pad [s len pad]
  (let [s (str s)]
    (str
      (apply str (take (- len (count s)) (cycle pad)))
      s)))

(defn remove-at [xs i]
  (into (subvec xs 0 i) (subvec xs (inc i))))

(defn insert-at [xs i x]
  (vec (concat (subvec xs 0 i) [x] (subvec xs i))))

(defn transpose [m]
  (vec (apply map vector m)))

(defn map2 [f coll]
  (map (partial map f) coll))

(defn find-where [pred coll]
  (reduce (fn [_ x] (when (pred x) (reduced x)))
          nil
          coll))

(defn str-index-of
  ([^String s ^String match]
   (str-index-of s match 0))
  ([^String s ^String match ^long from-idx]
   (let [i (.indexOf s match from-idx)]
     (when (not= -1 i) i))))

(defn str-indexes
  ([s from]
   (str-indexes s from 0))
  ([s from i]
   (when-let [i (str-index-of s from i)]
     (cons i (lazy-seq (str-indexes s from (inc i)))))))

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

(defn mapm
  ([vf m]
   (persistent!
     (reduce-kv (fn [m k v] (assoc! m k (vf v)))
                (transient m)
                m))))

(defn index-by
  ([kf coll]
   (index-by kf identity coll))
  ([kf vf coll]
   (->> coll
        (map (juxt kf vf))
        (into {}))))

(defn group-map [kf vf init coll]
  (persistent!
    (reduce
      (fn [ret x]
        (let [k (kf x)
              v (vf x)]
          (assoc! ret k (conj (get ret k init) v))))
      (transient {})
      coll)))

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

(defn window
  "Like (partition n 1 coll) but faster"
  ([coll]
   (window 2 coll))
  ([n coll]
   (->> (iterate rest coll)
        (take n)
        (apply map vector))))

(defn nth-iter [^long n f x]
  (if (pos? n)
    (recur (dec n) f (f x))
    x))

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

(defn md5 [^String s]
  (let [algorithm (doto (MessageDigest/getInstance "MD5") (.reset))
        raw (.digest algorithm (.getBytes s))]
    (format "%032x" (BigInteger. 1 raw))))

(defn throw-ex-data [data]
  (throw (ex-info "" data)))

(defmacro catch-ex-data [& body]
  `(try
     ~@body
     (catch Exception e#
       (ex-data e#))))

(defn prime? [n]
  (->> (range 2 (inc (int (Math/ceil (Math/sqrt n)))))
       (not-any? #(zero? (rem n %)))))


;;; LEADERBOARD


(defn format-leaderboard [data]
  (->> data
       :members
       (vals)
       (filter (comp pos? :stars))
       (sort-by (comp - :local_score))
       (map (fn [m]
              (-> m
                  (select-keys [:name :local_score :stars])
                  (assoc :completion
                         (->> (:completion_day_level m)
                              (map (fn [[day times]]
                                     [(parse-int (name day))
                                      (mapv #(some-> (get-in times [% :get_star_ts])
                                                     (parse-int)
                                                     (Instant/ofEpochSecond)
                                                     (.atZone (ZoneId/systemDefault))
                                                     (->> (.format DateTimeFormatter/ISO_DATE_TIME))
                                                     (subs 11 19))
                                            [:1 :2])]))
                              (into (sorted-map)))))))))

(defn fetch-leaderboard [year id]
  (http/get (format "https://adventofcode.com/%s/leaderboard/private/view/%s.json" year id)
            {:as :json
             :cookies {:session {:value @token}}}))

(ns advent-of-code.support
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clj-http.client :as http])
  (:import (clojure.lang PersistentQueue)
           (java.security MessageDigest)
           (java.time Instant ZoneId)
           (java.time.format DateTimeFormatter)))

(def token (delay (str/trim (slurp ".token"))))

(defn- res [year day suffix]
  (io/resource (format "inputs/%d/day%02d%s.txt" year day (str/join "-" (cons "" suffix)))))

(defn get-input [year day & suffix]
  (slurp (res year day suffix)))

(defn xor [a b]
  (not= (boolean a) (boolean b)))

(defn parse-int
  ([s] (Long/parseLong s))
  ([s r] (Long/parseLong s r)))

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

(defn split-ws [s]
  (str/split s #"\s+"))

(defn keywords [s]
  (map keyword (split-ws s)))

(defn split-lines
  ([input] (str/split-lines input))
  ([f input] (map f (str/split-lines input))))

(defn is-upper? [^String s]
  (every? #(Character/isUpperCase ^char %) s))

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

(defn left-pad [s len pad]
  (let [s (str s)]
    (str
      (apply str (take (- len (count s)) (cycle pad)))
      s)))

(defn queue [& args]
  (into PersistentQueue/EMPTY args))

(def conj-set (fnil conj #{}))

(defn remove-at [xs i]
  (into (subvec xs 0 i) (subvec xs (inc i))))

(defn insert-at [xs i x]
  (vec (concat (subvec xs 0 i) [x] (subvec xs i))))

(def indexed (partial map-indexed vector))

(defn transpose [m]
  (apply mapv vector m))

(defn juxtv
  ([fs]
   (partial juxtv fs))
  ([fs xs]
   (mapv (fn [f x] (if f (f x) x))
         fs
         xs)))

(defn map2 [f coll]
  (map (partial map f) coll))

(defn vec2+ [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

(defn vec3+ [[x1 y1 z1] [x2 y2 z2]]
  [(+ x1 x2) (+ y1 y2) (+ z1 z2)])

(defn vec+ [v1 v2]
  (case (count v1)
    2 (vec2+ v1 v2)
    3 (vec3+ v1 v2)
    (mapv + v1 v2)))

(defn subv
  ([v start]
   (subv v start (count v)))
  ([v start end]
   (let [start (cond-> start (neg? start) (+ (count v)))
         end (cond-> end (neg? end) (+ (count v)))]
     (subvec v start end))))

(defn lines->set [input]
  (set (for [[y line] (indexed (split-lines input))
             [x c] (indexed line)
             :when (= c \#)]
         [x y])))

(defn map-grid [f grid]
  (->> grid
       (map-indexed
         (fn [y row]
           (->> row
                (map-indexed (fn [x cell] (f x y cell)))
                (vec))))
       (vec)))

(defn reduce-grid [f init grid]
  (reduce-kv (fn [acc y row]
               (reduce-kv (fn [acc x v]
                            (let [acc (f acc x y v)]
                              (cond-> acc (reduced? acc) (reduced))))
                          acc
                          row))
             init
             grid))

(defn display-xy-set [grid]
  (let [[minx maxx] (apply (juxt min max) (map first grid))
        [miny maxy] (apply (juxt min max) (map second grid))]
    (->> (for [y (range miny (inc maxy))]
           (->> (for [x (range minx (inc maxx))]
                  (if (grid [x y]) "#" " "))
                (str/join "")))
         (str/join "\n"))))

(defn single [coll]
  (let [[h & t] (seq coll)]
    (when (nil? t)
      h)))

(defn intersects? [a b]
  (if (< (count a) (count b))
    (some b a)
    (some a b)))

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

(defn first-where [pred coll]
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

(defn neighbors4 [[x y]]
  [[(dec x) y] [(inc x) y] [x (dec y)] [x (inc y)]])

(defn neighbors8 [[x y]]
  (for [dx [-1 0 1]
        dy [-1 0 1]
        :when (not= dx dy 0)]
    [(+ x dx) (+ y dy)]))

(defn neighbors3d6 [[x y z]]
  (for [[dx dy dz] [[-1 0 0] [1 0 0] [0 -1 0] [0 1 0] [0 0 -1] [0 0 1]]]
    [(+ x dx) (+ y dy) (+ z dz)]))

(defn bounds3d [points]
  (let [minx (apply min (map #(nth % 0) points))
        maxx (apply max (map #(nth % 0) points))
        miny (apply min (map #(nth % 1) points))
        maxy (apply max (map #(nth % 1) points))
        minz (apply min (map #(nth % 2) points))
        maxz (apply max (map #(nth % 2) points))]
    [minx maxx miny maxy minz maxz]))

(defn bounds3d-contain? [bounds [x y z]]
  (let [[minx maxx miny maxy minz maxz] bounds]
    (and (<= minx x maxx)
         (<= miny y maxy)
         (<= minz z maxz))))

(defn dfs
  [down-f reduce-f init-pos init-state edges]
  (loop [stack (list [init-pos init-state])
         seen #{}
         res init-state]
    (if-let [[pos state] (peek stack)]
      (recur (->> (get edges pos)
                  (remove seen)
                  (map #(vector % (down-f state %)))
                  (into (pop stack)))
             (conj seen pos)
             (reduce-f res state))
      res)))

(defn dijkstra [start end? neighbors]
  (loop [queue (queue [start 0])
         seen #{}]
    (when-let [[pos n] (peek queue)]
      (cond (seen pos) (recur (pop queue) seen)
            (end? pos) n
            :else (recur (->> (neighbors pos)
                              (map #(vector % (inc n)))
                              (into (pop queue)))
                         (conj seen pos))))))

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
                                                     (Instant/ofEpochSecond)
                                                     (.atZone (ZoneId/systemDefault))
                                                     (->> (.format DateTimeFormatter/ISO_LOCAL_TIME)))
                                            [:1 :2])]))
                              (into (sorted-map)))))))))

(defn fetch-leaderboard [year id]
  (http/get (format "https://adventofcode.com/%s/leaderboard/private/view/%s.json" year id)
            {:as :json
             :cookies {:session {:value @token}}}))

(ns advent-of-code.2017.day7
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2017 7))

(defn parse-line [line]
  (let [[_ name weight top] (re-find #"([^ ]+) \((\d+)\)(?: -> ([^)]+))?" line)]
    [name (parse-int weight) (some-> top (str/split #", "))]))

(defn to-tower [lines]
  (index-by first (comp vec next) lines))

(defn parse-line' [line]
  (let [[_ name weight top] (re-find #"([^ ]+) \((\d+)\)(?: -> ([^)]+))?" line)]
    {:id       name
     :weight   (parse-int weight)
     :children (some-> top (str/split #", "))}))

(defn to-tower' [lines]
  (index-by :id lines))

; ----- Part 1 - iterative -----

(defn remove-children [tower]
  (->> tower
       (filter (fn [[k [w top]]]
                 (some #(get tower %) top)))
       (into {})))

(defn remove-dangling-refs [tower]
  (->> tower
       (map (fn [[k [w top]]]
              [k [w (filter #(get tower %) top)]]))
       (into {})))

(defn step-down [tower]
  (->> tower
       remove-children
       remove-dangling-refs))

(defn bottom [tower]
  (->> tower
       (iterate step-down)
       (drop-while #(> (count %) 1))
       first
       first
       first))

(defn remove-children' [tower]
  (->> tower
       (filter (fn [[k v]]
                 (some #(get tower %) (:children v))))
       (into {})))

(defn remove-dangling-refs' [tower]
  (->> tower
       (map (fn [[k v]]
              [k (update v :children (partial filter #(get tower %)))]))
       (into {})))

(defn step-down' [tower]
  (->> tower
       remove-children'
       remove-dangling-refs'))

(defn bottom''' [tower]
  (->> tower
       (iterate step-down')
       (drop-while #(> (count %) 1))
       first
       first
       first))

; ----- Part 1 - sets -----

(defn bottom' [tower]
  (let [children (->> tower
                      (map (comp second second))
                      (apply concat)
                      set)]
    (first (clojure.set/difference (set (keys tower)) children))))

(defn bottom'' [tower]
  (first (clojure.set/difference (set (keys tower))
                                 (set (mapcat :children (vals tower))))))

; ----- Part 2 -----

(defn should-weigh [tower tops weights]
  (let [bad-weight (some (fn [[k v]] (when (= 1 v) k))
                         (frequencies weights))
        target-weight (first (remove #{bad-weight} weights))
        bad-id (nth tops (.indexOf weights bad-weight))
        w (first (tower bad-id))]
    (+ w (- target-weight bad-weight))))

(defn find-unbalanced
  "returns combined weight or throws unbalanced one"
  [tower root]
  (let [[w tops] (get tower root)
        top-weights (map #(find-unbalanced tower %) tops)]
    (if (and (> (count tops) 2)
             (not (apply = top-weights)))
      (throw (ex-info "" {:result (should-weigh tower tops top-weights)}))
      (apply + w top-weights))))


(defn should-weigh' [tower children weights]
  (let [bad-weight (some (fn [[k v]] (when (= 1 v) k))
                         (frequencies weights))
        target-weight (first (remove #{bad-weight} weights))
        bad-id (nth children (.indexOf weights bad-weight))
        w (:weight (tower bad-id))]
    (+ w (- target-weight bad-weight))))

(defn find-unbalanced'
  "returns combined weight or throws unbalanced one"
  [tower root]
  (let [{:keys [weight children]} (get tower root)
        top-weights (map #(find-unbalanced' tower %) children)]
    (if (and (> (count children) 2)
             (not (apply = top-weights)))
      (throw (ex-info "" {:result (should-weigh' tower children top-weights)}))
      (apply + weight top-weights))))

(defn input->tower
  ([] (input->tower input))
  ([input]
   (->> input
        str/split-lines
        (map parse-line)
        to-tower)))

(defn input->tower'
  ([] (input->tower' input))
  ([input]
   (->> input
        str/split-lines
        (map parse-line')
        to-tower')))

(def solve1 (comp bottom input->tower))
(def solve1' (comp bottom' input->tower))
(def solve1'' (comp bottom'' input->tower'))
(def solve1''' (comp bottom''' input->tower'))

(defn solve2 [& args]
  (let [t (apply input->tower args)]
    (:result (catch-ex-data (find-unbalanced t (bottom' t))))))

(defn solve2' [& args]
  (let [t (apply input->tower' args)]
    (:result (catch-ex-data (find-unbalanced' t (bottom'' t))))))


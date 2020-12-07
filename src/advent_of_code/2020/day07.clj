(ns advent-of-code.2020.day07
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def input (get-input 2020 7))

(defn parse-line [line]
  (let [in (re-find #"\w+ \w+" line)
        out (->> (re-seq #"(\d+) (\w+ \w+)" line)
                 (map (fn [[_ d b]] [b (parse-int d)]))
                 (into {}))]
    [in out]))

(defn parse [input]
  (into {} (map parse-line (str/split-lines input))))

(defn invert [children]
  (->> (for [[outer inners] children
             inner (keys inners)]
         [inner outer])
       (group-map first second [])))

(defn possible-parents [parents bag]
  (->> (get parents bag)
       (mapcat #(possible-parents parents %))
       (cons bag)))

(defn bag-size [children bag]
  (->> (get children bag)
       (map (fn [[child n]]
              (* n (bag-size children child))))
       (reduce + 1)))

(defn solve1 [input]
  (let [parents (invert (parse input))]
    (count (set (next (possible-parents parents "shiny gold"))))))

(defn solve2 [input]
  (dec (bag-size (parse input) "shiny gold")))


;; The description hinted at a combinatorial explosion multiple times
;; "Apparently, nobody responsible for these regulations considered how long they would take to enforce!"
;; "be sure to count all of the bags, even if the nesting becomes topologically impractical!"
;; It turned out to be a red herring as the naive recursive version finishes in under 0.1ms after parsing
;; Largest bags in input: "wavy silver" "faded salmon" "clear beige" "striped salmon" "bright silver"


(defn bag-size-fast
  "No speed gain for shiny gold, but much faster for bigger bags"
  ([children bag]
   (bag-size-fast (memoize bag-size-fast) children bag))
  ([bag-size children bag]
   (->> (get children bag)
        (map (fn [[child-bag n]]
               (* n (bag-size bag-size children child-bag))))
        (reduce + 1))))

(defn total-sizes [children]
  (let [parents (invert children)]
    (loop [children children
           totals {}]
      (if-let [empty-bag (->> children
                              (some (fn [[k v]] (when (empty? v) k))))]
        (let [[children totals]
              (reduce (fn [[children totals] parent]
                        (let [n (get-in children [parent empty-bag])]
                          [(update children parent dissoc empty-bag)
                           (update totals parent (fnil + 0) (* n (inc (get totals empty-bag 0))))]))
                      [(dissoc children empty-bag) totals]
                      (get parents empty-bag))]
          (recur children totals))
        totals))))

#_(let [parsed (parse input)
        totals (time (total-sizes parsed))]
    (doseq [bag (cons "shiny gold" (take 5 (keys (sort-by (comp - val) totals))))]
      (println (str "\n" bag))
      (time (bag-size parsed bag))
      (time (bag-size-fast parsed bag))))

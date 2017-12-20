(ns advent-of-code.2017.day20
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input1
  (str "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>\n"
       "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>"))
(def test-input2
  (str "p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>\n"
       "p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>\n"
       "p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>\n"
       "p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>"))
(def input (get-input 2017 20))

(defn parse-line [i line]
  (let [[x y z, vx vy vz, ax ay az] (map parse-int (re-seq #"-?\d+" line))]
    {:i i :p [x y z] :v [vx vy vz] :a [ax ay az]}))

(defn parse [input]
  (->> input
       str/split-lines
       (map-indexed parse-line)))

(defn mov [t x v a]
  ; Not actually correct but good enough for part 1
  (+ x (* v t) (* a t t 0.5)))

(defn pos-at [t {[x y z] :p [vx vy vz] :v [ax ay az] :a}]
  [(mov t x vx ax)
   (mov t y vy ay)
   (mov t z vz az)])

(defn dist [[x y z]]
  (+ (abs x) (abs y) (abs z)))

(defn step [{:keys [p v a] :as particle}]
  (let [v (mapv + v a)]
    (-> particle
        (update :p (partial mapv + v))
        (assoc :v v))))

(defn remove-conflicts [ps]
  (->> ps
       (group-by :p)
       (map second)
       (remove #(> (count %) 1))
       (apply concat)))

(defn solve1 [input]
  (->> (parse input)
       (apply min-key (comp dist (partial pos-at 1000)))
       :i))

(defn solve2 [input]
  (-> (iterate #(remove-conflicts (mapv step %))
               (parse input))
      (nth 1000)
      count))

;;; ----------

(comment
  (defn q-eq2 [^long a*2 ^long b ^long c]
    (if (zero? a*2)
      (if (zero? b)
        (if (zero? c) :always #{})
        #{(/ (- c) b)})
      (let [d (- (* b b) (* 2 a*2 c))]
        (if (neg? d)
          #{}
          (let [s (Math/sqrt d)]
            (set [(/ (- s b) a*2)
                  (/ (- 0 s b) a*2)]))))))

  (defn will-collide? [pr1 pr2]
    (let [ts (->> [pr1 pr2]
                  (mapv (juxt :a :v :p))
                  (apply mapv vector)
                  (mapv (partial apply mapv -))
                  (apply mapv vector)
                  (mapv (partial apply q-eq2))
                  (reduce (fn [s x]
                            (cond
                              (= :always s) x
                              (= :always x) s
                              :else (clojure.set/intersection s x)))
                          :always))]
      (or (= :always ts)
          (not-every? neg? ts)))))

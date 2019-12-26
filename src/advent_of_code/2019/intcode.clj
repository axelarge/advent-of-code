(ns advent-of-code.2019.intcode
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(defn make-state [mem]
  {:mem mem
   :pos 0
   :output []})

(defn parse [input]
  (mapv parse-int (re-seq #"-?\d+" input)))

(defn parse-state [input]
  (make-state (parse input)))

(defn set-input [state input]
  (assoc state :input input))

(defn parse-opcode [code]
  {:opcode (rem code 100)
   :modes (->> (quot code 100)
               str
               reverse
               (map parse-char-int)
               (zipmap (range)))})

(defmulti step' (fn [state opcode modes] opcode))

(defn params [{:keys [mem pos debug]} modes x n]
  (when debug
    (println "  " (subvec mem (inc pos) (+ pos 1 x))))
  (->> (subvec mem (inc pos) (+ pos 1 x))
       (map-indexed (fn [i param]
                      (if (>= i n)
                        param
                        (case (get modes i 0)
                          1 param ; immediate mode
                          0 (get mem param))))))) ; position mode

(defn param [{:keys [mem]} modes i val]
  (let [mode (get modes i 0)]
    (case mode
      1 val ; immediate mode
      0 (get mem val)))) ; position mode

(defn set-mem [state pos val]
  (update state :mem assoc pos val))

(defmethod step' 1 [{:keys [mem] :as state} _ modes]
  (let [[a b dest] (params state modes 3 2)]
    (when (:debug state)
      (println ":" 1 a b dest))
    (-> state
        (set-mem dest (+ a b))
        (update :pos + 4))))

(defmethod step' 2 [state _ modes]
  (let [[a b dest] (params state modes 3 2)]
    (when (:debug state)
      (println ":" 2 a b dest))
    (-> state
        (set-mem dest (* a b))
        (update :pos + 4))))

(defmethod step' 3 #_input [{:keys [input] :as state} _ modes]
  (assert (some? input))
  (let [[addr] (params state modes 1 0)]
    (when (:debug state)
      (println ":" 3 addr))
    (-> state
        (set-mem addr (first input))
        (update :input next)
        (update :pos + 2))))

(defmethod step' 4 #_output [state _ modes]
  (let [[val] (params state modes 1 1)]
    (when (:debug state)
      (println ":" 4 val))
    (-> state
        (update :output conj val)
        (update :pos + 2))))

(defmethod step' 5 #_jump-if-true [state _ modes]
  (let [[jump? to] (params state modes 2 2)]
    (when (:debug state)
      (println ":" 5 jump? to))
    (if (zero? jump?)
      (update state :pos + 3)
      (assoc state :pos to))))

(defmethod step' 6 #_jump-if-false [state _ modes]
  (let [[no-jump? to] (params state modes 2 2)]
    (when (:debug state)
      (println ":" 5 no-jump? to))
    (if (zero? no-jump?)
      (assoc state :pos to)
      (update state :pos + 3))))

(defmethod step' 7 #_less-than [state _ modes]
  (let [[a b addr] (params state modes 3 2)]
    (when (:debug state)
      (println ":" 7 a b addr))
    (-> state
        (set-mem addr (if (< a b) 1 0))
        (update :pos + 4))))

(defmethod step' 8 #_equal [state _ modes]
  (let [[a b addr] (params state modes 3 2)]
    (when (:debug state)
      (println ":" 8 a b addr))
    (-> state
        (set-mem addr (if (= a b) 1 0))
        (update :pos + 4))))

(defmethod step' 99 #_halt [state _ _]
  (when (:debug state)
    (println ":" 99))
  (assoc state :halt? true))


(defn step [{:keys [mem pos] :as state}]
  (let [{:keys [opcode modes]} (parse-opcode (get mem pos))]
    (step' state opcode modes)))

(defn result [state]
  (->> (iterate step state)
       #_(take 1000)
       (find-where :halt?)))

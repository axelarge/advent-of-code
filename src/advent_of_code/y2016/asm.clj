(ns advent-of-code.y2016.asm
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def ^:dynamic *optimize?* true)

(defmacro without-optimizing [& body]
  `(binding [*optimize?* false]
     ~@body))

(defn parse-line [line]
  (edn/read-string (str "[" (str/replace line #"[a-z]+" #(str ":" %)) "]")))

(defn apply-changes [code changes]
  (reduce (fn [code [i lines]]
            (reduce (fn [code [di line]]
                      (assoc code (+ i di) line))
                    code
                    (map-indexed vector lines)))
          code
          changes))

(defn optimize-add [code]
  (->> code
       (window 3)
       (keep-indexed
         (fn [i [[a0 a1] [b0 b1] c]]
           (when (and (= :inc a0)
                      (= :dec b0)
                      (= [:jnz b1 -2] c))
             ;;(println "optimizing add at line" i)
             [i [[:add a1 b1] [:nop] [:nop]]])))
       (apply-changes code)))

(defn optimize-mul [code]
  (->> code
       (window 5)
       (keep-indexed
         (fn [i [[a0 a1 a2] b c [d0 d1] e]]
           (when (and (= :add a0)
                      (= [:nop] b c)
                      (= :dec d0)
                      (= [:jnz d1 -5] e))
             ;;(println "optimizing mul at line" i)
             [i (into [[:mul a1 a2 d1]] (repeat 4 [:nop]))])))
       (apply-changes code)))

(def optimizations (comp optimize-mul optimize-add))

(defn optimize [code]
  (cond-> code *optimize?* (optimizations)))

(defn parse [input]
  {:code (optimize (mapv parse-line (str/split-lines input)))
   :i 0})

(defn arg [state x]
  (if (number? x)
    x
    (get state x 0)))

(defn tgl-instr [[cmd :as instr]]
  (case cmd
    (:add :mul :nop) (throw (ex-info (str "Can't toggle " cmd) {}))
    :inc :dec
    :cpy :jnz
    (case (dec (count instr))
      1 :inc
      2 :cpy)))

(defn tgl [code i]
  (if-let [instr (get code i)]
    (-> code
        (assoc-in [i 0] (tgl-instr instr))
        (optimize))
    code))

(defn step [{:keys [i code] :as state}]
  (when-let [[cmd x y z] (get code i)]
    (-> (case cmd
          :cpy (assoc state y (arg state x))
          :inc (update state x (fnil inc 0))
          :dec (update state x (fnil dec 0))
          :jnz (cond-> state
                       (not= 0 (arg state x))
                       (update :i + (dec (arg state y))))
          :tgl (update state :code tgl (+ i (arg state x)))
          :add (update state x + (arg state y))
          :mul (update state x + (* (arg state y) (arg state z)))
          :nop state)
        (update :i inc))))

(defn runner [state]
  (->> (iterate step state)
       (take-while some?)))

(defn run [state]
  (->> (runner state)
       (last)
       :a))

(defn solve [input regs]
  (run (merge (parse input) regs)))

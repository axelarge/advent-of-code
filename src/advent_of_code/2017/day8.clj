(ns advent-of-code.2017.day8
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input
  (str "b inc 5 if a > 1\n"
       "a inc 1 if b < 5\n"
       "c dec -10 if a >= 1\n"
       "c inc -20 if c == 10"))

(def input (get-input 2017 8))

(defn parse-line [line]
  (let [[r op opp _ s c cp] (str/split line #" ")
        c (case c
            "!=" "not="
            "==" "="
            c)]
    {:r   r                     ; register
     :op  (if (= "inc" op) + -) ; operation
     :opp (parse-int opp)       ; operation param
     :s   s                     ; source register
     :c   (resolve (symbol c))  ; comparison
     :cp  (parse-int cp)}))     ; comparison param

(defn parse [input]
  (->> input
       str/split-lines
       (map parse-line)))

(defn step [regs {:keys [r s c cp op opp]}]
  (let [rv (get regs r 0)
        sv (get regs s 0)
        cv (c sv cp)]
    (if cv
      (let [value (op rv opp)]
        (-> regs
            (update :max-ever #(cond-> value % (max %)))
            (assoc r value)))
      regs)))

(defn solve [steps]
  (reduce step {} steps))

(defn solve1 [input]
  (-> input
      parse
      solve
      (dissoc :max-ever)
      vals
      (->> (apply max))))

(def solve2 (comp :max-ever solve parse))

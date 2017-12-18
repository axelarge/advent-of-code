(ns advent-of-code.2017.day18
  (:require [advent-of-code.support :refer :all]
            [clojure.string :as str]))

(def test-input
  (str "set a 1\n"
       "add a 2\n"
       "mul a a\n"
       "mod a 5\n"
       "snd a\n"
       "set a 0\n"
       "rcv a\n"
       "jgz a -1\n"
       "set a 1\n"
       "jgz a -2"))
(def input (get-input 2017 18))

;;; Commands

(defn resolve-param [{:keys [regs]} param]
  (if (keyword? param)
    (get regs param 0)
    param))

(defn make-op [f]
  (fn [state reg param]
    (-> state
        (update-in [:regs reg] (fnil f 0) (resolve-param state param))
        (update :idx inc))))

(def ops
  {:set (make-op (fn [_ x] x))
   :add (make-op +)
   :mul (make-op *)
   :mod (make-op rem)
   :snd (fn [state reg]
          (-> state
              (update :sounds conj (resolve-param state reg))
              (update :idx inc)))
   :rcv (fn [state reg]
          (if (zero? (resolve-param state reg))
            (update state :idx inc)
            (assoc state :result (last (:sounds state)))))
   :jgz (fn [state reg offset]
          (update state :idx + (if (pos? (resolve-param state reg))
                                 offset
                                 1)))})

;;; Parsing

(defn parse-param [param]
  (if (re-matches #"[a-z]" param)
    (keyword param)
    (parse-int param)))

(defn parse-line [line]
  (let [[command & params] (str/split line #"\s+")]
    [(get ops (keyword command))
     (mapv parse-param params)]))

(defn parse [input]
  (->> input
       str/split-lines
       (mapv parse-line)))

;;; Interpreting

(defn interpret [instructions]
  (loop [state {:regs {} :idx 0 :sounds []}]
    (let [[command args] (get instructions (:idx state))
          state (apply command state args)]
      (if (:result state)
        state
        (recur state)))))

(def solve1 (comp :result interpret parse))

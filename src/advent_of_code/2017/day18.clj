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
(def test-input2
  (str "snd 1\n"
       "snd 2\n"
       "snd p\n"
       "rcv a\n"
       "rcv b\n"
       "rcv c\n"
       "rcv d"))
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
                                 (resolve-param state offset)
                                 1)))})

(def ops2
  (assoc ops
    :snd (fn [state reg]
           (-> state
               (assoc :out (resolve-param state reg))
               (update :sent inc)
               (update :idx inc)))
    :rcv (fn [state reg]
           (if-let [x (first (:queue state))]
             (-> state
                 (assoc-in [:regs reg] x)
                 (update :queue subvec 1)
                 (update :idx inc))
             (assoc state :status :waiting)))))

;;; Parsing

(defn parse-param [param]
  (if (re-matches #"[a-z]" param)
    (keyword param)
    (parse-int param)))

(defn parse-line [line]
  (let [[command & params] (str/split line #"\s+")]
    [(keyword command)
     (mapv parse-param params)]))

(defn parse [input]
  (->> input
       str/split-lines
       (mapv parse-line)))

;;; Interpreting

(defn interpret [instructions]
  (loop [state {:regs {} :idx 0 :sounds []}]
    (let [[command args] (get instructions (:idx state))
          state (apply (get ops command) state args)]
      (if (:result state)
        state
        (recur state)))))

;;; Part 2

(defn step [instructions {:keys [idx status queue] :as state}]
  (if (or (= :done status)
          (and (= :waiting status)
               (empty? queue)))
    state
    (if-let [[command args] (get instructions idx)]
      (apply (get ops2 command) (assoc state :status :running) args)
      (assoc state :status :done))))

(defn transfer [from to]
  (if-let [x (:out from)]
    (update to :queue conj x)
    to))

(defn step-both [instructions [state0 state1]]
  (let [state0 (step instructions state0)
        state1 (step instructions (transfer state0 state1))
        state0 (transfer state1 state0)]
    [(dissoc state0 :out)
     (dissoc state1 :out)]))

(defn interpret2 [instructions]
  (loop [states [{:regs {:p 0} :queue [] :idx 0 :status :running :sent 0}
                 {:regs {:p 1} :queue [] :idx 0 :status :running :sent 0}]]
    (if (every? (comp #{:waiting :done} :status) states)
      states
      (recur (step-both instructions states)))))

(def solve1 (comp :result interpret parse))
(def solve2 (comp :sent second interpret2 parse))

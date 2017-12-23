(ns advent-of-code.2017.day23
  (:require [advent-of-code.support :refer :all]
            [advent-of-code.2017.day18 :refer [make-op resolve-param parse]]))

(def input (get-input 2017 23))

(def ops
  {:set (make-op (fn [_ x] x))
   :sub (make-op -)
   :mul (comp #(update % :mul-count (fnil inc 0))
              (make-op *))
   :jnz (fn [state reg offset]
          (update state :idx + (if (zero? (resolve-param state reg))
                                 1
                                 (resolve-param state offset))))})

(defn solve1 [input]
  (let [instructions (parse input)]
    (loop [state {:regs {} :idx 0}]
      (if-let [[command args] (get instructions (:idx state))]
        (recur (apply (get ops command) state args))
        (:mul-count state)))))

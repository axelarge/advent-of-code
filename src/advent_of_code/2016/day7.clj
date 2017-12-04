(ns advent-of-code.2016.day7
  (:require [clojure.string :as str]
            [advent-of-code.support :refer :all]))

(def input (get-input 2016 7))

(def good-pattern #"(?:^|\])[a-z]*([a-z])((?!\1)[a-z])\2\1")
(def bad-pattern #"\[[a-z]*([a-z])([a-z])\2\1")

(defn has-tls? [ip]
  (and (not (re-find bad-pattern ip))
       (re-find good-pattern ip)))

(def aba-pattern-outside #"(?:^|\])[a-z]*([a-z])((?!\1)[a-z])\1")
(def aba-pattern-inside #"\[[a-z]*([a-z])([a-z])\1")
(defn has-ssl? [ip]
  (let [outside (map rest (re-seq aba-pattern-outside ip))
        inside (set (map (comp reverse rest) (re-seq aba-pattern-inside ip)))]
    (some inside outside)))

(defn solve
  ([checker]
   (solve checker input))
  ([checker input]
   (count (filter checker (str/split-lines input)))))

(def solve1 (partial solve has-tls?))
(def solve2 (partial solve has-ssl?))

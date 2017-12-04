(ns advent-of-code.2017.day3)

(def input 325489)

(defn ring [n]
  (-> n
      Math/sqrt
      Math/ceil
      (* 0.5)
      int))

(defn ring-width [r]
  (inc (* r 2)))

(defn max-in-ring [r]
  (let [w (ring-width r)]
    (* w w)))

(defn nth-in-ring [n]
  (- n (max-in-ring (dec (ring n)))))

(defn offset [n]
  (let [r (ring n)]
    (if (zero? r)
      0
      (-> (nth-in-ring n)
          (rem (dec (ring-width r)))
          (- r)
          Math/abs))))

(defn distance [n]
  (+ (ring n)
     (offset n)))

(defn solve1
  ([] (solve1 input))
  ([input]
   (distance input)))

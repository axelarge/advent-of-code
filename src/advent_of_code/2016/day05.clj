(ns advent-of-code.2016.day05)

(defn md5 [string]
  (let [hashed
        (doto (java.security.MessageDigest/getInstance "MD5")
          (.reset)
          (.update (.getBytes string)))]
    (format "%032x" (new java.math.BigInteger 1 (.digest hashed)))))

(def pattern1 #"^00000(.)")
(defn extract-digit [hash]
  (second (re-find pattern1 hash)))

(def pattern2 #"^00000([0-7])(.)")
(defn extract-positioned-digit [hash]
  (if-let [[_ position digit] (re-find pattern2 hash)]
    {:position (Integer/parseInt position) :digit digit}))

(defn get-digit [extractor string]
  (extractor (md5 string)))

(defn next-digit [extractor string n]
  (loop [n n]
    (if-let [digit (get-digit extractor (str string n))]
      [n digit]
      (recur (inc n)))))

(defn get-digits [extractor string]
  ((fn step [n]
     (lazy-seq
       (let [[n digit] (next-digit extractor string n)]
         (cons digit (step (inc n))))))
   0))

(def test-input "abc")
(def real-input "cxdnnyjw")

(defn solve1 [string]
  (->> string
       (get-digits extract-digit)
       (take 8)
       (apply str)))

(def digits [{:position 7, :digit "c"}
             {:position 7, :digit "8"}
             {:position 0, :digit "9"}
             {:position 6, :digit "e"}
             {:position 4, :digit "2"}
             {:position 7, :digit "d"}
             {:position 7, :digit "9"}
             {:position 2, :digit "9"}
             {:position 7, :digit "a"}
             {:position 3, :digit "8"}
             {:position 6, :digit "a"}
             {:position 7, :digit "1"}
             {:position 4, :digit "e"}
             {:position 5, :digit "8"}
             {:position 4, :digit "2"}
             {:position 4, :digit "c"}
             {:position 5, :digit "6"}
             {:position 3, :digit "0"}
             {:position 4, :digit "f"}
             {:position 1, :digit "9"}
             {:position 4, :digit "f"}
             {:position 7, :digit "e"}
             {:position 7, :digit "4"}
             {:position 2, :digit "3"}
             {:position 3, :digit "4"}
             {:position 3, :digit "9"}
             {:position 2, :digit "b"}
             {:position 7, :digit "d"}
             {:position 5, :digit "c"}
             {:position 5, :digit "0"}
             {:position 5, :digit "2"}
             {:position 1, :digit "0"}
             {:position 3, :digit "0"}
             {:position 4, :digit "7"}
             {:position 4, :digit "6"}
             {:position 7, :digit "b"}
             {:position 7, :digit "7"}
             {:position 3, :digit "b"}
             {:position 5, :digit "2"}
             {:position 7, :digit "3"}])

(defn solve2 [string]
  (->> string
       (get-digits extract-positioned-digit)
       (map :digit)
       (take 8)
       (apply str)))

(defn solve3 [digits]
  (let [digit-map (reduce (fn [result {:keys [position digit]}]
                            (if (or (= (count result) 8)
                                    (get result position))
                              result
                              (assoc result position digit)))
                          {} digits)]
    (apply str (map (partial get digit-map) (range 8)))))

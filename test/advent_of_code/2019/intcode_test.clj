(ns advent-of-code.2019.intcode-test
  (:require [clojure.test :refer :all]
            [advent-of-code.2019.intcode :refer :all]
            [clojure.string :as str]))

(def eq-8-pos "3,9,8,9,10,9,4,9,99,-1,8")
(def eq-8-imm "3,3,1108,-1,8,3,4,3,99")
(def lt-8-pos "3,9,7,9,10,9,4,9,99,-1,8")
(def lt-8-imm "3,3,1107,-1,8,3,4,3,99")
(def jmp-pos "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9")
(def jmp-imm "3,3,1105,-1,9,1101,0,0,12,4,12,99,1")
(def large (str "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,\n"
                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,\n"
                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"))

(defn run-test [code input]
  (-> (parse-state code)
      (set-input input)
      (result)
      :output))

(deftest test-intcode-day5
  (is (= (:mem (step (parse-state "1002,4,3,4,33")))
         (mem-to-map [1002, 4, 3, 4, 99])))
  (is (= (run-test eq-8-pos [8]) [1]))
  (is (= (run-test eq-8-pos [9]) [0]))
  (is (= (run-test eq-8-imm [8]) [1]))
  (is (= (run-test eq-8-imm [9]) [0]))
  (is (= (run-test lt-8-pos [7]) [1]))
  (is (= (run-test lt-8-pos [8]) [0]))
  (is (= (run-test lt-8-pos [9]) [0]))
  (is (= (run-test lt-8-imm [7]) [1]))
  (is (= (run-test lt-8-imm [8]) [0]))
  (is (= (run-test lt-8-imm [9]) [0]))
  (is (= (run-test jmp-pos [0]) [0]))
  (is (= (run-test jmp-pos [13]) [1]))
  (is (= (run-test jmp-imm [0]) [0]))
  (is (= (run-test jmp-imm [13]) [1]))
  (is (= (run-test large [7]) [999]))
  (is (= (run-test large [8]) [1000]))
  (is (= (run-test large [9]) [1001])))

(deftest test-intcode-day9
  (is (= (str/join "," (run-test "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99" []))
         "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"))
  (is (= (count (str (first (run-test "1102,34915192,34915192,7,4,7,99,0" []))))
         16))
  (is (= (str (first (run-test "104,1125899906842624,99" [])))
         "1125899906842624")))


(ns clj-range.core-test
  (:require [clojure.test :refer :all])
  (:require [clj-range.core :as r] :reload))

(defmacro test-range [title fst mid1 mid2 lst]
 `(testing ~title 
  (testing "first and last"
    (is (= ~fst    (r/fst       (r/range ~fst ~lst))))
    (is (= ~lst    (r/lst       (r/range ~fst ~lst)))))

  (testing "inclusive?"
    (is (= false (r/inclusive? (r/range ~fst ~lst))))
    (is (= true  (r/inclusive? (r/range ~fst ~lst :inclusive))))
    (is (= false (r/inclusive? (r/range ~fst ~lst :exclusive)))))

  (testing "cmp"
    (defn f [x# y#] 0)
    (is (= f (r/cmp (r/range ~fst ~lst f)))))

  (testing "includes"
    (testing "exclusive"
      (is (= true  (r/includes? (r/range ~fst ~lst) ~fst)))
      (is (= true  (r/includes? (r/range ~fst ~lst) ~mid1)))
      (is (= false (r/includes? (r/range ~fst ~lst) ~lst)))

      (is (= true  (r/includes? (r/range ~fst ~lst :exclusive) ~fst)))
      (is (= true  (r/includes? (r/range ~fst ~lst :exclusive) ~mid1)))
      (is (= false (r/includes? (r/range ~fst ~lst :exclusive) ~lst))))             

    (testing "inclusive"
      (is (= true  (r/includes? (r/range ~fst ~lst :inclusive) ~fst)))
      (is (= true  (r/includes? (r/range ~fst ~lst :inclusive) ~mid1)))
      (is (= true  (r/includes? (r/range ~fst ~lst :inclusive) ~lst)))))

  (testing "overlaps?"
    (testing "exclusive"
      (is (= false  (r/overlaps? (r/range ~fst ~mid1) (r/range ~mid2 ~lst)))); ()[]
      (is (= false  (r/overlaps? (r/range ~fst ~mid1) (r/range ~mid1 ~lst)))); (|)
      (is (= true   (r/overlaps? (r/range ~fst ~lst ) (r/range ~mid1 ~mid2)))); ([])
      (is (= true   (r/overlaps? (r/range ~fst ~mid2) (r/range ~mid1 ~lst)))); ([)]
    )
    (testing "inclusive"
      (is (= false  (r/overlaps? (r/range ~fst ~mid1 :inclusive) (r/range ~mid2 ~lst)))); ()[]
      (is (= true   (r/overlaps? (r/range ~fst ~mid1 :inclusive) (r/range ~mid1 ~lst)))); (|)
      (is (= true   (r/overlaps? (r/range ~fst ~lst  :inclusive) (r/range ~mid1 ~mid2)))); ([])
      (is (= true   (r/overlaps? (r/range ~fst ~mid2 :inclusive) (r/range ~mid1 ~lst)))); ([)]
    )
  )

  ))

(deftest r 
   (test-range "integers" 0 1 5 10 )
   (test-range "strings" "A" "B" "C" "Z")
)
(run-tests)

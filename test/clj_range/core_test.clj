(ns clj-range.core-test
  (:require [clojure.test :refer :all])
  (:require [clj-range.core :as r] :reload))

(defmacro test-range-for [title fst mid1 mid2 lst]
 `(testing ~title 
  (testing "first and last"
    (is (= ~fst    (r/fst       (r/range ~fst ~lst))))
    (is (= ~lst    (r/lst       (r/range ~fst ~lst)))))

  (testing "inclusive?"
    (is (not (r/inclusive? (r/range ~fst ~lst))))
    (is      (r/inclusive? (r/range ~fst ~lst :inclusive)))
    (is (not (r/inclusive? (r/range ~fst ~lst :exclusive)))))

  (testing "cmp"
    (defn f [x# y#] 0)
    (is (= f (r/cmp (r/range ~fst ~lst f)))))

  (testing "includes"
    (testing "exclusive"
      (is      (r/includes? (r/range ~fst ~lst) ~fst))
      (is      (r/includes? (r/range ~fst ~lst) ~mid1))
      (is (not (r/includes? (r/range ~fst ~lst) ~lst)))
      (is (not (r/includes? (r/range ~mid1 ~lst) ~fst)))

      (is      (r/includes? (r/range ~fst ~lst :exclusive) ~fst))
      (is      (r/includes? (r/range ~fst ~lst :exclusive) ~mid1))
      (is (not (r/includes? (r/range ~fst ~lst :exclusive) ~lst)))
      (is (not (r/includes? (r/range ~mid1 ~lst :exclusive) ~fst))))

    (testing "inclusive"
      (is      (r/includes? (r/range ~fst ~lst :inclusive) ~fst))
      (is      (r/includes? (r/range ~fst ~lst :inclusive) ~mid1))
      (is      (r/includes? (r/range ~fst ~lst :inclusive) ~lst))
      (is (not (r/includes? (r/range ~mid1 ~lst :inclusive) ~fst)))))

  (testing "overlaps?"
    (testing "exclusive"
      (is (not  (r/overlaps? (r/range ~fst ~mid1) (r/range ~mid2 ~lst)))); ()[]
      (is (not  (r/overlaps? (r/range ~fst ~mid1) (r/range ~mid1 ~lst)))); (|)
      (is       (r/overlaps? (r/range ~fst ~lst ) (r/range ~mid1 ~mid2))); ([])
      (is       (r/overlaps? (r/range ~fst ~mid2) (r/range ~mid1 ~lst))); ([)]
    )
    (testing "inclusive"
      (is (not  (r/overlaps? (r/range ~fst ~mid1 :inclusive) (r/range ~mid2 ~lst)))); ()[]
      (is       (r/overlaps? (r/range ~fst ~mid1 :inclusive) (r/range ~mid1 ~lst))); (|)
      (is       (r/overlaps? (r/range ~fst ~lst  :inclusive) (r/range ~mid1 ~mid2))); ([])
      (is       (r/overlaps? (r/range ~fst ~mid2 :inclusive) (r/range ~mid1 ~lst))); ([)]
    )
  )

  ))

(deftest range-test 
   (test-range-for "integers" 0 1 5 10 )
   (test-range-for "strings" "A" "B" "C" "Z")
)

(deftest overlaps-for-many-ranges
   (is (not (r/overlaps? (r/range 0 10) (r/range 20 30) (r/range 40 50))))
   (is      (r/overlaps? (r/range 0 25) (r/range 20 30) (r/range 40 50)))
   (is      (r/overlaps? (r/range 0 10) (r/range  5 30) (r/range  5 50)))
   (is      (r/overlaps? (r/range 0 10) (r/range  5 30) (r/range 40 50)))
   (is      (r/overlaps? (r/range 0 10) (r/range 20 45) (r/range 40 50))))


(run-tests)

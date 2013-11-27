(ns git-statistics.aggregate-test
  (:use clojure.test
        git-statistics.aggregate))

(deftest seconds-to-month-test
  (is (= "197001" (:year-month (git-statistics.aggregate/seconds-to-month {:date 0}))))
  (is (= "200902" (:year-month (git-statistics.aggregate/seconds-to-month {:date 1234567890}))))
  (is (= "204010" (:year-month (git-statistics.aggregate/seconds-to-month {:date 2234567890})))))

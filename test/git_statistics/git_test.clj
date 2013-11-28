(ns git-statistics.git-test
  (:use clojure.test
        git-statistics.git))

(deftest get-repository-dir-test
  (is (= "/working-directory/project/" (git-statistics.git/get-repository-dir))))

(deftest get-revisions-dir-test
  (is (= "/working-directory/revisions" (git-statistics.git/get-revisions-dir))))


  

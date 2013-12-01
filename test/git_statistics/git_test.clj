(ns git-statistics.git-test
  (:use clojure.test
        git-statistics.git
        git-statistics.config))

(deftest get-repository-dir-test
  (is (= "/working-directory/project/" (git-statistics.git/get-repository-dir))))

(deftest get-revisions-dir-test
  (is (= "/working-directory/revisions" (git-statistics.git/get-revisions-dir))))

(deftest init-repository-test
  (with-redefs [clj-jgit.porcelain/git-clone-full (fn [clone-url repo-dir] {:clone-url clone-url :repo-dir repo-dir})]
        (init-repository)
        (is (= (:clone-url git-statistics.git/repository) git-statistics.config/git-clone-url))
        (is (= (:repo-dir git-statistics.git/repository) (git-statistics.git/get-repository-dir)))))


  

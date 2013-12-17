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

(deftest switch-revision-test
  (with-redefs [clj-jgit.porcelain/git-checkout (fn [repo revision] {:param1 repo :param2 revision})
                git-statistics.git/get-revision-name (constantly "revisionname")
                git-statistics.git/repository (constantly {:repo "a repository"})]
                (let [value (switch-revision {:repo "mockrepo"})]
                  (is (= (git-statistics.git/get-revision-name "test") "revisionname"))
                  (is (= (git-statistics.git/repository {:repo "a repository"} {:repository "a repository"})))
                  (is (= (clj-jgit.porcelain/git-checkout "test1" "test2") {:param1 "test1", :param2 "test2"}))
                  (is (= (:param1 value) nil)) ;; TODO: Figure out why nil?
                  (is (= (:param2 value) "revisionname"))
                  )))

(deftest get-list-of-revisions-test
  (with-redefs [clj-jgit.querying/rev-list (constantly ["item1"])]
    (is (= (git-statistics.git/get-list-of-revisions) ["item1"]))))

(deftest get-revisions-dir-test
    (is (= (git-statistics.git/get-revisions-dir) "/working-directory/revisions")))

(deftest get-revision-dir-test
  (with-redefs [git-statistics.git/get-revision-name (fn [x] x)]
    (is (= (git-statistics.git/get-revision-dir "subdir") "/working-directory/revisions/subdir/"))))
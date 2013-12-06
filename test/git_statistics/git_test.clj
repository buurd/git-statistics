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
  (with-redefs [clj-jgit.porcelain/git-checkout (fn [repo revision] {:repo repo :revision revision})
                git-statistics.git/get-revision-name (constantly "revisionname")]
                (let [value (switch-revision {:repo "mockrepo"})]
                  (is (= (git-statistics.git/get-revision-name "test") "revisionname"))
                  (is (= (git-statistics.git/repository {:repo "mockrepo"})))
                  (is (= (clj-jgit.porcelain/git-checkout "test1" "test2") {:repo "test1", :revision "test2"}))
                  (is (= (:repo value) nil))
                  (is (= (:revision value) "revisionname"))
                  )))
(ns git-statistics.core 
  (:use [clj-jgit.porcelain]
        [clj-jgit.querying]))

(def repository (:repo (git-clone-full git-clone-url (str git-checkout-directory))))

(def list-of-revisions (rev-list repository))

(defn switch-revision [revision]
  (git-checkout repository (.getName revision)))

(defn init-repoitory []
  (def repository (:repo (git-clone-full git-clone-url (str git-checkout-directory)))))
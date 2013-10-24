(ns git-statistics.git 
  (:require 
    git-statistics.config
    clj-jgit.porcelain 
    clj-jgit.querying))

(defn get-repository-dir []
  (str git-statistics.config/git-checkout-directory "project/"))

(defn get-repository []  
  (:repo (clj-jgit.porcelain/git-clone-full git-statistics.config/git-clone-url get-repository-dir)))

(defn switch-revision [revision]
  (clj-jgit.porcelain/git-checkout (get-repository) (.getName revision)))

(defn  get-list-of-revisions [] 
  (clj-jgit.querying/rev-list (get-repository)))

(defn create-revisions-dir []
  (.mkdir (java.io.File. (str git-statistics.config/git-checkout-directory "revisions"))))

(defn get-revision-dir [revision]
  (str git-statistics.config/git-checkout-directory "revisions/" (.getName revision) "/"))

(defn create-revision-dir [revision]
  (.mkdir (java.io.File. (get-revision-dir revision))))

(defn write-data-to-revision-folder [revision job-data]
  (spit (str (get-revision-dir revision) "job") {:date (.getCommitTime revision) :name (.getName revision) :data job-data}))
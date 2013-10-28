(ns git-statistics.git 
  (:require 
    git-statistics.config
    clj-jgit.porcelain 
    clj-jgit.querying))

(defn get-repository-dir []
  "build the path to where the repository is going to be stored"
  (str git-statistics.config/git-checkout-directory "project/"))

(defn init-repository []  
  "clones and sets up the repository"
  (println "get-repository")
  (clj-jgit.porcelain/git-clone-full git-statistics.config/git-clone-url (get-repository-dir)))

(defn switch-revision [revision]
  "changes the current version at the get-repository-dir"
  (clj-jgit.porcelain/git-checkout (clj-jgit.porcelain/load-repo (get-repository-dir)) (.getName revision)))

(defn  get-list-of-revisions [] 
  "get the list of revisions in the repository"
  (clj-jgit.querying/rev-list (clj-jgit.porcelain/load-repo (get-repository-dir))))

(defn create-revisions-dir []
  "creates a placeholder for the folders that contains the result from the version-jobs"
  (.mkdir (java.io.File. (str git-statistics.config/git-checkout-directory "revisions"))))

(defn get-revision-dir [revision]
  "get the path to the revision-dir for a specific revision"
  (str git-statistics.config/git-checkout-directory "revisions/" (.getName revision) "/"))

(defn create-revision-dir [revision]
  "creates a folder that contains the work for a specific revision"
  (.mkdir (java.io.File. (get-revision-dir revision))))

(defn write-data-to-revision-folder [revision job-data]
  "save the data for a specific revision and job to a file"
  (spit (str (get-revision-dir revision) "job") {:date (.getCommitTime revision) :name (.getName revision) :data job-data}))
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
  (def repository (clj-jgit.porcelain/git-clone-full git-statistics.config/git-clone-url (get-repository-dir))))


(defn switch-revision [revision]
  "changes the current version at the get-repository-dir"
  (println (.getName revision))
  (clj-jgit.porcelain/git-checkout (:repo repository) (.getName revision)))

(defn  get-list-of-revisions [] 
  "get the list of revisions in the repository"
  (clj-jgit.querying/rev-list (:repo repository))) 

(defn get-resivions-dir []
  "get the path to the revisions dir"
  (str git-statistics.config/git-checkout-directory "revisions"))

(defn create-revisions-dir []
  "creates a placeholder for the folders that contains the result from the version-jobs"
  (.mkdir (java.io.File. (get-resivions-dir))))

(defn get-revision-dir [revision]
  "get the path to the revision-dir for a specific revision"
  (str git-statistics.config/git-checkout-directory "revisions/" (.getName revision) "/"))

(defn create-revision-dir [revision]
  "creates a folder that contains the work for a specific revision"
  (.mkdir (java.io.File. (get-revision-dir revision))))

(defn write-jobdata-to-revision-folder [revision job job-data]
  "save the data for a specific revision and job to a file"
  (spit (str (get-revision-dir revision) (:name job)) 
             {:date (.getCommitTime revision) :name (.getName revision) :data job-data}))

(defn write-collected-jobdata-to-revisions-folder [job job-data]
  "save the collected data for all revisions into a single file"
  (spit (str (get-resivions-dir) "/" (:name job)) (prn-str job-data)))

(defn write-jobdata-to-aggregate-folder[job job-data]
  (spit (str git-statistics.config/git-checkout-directory "/" (:name job)) (prn-str job-data)))
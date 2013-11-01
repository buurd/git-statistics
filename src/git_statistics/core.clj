(ns git-statistics.core
  (:require 
    git-statistics.config
    git-statistics.git
    git-statistics.version
    git-statistics.aggregate))

(defn child-file-path [file child]
  "concatenates the file path with the name of the child"
  (str (.getAbsolutePath file) "/" child))

(defn delete-file-recursively [f]
  "remove the file f, if folder first remove it's contents."
  (let [file (java.io.File. f)]
    (if 
      (.isDirectory file)
      (doseq [child (.list file)]
          (do
            (delete-file-recursively (child-file-path file child))
            (.delete (java.io.File. (child-file-path file child)))))
      (.delete file))))

(defn work-on-revision [revision]
  "run all functions that should be run on a revision"
  (doseq [job git-statistics.version/version-jobs] 
    (let [job-data ((:function job) revision job)]
      (git-statistics.git/write-data-to-revision-folder revision job job-data))))

(defn work-on-all-revisions [] 
  "for each revision in the git repository run the version-jobs"
  (git-statistics.git/create-revisions-dir)
  (doseq  [revision (git-statistics.git/get-list-of-revisions)]
    (git-statistics.git/create-revision-dir revision)
    (git-statistics.git/switch-revision revision)
    (work-on-revision revision)))

(defn get-jobdata-for-revision [job file sub-dir]
  (println (str (.getAbsolutePath file) "/" sub-dir "/" (:name job)))
  (println (slurp (str (.getAbsolutePath file) "/" sub-dir "/" (:name job)))))

(defn collect-aggregates []
  "for all version-jobs, collect the data for each revision and put them in a single structure"
  (doseq [job git-statistics.version/version-jobs]
    (let [file (java.io.File. (git-statistics.git/get-resivions-dir))]
      (let [sub-dir (.list file)]
        (doall (map #(get-jobdata-for-revision job file %) sub-dir))))))
    
(defn begin
  "The starting point"
  []
    (println "begin")
      (delete-file-recursively git-statistics.config/git-checkout-directory)
      (git-statistics.git/init-repository)
      (work-on-all-revisions)
      (collect-aggregates))

(begin)
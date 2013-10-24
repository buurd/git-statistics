(ns git-statistics.core
  (:require 
    git-statistics.config
    git-statistics.git 
    git-statistics.version))

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
  (doseq [job git-statistics.version-jobs/version-jobs] 
    (let [job-data (job revision)]
      (println "do the job " (job revision))
      (println "expect the job " job-data)
      (write-data-to-revision-folder revision job-data))))

(defn work-on-all-revisions [] 
  "for each revision in the git repository run the version-jobs"
  (create-revisions-dir)
  (doseq  [revision list-of-revisions]
    (create-revision-dir revision)
    (switch-revision revision)
    (work-on-revision revision)))
    
(defn begin
  "The starting point"
  []
      (delete-file-recursively git-checkout-directory)
      (init-repoitory)
      (work-on-all-revisions)
    ;; aggregate data
    )

(begin)

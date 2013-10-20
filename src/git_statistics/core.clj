(ns git-statistics.core
  )

;; the url to the project from where to checkout the repository
(def git-clone-url "https://github.com/buurd/git-statistics.git")

;; the local filearea where we can work, eg store repository, store result-files etc.
(def git-checkout-directory "/working-directory/")

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
  (doseq [job version-jobs] 
    (println (str "working on " (.toString job)))
    (job revision)))

(defn work-on-all-revisions [] 
  "for each revision in the git repository run the version-jobs"
  (doseq  [revision list-of-revisions]
    (switch-revision revision)))
    ;;(work-on-revision revision)))
    
(defn begin
  "The starting point"
  []
      (delete-file-recursively git-checkout-directory)
      (init-repoitory)
      (work-on-all-revisions)
    ;; aggregate data
    )

(begin)
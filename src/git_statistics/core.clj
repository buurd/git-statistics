(ns git-statistics.core
  (:require clojure.contrib.java-utils))

;; a list of functions to run for each version
(def version-jobs [(fn [x] (+ 1 2)) (fn [x] (+ 2 3))])

;; a list of funtions to aggretate the results from version-jobs
(def aggregate-jobs [(fn [x] (+ 1 2)) (fn [x] (+ 2 3))])

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
  (doseq [funct version-jobs]
    (println (funct revision))))

(defn work-on-all-revisions [] 
    (doseq  [revision list-of-revisions]
        (work-on-revision revision)))

(defn begin
  "The starting point"
  []
  (
      (delete-file-recursively git-checkout-directory)
      (work-on-all-revisions)
    ;; aggregate data
    ))

(begin)
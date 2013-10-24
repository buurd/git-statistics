(ns git-statistics.version-jobs
  (:require git-statistics.git))

(defn count-files [revision] 
  (count (file-seq (java.io.File. (git-statistics.git/get-revision-dir revision)))))

;; a list of functions to run for each version
;;(def version-jobs [count-files (fn [x] (+ 2 3))])
(def version-jobs [count-files])

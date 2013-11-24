(ns git-statistics.version
  (:require git-statistics.git
            clj-jgit.porcelain 
            clj-jgit.querying))

(defn count-files [revision job] 
  (count (file-seq (java.io.File. git-statistics.config/git-checkout-directory))))

(defn commit-info [revision job]
  (let [info (clj-jgit.querying/commit-info (:repo git-statistics.git/repository) revision)]
  {:changed-files (:changed_files info)
   :author (:author info)
   :email (:email info) }))

;; a list of functions to run for each version
;;(def version-jobs [count-files (fn [x] (+ 2 3))])
(def version-jobs [{:name "count-files"
                    :function count-files} 
                   {:name "commit-info"
                    :function commit-info}])

(ns git-statistics.version
  (:require git-statistics.git))

(defn count-files [revision job] 
  (println (count (file-seq (java.io.File. git-statistics.config/git-checkout-directory))))
  (count (file-seq (java.io.File. git-statistics.config/git-checkout-directory))))

;; a list of functions to run for each version
;;(def version-jobs [count-files (fn [x] (+ 2 3))])
(def version-jobs [{:name "count-files"
                    :function count-files}])

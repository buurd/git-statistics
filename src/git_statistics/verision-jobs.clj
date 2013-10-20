(ns git-statistics.core
  (:require clojure.contrib.java-utils))

(defn count-files [revision] 
  (println (count (file-seq (java.io.File. git-checkout-directory)))))

;; a list of functions to run for each version
;;(def version-jobs [count-files (fn [x] (+ 2 3))])
(def version-jobs [count-files])

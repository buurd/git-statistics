(ns git-statistics.core
  (:require clojure.contrib.java-utils))

;; a list of funtions to aggretate the results from version-jobs
(def aggregate-jobs [(fn [x] (+ 1 2)) (fn [x] (+ 2 3))])
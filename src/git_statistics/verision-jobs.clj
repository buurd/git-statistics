(ns git-statistics.core
  (:require clojure.contrib.java-utils))

;; a list of functions to run for each version
(def version-jobs [(fn [x] (+ 1 2)) (fn [x] (+ 2 3))])
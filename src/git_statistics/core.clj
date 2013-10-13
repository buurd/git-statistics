(ns git-statistics.core)

;; a list of functions to run for each version
(def version-jobs [#(+ 1 2) #(+ 2 3)])

;; a list of funtions to aggretate the results from version-jobs
(def aggregate-jobs [#(+ 1 2) #(+ 2 3)])

;; the url to the project from where to checkout the repository
(def git-clone-url "https://github.com/buurd/git-statistics.git")

;; the local filearea where we can work, eg store repository, store result-files etc.
(def git-checkout-directory "/working-directory/")

;; the starting point of the work
(defn begin
  "The starting point"
  []
  ())

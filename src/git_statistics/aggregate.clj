(ns git-statistics.aggregate
  (:require git-statistics.git
            clj-time.coerce
            clj-time.format))

(def date-formatter (clj-time.format/formatter "yyyyMM"))

(defn seconds-to-month [item]
  (assoc item :year-month (clj-time.format/unparse date-formatter (clj-time.coerce/from-long (* (:date item) 1000)))))

(defn read-file [path]
  "Read a file and convert to clojure"
  (map read-string (read-string (slurp path))))

(defn average-count [list]
  {:year-month (first list)  :file-count (double (/ (reduce + (map :data list)) (count list)))})

(defn count-files-by-month [x] 
  "From the result of the count-file-job, aggregate the result by month"
 (let [list (read-file (str (git-statistics.git/get-revisions-dir) "/count-files"))]
   (map #(average-count % )(partition-by #(:year-month %) (sort-by :year-month (map seconds-to-month list))))))

;; a list of funtions to aggretate the results from version-jobs
(def aggregate-jobs [{:name "count-files"
                      :function count-files-by-month}])
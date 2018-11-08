(ns ui.utilities.project
  (:require [cljs.nodejs :as node]
            [ui.utilities.storage :as storage :refer [storage]]))

; A series of utilities common to projects and pages

(defn get-project-page-count [project]
  "Gets the count of pages associated with a proect"
  (let [current-pages (.get storage project)]
    (if (not= (count current-pages) 0)
    (count (js->clj (.parse js/JSON current-pages)))
    "0")))

(defn get-all-projects [project-reference]
  "Gets all the projects and resets the current atom state with the updated list"
  (let [current-projects (.get storage "projects")]
  (.log js/console current-projects)
  (if (not= (count current-projects) 0)
    (reset! project-reference (js->clj (.parse js/JSON current-projects) :keywordize-keys true)))))

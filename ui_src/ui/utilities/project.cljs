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

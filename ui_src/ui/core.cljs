(ns ui.core
  (:require [reagent.core :as reagent :refer [atom]]
            [ui.core.components.sidebar :as sidebar]
            [ui.core.components.dashboard :as dashboard]
            [ui.core.components.project :as project]
            [ui.utilities.storage :as storage :refer [storage]]
            [ui.core.components.links :as links]
            [cljs.nodejs :as node]
            [clojure.string :as string :refer [split-lines]]))

(enable-console-print!)

(defonce current-view (atom {
  :dashboard-view-active true
  :project-view-active false
  :links-view-active false}))
(defonce current-project (atom ""))
(defonce current-page (atom ""))

(let [current-projects (.get storage "projects")]
(if (not= (count current-projects) 0)
  (defonce projects (atom (js->clj (.parse js/JSON current-projects) :keywordize-keys true)))
  (defonce projects (atom ""))))


(defonce projects (atom
  (js->clj (.parse js/JSON (.get storage "projects")) :keywordize-keys true)))

(defn root-component []
  [:div.mainWrapper
   (sidebar/render)
   [:div.bodyWrapper
    (if (:dashboard-view-active @current-view)
      (dashboard/render projects current-view current-project))
    (if (:project-view-active @current-view)
      (project/render projects current-view current-project current-page))
    (if (:links-view-active @current-view)
      (links/render projects current-view current-page))]])

(reagent/render
  [root-component]
  (js/document.getElementById "app-container"))

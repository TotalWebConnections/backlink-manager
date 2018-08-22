(ns ui.core
  (:require [reagent.core :as reagent :refer [atom]]
            [ui.core.components.sidebar :as sidebar]
            [ui.core.components.dashboard :as dashboard]
            [ui.core.components.project :as project]
            [ui.core.components.links :as links]
            [cljs.nodejs :as node]
            [clojure.string :as string :refer [split-lines]]))

(def join-lines (partial string/join "\n"))

(def Store
  (node/require "electron-store"))

(def storage (new Store))


(enable-console-print!)

(defonce current-view (atom {
  :dashboard-view-active true
  :project-view-active false
  :links-view-active false}))

(defonce current-project (atom ""))

(defonce projects (atom
  (js->clj (.parse js/JSON (.get storage "projects")) :keywordize-keys true)))
    ; (.get storage "projects")))

; (.log js/console @links-view-active)


(defn root-component []
  [:div.mainWrapper
   (sidebar/render)
   [:div.bodyWrapper
    (if (:dashboard-view-active @current-view)
      (dashboard/render projects current-view current-project))
    (if (:project-view-active @current-view)
      (project/render projects current-project))
    (if (:links-view-active @current-view)
      (links/render))]])

(reagent/render
  [root-component]
  (js/document.getElementById "app-container"))

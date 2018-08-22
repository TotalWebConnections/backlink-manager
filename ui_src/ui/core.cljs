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

(defonce dashboard-view-active (atom true))
(defonce project-view-active   (atom false))
(defonce links-view-active     (atom false))

(defonce projects (atom
  (js->clj (.parse js/JSON (.get storage "test")))))
    ; (.get storage "test")))

(.log js/console projects)


(defn root-component []
  [:div.mainWrapper
   (sidebar/render)
   [:div.bodyWrapper
    (dashboard/render projects)
    (project/render)
    (links/render)]])

(reagent/render
  [root-component]
  (js/document.getElementById "app-container"))

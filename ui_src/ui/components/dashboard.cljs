(ns ui.core.components.dashboard
  (:require [cljs.nodejs :as node]
            [reagent.core :as reagent :refer [atom]]))


; TODO remove this
(def Store
  (node/require "electron-store"))

(def storage (new Store))

(defonce new-project (atom ""))

(defn change-view [current-view title current-project]
  (reset! current-view {
    :dashboard-view-active false
    :project-view-active true
    :links-view-active false})
  (reset! current-project title))

(defn add-project [projects]
  ;~/Library/App
  (let [current-projects (into [] @projects)]
    ; (.log js/console (.stringify js/JSON (current-projects)))
  (.set storage "projects" (.stringify js/JSON (clj->js (conj current-projects {:title @new-project}))))
  (reset! projects @new-project)
  (reset! new-project "")))

(defn render [projects current-view current-project]
  [:div.Dashboard
  [:p "Dashboard Text"]
  (for [item @projects]
     ^{:key (:title item)}
      [:p  {:on-click #(change-view current-view (:title item) current-project)}
           (:title item)])
  [:input {:type "text"
           :value @new-project
           :on-change #(reset! new-project (-> % .-target .-value))}]
  [:button
      {:on-click #(add-project projects)}
      "Add New Project"]])

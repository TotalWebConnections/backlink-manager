(ns ui.components.dashboard
  (:require [cljs.nodejs :as node]
    [ui.utilities.storage :as storage :refer [storage]]
    [ui.utilities.project :as project-utils]
    [reagent.core :as reagent :refer [atom]]))


(defonce new-project (atom ""))

(defn change-view [current-view title current-project]
  "Goes to the project view"
  (reset! current-view {
    :dashboard-view-active false
    :project-view-active true
    :links-view-active false})
  (reset! current-project title))

(defn add-project [projects]
  "Adds a new project to the store - saved at: ~/Library/App"
  (let [current-projects (into [] @projects)]
    ; (.log js/console (.stringify js/JSON (current-projects)))
  (.set storage "projects" (.stringify js/JSON (clj->js (conj current-projects {:title @new-project}))))
  (reset! projects @new-project)
  (reset! new-project "")))

(defn render [projects current-view current-project]
  [:div.Dashboard
    [:h1 "PROJECTS"]
    [:input {:type "text"
             :value @new-project
             :on-change #(reset! new-project (-> % .-target .-value))}]
    [:button
        {:on-click #(add-project projects)}
        "Add New Project"]
    (for [item @projects]
      ^{:key (:title item)}
      [:div.project-wrapper {:on-click #(change-view current-view (:title item) current-project)}
        [:p (:title item)]
        [:p "Pages: "(project-utils/get-project-page-count (:title item))]
        [:div.options-holder [:i.fas.fa-ellipsis-v]]])])

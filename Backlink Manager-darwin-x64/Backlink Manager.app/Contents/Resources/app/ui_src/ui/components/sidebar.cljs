(ns ui.components.sidebar
  (:require [cljs.nodejs :as node]
    [ui.utilities.storage :as storage :refer [storage]]
    [ui.utilities.project :as project-utils]
    [reagent.core :as reagent :refer [atom]]))



(defn change-view [current-view title current-project]
  "Goes to the project view"
  (reset! current-view {
    :dashboard-view-active false
    :project-view-active true
    :links-view-active false})
  (reset! current-project title))


(defn render [projects current-view current-project]
  [:div.Sidebar
  (for [item @projects]
    ^{:key (:title item)}
    [:div.project-wrapper {:on-click #(change-view current-view (:title item) current-project)}
      [:p (:title item)]])])

(ns ui.core.components.project
  (:require [cljs.nodejs :as node]
    [ui.utilities.storage :as storage :refer [storage]]
            [reagent.core :as reagent :refer [atom]]))

(defonce new-page (atom ""))

(defn change-view [current-view item current-project current-page]
  (reset! current-view {
    :dashboard-view-active false
    :project-view-active false
    :links-view-active true})
    (reset! current-page item))

(defn go-back [current-view]
  (reset! current-view {
    :dashboard-view-active true
    :project-view-active false
    :links-view-active false}))

(defn add-page [projects current-project]
    (let [current-pages (.get storage @current-project)]
    (if (not= (count current-pages) 0)
      (.set storage @current-project (.stringify js/JSON (clj->js (conj (into [] (.parse js/JSON current-pages)) @new-page))))
      (.set storage @current-project (.stringify js/JSON (clj->js [@new-page]))))))

(defn get-current-pages [current-project]
  (let [current-pages (.get storage @current-project)]
    (if (not= (count current-pages) 0)
    (do (js->clj (.parse js/JSON current-pages)))
    [])))

(defn get-page-link-count [item]
  (let [current-links (.get storage item)]
    (if (not= (count current-links) 0)
    (count (clj->js (into [] (.parse js/JSON current-links))))
    "0")))

(defn render [projects current-view current-project current-page]
  [:div.Project
    [:p  {:on-click #(go-back current-view)}
       "Go Back"]
    [:input {:type "text"
             :value @new-page
             :on-change #(reset! new-page (-> % .-target .-value))}]
    [:button
      {:on-click #(add-page @projects current-project)}
      "Add New Project"]
    (for [item @projects]
        (if (= (:title item) @current-project)
          (do ^{:key (:title item)}
              [:h3  "Project: "(:title item)])))
    (for [item (get-current-pages current-project)]
        (do
          [:div
            ^{:key item}
            [:p {:on-click #(change-view current-view item current-project current-page)}
              item " - Link Count: " (get-page-link-count item)]]))])

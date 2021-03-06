(ns ui.components.links
  (:require [cljs.nodejs :as node]
            [ui.utilities.storage :as storage :refer [storage]]
            [reagent.core :as reagent :refer [atom]]))


(defonce new-link (atom ""))

(defn add-link [current-page current-view]
  "Adds a new link to the current page"
  (let [current-links (.get storage @current-page)]
  (.log js/console current-links)
  (if (not= (count current-links) 0)
    (.set storage @current-page (.stringify js/JSON (clj->js (conj (into [] (.parse js/JSON current-links)) @new-link))))
    (.set storage @current-page (.stringify js/JSON (clj->js [@new-link])))))
    (reset! current-view {
      :dashboard-view-active false
      :project-view-active false
      :links-view-active true})) ; Dirty here too but lack a better solution, maybe extract it to a new file since it's reused

(defn get-links [current-page]
  "Gets all the links for the current page or returns an empty vector"
  (let [current-links (.get storage @current-page)]
    (if (not= (count current-links) 0)
    (clj->js (into [] (.parse js/JSON current-links)))
    [])))

(defn go-back [current-view]
  "Goes back to the project view"
  (reset! current-view {
    :dashboard-view-active false
    :project-view-active true
    :links-view-active false}))

(defn render [projects current-view current-page]
  [:div.Links
    [:p  {:on-click #(go-back current-view)}
       "Go Back"]
    [:h1 @current-page]
    [:input {:type "text"
             :value @new-link
             :on-change #(reset! new-link (-> % .-target .-value))}]
    [:button
      {:on-click #(add-link current-page current-view)}
      "Add New Link"]
    (for [link (get-links current-page)]
        [:div.links-wrapper
          [:p ^{:key item} link]])])

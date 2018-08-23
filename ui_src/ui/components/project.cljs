(ns ui.core.components.project
  (:require [cljs.nodejs :as node]
            [reagent.core :as reagent :refer [atom]]))

; TODO remove this
(def Store
  (node/require "electron-store"))

(def storage (new Store))

(defonce new-page (atom ""))

; TODO keep this for possible us later depenig on how items are saved
; (defn add-page [projects current-project]
;   (let [projectReference
;           (filter (fn [project]
;            (= (:title project) @current-project)) projects)]
;     (.log js/console (assoc (into {} projectReference) :pages [@new-page]))
;     (.set storage "stuff" (.stringify js/JSON (clj->js (assoc (into {} projectReference) :pages [@new-page]))))
;     (.log js/console @current-project))
;   )

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
    [])
  ))

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
              ; (.log js/console (js->clj (.parse js/JSON (.get storage @current-project)) ))
    (for [item (get-current-pages current-project)]
        ^{:key item}
        [:p {:on-click #(change-view current-view item current-project current-page)} item])])
; (js->clj (.parse js/JSON (.get storage @current-project)

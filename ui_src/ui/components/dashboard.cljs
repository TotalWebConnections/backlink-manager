(ns ui.core.components.dashboard
  (:require [cljs.nodejs :as node]
            [reagent.core :as reagent :refer [atom]]))


; TODO remove this
(def Store
  (node/require "electron-store"))

(def storage (new Store))

(defonce new-project (atom ""))

(defn add-project [projects]
  ;~/Library/App
  (let [current-projects (into [] @projects)]
    ; (.log js/console (.stringify js/JSON (current-projects)))
  ; (.set storage "test" (.stringify js/JSON (clj->js (assoc current-projects :title @new-project))))
  (.set storage "projects" (.stringify js/JSON (clj->js (conj current-projects {:title @new-project}))))
  (reset! projects @new-project)
  (reset! new-project "")))

(defn render [projects]
  [:div.Dashboard
  [:p "Dashboard Text"]
  (for [item @projects]
     ^{:key (:title item)} [:p (:title item)])
  [:input {:type "text"
           :value @new-project
           :on-change #(reset! new-project (-> % .-target .-value))}]
  [:button
      {:on-click #(add-project projects)}
      "Add New Project"]])

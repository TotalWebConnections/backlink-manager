(ns ui.core.components.links
  (:require [cljs.nodejs :as node]
            [reagent.core :as reagent :refer [atom]]))


; TODO remove this
(def Store
  (node/require "electron-store"))

(def storage (new Store))

(defonce new-link (atom ""))

(defn add-link [current-page]
  (let [current-links (.get storage @current-page)]
  (.log js/console current-links)
  (if (not= (count current-links) 0)
    (.set storage @current-page (.stringify js/JSON (clj->js (conj (into [] (.parse js/JSON current-links)) @new-link))))
    (.set storage @current-page (.stringify js/JSON (clj->js [@new-link])))))
  )

(defn render [projects current-view current-page]
  [:div.Links
    [:h2 @current-page]
    [:input {:type "text"
             :value @new-link
             :on-change #(reset! new-link (-> % .-target .-value))}]
    [:button
      {:on-click #(add-link current-page)}
      "Add New Project"]
    [:p "testsss"]])

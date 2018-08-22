(ns ui.core.components.project
  (:require [cljs.nodejs :as node]
            [reagent.core :as reagent :refer [atom]]))

; TODO remove this
(def Store
  (node/require "electron-store"))

(def storage (new Store))

(defonce new-page (atom ""))

; (defonce project-pages (atom ))


; TODO keep this for possible us later depenig on how items are saved
; (defn add-page [projects current-project]
;   (let [projectReference
;           (filter (fn [project]
;            (= (:title project) @current-project)) projects)]
;     (.log js/console (assoc (into {} projectReference) :pages [@new-page]))
;     (.set storage "stuff" (.stringify js/JSON (clj->js (assoc (into {} projectReference) :pages [@new-page]))))
;     (.log js/console @current-project))
;   )

(defn add-page [projects current-project]
  ; (.log js/console (js->clj (.parse js/JSON (.get storage @current-project)) :keywordize-keys true))
    (let [current-pages (js->clj (.parse js/JSON (.get storage @current-project)) :keywordize-keys true)]
    (.set storage @current-project (.stringify js/JSON (clj->js (conj current-pages @new-page))))
    )
    ; (.set storage @current-project (.stringify js/JSON (clj->js [@new-page]))))
    )
      ; (.stringify js/JSON (clj->js (conj current-projects {:title @new-project}))))
(defn render [projects current-project]
  [:div.Project
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
            (.log js/console (js->clj (.parse js/JSON (.get storage @current-project)) ))
  (for [item (js->clj (.parse js/JSON (.get storage @current-project)) :keywordize-keys true)]
      ^{:key item}
      [:p item])
            ])

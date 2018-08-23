(ns ui.utilities.storage
  (:require [cljs.nodejs :as node]
            [reagent.core :as reagent :refer [atom]]))

; Handles setting up store to save/load local data

(def Store
  (node/require "electron-store"))

(def storage (new Store))

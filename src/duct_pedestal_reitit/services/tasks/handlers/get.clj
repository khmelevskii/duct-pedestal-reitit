(ns duct-pedestal-reitit.services.tasks.handlers.get
  (:require [duct-pedestal-reitit.boundary.tasks :as boundary.tasks]))

(defn response [{:keys [db]}]
  {:status 200
   :body   (boundary.tasks/get-list db)})

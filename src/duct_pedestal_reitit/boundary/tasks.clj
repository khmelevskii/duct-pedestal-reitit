(ns duct-pedestal-reitit.boundary.tasks
  (:require [duct.database.sql]))

(defprotocol TasksDatabase
  (get-list [db]))

(extend-protocol TasksDatabase
  duct.database.sql.Boundary

  (get-list [{db :spec}]
    (println db)
    {:test :get-list}))

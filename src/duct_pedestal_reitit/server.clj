(ns duct-pedestal-reitit.server
  (:require
   [duct.core :as duct]
   [integrant.core :as ig]
   [io.pedestal.http :as http]
   [reitit.pedestal :as pedestal]))

(defmethod ig/init-key :duct-pedestal-reitit/server
  [_ {:keys [base-service
             service
             dev?]}]
  (let [config (duct/merge-configs
                base-service
                service)

        {router ::http/router
         port   ::http/port
         host   ::http/host} config]

    (println (str "\nCreating your " (when dev? "[DEV] ")
                  "server http://" (or host "localhost") ":" port))
    (cond-> config
      true (dissoc ::http/router)
      true http/default-interceptors
      true (pedestal/replace-last-interceptor
            (pedestal/routing-interceptor router))
      dev? http/dev-interceptors
      true http/create-server
      true http/start)))

(defmethod ig/halt-key! :duct-pedestal-reitit/server
  [_ server]
  (http/stop server))

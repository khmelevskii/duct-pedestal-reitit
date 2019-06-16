(ns duct-pedestal-reitit.modules.pedestal
  (:require
   [duct.core :as duct]
   [duct.core.merge :as merge]
   [integrant.core :as ig]
   [io.pedestal.http :as http]))

(defn- get-environment [config options]
  (get options
       :environment
       (:duct.core/environment config :production)))

(def prod-service
  {:env                     :prod
   ::http/resource-path     "/public"
   ::http/type              :jetty
   ::http/port              8080
   ::http/routes            []
   ::http/container-options {:h2c? true
                             :h2?  false
                             :ssl? false}})

(def dev-service
  (merge prod-service
         {:env                   :dev
          ::http/join?           false
          ::http/allowed-origins {:creds           true
                                  :allowed-origins (constantly true)}
          ::http/secure-headers  {:content-security-policy-settings {:object-src "'none'"}}}))

(def base-service
  {:production  prod-service
   :development dev-service})

(defmethod ig/init-key :duct-pedestal-reitit.modules/pedestal
  [_ options]
  (fn [config]
    (let [environment (get-environment config options)]
      (duct/merge-configs
       config
       {:duct-pedestal-reitit/server
        {:base-service (merge/displace (get base-service environment))
         :dev?         (= environment :development)}}))))

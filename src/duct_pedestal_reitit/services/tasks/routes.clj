(ns duct-pedestal-reitit.services.tasks.routes
  (:require
   [integrant.core :as ig]
   [duct-pedestal-reitit.interceptors.databases :refer [attach-db-interceptor]]
   [duct-pedestal-reitit.services.tasks.handlers.get :as handlers.get]
   [clojure.spec.alpha :as s]))

;; example of route validation
(s/def ::archived boolean?)
(s/def ::query
  (s/keys :req-un [::archived]))

(defmethod ig/init-key :duct-pedestal-reitit.services.tasks/routes
  [_ {:keys [db]}]
  ["/tasks"
   {:get {:handler handlers.get/response
          :parameters {:query ::query}
          :interceptors [(attach-db-interceptor db)]}}])

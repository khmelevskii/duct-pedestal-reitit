(ns duct-pedestal-reitit.interceptors.exception
  (:require
   [muuntaja.core :as muuntaja]
   [reitit.coercion :as coercion]
   [reitit.http.interceptors.exception :as exception]
   [clojure.spec.alpha :as s]))

(defn create-coercion-handler [status]
  (fn [exception _]
    {:status  status
     :headers {"Content-Type" "application/json; charset=utf-8"}
     :body    (muuntaja/encode
               "application/json"
               {:request-validation-problems
                (as-> exception $
                  (ex-data $)
                  (get-in $ [:problems ::s/problems])
                  (map #(select-keys %
                                     [:path
                                      :pred
                                      :val]) $))})}))

(defn exception-interceptor []
  (exception/exception-interceptor
   (merge
    exception/default-handlers
    {::coercion/request-coercion (create-coercion-handler 400)
     ::coercion/response-coercion (create-coercion-handler 500)})))

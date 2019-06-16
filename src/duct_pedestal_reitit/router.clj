(ns duct-pedestal-reitit.router
  (:require
   [integrant.core :as ig]
   [reitit.http :as http]
   [reitit.coercion.spec]
   [reitit.http.coercion :as coercion]
   [reitit.http.interceptors.parameters :as parameters]
   [muuntaja.interceptor :as muuntaja]
   [duct-pedestal-reitit.interceptors.exception :as exception]))

(def common-interceptors
  [;; query-params & form-params
   (parameters/parameters-interceptor)
   ;; content-negotiation
   (muuntaja/format-negotiate-interceptor)
   ;; handle exceptions
   (exception/exception-interceptor)
   ;; encoding response body
   (muuntaja/format-response-interceptor)
   ;; decoding request body
   (muuntaja/format-request-interceptor)
   ;; coercing response bodys
   (coercion/coerce-response-interceptor)
   ;; coercing request parameters
   (coercion/coerce-request-interceptor)])

(defmethod ig/init-key :duct-pedestal-reitit/router
  [_ {:keys [tasks]}]
  (http/router
   [tasks]
   {:data {:coercion     reitit.coercion.spec/coercion
           :interceptors common-interceptors}}))

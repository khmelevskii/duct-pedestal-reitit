(ns duct-pedestal-reitit.interceptors.databases)

(defn attach-db-interceptor [db]
  {:name ::attach-db
   :enter
   (fn [context]
     (assoc-in context [:request :db] db))})

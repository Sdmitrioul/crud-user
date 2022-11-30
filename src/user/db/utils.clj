(ns user.db.utils
  (:require [scjsv.core :refer [json-validator]]))

(defn success-result
  [status body] {
                 :status  status
                 :headers {"Content-Type" "application/json"}
                 :body    body
                 })

(defn get-id
  [req]
  (-> (get-in req [:route-params :id])
      (Integer/parseInt))
  )

(def user-validator
  (json-validator
    {
     :$schema    "http://json-schema.org/draft-04/schema#"
     :type       "object"
     :properties {
                  :full_name  {:type "string"}
                  :gender     {:type "integer"}
                  :birth_date {:type "string"}
                  :address    {:type "string"}
                  :oms_number {:type "integer"}
                  }
     :required   [:full_name :gender :birth_date :address :oms_number]
     }
    )
  )
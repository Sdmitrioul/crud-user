(ns user.handlers.utils
  (:require [clj-time.format :refer [formatter parse unparse]]
            [clj-time.jdbc]
            [clojure.data.json :as json]
            [scjsv.core :refer [json-validator]]))

(comment "RegExp to test a string for a full ISO 8601 Date")

(def user-validator
  (json-validator
    {:$schema    "https://json-schema.org/draft-04/schema#"
     :type       "object"
     :properties {:full_name  {:type "string"}
                  :gender     {:type "integer"}
                  :birth_date {:type    "string"
                               :pattern #"^\d{4}-\d\d-\d\dT\d\d:\d\d:\d\d(\.\d+)?(([+-]\d\d:\d\d)|Z)?$"
                               }
                  :address    {:type "string"}
                  :oms_number {:type "integer"}
                  }
     :required   [:full_name :gender :birth_date :address :oms_number]
     }))

(defn get-id
  [req]
  (-> (get-in req [:route-params :id])
      (Integer/parseInt))
  )

(defn get-body
  [req]
  (->
    req
    (:body)
    (slurp))
  )

(defn date-reader [key value]
  (if (= key :birth_date)
    (parse value)
    value
    )
  )

(defn date-writer
  [key value]
  (if
    (= key :birth_date)
    (->
      "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
      (formatter)
      (unparse value))
    value
    ))

(defn parse-json-user
  [user]
  (json/read-str
    user
    :key-fn keyword
    :value-fn date-reader))

(defn response
  [status body] {:status  status
                 :headers {"Content-Type" "application/json"}
                 :body    (json/write-str body :value-fn date-writer)
                 })
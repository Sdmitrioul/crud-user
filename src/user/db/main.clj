(ns user.db.main
  (:require [clojure.java.jdbc :as jdbc]
            [dotenv :refer [env]]
            [honeysql.core :as sql])
  (:import (java.time LocalDate)))

(def -db
  {
   :dbtype   "postgresql"
   :dbname   (env :DB_NAME)
   :host     (env :DB_HOST)
   :user     (env :DB_USER)
   :password (env :DB_PASS)
   }
  )

(defn get-users
  []
  (jdbc/query -db (sql/format {
                               :select [:*]
                               :from   [:users]}))
  )

(defn get-user
  [id]
  (jdbc/query
    -db
    (sql/format {
                 :select [:*]
                 :from   [:users]
                 :where  [:= :user_id id]
                 }
                )
    )
  )

(defn delete-user
  [id]
  (jdbc/delete!
    -db
    :users
    ["user_id = ?" id]
    )
  )

(defn create-user
  [user]
  (let [_ (println user)] (jdbc/insert!
    -db
    :users
    user)))
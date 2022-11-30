(ns user.handlers.main
  (:require [clojure.data.json :as json]
            [user.db.main :as db]
            [user.db.utils :refer :all]))

(defn get-all-users
  "Retrieve all users from bd!"
  [_]
  (success-result
    200
    (json/write-str {:result (-> (db/get-users))})
    )
  )

(defn get-user-by-id
  "Get user from bd by id!"
  [req]
  (let
    [id (get-id req)]
    (success-result 200 (json/write-str (-> id (db/get-user))))
    ))

(defn delete-user-by-id
  "Delete user from bd!"
  [req]
  (let [id (get-id req)]
    (success-result 201 (json/write-str (-> id (db/delete-user))))
    ))

(defn create-user
  "Create user in DB"
  [req]
  (let []
    )
  )


(ns user.handlers.main
  (:require [user.db.main :as db]
            [user.handlers.utils :refer :all]))

(defn get-all-users
  "Retrieve all users from bd!"
  [_]
  (response
    200
    {:result (db/get-users)}
    )
  )

(defn get-user-by-id
  "Get user from bd by id!"
  [req]
  (let
    [id (get-id req)]
    (response
      200
      {:result (db/get-user id)}
      )))

(defn delete-user-by-id
  "Delete user from bd!"
  [req]
  (let [id (get-id req)]
    (response
      202
      {:result (db/delete-user id)}
      )))

(defn update-user-by-id
  [req]
  (let [id (get-id req)
        body (get-body req)
        report (user-validator body)]
    (if
      (nil? report)
      (response 200 {:result (-> body (parse-json-user) (db/update-user id))})
      (response 400 {:error report}))
    )
  )

(defn create-user
  "Create user in DB!"
  [req]
  (let [body (get-body req)
        report (user-validator body)]
    (if
      (nil? report)
      (response 201 {:result (-> body (parse-json-user) (db/create-user))})
      (response 400 {:error report})
      )
    )
  )


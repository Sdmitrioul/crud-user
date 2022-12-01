(ns user.handlers.main
  (:require [clojure.data.json :as json]
            [user.db.main :as db]
            [user.handlers.utils :refer :all]))

(defn get-all-users
  "Retrieve all users from bd!"
  [_]
  (response
    200
    (json/write-str
      {:result (-> (db/get-users))}
      :value-fn date-writer)
    )
  )

(defn get-user-by-id
  "Get user from bd by id!"
  [req]
  (let
    [id (get-id req)]
    (response
      200
      (json/write-str
        (-> id (db/get-user))
        :value-fn date-writer))
    ))

(defn delete-user-by-id
  "Delete user from bd!"
  [req]
  (let [id (get-id req)]
    (response
      202
      (json/write-str (-> id (db/delete-user))))
    ))

(defn create-user
  "Create user in DB!"
  [req]
  (let [body (get-body req)
        report (user-validator body)]
    (if
      (nil? report)
      (response
        201
        (json/write-str
          (db/create-user (json/read-str
                            body
                            :key-fn keyword
                            :value-fn date-reader))
          :value-fn date-writer))
      (response 400 (json/write-str {:error report}))
      )
    )
  )


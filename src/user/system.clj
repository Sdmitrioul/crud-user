(ns user.system
  (:require [clojure.java.io :as io]
            [user.handlers.main :as handlers]
            [compojure.route :as route]
            [compojure.core :refer [GET DELETE routes]]
            [integrant.core :as ig]
            [org.httpkit.server :as server]))

(def system {:app/config  {}

             :app/handler  {}

             :app/adapter {:config  (ig/ref :app/config)
                           :handler (ig/ref :app/handler)}}
  )

(defmethod ig/init-key :app/handler [_ _]
  (routes (GET "/" [] (io/resource "public/index.html"))
          (GET "/api/users" [] handlers/get-all-users)
          (GET "/api/users/:id" [] handlers/get-user-by-id)
          (DELETE "/api/users/:id" [] handlers/delete-user-by-id)
          (route/resources "/")
          (route/not-found "This page doesn't exist!")))

(defmethod ig/init-key :app/config [_ _]
  (read-string (slurp (io/resource "config.edn"))))

(defmethod ig/init-key :app/adapter [_ {:keys [config handler]}]
  (server/run-server handler {:port (:port config)}))

(defmethod ig/halt-key! :app/adapter [_ stop] (stop))

(comment (read-string (slurp (io/resource "config.edn"))))
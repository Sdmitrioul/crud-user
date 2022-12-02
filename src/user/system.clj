(ns user.system
  (:require [clojure.java.io :as io]
            [compojure.core :refer [DELETE GET POST PUT defroutes]]
            [compojure.route :as route]
            [dotenv :refer [env]]
            [org.httpkit.server :as http]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.defaults :refer :all]
            [user.handlers.main :as handlers])
  (:gen-class))

(defonce server (atom nil))

(defroutes app-routes
           (GET "/" [] (io/resource "public/index.html"))
           (GET "/api/users" [] handlers/get-all-users)
           (GET "/api/users/:id" [] handlers/get-user-by-id)
           (GET "/api/users/search" [] handlers/search-for-user)
           (POST "/api/users" [] handlers/create-user)
           (PUT "/api/users/:id" [] handlers/update-user-by-id)
           (DELETE "/api/users/:id" [] handlers/delete-user-by-id)
           (route/resources "/")
           (route/not-found "This page doesn't exist!"))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn start-server
  [port]
  (reset!
    server
    (http/run-server
      (wrap-cors
        (wrap-defaults #'app-routes api-defaults)
        :access-control-allow-origin [#".*"]
        :access-control-allow-methods [:get :post :put :delete])
      {:port port :join? false}
      )))

(comment
  (start-server 3000)
  (stop-server))

(defn -main [& args]
  (let [port (Integer/parseInt (env :APP_PORT))]
    (start-server port)
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))
    ))
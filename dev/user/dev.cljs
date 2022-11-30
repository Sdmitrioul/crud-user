(ns ^:figwheel-hooks user.dev
  (:require [user.client.main :as main]))

(defn ^:after-load render [] (main/render))


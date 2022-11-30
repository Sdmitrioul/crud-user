(ns user.client.main
  (:require [dumdom.core :as d]))

(d/defcomponent Page [props] [:div.page [:div.surface [:div.skyline "Hello"]]])

(defn render [] (d/render (Page {}) (js/document.getElementById "main")))

(println "Hello from main!")

(println "Buy!")

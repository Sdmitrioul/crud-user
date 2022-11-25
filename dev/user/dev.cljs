(ns ^:figwheel-hooks user.dev)

(defn ^:after-load render [] (println "Hi user!"))

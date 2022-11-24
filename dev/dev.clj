(ns dev
  (:require [integrant.repl :as repl]
            [user.system :as system]))

(defn start []
  (set! *print-namespace-maps* false)
  (repl/set-prep!
    (constantly system/system))
  (repl/go))

(defn stop [] (repl/halt))

(comment (start)
         (stop)
         integrant.repl.state/system)

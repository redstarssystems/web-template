(ns {{namespace}}.web
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :as resource]
            [ring.middleware.content-type :as content]
            [ring.middleware.not-modified :as not-modified]
            [ring.middleware.params :as params]
            [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.ring.coercion :as rrc]
            [clojure.java.io :as io]
            [io.pedestal.log :as log]
            [reitit.middleware :as middleware]
            [{{namespace}}.pages.templates :as templates])
  (:import (org.eclipse.jetty.server Server)))


(defn app
  [*ctx]
  (ring/ring-handler
    (ring/router
      ["/"
       ["hello" {:get {:handler templates/hello-handler}}]
       ["debug" {:get {:handler (partial templates/debug-handler *ctx)}}]])
    (ring/routes
      (ring/create-resource-handler {:path "/"})
      (ring/create-default-handler)
      )))

(defn run-server
  "Run jetty server.
  Returns:
   * ^Server object of Jetty."
  ^Server
  [handler conf]
  (log/info :m "Starting Jetty server..." :host (:web-host conf) :port (:web-port conf))
  (jetty/run-jetty handler {:host (:web-host conf) :port (:web-port conf) :join? false}))

(defn stop-server
  "Stop jetty server.
  Returns: nil"
  [^Server server-obj]
  (log/info :m "Stopping Jetty server...")
  (.stop server-obj))




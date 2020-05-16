(ns {{namespace}}.web.router
  (:require [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]]
            [reitit.ring :as ring]
            [org.rssys.context.core :as context]
            [{{namespace}}.web.pages.monitoring :as monitoring]
            [{{namespace}}.web.middleware.security :as security]
            [{{namespace}}.web.middleware.response :as response]
            [{{namespace}}.web.pages.index :as index]))


(defn monitoring-routes
  "Build routes for /monitoring path"
  [*ctx]
  (let [web-config (-> (context/get-component *ctx :web) :config)]
    ["/monitoring" {:middleware [[wrap-params]
                                 [wrap-keyword-params]
                                 [security/admin-network-check (:admin-networks web-config)]
                                 [response/convert-response->format]]}
     ["/debug" {:get {:handler monitoring/debug-handler}}]
     ["/id" {:get {:handler (partial monitoring/id-handler *ctx)}}]
     ["/heap_dump"
      ["" {:get {:handler monitoring/heap-dump-handler}}]
      ["/download" {:get {:handler (partial monitoring/download-dump-handler *ctx)}}]]
     ["/thread_dump"
      ["" {:get {:handler monitoring/thread-dump-handler}}]
      ["/download" {:get {:handler (partial monitoring/download-dump-handler *ctx)}}]]
     ]))

(defn routes
  "Build routes for web application using given context.
  Returns: reitit/Router."
  [*ctx]
  (ring/router
    ["" {:no-doc true}
     ["/" {:get {:handler index/handler}}]
     (monitoring-routes *ctx)]))


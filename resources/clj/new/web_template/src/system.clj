(ns {{namespace}}.system
  (:require [environ.core :refer [env]]
            [org.rssys.context.core :as context]
            [io.pedestal.log :as log]
            [{{namespace}}.web.server :as server]
            [{{namespace}}.config :as config]))


(defn new-context
  "Build a new system context using variables from OS.
  Returns:
    * `atom` - a new system context as map in atom."
  [env]
  (let [*new-ctx   (atom {})
        system-map [{:id         :cfg
                     :config     (zipmap config/expected-env-list ((apply juxt config/expected-env-list) env))
                     :start-deps #{}
                     :start-fn   #(-> % config/coerce-env config/validate-env)
                     :stop-fn    #(do % nil)}

                    ;; add system components here

                    {:id         :web
                     :config     (fn [ctx] (-> ctx :context/components :cfg :state-obj))
                     :start-deps #{:cfg}
                     :start-fn   (fn [config] (server/run (server/app *new-ctx) config))
                     :stop-fn    server/stop}
                    ]
        ]
    (context/build-context *new-ctx system-map)))


(comment
  (def *ctx (new-context env))
  (context/start-all *ctx)
  (context/stop-all *ctx)
  (user/reload-code *ctx)
  (user/reload-code *ctx :web)
  ((server/app *ctx) {:request-method :get :uri "/monitoring/id" :headers {:accept "application/edn"}})
  ((server/app *ctx) {:request-method :get :uri "/monitoring/id" :headers {:accept "text/plain"}})
  ((server/app *ctx) {:request-method :get :uri "/monitoring/debug" :headers {:accept "application/json"}})
  ((server/app *ctx) {:request-method :get :uri "/monitoring/debug" :headers {:accept "application/transit+json"}})
  ((server/app *ctx) {:request-method :get :uri "/monitoring/heap_dump/download" :query-string "filename=1588888455543.hprof"})
  )


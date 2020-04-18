(ns {{namespace}}.system
  (:require [environ.core :refer [env]]
            [org.rssys.context.core :as context]
            [io.pedestal.log :as log]
            [{{namespace}}.web :as web]))

;; expected Environment variables
(def expected-env-list [:web-host :web-port])

(defn coerce-env
  "Coerce parameters from environment variables map.
  Returns:
    * map   - with coerced parameters."
  [env]
  (assoc env :web-port (Integer/parseInt (:web-port env))))

(defn new-context
  "Build a new system context using variables from OS.
  Returns:
    * `atom` - a new system context as map in atom."
  [env]
  (let [*new-ctx   (atom {})
        system-map [{:id         :cfg
                     :config     (zipmap expected-env-list ((apply juxt expected-env-list) env))
                     :start-deps []
                     :start-fn   coerce-env
                     :stop-fn    #(do % nil)}

                    ;; add your components here

                    {:id         :web
                     :config     (fn [ctx] (-> ctx :context/components :cfg :state-obj))
                     :start-deps [:cfg]
                     :start-fn   (fn [config] (web/run-server (web/app *new-ctx) config))
                     :stop-fn    web/stop-server}
                    ]
        ]
    (context/build-context *new-ctx system-map)))

(comment
  (def *ctx (new-context env))
  (context/start-all *ctx)
  @*ctx
  (context/stop-all *ctx))

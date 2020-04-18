(ns user)

;; debug print with #p
(require 'hashp.core)

;; debug fns and s-exps with dbg, dbgn, dbg-last
(use 'debux.core)

;; dynamic loading of libraries, e.g. (deps/add-lib 'philoskim/debux {:mvn/version "0.6.3"})
(require '[clj-deps.core :as deps])

;; set global dev flag, which can be checked in runtime
;; (when (resolve 'user/dev-mode) user/dev-mode)
(def dev-mode true)

(println (pr-str {:msg "development mode" :status (if (true? dev-mode) "on" "off")}) \newline)





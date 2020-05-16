(ns {{namespace}}.web.pages.index
  (:require [unifier.response :as r]
            [{{namespace}}.web.pages.templates :as templates]))

(defn handler
  "index '/' page handler"
  [req]
  (r/as-success
    (templates/main
      [:div.jumbotron.text-center
       [:h2 "webapp01 index page"]])))


(ns {{namespace}}.pages.templates
  (:require [hiccup.core :as hiccup]))

(defn main
  "main template"
  [data]
  (hiccup/html
    [:html {:lang "en"}
     [:head
      [:meta {:charset "utf-8"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1, shrink-to-fit=no"}]
      [:link {:rel "stylesheet" :href "css/bootstrap.min.css" :type "text/css"}]
      [:link {:rel "stylesheet" :href "css/app.css" :type "text/css"}]
      [:title "{{name}}"]]
     [:body
      data
      [:script {:src "js/jquery-3.4.1.slim.min.js" :type "text/javascript"}]
      [:script {:src "js/bootstrap.bundle.min.js" :type "text/javascript"}]]]))

(defn hello-handler
  "standard handler"
  [req]
  {:status  200
   :headers {"Content-Type" "text/html;charset=utf-8"}
   :body    (main [:div.container
                   [:div
                    [:h1 "Hello world!"]]])})


(defn debug-handler
  "returns request and context in a text form."
  [*ctx req]
  {:status  200
   :headers {"Content-Type" "text/plain;charset=utf-8"}
   :body    (str
              (with-out-str (clojure.pprint/pprint @*ctx))
              \newline \newline
              (with-out-str (clojure.pprint/pprint req)))})

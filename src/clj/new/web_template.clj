(ns clj.new.web-template
  (:require [clj.new.templates :refer [renderer project-data ->files raw-resourcer]]))

(defn web-template
  "entry point to run template."
  [name]
  (let [render (renderer "web-template")                    ;; for text files only
        raw-render (raw-resourcer "web-template")           ;; for binary files
        data   (project-data name)]
    (println "Generating project from application template https://github.com/redstarssystems/web-template.git")
    (->files data
      [".clj-kondo/config.edn" (render ".clj-kondo/config.edn" data)]
      ["dev/src/user.clj" (render "dev/src/user.clj" data)]
      ["resources/log4j2.xml" (render "resources/log4j2.xml" data)]
      ["resources/public/index.html" (render "resources/public/index.html" data)]
      ["resources/public/css/app.css" (render "resources/public/css/app.css" data)]
      ["resources/public/img/admins-sign.png" (raw-render "resources/public/img/admins-sign.png")]
      ["resources/public/img/stop-sign.png" (raw-render "resources/public/img/stop-sign.png")]
      ["resources/public/css/bootstrap.min.css" (raw-render "resources/public/css/bootstrap.min.css")]
      ["resources/public/js/jquery-3.4.1.slim.min.js" (raw-render "resources/public/js/jquery-3.4.1.slim.min.js")]
      ["resources/public/js/bootstrap.bundle.min.js" (raw-render "resources/public/js/bootstrap.bundle.min.js")]
      ["src/{{nested-dirs}}/core.clj" (render "src/core.clj" data)]
      ["src/{{nested-dirs}}/system.clj" (render "src/system.clj" data)]
      ["src/{{nested-dirs}}/config.clj" (render "src/config.clj" data)]
      ["src/{{nested-dirs}}/web/server.clj" (render "src/web/server.clj" data)]
      ["src/{{nested-dirs}}/web/router.clj" (render "src/web/router.clj" data)]
      ["src/{{nested-dirs}}/web/middleware/response.clj" (render "src/web/middleware/response.clj" data)]
      ["src/{{nested-dirs}}/web/middleware/security.clj" (render "src/web/middleware/security.clj" data)]
      ["src/{{nested-dirs}}/web/pages/index.clj" (render "src/web/pages/index.clj" data)]
      ["src/{{nested-dirs}}/web/pages/monitoring.clj" (render "src/web/pages/monitoring.clj" data)]
      ["src/{{nested-dirs}}/web/pages/templates.clj" (render "src/web/pages/templates.clj" data)]
      ["test/{{nested-dirs}}/core_test.clj" (render "test/core_test.clj" data)]
      ["test/{{nested-dirs}}/web/middleware/security_test.clj" (render "test/security_test.clj" data)]
      [".editorconfig" (render ".editorconfig" data)]
      [".env" (render ".env" data)]
      [".envrc" (render ".envrc" data)]
      [".gitignore" (render ".gitignore" data)]
      ["CHANGELOG.adoc" (render "CHANGELOG.adoc" data)]
      ["deps.edn" (render "deps.edn" data)]
      ["LICENSE" (raw-render "LICENSE")]
      ["Makefile" (render "Makefile" data)]
      ["pbuild.edn" (render "pbuild.edn" data)]
      ["README.adoc" (render "README.adoc" data)]
      ["tests.edn" (render "tests.edn" data)]
      )))


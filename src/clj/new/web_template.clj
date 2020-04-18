(ns clj.new.web-template
  (:require [clj.new.templates :refer [renderer project-data ->files]]))

(defn web-template
  "entry point to run template."
  [name]
  (let [render (renderer "web-template")
        data   (project-data name)]
    (println "Generating project from application template https://github.com/redstarssystems/web-template.git")
    (->files data
      [".clj-kondo/config.edn" (render ".clj-kondo/config.edn" data)]
      ["dev/src/user.clj" (render "dev/src/user.clj" data)]
      ["resources/log4j2.xml" (render "resources/log4j2.xml" data)]
      ["resources/public/index.html" (render "resources/public/index.html" data)]
      ["resources/public/css/app.css" (render "resources/public/css/app.css" data)]
      ["resources/public/img/readme.txt" (render "resources/public/img/readme.txt" data)]
      ["resources/public/css/bootstrap.min.css" (render "resources/public/css/bootstrap.min.css" data)]
      ["resources/public/js/jquery-3.4.1.slim.min.js" (render "resources/public/js/jquery-3.4.1.slim.min.js" data)]
      ["resources/public/js/bootstrap.bundle.min.js" (render "resources/public/js/bootstrap.bundle.min.js" data)]
      ["src/{{nested-dirs}}/core.clj" (render "src/core.clj" data)]
      ["src/{{nested-dirs}}/system.clj" (render "src/system.clj" data)]
      ["src/{{nested-dirs}}/web.clj" (render "src/web.clj" data)]
      ["src/{{nested-dirs}}/pages/templates.clj" (render "src/pages/templates.clj" data)]
      ["test/{{nested-dirs}}/core_test.clj" (render "test/core_test.clj" data)]
      [".editorconfig" (render ".editorconfig" data)]
      [".env" (render ".env" data)]
      [".envrc" (render ".envrc" data)]
      [".gitignore" (render ".gitignore" data)]
      ["CHANGELOG.adoc" (render "CHANGELOG.adoc" data)]
      ["deps.edn" (render "deps.edn" data)]
      ["LICENSE" (render "LICENSE" data)]
      ["Makefile" (render "Makefile" data)]
      ["pbuild.edn" (render "pbuild.edn" data)]
      ["README.adoc" (render "README.adoc" data)]
      ["tests.edn" (render "tests.edn" data)]
      )))


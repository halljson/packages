(def +lib-version+ "0.2.0")
(def +version+ (str +lib-version+ "-0"))

(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.10.0" :scope "test"]])
                  ;[cljsjs/mojs "0.2.0-0"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(task-options!
  pom {:project     'cljsjs/mojs
       :version     +version+
       :description "Mojs - a motion graphics toolbelt for the web."
       :url         "http://mojs.io/"
       :scm         {:url "https://github.com/legomushroom/mojs"}
       :license     {"BSD 3-Clause" "http://opensource.org/licenses/BSD-3-Clause"}})

(deftask package []
  (comp
   (download
    :url
    (format "https://cdn.jsdelivr.net/npm/mojs@%s/lib/mojs.js" +lib-version+))
   (sift :move {#".*mojs\.js" "cljsjs/mojs/mojs.inc.js"})
   (sift :include #{#"^cljsjs"})
   (deps-cljs :name "cljsjs.mojs")
   (validate-checksums)
   (pom)
   (jar)))

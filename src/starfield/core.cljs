(ns starfield.core
  (:require [clojure.string :as s]
            [reagent.core :as r]))

(def ^:const magic 50000)
(def stars (r/atom (->> (fn [] {:x (rand-int magic)
                                :y (rand-int magic)
                                :z (rand-int (* 4 magic))})
                        repeatedly (take 100))))

(defn move [stars]
  (for [{:keys [z] :as star} stars]
    (if (< z 0)
      {:x (rand-int magic), :y (rand-int magic), :z (* 4 magic)}
      (update-in star [:z] #(- % (/ magic 25))))))

(defonce updater (js/setInterval #(swap! stars move) 50))

(defn main-component []
  [:div
   (for [[{:keys [x y z]} i] (map vector @stars (iterate inc 0))]
     (let [scale (/ magic 5 z 100)
           size (* scale 200)
           f #(+ (* (- % (/ magic 2)) scale) (- 50 (/ size 2)))]
       [:div.star {:key (str "star-" i)
                   :style {:left (str (f x) "vw")
                           :top (str (f y) "vh")
                           :width (str size "vw")
                           :height (str size "vw")
                           :opacity size}}
        " "]))])

(defn ^:export main [el]
  (r/render-component [main-component] el))

(ns starfield.core
  (:require [clojure.string :as s]
            [reagent.core :as r]))

(def ^:const magic 50000)
(defn rand-image []
  (rand-nth ["mug.png" "lieveheers.png"]))
(defn rand-class []
  (rand-nth ["cw" "ccw" "cw-slow" "ccw-slow" "cw-fast" "ccw-fast"]))

(defn new-star []
  {:x (rand-int magic)
   :y (rand-int magic)
   :z (rand-int (* 4 magic))
   :image (rand-image)
   :class (rand-class)})

(def stars (r/atom (->> new-star repeatedly (take 100))))

(defn move [stars]
  (for [{:keys [z] :as star} stars]
    (if (< z 0)
      (assoc star :z (* 4 magic))
      (update-in star [:z] #(- % (/ magic 25))))))

(def running (r/atom true))

(defonce updater (js/setInterval #(when @running (swap! stars move)) 50))

(defn main-component []
  [:div.star-field
   {:on-click #(do (swap! running not) nil)}
   (for [[{:keys [x y z image class]} i] (map vector @stars (iterate inc 0))]
     (let [scale (/ magic 5 z 100)
           size (* scale 500)
           f #(+ (* (- % (/ magic 2)) scale) (- 50 (/ size 2)))]
       [:img.star {:key (str "star-" i)
                   :class class
                   :src image
                   :style {:left (str (f x) "vw")
                           :top (str (f y) "vh")
                           :width (str size "vw")
                           :height (str size "vw")
                           :opacity size}}]))])

(defn ^:export main [el]
  (r/render-component [main-component] el))

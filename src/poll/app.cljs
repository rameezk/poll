(ns poll.app
  (:require [reagent.dom :as rd]
            [re-frame.core :as rf]
            [poll.components :as c]))

(def v 1)

(defn- version []
  [:span {:style {:position "fixed" :right "1rem" :bottom "1rem"}}(str "v" v)])

(defn app []
  [:div.section
   {:style {:height "100vh"}}
   [c/polls]
   [c/main-controls]
   [:footer [version]]])

(defn mount-reagent []
  (rd/render app (js/document.getElementById "app")))

(defn ^:export run []
  ;; (rf/dispatch-sync [::state/initialize])
  (mount-reagent))

(defn ^:dev/after-load shaddow-start [] (mount-reagent))
(defn ^:dev/before-load shaddow-stop [])


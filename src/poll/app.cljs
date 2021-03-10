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
   [:div.container
    [:div.columns.is-centered
     [:div.column.is-half
      [:article.panel.is-primary
       [:p.panel-heading #_"What do you think the price of bananas are?"
        [:div.control
         [:input.input {:type "text" :placeholder "What is your question?"}]]]
       [:div.panel-block
        [:div.control
         [:input.input.is-expanded {:type "text" :placeholder "Choice 1"}]]]
       [:div.panel-block
        [:div.control
         [:input.input.is-expanded {:type "text" :placeholder "Choice 2"}]]]
       [:div.panel-block
        [:div.container
         [:div.columns.is-centered
          [:div.column
           [:div.buttons.is-centered
            [:button.button.is-success "Save"]
            [:button.button.is-info "Add Choice"]]]]]]]]]]
   [:div.section
    [:div.container
     [:div.columns.is-centered
      [:div.colum
       [:div.buttons.is-centered
        [:button.button.is-success "Publish"]
        [:button.button.is-info "Add Another Poll"]]]]]]
   [:footer [version]]])

(defn mount-reagent []
  (rd/render app (js/document.getElementById "app")))

(defn ^:export run []
  ;; (rf/dispatch-sync [::state/initialize])
  (mount-reagent))

(defn ^:dev/after-load shaddow-start [] (mount-reagent))
(defn ^:dev/before-load shaddow-stop [])


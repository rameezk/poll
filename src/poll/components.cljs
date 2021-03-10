(ns poll.components
  (:require [reagent.core :as r]))

(def data
  (r/atom {:polls [{:id       1
                    :question "How old are you?"
                    :choices  ["Option 1" "Option 2"]}
                   {:id       2
                    :question "How old are you?"
                    :choices  ["Option 1" "Option 2"]}]}))

(defn- add-new-poll
  "Add a new poll"
  []
  (swap!
    data
    update-in [:polls]
    conj {:id      (random-uuid)
          :choices ["Option 1" "Option 2"]}))

(defn- remove-by-id
  "Remove by id"
  [v id]
  (prn "v = " v)
  (prn "id = " id)
  (remove #(= (:id %) id) v))

(defn- delete-poll
  "Delete a poll with specified poll ID"
  [poll-id]
  (swap! data
         update-in [:polls]
         remove-by-id poll-id))

(defn main-controls
"Main controls"
[]
[:div.section
 [:div.container
  [:div.columns.is-centered
   [:div.colum
    [:div.buttons.is-centered
     [:button.button.is-success "Publish"]
     [:button.button.is-info
      {:on-click #(add-new-poll)}
      "Add Another Poll"]]]]]])

(defn- choice
"A choice"
[placeholder editable]
(if editable 
  [:div.panel-block {:key placeholder}
   [:div.control
    [:input.input.is-expanded {:type "text" :placeholder placeholder}]]]
  nil))

(defn- poll-controls
"Poll controls"
[poll-id editable]
[:div.panel-block
 [:div.container
  [:div.columns.is-centered
   [:div.column
    [:div.buttons.is-centered
     [:button.button.is-success "Save"]
     [:button.button.is-info
      {:on-click #(swap!
                    data
                    update-in
                    [:choices]
                    conj
                    "Another choice")} "Add Choice"]
     [:button.button.is-danger
      {:on-click #(delete-poll poll-id)}
      "Delete Poll"]]]]]])

(defn- poll
"A single poll"
[{:keys [id question choices]}]
[:div.section {:key id}
 [:div.container
  [:div.columns.is-centered
   [:div.column.is-half
    [:article.panel.is-primary
     [:div.panel-heading #_"What do you think the price of bananas are?"
      [:div.control
       [:input.input {:type "text" :placeholder question #_ "What is your question?"}]]]
     (map #(choice % true) choices)
     (poll-controls id true)]]]]])

(defn polls
"Container for polls"
[]
[:div.section
 (map #(poll %) (:polls @data))])


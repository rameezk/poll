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

(defn- add-new-choice-by-id
  "Add new choice by ID"
  [v id]
  (reduce (fn [acc poll]
            (if (= (:id poll) id)
              (conj acc (update-in poll [:choices] conj "Another option"))
              (conj acc poll)))
          [] v))

(defn- add-new-choice-to-poll
  "Add a new choice to a poll"
  [poll-id]
  (swap!
    data
    update-in [:polls]
    add-new-choice-by-id poll-id))

(defn- remove-by-id
  "Remove by id"
  [v id]
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
        {:on-click #(add-new-choice-to-poll poll-id)} "Add Choice"]
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


package com.example.sharingapp;

import java.util.ArrayList;

/**
 * Superclass of Item, ItemList, Contact, ContactList
 */
public class Observable {

    private ArrayList<Observer> observers = null;

    public Observable(){
        observers = new ArrayList<Observer>();
    }

    // Notify observers when need to update any changes made to model
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }
}
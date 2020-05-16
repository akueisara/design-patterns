package com.example.sharingapp;

public class ContactController {

    private Contact contact;

    public ContactController(Contact contact){
        this.contact = contact;
    }

    public String getId(){
        return contact.getId();
    }

    public void setId() {
        contact.setId();
    }

    public void setUsername(String username) {
        contact.setUsername(username);
    }

    public String getUsername() {
        return contact.getUsername();
    }

    public void setEmail(String email) {
        contact.setEmail(email);
    }

    public String getEmail() {
        return contact.getEmail();
    }

    public Contact getContact() { return this.contact; }

    public void addObserver(Observer observer) {
        contact.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        contact.removeObserver(observer);
    }
}

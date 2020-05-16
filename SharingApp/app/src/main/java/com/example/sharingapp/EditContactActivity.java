package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Editing a pre-existing contact consists of deleting the old contact and adding a new contact with the old
 * contact's id.
 * Note: You will not be able contacts which are "active" borrowers
 */
public class EditContactActivity extends AppCompatActivity implements Observer {

    private ContactList contact_list = new ContactList();
    private ContactListController contact_list_controller = new ContactListController(contact_list);

    private Contact contact;
    private ContactController contact_controller = new ContactController(contact);

    private EditText email;
    private EditText username;
    private Context context;

    private boolean on_create_update = false;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        context = getApplicationContext();
        contact_list_controller.loadContacts(context);

        Intent intent = getIntent();
        pos = intent.getIntExtra("position", 0);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);

        on_create_update = true;

        contact_list_controller.addObserver(this);
        contact_list_controller.loadContacts(context);

        on_create_update = false;
    }

    public void saveContact(View view) {

        String email_str = email.getText().toString();

        if (email_str.equals("")) {
            email.setError("Empty field!");
            return;
        }

        if (!email_str.contains("@")){
            email.setError("Must be an email address!");
            return;
        }

        String username_str = username.getText().toString();
        String id = contact_controller.getId(); // Reuse the contact id

        // Check that username is unique AND username is changed (Note: if username was not changed
        // then this should be fine, because it was already unique.)
        if (!contact_list_controller.isUsernameAvailable(username_str) && !(contact_controller.getUsername().equals(username_str))) {
            username.setError("Username already taken!");
            return;
        }

        Contact updated_contact = new Contact(username_str, email_str, id);

        // Edit item
        boolean success = contact_list_controller.editContact(contact, updated_contact, context);
        if (!success){
            return;
        }

        // End EditContactActivity
        contact_list_controller.removeObserver(this);

        finish();
    }

    public void deleteContact(View view) {

        // Delete contact
        boolean success = contact_list_controller.deleteContact(contact, context);
        if (!success){
            return;
        }

        // End EditContactActivity
        contact_list_controller.removeObserver(this);

        finish();
    }

    @Override
    public void update() {
        if (on_create_update){
            contact = contact_list_controller.getContact(pos);

            username.setText(contact_controller.getUsername());
            email.setText(contact_controller.getEmail());
        }
    }
}

package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Displays a list of all contacts
 */
public class ContactsActivity extends AppCompatActivity {

    private ContactList contact_list = new ContactList();
    private ListView my_contacts;
    private ArrayAdapter<Contact> adapter;
    private Context context;
    private ItemList item_list = new ItemList();
    private ContactList active_borrowers_list = new ContactList();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        context = getApplicationContext();
        contact_list.loadContacts(context);
        item_list.loadItems(context);

        my_contacts = (ListView) findViewById(R.id.my_contacts);
        adapter = new ContactAdapter(ContactsActivity.this, contact_list.getContacts());
        my_contacts.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // When contact is long clicked, this starts EditContactActivity
        my_contacts.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {

                Contact contact = adapter.getItem(pos);

                ArrayList<Contact> active_borrowers = item_list.getActiveBorrowers();
                active_borrowers_list.setContacts(active_borrowers);

                // Prevent contact from editing an "active" borrower.
                if (active_borrowers_list != null) {
                    if (active_borrowers_list.hasContact(contact)) {
                        CharSequence text = "Cannot edit or delete active borrower!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(context, text, duration).show();
                        return true;
                    }
                }

                contact_list.loadContacts(context); // Must load contacts again here
                int meta_pos = contact_list.getIndex(contact);

                Intent intent = new Intent(context, EditContactActivity.class);
                intent.putExtra("position", meta_pos);
                startActivity(intent);

                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        context = getApplicationContext();
        contact_list.loadContacts(context);

        my_contacts = (ListView) findViewById(R.id.my_contacts);
        adapter = new ContactAdapter(ContactsActivity.this, contact_list.getContacts());
        my_contacts.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void addContactActivity(View view){
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }
}

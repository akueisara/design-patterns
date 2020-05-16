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

/**
 * Displays a list of all contacts
 * Note: You will not be able edit/delete contacts which are "active" borrowers
 */
public class ContactsActivity extends AppCompatActivity implements Observer {

    private ContactList contact_list = new ContactList();
    private ContactListController contact_list_controller = new ContactListController(contact_list);

    private ContactList active_borrowers_list = new ContactList();
    private ContactListController active_borrowers_list_controller = new ContactListController(active_borrowers_list);

    private ItemList item_list = new ItemList();
    private ItemListController item_list_controller = new ItemListController(item_list);

    private ListView my_contacts;
    private ArrayAdapter<Contact> adapter;
    private Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        context = getApplicationContext();

        contact_list_controller.addObserver(this);
        contact_list_controller.loadContacts(context);
        item_list_controller.loadItems(context);

        // When contact is long clicked, this starts EditContactActivity
        my_contacts.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {

                Contact contact = adapter.getItem(pos);

                // Do not allow an "active" borrower to be edited
                active_borrowers_list_controller.setContacts(item_list_controller.getActiveBorrowers());
                if (active_borrowers_list_controller != null) {
                    if (active_borrowers_list_controller.hasContact(contact)) {
                        CharSequence text = "Cannot edit or delete active borrower!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(context, text, duration).show();
                        return true;
                    }
                }

                contact_list_controller.loadContacts(context); // must load contacts again here
                int meta_pos = contact_list_controller.getIndex(contact);

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
        contact_list_controller.loadContacts(context);
    }

    public void addContactActivity(View view){
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the activity is destroyed, thus we remove this activity as a listener
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        contact_list_controller.removeObserver(this);
    }

    /**
     * Update the view
     */
    @Override
    public void update(){
        my_contacts = (ListView) findViewById(R.id.my_contacts);
        adapter = new ContactAdapter(ContactsActivity.this, contact_list_controller.getContacts());
        my_contacts.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
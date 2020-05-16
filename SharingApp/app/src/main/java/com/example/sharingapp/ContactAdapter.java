package com.example.sharingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * ContactAdapter is responsible for what information is displayed in ListView entries.
 */
public class ContactAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater inflater;
    private Context context;

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // getItem(position) gets the "contact" at "position" in the "contacts" ArrayList
        // (where "contacts" is a parameter in the ContactAdapter creator as seen above ^^)
        Contact contact = getItem(position);

        String username = "Username: " + contact.getUsername();
        String email = "Email: " + contact.getEmail();

        // Check if an existing view is being reused, otherwise inflate the view.
        if (convertView == null) {
            convertView = inflater.from(context).inflate(R.layout.contactlist_contact, parent, false);
        }

        TextView username_tv = (TextView) convertView.findViewById(R.id.username_tv);
        TextView email_tv = (TextView) convertView.findViewById(R.id.email_tv);
        ImageView photo = (ImageView) convertView.findViewById(R.id.contacts_image_view);

        photo.setImageResource(android.R.drawable.ic_menu_gallery);

        username_tv.setText(username);
        email_tv.setText(email);

        return convertView;
    }
}

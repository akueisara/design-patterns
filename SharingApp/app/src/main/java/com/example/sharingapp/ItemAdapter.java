package com.example.sharingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Item Adapter is responsible for what information is displayed in ListView entries.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    private LayoutInflater inflater;
    private Fragment fragment;
    private Context context;

    public ItemAdapter(Context context, ArrayList<Item> items, Fragment fragment) {
        super(context, 0, items);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // getItem(position) gets the "item" at "position" in the "items" ArrayList
        // (where "items" is a parameter in the ItemAdapter creator as seen above ^^)
        // Note: getItem() is not a user-defined method in the Item/ItemList class!
        // The "Item" in the method name is a coincidence...
        Item item = getItem(position);

        String title = "Title: " + item.getTitle();
        String description = "Description: " + item.getDescription();
        Bitmap thumbnail = item.getImage();
        String status = "Status: " + item.getStatus();

        // Check if an existing view is being reused, otherwise inflate the view.
        if (convertView == null) {
            convertView = inflater.from(context).inflate(R.layout.itemlist_item, parent, false);
        }

        TextView title_tv = (TextView) convertView.findViewById(R.id.title_tv);
        TextView status_tv = (TextView) convertView.findViewById(R.id.status_tv);
        TextView description_tv = (TextView) convertView.findViewById(R.id.description_tv);
        ImageView photo = (ImageView) convertView.findViewById(R.id.image_view);

        if (thumbnail != null) {
            photo.setImageBitmap(thumbnail);
        } else {
            photo.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        title_tv.setText(title);
        description_tv.setText(description);

        // AllItemFragments: itemlist item shows title, description and status
        if (fragment instanceof AllItemsFragment ) {
            status_tv.setText(status);
        }

        // BorrowedItemsFragment/AvailableItemsFragment: itemlist item shows title and description only
        if (fragment instanceof BorrowedItemsFragment || fragment instanceof AvailableItemsFragment) {
            status_tv.setVisibility(View.GONE);
        }

        return convertView;
    }
}

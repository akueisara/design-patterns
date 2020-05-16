package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Superclass of AvailableItemsFragment, BorrowedItemsFragment and AllItemsFragment
 */
public abstract class ItemsFragment extends Fragment {

    ItemList item_list = new ItemList();
    View rootView = null;
    private ListView list_view = null;
    private ArrayAdapter<Item> adapter = null;
    private ArrayList<Item> selected_items;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        item_list.loadItems(context);
        this.inflater = inflater;
        this.container = container;

        return rootView;
    }

    public void setVariables(int resource, int id ) {
        rootView = inflater.inflate(resource, container, false);
        list_view = (ListView) rootView.findViewById(id);
        selected_items = filterItems();
    }

    public void setAdapter(Fragment fragment){
        adapter = new ItemAdapter(context, selected_items, fragment);
        list_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // When item is long clicked, this starts EditItemActivity
        list_view.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {

                Item item = adapter.getItem(pos);

                int meta_pos = item_list.getIndex(item);

                if (meta_pos >= 0) {

                    Intent edit = new Intent(context, EditItemActivity.class);
                    edit.putExtra("position", meta_pos);
                    startActivity(edit);
                }
                return true;
            }
        });
    }

    /**
     * filterItems is implemented independently by AvailableItemsFragment, BorrowedItemsFragment and AllItemsFragment
     * @return selected_items
     */
    public abstract ArrayList<Item> filterItems();
}

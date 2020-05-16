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
public abstract class ItemsFragment extends Fragment implements Observer {

    private ItemList item_list = new ItemList();
    ItemListController item_list_controller = new ItemListController(item_list);

    View rootView;
    private ListView list_view;
    private ArrayAdapter<Item> adapter;
    private ArrayList<Item> selected_items;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Context context;
    private Fragment fragment;
    private boolean update = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();

        // Don't update view yet. Wait until after items have been filtered.
        item_list_controller.loadItems(context);
        update = true;

        this.inflater = inflater;
        this.container = container;

        return rootView;
    }

    public void setVariables(int resource, int id ) {
        rootView = inflater.inflate(resource, container, false);
        list_view = (ListView) rootView.findViewById(id);
        selected_items = filterItems();
    }

    public void loadItems(Fragment fragment){
        this.fragment = fragment;
        item_list_controller.addObserver(this);
        item_list_controller.loadItems(context);
    }

    public void setFragmentOnItemLongClickListener(){

        // When item is long clicked, this starts EditItemActivity
        list_view.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {

                Item item = adapter.getItem(pos);

                int meta_pos = item_list_controller.getIndex(item);
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

    /**
     * Called when the activity is destroyed, thus we remove this fragment as an observer
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        item_list_controller.removeObserver(this);
    }

    /**
     * Update the view
     */
    @Override
    public void update(){
        if (update) {
            adapter = new ItemAdapter(context, selected_items, fragment);
            list_view.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
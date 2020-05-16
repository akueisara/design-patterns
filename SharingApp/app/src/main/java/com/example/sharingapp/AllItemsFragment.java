package com.example.sharingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Displays a list of all items
 */
public class AllItemsFragment extends ItemsFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater,container, savedInstanceState);
        super.setVariables(R.layout.all_items_fragment, R.id.my_items);
        super.setAdapter(AllItemsFragment.this);

        return rootView;
    }

    public ArrayList<Item> filterItems() {
        return item_list.getItems();
    }

}

package com.example.sharingapp;
import android.content.Context;

/**
 * Command to edit a pre-existing item
 */
public class EditItemCommand extends Command {
    private ItemList item_list;
    private Item old_item;
    private Item new_item;
    private Context context;

    public EditItemCommand(ItemList item_list, Item old_item, Item new_item, Context context) {
        this.item_list = item_list;
        this.old_item = old_item;
        this.new_item = new_item;
        this.context = context;
    }

    public void execute() {
        item_list.deleteItem(old_item);
        item_list.addItem(new_item);
        setIsExecuted(item_list.saveItems(context));
    }
}

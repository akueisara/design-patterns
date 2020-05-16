package com.example.sharingapp;
import android.content.Context;

/**
 * Command to delete an item
 */
public class DeleteItemCommand extends Command {
    private ItemList item_list;
    private Item item;
    private Context context;

    public DeleteItemCommand(ItemList item_list, Item item, Context context) {
        this.item_list = item_list;
        this.item = item;
        this.context = context;
    }

    public void execute() {
        item_list.deleteItem(item);
        setIsExecuted(item_list.saveItems(context));
    }
}

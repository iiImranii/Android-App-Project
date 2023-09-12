package uk.ac.le.co2103.part2;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class ShoppingListAdapter extends ListAdapter<ShoppingList, ShoppingListViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Integer listId);
    }

    public interface OnItemLongClickListener {
        void onLongClick(Integer listId);
    }
    private final OnItemClickListener listener;
    private final OnItemLongClickListener listenerL;

    public ShoppingListAdapter(@NonNull DiffUtil.ItemCallback<ShoppingList> diffCallback, OnItemClickListener listener,OnItemLongClickListener listenerL) {
        super(diffCallback);
        this.listener = listener;
        this.listenerL = listenerL;
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ShoppingListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ShoppingListViewHolder holder, int position) {
        ShoppingList current = getItem(position);
        holder.bind(current.getListId(),current.getListName(),current.getImageURL(),listener,listenerL);
    }

    static class ItemDiff extends DiffUtil.ItemCallback<ShoppingList> {

        @Override
        public boolean areItemsTheSame(@NonNull ShoppingList oldItem, @NonNull ShoppingList newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShoppingList oldItem, @NonNull ShoppingList newItem) {
            return oldItem.getListName().equals(newItem.getListName());
        }
    }
}

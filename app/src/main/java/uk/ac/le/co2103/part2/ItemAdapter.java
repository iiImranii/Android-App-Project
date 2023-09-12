package uk.ac.le.co2103.part2;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class ItemAdapter extends ListAdapter<Item, ItemViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Integer itemId);
    }

    public interface OnItemLongClickListener {
        void onLongClick(String desc);
    }
    private final OnItemClickListener listener;
    private final OnItemLongClickListener listenerL;

    public ItemAdapter(@NonNull DiffUtil.ItemCallback<Item> diffCallback, OnItemClickListener listener, OnItemLongClickListener listenerL) {
        super(diffCallback);
        this.listener = listener;
        this.listenerL = listenerL;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item current = getItem(position);
        holder.bind(current.getItemId(),current.getParentId(),current.getItemName(),current.getQuantity(),current.getUnit(),current.getItemDesc(),listener,listenerL);
    }

    static class ItemDiff extends DiffUtil.ItemCallback<Item> {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getItemName().equals(newItem.getItemName());
        }
    }
}

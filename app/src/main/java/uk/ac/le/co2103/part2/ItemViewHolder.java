package uk.ac.le.co2103.part2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView itemNameT;
    private final TextView itemQuantityT;
    private final TextView itemUnitT;
    private ItemViewHolder(View itemView) {
        super(itemView);
        itemNameT = itemView.findViewById(R.id.productNameSection2);
        itemQuantityT = itemView.findViewById(R.id.productQuantitySection2);
        itemUnitT = itemView.findViewById(R.id.productUnitSection2);
    }

    public void bind(Integer Id, Integer parentId, String itemName, Integer itemQ, String itemUnit, String itemDesc, ItemAdapter.OnItemClickListener listener, ItemAdapter.OnItemLongClickListener listenerL) {
        itemNameT.setText(itemName);
        itemQuantityT.setText(" Quantity: "+Integer.toString(itemQ));
        itemUnitT.setText("Unit: "+itemUnit);



        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { listener.onItemClick(Id);}

        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listenerL.onLongClick(itemDesc);
                return false;
            }
        });
    }


    static ItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_products, parent, false);
        return new ItemViewHolder(view);
    }


}

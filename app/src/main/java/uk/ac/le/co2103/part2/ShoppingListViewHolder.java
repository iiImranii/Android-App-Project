package uk.ac.le.co2103.part2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ShoppingListViewHolder extends RecyclerView.ViewHolder {
    private final TextView itemTextView;
    private final ImageView listImageView;
    private Context context;

    private ShoppingListViewHolder(View itemView) {
        super(itemView);
        itemTextView = itemView.findViewById(R.id.productNameSection);
        listImageView = itemView.findViewById(R.id.imageView);
        this.context = context;
    }


    public void bind(Integer Id, String text, String imageURL, ShoppingListAdapter.OnItemClickListener listener, ShoppingListAdapter.OnItemLongClickListener listenerL) {
        itemTextView.setText(text);
        if (imageURL != null) {
            Uri imgUri= Uri.parse(imageURL);
            listImageView.setImageURI(null);
           // Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
          //  intent.addCategory(Intent.CATEGORY_OPENABLE);
          //  intent.setType("image/*");
           // DocumentFile fileg = DocumentFile.fromTreeUri(context, imgUri);
          //  intent.putExtra(EXTRA_INITIAL_URI, fileg.getUri());
           // ((Activity)context).startActivityForResult(intent,7);
           // start

            listImageView.setImageURI(imgUri);
            //listImageView.setImageURI(Uri.parse(imageURL));
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(Id);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override public boolean onLongClick(View v) {
                listenerL.onLongClick(Id);
                return false;
            }

        });



    }

    static ShoppingListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);

        return new ShoppingListViewHolder(view);
    }


}

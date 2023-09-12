package uk.ac.le.co2103.part2;

import static java.lang.System.exit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

public class ShoppingListActivity extends AppCompatActivity {
    private ItemViewModel itemViewModel;
     int ADD_PRODUCT_CODE = 2;
    int UPDATE_PRODUCT_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        RecyclerView recyclerView = findViewById(R.id.productRecyclerView);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


            final ItemAdapter adapter = new ItemAdapter(new ItemAdapter.ItemDiff(), new ItemAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Integer itemId) {


                    builder.setMessage("Would you like to delete or edit your product?");
                    builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (Item z : itemViewModel.getAllItems().getValue()) {
                                if (z.getItemId() == itemId) {
                                    Intent updateIntent = new Intent(ShoppingListActivity.this,UpdateProductActivity.class);
                                    updateIntent.putExtra("productId", itemId);
                                    updateIntent.putExtra("parentId", z.getParentId());
                                    updateIntent.putExtra("productName",z.getItemName());
                                    updateIntent.putExtra("productQuantity",z.getQuantity().toString());
                                    updateIntent.putExtra("productUnit",z.getUnit());
                                    updateIntent.putExtra("productDesc",z.getItemDesc());

                                    startActivityForResult(updateIntent, UPDATE_PRODUCT_CODE);
                                }
                            }


                        }
                    });
                    builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //perform any action
                            for (Item i : itemViewModel.getAllItems().getValue()) {
                                if (i.getItemId() == itemId) {
                                    itemViewModel.deleteListById(itemId);
                                }
                            }

                            Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }, new ItemAdapter.OnItemLongClickListener() {
                @Override
                public void onLongClick(String desc) {
                    Toast.makeText(getApplicationContext(), desc, Toast.LENGTH_LONG).show();
                }
            });

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        Intent rIntent = getIntent();
        Integer parentId = rIntent.getIntExtra("parentId", 0);
        List<Item> initial = new ArrayList<Item>();

        itemViewModel.getAllItems().observe(this,items ->{
            for (Item i: items) {
                if (i.getParentId() == parentId) {
                    initial.add(i);

                }
            }

        });
        adapter.submitList(initial);



           // for (Item i: itemViewModel.getAllItems().getValue()) {
           //     if (parentId == i.getParentId()) {
           //         initial.add(i);
           //     }
          //  }

           // adapter.submitList(initial);






        FloatingActionButton fabAddProduct = findViewById(R.id.fabAddProduct);
        fabAddProduct.setOnClickListener(view -> {
            Intent addProductIntent = new Intent(ShoppingListActivity.this, AddProductActivity.class);
            Intent fIntent = getIntent();
            Integer pparentId = fIntent.getIntExtra("parentId", 0);
            addProductIntent.putExtra("listParentId", pparentId);
            startActivityForResult(addProductIntent, ADD_PRODUCT_CODE);


        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADD_PRODUCT_CODE && resultCode == RESULT_OK) {
            String productName = data.getStringExtra(AddProductActivity.EXTRA_REPLY_INAME);
            int productQuantity=data.getIntExtra(AddProductActivity.EXTRA_REPLY_IQUANTITY,1);
            String productUnit=data.getStringExtra(AddProductActivity.EXTRA_REPLY_IUNIT);
            String productDesc=data.getStringExtra(AddProductActivity.EXTRA_REPLY_IDESC);
            int productParentId=data.getIntExtra(AddProductActivity.EXTRA_REPLY_IPID,1);
            int productId = data.getIntExtra(AddProductActivity.EXTRA_REPLY_ITEMID,1);
            itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
            AtomicInteger itemCount = new AtomicInteger();

           // itemViewModel.getAllItems().observe(this, items -> {itemCount

            for (Item z: itemViewModel.getAllItems().getValue()) {
                    if (z.getItemName().equals( productName) && z.getParentId() == productParentId ) {
                        itemCount.addAndGet(1);
                    }
                }


           // });
            if (itemCount.get() >= 1)  {
                Toast.makeText(getApplicationContext(), "Product already exists", Toast.LENGTH_LONG).show();
            } else {
                Item newItem = new Item(productParentId,productName,productQuantity,productUnit,productDesc);
                itemViewModel.insert(newItem);
                finish();
            }

        } else if (requestCode==UPDATE_PRODUCT_CODE && resultCode == RESULT_OK) {


            itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
            String productName = data.getStringExtra(UpdateProductActivity.EXTRA_REPLY_UPDATED_NAME);
            int productQuantity=data.getIntExtra(UpdateProductActivity.EXTRA_REPLY_UPDATED_QUANTITY,1);
            String productUnit=data.getStringExtra(UpdateProductActivity.EXTRA_REPLY_UPDATED_UNIT);
            String productDesc=data.getStringExtra(UpdateProductActivity.EXTRA_REPLY_UPDATED_DESC);
            int productParentId=data.getIntExtra(UpdateProductActivity.EXTRA_REPLY_UPDATED_PARENTID,1);
            int productId = data.getIntExtra(UpdateProductActivity.EXTRA_REPLY_UPDATED_ITEMID,1);
            AtomicInteger itemCount = new AtomicInteger();

                for (Item z: itemViewModel.getAllItems().getValue()) {
                    if (z.getItemName().equals(productName) && z.getParentId() == productParentId && z.getItemId()!= productId) {
                        itemCount.addAndGet(1);

                    }
                }

                if (itemCount.get() >= 1)  {
                    Toast.makeText(getApplicationContext(), "Product already exists", Toast.LENGTH_LONG).show();
                } else {
                    for (Item g: itemViewModel.getAllItems().getValue()) {
                        if (g.getItemId() == productId) {
                            if ( productDesc.length() >0) {
                                itemViewModel.updateDescById(productId,productDesc);
                            }
                            itemViewModel.updateNameById(productId,productName);
                            itemViewModel.updateQuantityById(productId,productQuantity);
                            itemViewModel.updateUnitById(productId,productUnit);
                            System.out.println("......");

                            finish();

                        }
                    }

                }


            //Toast.makeText(getApplicationContext(), "List was unable to save.", Toast.LENGTH_LONG).show();
        }
    }



}
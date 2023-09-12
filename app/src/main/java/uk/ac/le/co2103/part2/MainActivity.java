package uk.ac.le.co2103.part2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ShoppingListViewModel shoppingListViewModel;
    public static final int ADD_LIST_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        shoppingListViewModel.getAllLists().observe(this, items -> {


        final ShoppingListAdapter adapter = new ShoppingListAdapter(new ShoppingListAdapter.ItemDiff(),new ShoppingListAdapter.OnItemClickListener() {
        public void onItemClick(Integer listId) {
            Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
            intent.putExtra("parentId",listId);
            startActivity(intent);

        }
        },new ShoppingListAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(Integer listId) {


                builder.setMessage("Are you sure you want to delete this list from the shopping list?");

                builder.setPositiveButton("Yes, please", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //perform any action
                        for (ShoppingList i: shoppingListViewModel.getAllLists().getValue()) {
                            if (i.getListId() == listId) {
                                shoppingListViewModel.deleteListById(listId);
                            }
                        }
                        Toast.makeText(getApplicationContext(), "Yes clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //perform any action
                        Toast.makeText(getApplicationContext(), "No clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                //creating alert dialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


               // System.out.println("List of id was held");

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, "Adding items to adapter");
        adapter.submitList(items);
        recyclerView.setAdapter(adapter);
        final FloatingActionButton button = findViewById(R.id.fab);


        button.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this, CreateListActivity.class);
            startActivityForResult(intent, ADD_LIST_ACTIVITY_REQUEST_CODE);
        });
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==ADD_LIST_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

           // String[] array = data.getStringArrayExtra(CreateListActivity.EXTRA_REPLY);
            String listName = data.getStringExtra(CreateListActivity.EXTRA_REPLY_NAME);
            String listURI = data.getStringExtra(CreateListActivity.EXTRA_REPLY_URI);
            ShoppingList shoppingList = new ShoppingList(listName, listURI);
           // System.out.println(list.getListName());
            shoppingListViewModel.insert(shoppingList);

        }
        else {
            Toast.makeText(getApplicationContext(), "List was unable to save.", Toast.LENGTH_LONG).show();
        }
    }
}
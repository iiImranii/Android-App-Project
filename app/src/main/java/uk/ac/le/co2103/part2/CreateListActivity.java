package uk.ac.le.co2103.part2;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateListActivity extends AppCompatActivity {
    private String newShopListName;
    private Uri newShopListURI = null;
    private String stringURI = null;
    public static final String EXTRA_REPLY_NAME = "uk.ac.le.co2103.part2.REPLY_NAME";
    public static final String EXTRA_REPLY_URI = "uk.ac.le.co2103.part2.REPLY_URI";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_list_activity);

        final FloatingActionButton buttonFindImage = findViewById(R.id.importImageButton);
        final Button createListButton = findViewById(R.id.finalizeButton);
        final TextView nameField = findViewById(R.id.shoppingListName);
        buttonFindImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(gallery,"Select Shopping List Image"),44);
            }
    });
        createListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent replyIntent = new Intent();
               if (TextUtils.isEmpty(nameField.getText())) {
                   Toast.makeText(getApplicationContext(), "Please choose a name for your shopping list.", Toast.LENGTH_LONG).show();
               } else {
                   String listName = nameField.getText().toString();
                   replyIntent.putExtra(EXTRA_REPLY_NAME,listName);
                   replyIntent.putExtra(EXTRA_REPLY_URI,stringURI);
                   setResult(RESULT_OK, replyIntent);
               }
               finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 44 && resultCode == RESULT_OK) {
            newShopListURI = data.getData();
            getContentResolver().takePersistableUriPermission(newShopListURI, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            stringURI = newShopListURI.toString();
        }
    }
}
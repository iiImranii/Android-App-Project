package uk.ac.le.co2103.part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class UpdateProductActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY_UPDATED_NAME = "uk.ac.le.co2103.part2.REPLY_UPDATED_NAME";
    public static final String EXTRA_REPLY_UPDATED_QUANTITY = "uk.ac.le.co2103.part2.REPLY_UPDATED_QUANTITY";
    public static final String EXTRA_REPLY_UPDATED_UNIT = "uk.ac.le.co2103.part2.REPLY_UPDATED_UNIT";
    public static final String EXTRA_REPLY_UPDATED_DESC = "uk.ac.le.co2103.part2.REPLY_UPDATED_DESC";
    public static final String EXTRA_REPLY_UPDATED_ITEMID = "uk.ac.le.co2103.part2.REPLY_UPDATED_ITEMID";
    public static final String EXTRA_REPLY_UPDATED_PARENTID = "uk.ac.le.co2103.part2.REPLY_UPDATED_PARENTID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);


        Button finishButton = findViewById(R.id.finishUpdatedProductButton);
        TextView nameField = findViewById(R.id.changeProductName);
        TextView productQuantity = findViewById(R.id.changeProductQuantity);
        Spinner productUnit = findViewById(R.id.changeUnitSpinner);
        TextView productDesc = findViewById(R.id.changeProductDesc);
        Button incQuantity = findViewById(R.id.incQuantityButton);
        Button decQuantity = findViewById(R.id.decQuantityButton);

        Intent reciever = getIntent();
        nameField.setText(reciever.getStringExtra("productName"));
        productQuantity.setText(reciever.getStringExtra("productQuantity"));
        ArrayAdapter<String> myAdap = (ArrayAdapter<String>) productUnit.getAdapter(); //cast to an ArrayAdapter
        int spinnerPosition = myAdap.getPosition(reciever.getStringExtra("productUnit"));
        productUnit.setSelection(spinnerPosition);
        productDesc.setText(reciever.getStringExtra("productDesc"));
        incQuantity.setOnClickListener( view -> {
            if (TextUtils.isEmpty(productQuantity.getText().toString())) {
                productQuantity.setText("1");

            } else {
                productQuantity.setText( Integer.toString( Integer.parseInt(productQuantity.getText().toString())+1));
            }
        });

        decQuantity.setOnClickListener(view ->  {
            if (TextUtils.isEmpty(productQuantity.getText().toString())) {
                productQuantity.setText("0");

            } else {
                if (Integer.parseInt(productQuantity.getText().toString()) > 0) {
                    productQuantity.setText( Integer.toString( Integer.parseInt(productQuantity.getText().toString())-1));
                }

            }

        });


        finishButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(nameField.getText())||TextUtils.isEmpty(productQuantity.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Invalid updation.", Toast.LENGTH_LONG).show();
            } else {

                String listName = nameField.getText().toString();
                // String[] dataTable = new String[] {listName,newShopListURI};
                //replyIntent.putExtra(EXTRA_REPLY,dataTable);
                replyIntent.putExtra(EXTRA_REPLY_UPDATED_NAME,listName);
                replyIntent.putExtra(EXTRA_REPLY_UPDATED_DESC,productDesc.getText().toString());
                replyIntent.putExtra(EXTRA_REPLY_UPDATED_UNIT,productUnit.getSelectedItem().toString());
                replyIntent.putExtra(EXTRA_REPLY_UPDATED_QUANTITY,Integer.valueOf( productQuantity.getText().toString()));
                replyIntent.putExtra(EXTRA_REPLY_UPDATED_ITEMID,getIntent().getIntExtra("productId",0));
                replyIntent.putExtra(EXTRA_REPLY_UPDATED_PARENTID,getIntent().getIntExtra("parentId",0));
                setResult(RESULT_OK, replyIntent);


            }
            finish();
        });
    }
}
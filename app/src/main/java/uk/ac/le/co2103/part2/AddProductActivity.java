package uk.ac.le.co2103.part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY_INAME = "uk.ac.le.co2103.part2.REPLY_INAME";
    public static final String EXTRA_REPLY_IQUANTITY = "uk.ac.le.co2103.part2.REPLY_IQUANTITY";
    public static final String EXTRA_REPLY_IUNIT = "uk.ac.le.co2103.part2.REPLY_IUNIT";
    public static final String EXTRA_REPLY_IDESC = "uk.ac.le.co2103.part2.REPLY_IDESC";
    public static final String EXTRA_REPLY_IPID = "uk.ac.le.co2103.part2.REPLY_IPID";
    public static final String EXTRA_REPLY_ITEMID = "uk.ac.le.co2103.part2.REPLY_ITEMID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Button finalButton = findViewById(R.id.finishAddProduct);
        
        TextView productName = findViewById(R.id.editTextName);
        TextView productQuantity = findViewById(R.id.editTextQuantity);
        TextView productDesc = findViewById(R.id.productDescriptionInput);
        final Boolean[] clicked = {false};
        finalButton.setOnClickListener(view ->  {

                Intent replyIntent = new Intent();

                Spinner productUnitSpinner = findViewById(R.id.spinner);
                String productUnit = productUnitSpinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(productName.getText())|| Integer.valueOf( productQuantity.getText().toString()) <= 0 ) {
                    Toast.makeText(getApplicationContext(), "Invalid Product entry, please try again!", Toast.LENGTH_LONG).show();
                } else {

                    replyIntent.putExtra(EXTRA_REPLY_INAME,productName.getText().toString());
                    replyIntent.putExtra(EXTRA_REPLY_IUNIT,productUnit);
                    replyIntent.putExtra(EXTRA_REPLY_ITEMID,getIntent().getIntExtra("productId",0));
                    replyIntent.putExtra(EXTRA_REPLY_IQUANTITY,Integer.valueOf( productQuantity.getText().toString()));
                    if (!TextUtils.isEmpty(productDesc.getText()) ) {
                        replyIntent.putExtra(EXTRA_REPLY_IDESC,productDesc.getText().toString());
                    }
                    replyIntent.putExtra(EXTRA_REPLY_IPID,getIntent().getIntExtra("listParentId",0));
                    setResult(RESULT_OK, replyIntent);



                }

            finish();
        });
    }
}
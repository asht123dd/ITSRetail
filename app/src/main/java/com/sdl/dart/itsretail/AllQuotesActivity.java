package com.sdl.dart.itsretail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AllQuotesActivity extends AppCompatActivity {
    TextView price,qty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_quotes);
        init();
    }
    public void init(){
        TableLayout ll = (TableLayout) findViewById(R.id.tabLayout);


        for (int i = 0; i <2; i++) {

            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            row.setWeightSum(8);

            price = new TextView(this);
            TableRow.LayoutParams param = new TableRow.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    4.0f
            );
           price.setLayoutParams(param);
            price.setGravity(Gravity.CENTER);
            qty = new TextView(this);
           qty.setLayoutParams(param);
            qty.setGravity(Gravity.CENTER);
            price.setText("hello");
            qty.setText("10");

            row.addView(price);
            row.addView(qty);
            ll.addView(row,i);
        }
    }
}

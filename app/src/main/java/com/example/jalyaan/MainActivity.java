package com.example.jalyaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {
    TextView quantity_tv,water_price_tv,total_water_price_tv,bottle_price_tv,total_bottle_price_tv,total_price_tv;
    CardView paymentBtn;
    FirebaseAuth auth;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Checkout.preload(getApplicationContext());
        getSupportActionBar().setTitle("Book Now");
        auth = FirebaseAuth.getInstance();
        quantity_tv = findViewById(R.id.quantity_tv);
        water_price_tv = findViewById(R.id.water_price_tv);
        total_water_price_tv = findViewById(R.id.total_water_price_tv);
        bottle_price_tv = findViewById(R.id.bottle_price_tv);
        total_bottle_price_tv = findViewById(R.id.total_bottle_price_tv);
        total_price_tv = findViewById(R.id.total_price_tv);
        paymentBtn = findViewById(R.id.paymentBtn);
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
    }
    public void startPayment() {
        //things i do
        int quantity = Integer.parseInt(quantity_tv.getText().toString());
        String price = String.valueOf((quantity*20 + quantity*100)*100);

        //end


        Checkout checkout = new Checkout();
        checkout.setKeyID(getString(R.string.rzPayTestKey));



        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.icon);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "JAl YAAN");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", price);//pass amount in currency subunits
            options.put("prefill.email", "guptadivyanshu29@gmail.com");
            options.put("prefill.contact","9131427999");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.contact_us:
                startActivity(new Intent(MainActivity.this,ContactUs.class));
                break;

            case R.id.history:
                
                Toast.makeText(this, "History", Toast.LENGTH_SHORT).show();
                break;

            case R.id.setting:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                //Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();

                break;

            case R.id.complaint:
                startActivity(new Intent(MainActivity.this,ComplaintActivity.class));
                //Toast.makeText(this, "Complaints", Toast.LENGTH_SHORT).show();
                break;

            case R.id.log_out:
                Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,SignUpActivity.class));
                finishAffinity();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public void increment(View view) {
        int i = Integer.parseInt(quantity_tv.getText().toString());
        if(i>=100){
            Toast.makeText(this, "Can not order more than 100", Toast.LENGTH_SHORT).show();
            return;
        }
        i++;
        quantity_tv.setText(String.valueOf(i));
        calculatePrice(i);
    }

    public void decrement(View view) {
        int i = Integer.parseInt(quantity_tv.getText().toString());
        if(i<=1){
            Toast.makeText(this, "Can not order item less than 1", Toast.LENGTH_SHORT).show();
            return;
        }
        i--;
        quantity_tv.setText(String.valueOf(i));
        calculatePrice(i);
    }
    public void calculatePrice(int quantity){
        int waterPrice = quantity*20;
        int bottlePrice = quantity*100;
        int totalPrice = waterPrice+bottlePrice;
        water_price_tv.setText("Water Price : " + quantity +" x 20.0");
        total_water_price_tv.setText(" ₹ " +waterPrice);
        bottle_price_tv.setText("Bottle Price : " + quantity + " x 100.0");
        total_bottle_price_tv.setText(" ₹ " + bottlePrice);
        total_price_tv.setText(" ₹ " + totalPrice);
    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful : " + s, Toast.LENGTH_SHORT).show();
        Order order = createOrder(s);
        DatabaseReference node = FirebaseDatabase.getInstance().getReference("Orders");
        node.child(order.getUid()).push().setValue(order);
        Toast.makeText(this, "Order Inserted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Fail and cause is "+s, Toast.LENGTH_LONG).show();
    }

    public Order createOrder(String orderId){
        String quantity = quantity_tv.getText().toString();
        String total = String.valueOf(Integer.parseInt(quantity)*100 + Integer.parseInt(quantity)*20);
        return new Order(orderId,auth.getUid().toString(),quantity,total);
    }

}
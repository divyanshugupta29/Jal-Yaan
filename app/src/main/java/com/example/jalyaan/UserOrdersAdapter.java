package com.example.jalyaan;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserOrdersAdapter extends FirebaseRecyclerAdapter<Order,UserOrdersAdapter.UserOrderViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserOrdersAdapter(@NonNull FirebaseRecyclerOptions<Order> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserOrderViewHolder holder, int position, @NonNull Order model) {
        holder.orderTV.setText(model.getOrderID());
        holder.priceTV.setText(model.getPrice());
        holder.quantityTV.setText(model.getQuantiy());
        if (model.isDelivered()){
            holder.isDeliveredTV.setText("Delivered");
            holder.isDeliveredTV.setBackgroundColor(Color.GREEN);
        }
        Log.v("Order",model.getOrderID());
        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseDatabase.getInstance().getReference().child("Orders").child(auth.getUid().toString())
                        .child(getRef(holder.getAdapterPosition()).getKey()).removeValue();
            }
        });
    }

    @NonNull
    @Override
    public UserOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order,parent,false);
        return new UserOrderViewHolder(view);
    }

    class UserOrderViewHolder extends RecyclerView.ViewHolder{
        TextView quantityTV,priceTV,orderTV,isDeliveredTV;
        Button cancelBtn;
        public UserOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            quantityTV = itemView.findViewById(R.id.order_quantityTV);
            priceTV = itemView.findViewById(R.id.order_priceTV);
            orderTV = itemView.findViewById(R.id.order_orderID_TV);
            cancelBtn = itemView.findViewById(R.id.order_cancelOrderBtn);
            isDeliveredTV = itemView.findViewById(R.id.order_isDeliveredTV);

        }
    }
}

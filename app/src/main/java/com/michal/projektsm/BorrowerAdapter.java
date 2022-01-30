package com.michal.projektsm;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import android.app.Activity;
import android.graphics.Color;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michal.projektsm.roomdatabase.DebtEntity;
import com.michal.projektsm.roomdatabase.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class BorrowerAdapter extends RecyclerView.Adapter<BorrowerAdapter.ViewHolder> {
    public static final String TAG = "BorrowerAdapter";
    private List<DebtEntity> mDataSet;

    public List<DebtEntity> getmDataSet() {
        return mDataSet;
    }

    public void setmDataSet(List<DebtEntity> mDataSet) {
        this.mDataSet = mDataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView amountTextView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(v1 -> Log.d(TAG, "Element " + getAdapterPosition() + " clicked."));
            nameTextView = (TextView) v.findViewById(R.id.borrowerName);
            amountTextView = (TextView) v.findViewById(R.id.borrowerAmount);
        }

        public TextView getNameTextView() { return nameTextView;
        }
        public TextView getAmountTextView() { return amountTextView; }
    }

    public BorrowerAdapter(List<DebtEntity> dataSet) {
        this.mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_borrower, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        viewHolder.getNameTextView().setText(mDataSet.get(position).getBorrower());
        viewHolder.getAmountTextView().setText(CurrencyConverter.getInstance().getCurrency(mDataSet.get(position).getAmount().floatValue()));
        viewHolder.getAmountTextView().setText(CurrencyConverter.getInstance().getCurrency(mDataSet.get(position).getAmount()));
        if(mDataSet.get(position).getAmount() < 0){
            viewHolder.getAmountTextView().setTextColor(Color.parseColor("#FA2917"));
        } else {
            viewHolder.getAmountTextView().setTextColor(Color.parseColor("#2DDB3F"));
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
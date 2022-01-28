package com.michal.projektsm;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michal.projektsm.roomdatabase.DebtEntity;

import java.util.ArrayList;
import java.util.List;

public class BorrowerAdapter extends RecyclerView.Adapter<BorrowerAdapter.ViewHolder> {
    public static final String TAG = "BorrowerAdapter";
    private List<DebtEntity> mDataSet;

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

        public TextView getNameTextView() {
            return nameTextView;
        }
        public TextView getAmountTextView() {
            return amountTextView;
        }
    }

    public BorrowerAdapter(List<DebtEntity> dataSet) {
        mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_borrower, viewGroup, false);

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getNameTextView().setText(mDataSet.get(position).getBorrower());
        viewHolder.getAmountTextView().setText(String.valueOf(mDataSet.get(position).getAmount()));
        Log.d(TAG, "Element " + position + " set!!!");
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
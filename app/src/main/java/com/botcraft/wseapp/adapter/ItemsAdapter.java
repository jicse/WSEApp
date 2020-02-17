package com.botcraft.wseapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.botcraft.wseapp.R;
import com.botcraft.wseapp.model.Electronics;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> implements ItemTouchHelperAdapter {

    private List<Electronics> mDataset = new ArrayList<>();
    private static OnItemClickListener mItemClickListener;
    private static final int TYPE_ITEM = 0;
    private final LayoutInflater mInflater;
    private final OnStartDragListener mDragStartListener;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,ItemTouchHelperViewHolder {
        public ImageView imageView;
        public TextView textViewName, textViewPrice;

        public MyViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.image_view);
            textViewName = v.findViewById(R.id.txt_name);
            textViewPrice = v.findViewById(R.id.txt_price);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public ItemsAdapter(Context context, OnStartDragListener dragListner) {
        this.mInflater = LayoutInflater.from(context);
        mDragStartListener = dragListner;
        mContext = context;
    }

    public void setItems(List<Electronics> electronics){
        this.mDataset = electronics;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public ItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }
        throw new RuntimeException("Illegal Type " + viewType);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textViewName.setText(mDataset.get(position).getName());
        holder.textViewPrice.setText(mDataset.get(position).getPrice());
        Glide.with(mContext).load(mDataset.get(position).getImageUrl())
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(holder.imageView);

        holder.imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onItemDismiss(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < mDataset.size() && toPosition < mDataset.size()) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mDataset, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mDataset, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }
        return true;
    }

    public void updateList(List<Electronics> list) {
        mDataset = list;
        notifyDataSetChanged();
    }

}

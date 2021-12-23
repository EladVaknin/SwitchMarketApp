package com.example.swichmarketapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swichmarketapp.R;
import com.example.swichmarketapp.models.Item;
import com.example.swichmarketapp.utlities.CacheUtilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder> {

    private final List<Item> mData = new ArrayList<>();
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public ItemRecyclerAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = mData.get(position);
        holder.mDecriptionTextView.setText(holder.mDecriptionTextView.getText() + " " + item.getDescription());
        holder.mToSwitchTextView.setText(holder.mToSwitchTextView.getText() + " " + item.getTosWitch());
        holder.mPriceTextView.setText(holder.mPriceTextView.getText() + " " + item.getPrice());
        Picasso.get().load(item.getPhoto()).into(holder.mItemImage);
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void clearAllData() {
        mData.clear();
        notifyDataSetChanged();

    }

    public void setNewItems(List<Item> itemList) {
        mData.clear();
        mData.addAll(itemList);
        notifyDataSetChanged();
    }


    // stores and recycles views as they are scrolled off screen
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView mItemImage;
        ImageView mChatButton;
        TextView mPriceTextView;
        TextView mDecriptionTextView;
        TextView mToSwitchTextView;

        ItemViewHolder(View itemView) {
            super(itemView);
            mChatButton = itemView.findViewById(R.id.chat_button);
            mItemImage = itemView.findViewById(R.id.image_item);
            mPriceTextView = itemView.findViewById(R.id.price_edit_text);
            mDecriptionTextView = itemView.findViewById(R.id.description_edit_text);
            mToSwitchTextView = itemView.findViewById(R.id.to_switch_text);
            mChatButton.setOnClickListener(v -> mClickListener.onItemClick(mData.get(getAdapterPosition())));
        }
    }


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(Item item);
    }
}

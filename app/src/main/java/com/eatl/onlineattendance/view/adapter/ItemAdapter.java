package com.eatl.onlineattendance.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.eatl.onlineattendance.R;
import com.eatl.onlineattendance.model.Datum;

public class ItemAdapter extends PagedListAdapter<Datum, ItemAdapter.ItemViewHolder> {

    private Context mCtx;
    private ItemClickListener listener;

    public ItemAdapter(Context mCtx,ItemClickListener listener) {
        super(DATUM_ITEM_CALLBACK);
        this.mCtx = mCtx;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final Datum item =getItem(position) ;

        if (item != null) {
            holder.textViewName.setText(item.getName());
            holder.textViewDetails.setText(item.getAddress());

        }else{
            Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(item);
            }
        });
    }


    public static final DiffUtil.ItemCallback<Datum> DATUM_ITEM_CALLBACK=new DiffUtil.ItemCallback<Datum>() {
        @Override
        public boolean areItemsTheSame(@NonNull Datum oldItem, @NonNull Datum newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Datum oldItem, @NonNull Datum newItem) {
            return oldItem==newItem;
        }
    };


    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName,textViewDetails;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDetails = itemView.findViewById(R.id.textViewDetails);
        }
    }


    public interface ItemClickListener {
        public void onItemClick(Datum datum);
    }
}

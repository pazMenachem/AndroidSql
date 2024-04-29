package com.akash.dailyshoplist.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.dailyshoplist.Database.DataBaseHandler;
import com.akash.dailyshoplist.Model.ItemListModel;
import com.akash.dailyshoplist.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<ItemListModel> itemList;
    private Context context;
    private LayoutInflater ItemInflater;
    private static final String TAG = "ItemAdapter";

    public ItemAdapter(ArrayList<ItemListModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
        ItemInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = ItemInflater.inflate(R.layout.item_list_view, viewGroup , false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if(itemList != null)
        {
            ItemListModel itemListModel = itemList.get(i);
            viewHolder.item_Name.setText(itemListModel.getItem_name());

            Log.d(TAG, "onBindViewHolder: " + viewHolder.is_Checked.isChecked());

            //Log.d(TAG, "onBindViewHolder: " + i);
            viewHolder.is_Checked.setChecked(itemListModel.isIs_Item_Checked());

        }

    }

    @Override
    public int getItemCount() {
        if(itemList != null)
            return itemList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView item_Name;
        public CheckBox is_Checked;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_Name = itemView.findViewById(R.id.Item_name);
            is_Checked = itemView.findViewById(R.id.Item_Checked);

            itemView.setOnLongClickListener(this);
            is_Checked.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            DataBaseHandler handler = new DataBaseHandler(context);
           // Log.d(TAG, "onClick: " +pos);

            Log.d(TAG, "onClick: " + itemList.get(pos).getItem_name());
            //Log.d(TAG, "onClick: " + itemList.get(pos).getItem_id());
            handler.UpdateItemChecked(itemList.get(pos).getItem_name() , is_Checked.isChecked());
        }

        @Override
        public boolean onLongClick(View v) {
            int pos = getAdapterPosition();
            Log.d(TAG, "onLongClick: " + pos);
            return false;
        }
    }
}

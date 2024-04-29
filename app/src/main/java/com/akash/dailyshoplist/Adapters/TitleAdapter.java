package com.akash.dailyshoplist.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.akash.dailyshoplist.Database.DataBaseHandler;
import com.akash.dailyshoplist.ItemActivity;
import com.akash.dailyshoplist.Model.TitleListModel;
import com.akash.dailyshoplist.R;

import java.util.ArrayList;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ViewHolder> {

    public static final String ID_TITLE = "com.title_id.DailyShopList";
    public static final String NAME_TITLE = "com.title_name.DailyShopList";
    private ArrayList<TitleListModel> titleList;
    private Context context;
    private LayoutInflater inflater;


    public TitleAdapter(ArrayList<TitleListModel> titleList , Context context)
    {
        this.titleList = titleList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.title_row_view , null , false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TitleListModel listModel = titleList.get(i);
        viewHolder.title_view.setText(listModel.getTitle_name());
    }

    public void setTitles(ArrayList<TitleListModel> titleList)
    {
        this.titleList = titleList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(titleList != null)
            return titleList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView title_view;
        public ImageButton delete_button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_view = itemView.findViewById(R.id.title_view);
            delete_button = itemView.findViewById(R.id.Delete_button);
            delete_button.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.Delete_button) {
                DeleteItems();
            }
            else {
                OpenItemActivity();
            }
        }



        private void DeleteItems() {

            DataBaseHandler handler = new DataBaseHandler(context);
            handler.DeleteData(titleList.get(getAdapterPosition()).getTitle_Id());
            titleList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());

        }

        private void OpenItemActivity() {
            int titleId = titleList.get(getAdapterPosition()).getTitle_Id();
            String title_name = titleList.get(getAdapterPosition()).getTitle_name();
            if (TextUtils.isEmpty(title_name)) {
                title_name = "";
            }
            Intent intent = new Intent(context, ItemActivity.class);
            intent.putExtra(ID_TITLE, titleId);
            intent.putExtra(NAME_TITLE, title_name);
            context.startActivity(intent);
        }

    }


}

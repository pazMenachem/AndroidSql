package com.akash.dailyshoplist;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.akash.dailyshoplist.Adapters.ItemAdapter;
import com.akash.dailyshoplist.Adapters.TitleAdapter;
import com.akash.dailyshoplist.Database.DataBaseHandler;
import com.akash.dailyshoplist.Model.ItemListModel;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView title_text;
    private EditText  item_text;
    private Button Cancel , Add;
    private DataBaseHandler handler;
    private LayoutInflater inflater;
    private ArrayList<ItemListModel> ItemList;
    private View view;
    public static final String EXTRA_TITLE = "com.DailyShopList.application";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Toolbar toolbar = findViewById(R.id.toolBar);
        title_text = findViewById(R.id.Item_title);
        handler = new DataBaseHandler(ItemActivity.this);
        dialogBuilder = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Log.d("Intent_data " , "" +getIntent().getIntExtra(TitleAdapter.ID_TITLE , 0) );
        ItemList = handler.getItems(getIntent().getIntExtra(TitleAdapter.ID_TITLE , 0));
        title_text.setText(getIntent().getStringExtra(TitleAdapter.NAME_TITLE));
        if(ItemList.isEmpty() && title_text.getText().toString().isEmpty())
        {
            Log.d("ItemListOnCreate" , "First Time user Create itemList");
            ItemList = new ArrayList<>();
            AddTitlePopUp();
        }








        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(ItemList.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(ItemList , ItemActivity.this);
        recyclerView.setAdapter(adapter);



    }

    private void AddTitlePopUp() {
        view = inflater.inflate(R.layout.edit_title_view, null , false);
        dialogBuilder.setTitle("Shopping list name");
        Button save_button = view.findViewById(R.id.Save_button);
        Button cancel = view.findViewById(R.id.Cancel_title);
        final EditText title_editor = view.findViewById(R.id.add_title);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SaveTitle(title_editor);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialogBuilder.setView(view);
        dialogBuilder.setCancelable(false);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
            {


                finish();
                break;
            }
            case R.id.add:
            {
                AddItemDialogPopUp();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void SaveTitle(EditText title_edit_text) {
        String title = title_edit_text.getText().toString().trim();
        handler.AddTitle(new ItemListModel(title));
        title_text.setText(title);
        dialog.dismiss();
    }

    private void AddItemDialogPopUp() {
        dialogBuilder.setTitle("Shopping list");
        view = inflater.inflate(R.layout.edit_items_view , null , false);
        item_text = view.findViewById(R.id.enter_item);
        Add = view.findViewById(R.id.Add_button);
        Cancel = view.findViewById(R.id.Cancel_button);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveItemList();

                dialog.dismiss();

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setView(view);

        dialog = dialogBuilder.create();
        dialog.show();


    }

    private void SaveItemList() {
        int title_id = handler.getTitleId(title_text.getText().toString());
        ItemListModel data = new ItemListModel(title_id , item_text.getText().toString() , false);
        handler.AddItem(data);

        ItemList.add(data);
        adapter.notifyItemInserted(ItemList.size());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.close();
    }
}

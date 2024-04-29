package com.akash.dailyshoplist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.akash.dailyshoplist.Model.ItemListModel;
import com.akash.dailyshoplist.Model.TitleListModel;
import com.akash.dailyshoplist.Utils.DataUtils;

import java.util.ArrayList;


public class DataBaseHandler extends SQLiteOpenHelper {
    private static final String TAG = "DataBaseHandler";
    public DataBaseHandler(Context context) {
        super(context, DataUtils.DB_NAME, null, DataUtils.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_1 = "CREATE TABLE " + DataUtils.TITLE_LIST_TABLE + "("
                + DataUtils.TITLE_ID    + " INTEGER PRIMARY KEY, "
                + DataUtils.TITLE_NAME  + " TEXT " + ")";

        String CREATE_TABLE_2 = "CREATE TABLE " + DataUtils.ITEM_LIST_TABLE + "("
                + DataUtils.TITLE_LIST_ID   + " INTEGER, "
                + DataUtils.ITEM_NAME       + " TEXT, "
                + DataUtils.IS_ITEM_CHECKED + " BOOLEAN, "
                + "FOREIGN KEY " + "(" + DataUtils.TITLE_LIST_ID + ")"
                + " REFERENCES " + DataUtils.TITLE_LIST_TABLE + "(" + DataUtils.TITLE_ID + ")"
                + " ON DELETE CASCADE "
                + ")";

        db.execSQL(CREATE_TABLE_1);
        db.execSQL(CREATE_TABLE_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE_1 = "DROP TABLE IF EXISTS " + DataUtils.TITLE_LIST_TABLE;
        String DROP_TABLE_2 = "DROP TABLE IF EXISTS " + DataUtils.ITEM_LIST_TABLE;

        db.execSQL(DROP_TABLE_1);
        db.execSQL(DROP_TABLE_2);
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON");
    }


    public void AddTitle(ItemListModel itemListModel)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataUtils.TITLE_NAME , itemListModel.getTitle_name());
        database.insert(DataUtils.TITLE_LIST_TABLE, null , values);
        Log.d("AddTitle" , itemListModel.getTitle_name());
        database.close();
    }

    public void AddItem(ItemListModel itemListModel)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("ADDITEM()", " "+itemListModel.getTitle_Id());
        values.put(DataUtils.TITLE_LIST_ID , itemListModel.getTitle_Id());
        values.put(DataUtils.ITEM_NAME , itemListModel.getItem_name());
        values.put(DataUtils.IS_ITEM_CHECKED , itemListModel.isIs_Item_Checked());
        database.insert(DataUtils.ITEM_LIST_TABLE , null , values);
    }

    //Get Title ids according to title_name
    public int getTitleId(String title_name)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        String GET_ID = "SELECT " + DataUtils.TITLE_ID + " FROM " + DataUtils.TITLE_LIST_TABLE + " WHERE "
                + DataUtils.TITLE_NAME + " =? " ;

        Cursor cursor = database.rawQuery(GET_ID , new String[] {title_name});
        if(cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        int id = cursor.getInt(0);
        Log.d("Title id : " , "id->" +id);
        cursor.close();
        database.close();
        return id;
    }

    //Get Items List according to title_id

    public ArrayList<ItemListModel> getItems(int id)
    {
        Log.d("getItems()" , "" +id);
        ArrayList<ItemListModel> getItems = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        String GET_ITEMS = "SELECT * FROM " +DataUtils.ITEM_LIST_TABLE + " WHERE " +DataUtils.TITLE_LIST_ID + "=?";
        Cursor cursor = database.rawQuery(GET_ITEMS , new String[] {String.valueOf(id)});

        if(cursor.moveToFirst())
        {
            do {
                ItemListModel model = new ItemListModel(cursor.getString(1)  , cursor.getInt(2)>0);

                getItems.add(model);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return getItems;
    }


    //Get All Titles name from title tables

    public ArrayList<TitleListModel> getAllTitles()
    {
        ArrayList<TitleListModel> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String GET_TITLE = "SELECT * FROM " + DataUtils.TITLE_LIST_TABLE;
        Cursor cursor = database.rawQuery(GET_TITLE , null);

        if(cursor.moveToFirst())
        {
            do {
                TitleListModel dataModel = new TitleListModel(cursor.getInt(0) , cursor.getString(1));
                list.add(dataModel);
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return list;
    }


    public void DeleteData(int id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DataUtils.TITLE_LIST_TABLE,
                DataUtils.TITLE_ID + "=?" ,
                new String[]{String.valueOf(id)});

    }

    public void UpdateItemChecked(String ItemName , boolean item_is_Checked)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataUtils.IS_ITEM_CHECKED , item_is_Checked);
        database.update(DataUtils.ITEM_LIST_TABLE , values , DataUtils.ITEM_NAME + "=?" , new String[]{ItemName});
        database.close();
    }
}

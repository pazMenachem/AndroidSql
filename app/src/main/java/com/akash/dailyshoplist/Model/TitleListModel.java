package com.akash.dailyshoplist.Model;

public class TitleListModel {
    private int Title_Id;
    private String Title_name;

    public TitleListModel() {
    }

    public TitleListModel(int Title_id)
    {
        this.Title_Id = Title_id;
    }

    public TitleListModel(String Title_name) {
        this.Title_name = Title_name;
    }

    public TitleListModel(int Title_Id , String Title_name) {
        this.Title_Id = Title_Id;
        this.Title_name = Title_name;
    }

    public int getTitle_Id() {
        return Title_Id;
    }

    public void setTitle_Id(int title_Id) {
        Title_Id = title_Id;
    }

    public String getTitle_name() {
        return Title_name;
    }

    public void setTitle_name(String title_name) {
        Title_name = title_name;
    }
}

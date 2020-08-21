package com.eatl.onlineattendance.view.adapter;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.eatl.onlineattendance.model.Datum;

public class ItemDataSourceFactory extends DataSource.Factory {

    //creating the mutable live data
    private MutableLiveData<ItemDataSource> itemLiveDataSource = new MutableLiveData<>();
    private ItemDataSource itemDataSource;


    @Override
    public DataSource<Integer, Datum> create() {
        //getting our data source object
         itemDataSource = new ItemDataSource();

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource);
        //returning the datasource
        return itemDataSource;
    }


    public MutableLiveData<ItemDataSource> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}

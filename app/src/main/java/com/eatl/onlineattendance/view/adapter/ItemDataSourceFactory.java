package com.eatl.onlineattendance.view.adapter;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.eatl.onlineattendance.model.Datum;

public class ItemDataSourceFactory extends DataSource.Factory {

    //creating the mutable live data
   // private MutableLiveData<PageKeyedDataSource<Integer, Datum>> itemLiveDataSource = new MutableLiveData<>();

    private MutableLiveData<ItemDataSource> itemLiveDataSource = new MutableLiveData<>();

    // private MutableLiveData<ItemDataSource> mutableLiveData=new MutableLiveData<>();
    private ItemDataSource itemDataSource;


    @Override
    public DataSource<Integer, Datum> create() {
        //getting our data source object
         itemDataSource = new ItemDataSource();

        Log.e("hellopapa",""+itemDataSource);
        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource);
        //returning the datasource
        return itemDataSource;
    }


    public MutableLiveData<ItemDataSource> getItemLiveDataSource() {
        return itemLiveDataSource;
    }

    //getter for itemlivedatasource
//    public MutableLiveData<PageKeyedDataSource<Integer, Datum>> getItemLiveDataSource() {
//        return itemLiveDataSource;
//    }
}

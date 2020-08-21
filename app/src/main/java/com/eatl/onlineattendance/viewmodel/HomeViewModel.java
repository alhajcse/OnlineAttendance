package com.eatl.onlineattendance.viewmodel;


import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.eatl.onlineattendance.model.Datum;
import com.eatl.onlineattendance.view.adapter.ItemDataSource;
import com.eatl.onlineattendance.view.adapter.ItemDataSourceFactory;

import java.util.List;


public class HomeViewModel extends AndroidViewModel {

    public   LiveData<PagedList<Datum>> itemPagedList;
    private LiveData<ItemDataSource> liveDataSource;


    public HomeViewModel(Application application) {
        super(application);

        //getting our data source factory
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory();

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig = (
                         new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ItemDataSource.PAGE_SIZE).build();

        //Building the paged list
        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)).build();
    }



    public LiveData<PagedList<Datum>> getMoviesPagedList() {
        return itemPagedList;
    }

}

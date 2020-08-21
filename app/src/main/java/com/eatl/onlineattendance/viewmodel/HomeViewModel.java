package com.eatl.onlineattendance.viewmodel;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;


import com.eatl.onlineattendance.model.Datum;
import com.eatl.onlineattendance.model.ItemModel;
import com.eatl.onlineattendance.network.NetworkApiClient;
import com.eatl.onlineattendance.network.NetworkApiInterface;
import com.eatl.onlineattendance.view.adapter.ItemDataSource;
import com.eatl.onlineattendance.view.adapter.ItemDataSourceFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {

    public   LiveData<PagedList<Datum>> itemPagedList;
   // LiveData<PageKeyedDataSource<Integer, Datum>> liveDataSource;

    private LiveData<ItemDataSource> liveDataSource;



    public MutableLiveData<List<Datum>> listMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading=new MutableLiveData<>();


    public HomeViewModel(Application application) {
        super(application);


        //getting our data source factory
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory();

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

       // Log.e("modelV",""+liveDataSource);

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
    public LiveData<Boolean> getIsLoading(){
        return isLoading;
    }



    public LiveData<List<Datum>> getStoreList(int page) {

        isLoading.postValue(true);

        NetworkApiInterface apiInterface= NetworkApiClient.getClient().create(NetworkApiInterface.class);


        Call<ItemModel> call = apiInterface.getOnlineStores(page);

        //calling the api
        call.enqueue(new Callback<ItemModel>() {
            @Override
            public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {


                ItemModel itemModel=response.body();



                Log.e("json tutor", "error --> ");

                if(itemModel.getData().size()!=0) {


                    listMutableLiveData.setValue(itemModel.getData());


                }

                isLoading.postValue(false);


            }

            @Override
            public void onFailure(Call<ItemModel> call, Throwable t) {

                isLoading.postValue(false);

                Log.e("json tutor", "error --> " + t.getMessage());
            }
        });



        return listMutableLiveData;
    }







}

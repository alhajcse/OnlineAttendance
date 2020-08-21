package com.eatl.onlineattendance.view.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.eatl.onlineattendance.model.Datum;
import com.eatl.onlineattendance.model.ItemModel;
import com.eatl.onlineattendance.network.NetworkApiClient;
import com.eatl.onlineattendance.network.NetworkApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Datum> {

    //the size of a page that we want
    public static final int PAGE_SIZE = 55;

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;

    //we need to fetch from stackoverflow
    private static final String SITE_NAME = "stackoverflow";


    //this will be called once to load the initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Datum> callback) {


        NetworkApiInterface apiInterface= NetworkApiClient.getClient().create(NetworkApiInterface.class);
        Call<ItemModel> call = apiInterface.getOnlineStores(FIRST_PAGE);

        //calling the api
        call.enqueue(new Callback<ItemModel>() {
            @Override
            public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {


                ItemModel itemModel=response.body();
                List<Datum> data = new ArrayList<>();


                if (itemModel!= null) {

                    data = (List<Datum>) itemModel.getData();

                    callback.onResult(data, null, FIRST_PAGE + 1);
                }
                Log.e("itemModel init size", " --> "+itemModel.getData().size());




            }

            @Override
            public void onFailure(Call<ItemModel> call, Throwable t) {


              //  Log.e("json tutor", "error --> " + t.getMessage());
            }
        });




    }

    //this will load the previous page
    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Datum> callback) {


        Log.e("loadBefore param key", " --> "+params.key);

//
//        NetworkApiInterface apiInterface= NetworkApiClient.getClient().create(NetworkApiInterface.class);
//
//
//        Call<ItemModel> call = apiInterface.getOnlineStores(params.key);
//
//        //calling the api
//        call.enqueue(new Callback<ItemModel>() {
//            @Override
//            public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
//
//
//                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
//                if (response.body() != null) {
//
//                    //passing the loaded data
//                    //and the previous page key
//                    callback.onResult(response.body().getData(), adjacentKey);
//                }
//               // Log.e("json tutor", "error --> ");
//
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ItemModel> call, Throwable t) {
//
//
//                Log.e("json tutor", "errjjor --> " + t.getMessage());
//            }
//        });
//


    }

    //this will load the next page
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Datum> callback) {


        Log.e("loadAfter param key", " --> "+params.key);
        NetworkApiInterface apiInterface= NetworkApiClient.getClient().create(NetworkApiInterface.class);
        Call<ItemModel> call = apiInterface.getOnlineStores(params.key);

        //calling the api
        call.enqueue(new Callback<ItemModel>() {
            @Override
            public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {


                if (response.body() != null) {
                    //if the response has next page
                    //incrementing the next page number
                    Integer key = (params.key == response.body().getMeta().getTotal()) ? null : params.key+1;
                    //passing the loaded data and next page value
                    callback.onResult(response.body().getData(), key);
                }



            }

            @Override
            public void onFailure(Call<ItemModel> call, Throwable t) {


                Log.e("json tutor", "ekkrror --> " + t.getMessage());
            }
        });




    }
}
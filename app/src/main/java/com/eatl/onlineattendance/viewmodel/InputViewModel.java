package com.eatl.onlineattendance.viewmodel;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.eatl.onlineattendance.model.AttendanceModel;
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

public class InputViewModel extends AndroidViewModel {


    private final MutableLiveData<Boolean> isLoading=new MutableLiveData<>();

    private MutableLiveData<AttendanceModel> listMutableLiveData = new MutableLiveData<>();


    public InputViewModel(Application application) {
        super(application);


    }

    public LiveData<Boolean> getIsLoading(){
        return isLoading;
    }


    public LiveData<AttendanceModel> setOnlineAttandance(String name,String uid,String latitude,String longitude,String req_id) {

        isLoading.postValue(true);

        NetworkApiInterface apiInterface= NetworkApiClient.getClient().create(NetworkApiInterface.class);


        Call<AttendanceModel> call = apiInterface.setOnlineAttandance(name,uid,latitude,longitude,req_id);

        //calling the api
        call.enqueue(new Callback<AttendanceModel>() {
            @Override
            public void onResponse(Call<AttendanceModel> call, Response<AttendanceModel> response) {


                AttendanceModel attendanceModel=response.body();
                if(attendanceModel!=null) {
                    listMutableLiveData.setValue(attendanceModel);
                }

                isLoading.postValue(false);


            }

            @Override
            public void onFailure(Call<AttendanceModel> call, Throwable t) {

                isLoading.postValue(false);
            }
        });



        return listMutableLiveData;
    }



}

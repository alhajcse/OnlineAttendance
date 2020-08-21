package com.eatl.onlineattendance.network;

import com.eatl.onlineattendance.model.AttendanceModel;
import com.eatl.onlineattendance.model.ItemModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetworkApiInterface {

    // get online stores
    @GET("api/stores")
    Call<ItemModel> getOnlineStores(@Query("page") Integer page);



    // store attendance
    @POST("api/attendance")
    @FormUrlEncoded
    Call<AttendanceModel> setOnlineAttandance(
            @Field("name") String name,
            @Field("uid") String uid,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("request_id") String request_id );


}

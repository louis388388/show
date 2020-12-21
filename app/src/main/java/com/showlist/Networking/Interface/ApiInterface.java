package com.showlist.Networking.Interface;

import com.showlist.Networking.Entity.BaseEntity;
import com.showlist.Networking.Entity.Entity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    //取得多筆資料
    @GET("interview/dramas-sample.json")
    Call<BaseEntity<Entity>> getPosts();

}

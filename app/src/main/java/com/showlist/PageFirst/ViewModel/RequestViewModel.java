package com.showlist.PageFirst.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.showlist.Networking.Entity.BaseEntity;
import com.showlist.Networking.Entity.Entity;
import com.showlist.Networking.networkClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestViewModel extends ViewModel {

    private String search_result;
    public List<Entity> entityList = new ArrayList<>();
    public MutableLiveData<List<Entity>> addEntityMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isInternetAvailable = new MutableLiveData<Boolean>();


    public void getPosts(){
        networkClient.getINSTANCE().getPosts().enqueue(new Callback<BaseEntity<Entity>>() {

            @Override
            public void onResponse(Call<BaseEntity<Entity>> call, Response<BaseEntity<Entity>> response) {
                if (response.body()!= null){

                    entityList.addAll(response.body().getData());
                    addEntityMutableLiveData.setValue(entityList);
                    isInternetAvailable.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<BaseEntity<Entity>> call, Throwable t) {

                Log.d("onFailure", "Failure Code: "+ t);
                isInternetAvailable.setValue(false);
            }
        });
    }

    public String getSearch_result() {
        return search_result;
    }

    public void setSearch_result(String search_result) {
        this.search_result = search_result;
    }
}
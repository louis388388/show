package com.showlist.PageFirst.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.showlist.Networking.Entity.Entity;
import com.showlist.PageFirst.View.Adapter.Adapter;
import com.showlist.PageFirst.ViewModel.RequestViewModel;
import com.showlist.R;
import com.showlist.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import static com.showlist.Util.Index_Struct.*;

public class MainActivity extends AppCompatActivity {

    public static RequestViewModel requestViewModel;
    ActivityMainBinding binding;
    public static Adapter adapter;
    private List<Entity> entityList= new ArrayList<>();
    private List<Entity> showList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        requestViewModel = ViewModelProviders.of(this).get(RequestViewModel.class);

        initValue();  //從sharePreference拿取APP關閉前搜索資料
        setUpLiveDataObserver(); //設定Observer監聽API回傳資料
        setUpRequestAPI(); //API呼叫
        setUpSearchListener(); //動態監聽搜索字串
        setUpCleanButton(); //設定清空搜索按鈕
    }

    private void initValue(){
        requestViewModel.setSearch_result(getSharedPreferences(SEARCH_SHARE_PREFERENCE, MODE_PRIVATE)
                .getString(SEARCH, ""));
        binding.etSearch.setText(requestViewModel.getSearch_result());
    }

    private void setUpRequestAPI(){
        requestViewModel.getPosts();
    }

    private void setUpLiveDataObserver(){
        requestViewModel.addEntityMutableLiveData.observe(this, entities -> {

            entityList.clear();
            entityList.addAll(entities);

            if (requestViewModel.getSearch_result().equals(null)||requestViewModel.getSearch_result().equals("")) {
                adapter = new Adapter(entities);
                binding.rcShowList.setLayoutManager(new LinearLayoutManager(this));
                binding.rcShowList.setAdapter(adapter);
            } else {
                showListView();
            }
        });

        requestViewModel.isInternetAvailable.observe(this, bool -> {

            if (!bool) {
                AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Oops!!!");
                alertDialog.setMessage("網路出現異常，請開確認網路！");
                alertDialog.setPositiveButton("重試", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestViewModel.getPosts();
                    }
                });
                alertDialog.setNeutralButton("關閉App,重新確認網路",(dialog, which) -> {
                    finish();
                });
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });
    }

    private void setUpCleanButton(){
        binding.btnCleanSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etSearch.setText("");
            }
        });
    }

    private void setUpSearchListener(){
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showListView();
                storeSearchHistory(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void showListView() {
        //獲得輸入內容
        String str = binding.etSearch.getText().toString().trim();

        showList.clear();

        for (int i=0; i< entityList.size(); i++){
            if (entityList.get(i).getName().contains(str)){
                showList.add(entityList.get(i));
            }
        }

        adapter = new Adapter(showList);
        binding.rcShowList.setLayoutManager(new LinearLayoutManager(this));
        binding.rcShowList.setAdapter(adapter);

    }

    private void storeSearchHistory(CharSequence charSequence){
        SharedPreferences pref = getSharedPreferences(SEARCH_SHARE_PREFERENCE, MODE_PRIVATE);
        pref.edit()
                .putString(SEARCH, charSequence.toString())
                .commit();
    }
}
package com.showlist.PageSecond.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.showlist.PageSecond.viewmodel.DetailViewModel;
import com.showlist.R;
import com.showlist.Util.Index_Struct;
import com.showlist.databinding.ActivityDetailPageBinding;

public class ActivityDetailPage extends AppCompatActivity {

    ActivityDetailPageBinding activityDetailPageBinding;
    DetailViewModel detailViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityDetailPageBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_page);
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        readingIncomingData(getIntent());
        setContentView();

    }

    private void readingIncomingData(Intent intent){

        if (intent == null || intent.getExtras() == null)
            return;

        if (intent.getExtras().containsKey(Index_Struct.NAME)) {
            detailViewModel.setName(intent.getExtras().getString(Index_Struct.NAME, ""));
        }
        if (intent.getExtras().containsKey(Index_Struct.RATING)) {
            detailViewModel.setRating(intent.getExtras().getFloat(Index_Struct.RATING, 0));
        }
        if (intent.getExtras().containsKey(Index_Struct.CREATE_AT)) {
            detailViewModel.setCreated_at(intent.getExtras().getString(Index_Struct.CREATE_AT, ""));
        }
        if (intent.getExtras().containsKey(Index_Struct.THUMB)) {
            detailViewModel.setThumb(intent.getExtras().getString(Index_Struct.THUMB, ""));
        }
        if (intent.getExtras().containsKey(Index_Struct.TOTAL_VIEWS)) {
            detailViewModel.setTotal_views(intent.getExtras().getInt(Index_Struct.TOTAL_VIEWS, 0));
        }
    }

    private void setContentView(){

        //Set address textView
        activityDetailPageBinding.tvName.setText(detailViewModel.getName());
        activityDetailPageBinding.tvRating.setText("Rating: "+ detailViewModel.getRating());
        activityDetailPageBinding.createAt.setText("Create at: "+detailViewModel.getCreated_at());
        activityDetailPageBinding.tvTotalViews.setText("Views: "+ detailViewModel.getTotal_views());

        //Set Picture drawable
        GlideUrl url = new GlideUrl(detailViewModel.getThumb(), new LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build());

        Glide.with(activityDetailPageBinding.getRoot().getContext()).load(url).into(activityDetailPageBinding.imageView);

        //Set Back button
        activityDetailPageBinding.btnBack.setOnClickListener(v -> finish());

    }

}

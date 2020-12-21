package com.showlist.PageFirst.View.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.showlist.Networking.Entity.Entity;
import com.showlist.R;
import com.showlist.PageSecond.view.ActivityDetailPage;
import com.showlist.Util.Index_Struct;
import com.showlist.Util.TimeConverter;
import com.showlist.databinding.AdapterContentBinding;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private final List<Entity> entityList;
    //時間轉換格式 ISO8601轉一般格式
    private static TimeConverter timeConverter = new TimeConverter("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy/MM/dd HH:mm", "UTC");

    public Adapter (List<Entity> entity){
        entityList = entity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterContentBinding adapterContentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_content, parent, false);
        return new ViewHolder(adapterContentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.updateViewData(entityList.get(position).getName(),
                entityList.get(position).getRating(),
                entityList.get(position).getCreated_at(),
                entityList.get(position).getThumb(),
                entityList.get(position).getTotal_views());
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AdapterContentBinding adapterContentBinding;

        ViewHolder(@NonNull  AdapterContentBinding adapterContentBinding) {
            super(adapterContentBinding.getRoot());
            this.adapterContentBinding = adapterContentBinding;
        }

        private void updateViewData(String name, float rating, String created_at, String thumb, int total_views) {

            adapterContentBinding.tvName.setText(name);
            adapterContentBinding.tvRating.setText("rating: "+rating);
            adapterContentBinding.tvCreatedAt.setText(timeConverter.convert(created_at));

            //Setting Up ImageView 使用Glide Library
            GlideUrl url = new GlideUrl(thumb, new LazyHeaders.Builder()
                    .addHeader("User-Agent", "your-user-agent")
                    .build());
            Glide.with(adapterContentBinding.getRoot().getContext()).load(url).centerCrop().into(adapterContentBinding.imgThumb);

            adapterContentBinding.clDetailColumn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ActivityDetailPage.class);
                    intent.putExtra(Index_Struct.NAME,name);
                    intent.putExtra(Index_Struct.RATING,rating);
                    intent.putExtra(Index_Struct.CREATE_AT,timeConverter.convert(created_at));
                    intent.putExtra(Index_Struct.THUMB,thumb);
                    intent.putExtra(Index_Struct.TOTAL_VIEWS,total_views);

                    v.getContext().startActivity(intent);
                }
            });

        }
    }
}

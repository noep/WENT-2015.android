package org.sopt.appjam.went.Controller;

/**
 * Created by NOEP on 15. 7. 6..
 */

/**

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import org.sopt.appjam.went.Communication.AppController;
import org.sopt.appjam.went.Model.Photo;
import org.sopt.appjam.went.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Endpoint;

class PhotoViewHolder extends RecyclerView.ViewHolder {

    private Context context;

    @InjectView(R.id.ImageView_pic)
    ImageView imageViewPhoto;

    @InjectView(R.id.editText_title)
    EditText textViewTitle;

    public PhotoViewHolder(View view) {

        super(view);

        this.context = view.getContext();
        ButterKnife.inject(this, view);

    }

    public void set(Photo photo) {

        textViewTitle.setText(photo.title);
        //String endpoint = AppController.getInstance().getEndpoint();
        String endpoint = AppController.ENDPOINT;

        if (TextUtils.isEmpty(endpoint))
            return;

        Glide.with(context).load(endpoint.concat(String.format("/photos/%d/image", photo.id))).into(imageViewPhoto);
    }
}

public class PhotoAdapter extends RecyclerView.Adapter {

    private ArrayList<Photo> source = new ArrayList<>();
    public void setSource(ArrayList<Photo> source) {

        this.source.clear();
        this.source.addAll(source);

        this.notifyDataSetChanged();
    }

    public void push(Photo photo) {

        this.source.add(0, photo);
        this.notifyDataSetChanged();
    }

    public void clear() {

        this.source.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup group, int type) {

        View view = View.inflate(group.getContext(), R.layout.layout_photo_item, null);
        PhotoViewHolder holder = new PhotoViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Photo photo = source.get(position);
        ((PhotoViewHolder) holder).set(photo);
    }

    @Override
    public int getItemCount() {
        return source != null ? source.size() : 0;
    }
}

 필요없음
 */
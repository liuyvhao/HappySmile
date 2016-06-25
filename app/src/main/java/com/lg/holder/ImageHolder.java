package com.lg.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lg.happysmile.R;

public class ImageHolder extends RecyclerView.ViewHolder {
    private TextView img_title;
    private SimpleDraweeView img;
    private LinearLayout img_line;
    private ProgressBar img_pro;

    public ImageHolder(View itemView) {
        super(itemView);
        img_title = (TextView) itemView.findViewById(R.id.img_title);
        img = (SimpleDraweeView) itemView.findViewById(R.id.img);
        img_line = (LinearLayout) itemView.findViewById(R.id.img_line);
        img_pro=(ProgressBar)itemView.findViewById(R.id.img_pro);
    }

    public TextView getImg_title() {
        return img_title;
    }

    public SimpleDraweeView getImg() {
        return img;
    }

    public LinearLayout getImg_line() {
        return img_line;
    }

    public ProgressBar getImg_pro() {
        return img_pro;
    }
}

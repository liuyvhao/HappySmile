package com.lg.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lg.happysmile.R;

public class CarefullyHolder extends RecyclerView.ViewHolder {
    private TextView carefully_tv;
    private LinearLayout carefully_line;
    private ProgressBar carefully_pro;

    public CarefullyHolder(View itemView) {
        super(itemView);
        carefully_tv = (TextView) itemView.findViewById(R.id.carefully_tv);
        carefully_line = (LinearLayout) itemView.findViewById(R.id.carefully_line);
        carefully_pro=(ProgressBar)itemView.findViewById(R.id.carefully_pro);
    }

    public ProgressBar getCarefully_pro() {
        return carefully_pro;
    }

    public LinearLayout getCarefully_line() {
        return carefully_line;
    }

    public TextView getCarefully_tv() {
        return carefully_tv;
    }
}

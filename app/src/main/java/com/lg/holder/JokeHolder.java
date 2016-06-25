package com.lg.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lg.happysmile.R;

public class JokeHolder extends RecyclerView.ViewHolder {
    private TextView joke_tv;
    private LinearLayout joke_line;
    private ProgressBar joke_pro;

    public JokeHolder(View itemView) {
        super(itemView);
        joke_tv = (TextView) itemView.findViewById(R.id.joke_tv);
        joke_line = (LinearLayout) itemView.findViewById(R.id.joke_line);
        joke_pro = (ProgressBar) itemView.findViewById(R.id.joke_pro);
    }

    public ProgressBar getJoke_pro() {
        return joke_pro;
    }

    public LinearLayout getJoke_line() {
        return joke_line;
    }

    public TextView getJoke_tv() {
        return joke_tv;
    }
}

package com.lg.fragment;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lg.adapter.CarefullyAdapter;
import com.lg.happysmile.R;
import com.lg.service.SmileService;
import com.lg.util.DividerLinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CarefullyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private SwipeRefreshLayout carefully_srl;
    public static int num;
    public static List<String> strings;
    public static RecyclerView carefully_recycler;
    public static CarefullyAdapter carefully_dapter;
    public static LinearLayout carefully_frg_line;
    private static LinearLayout carefully_error;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_carefully, container, false);
        init();
        CarefullyAsyncTask carefullyAsyncTask = new CarefullyAsyncTask();
        carefullyAsyncTask.execute();
        return view;
    }

    private void init() {
        //初始页数为1
        num = 1;
        strings = new ArrayList<>();
        carefully_recycler = (RecyclerView) view.findViewById(R.id.carefully_recycler);
        carefully_frg_line = (LinearLayout) view.findViewById(R.id.carefully_frg_line);
        carefully_srl = (SwipeRefreshLayout) view.findViewById(R.id.carefully_srl);
        carefully_error = (LinearLayout) view.findViewById(R.id.carefully_error);
        //设置下拉刷新
        carefully_srl.setOnRefreshListener(this);
        carefully_dapter = new CarefullyAdapter();
        //设置显示模式
        carefully_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //画分割线
        carefully_recycler.addItemDecoration(new DividerLinearItemDecoration(getContext(), DividerLinearItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onRefresh() {
        //重回第一页
        num = 1;
        //清除list数据
        strings.clear();
        //刷新adapter
        carefully_dapter.notifyDataSetChanged();
        //显示刷新页面
        carefully_frg_line.setVisibility(View.VISIBLE);
        //异步加载数据
        new CarefullyAsyncTask().execute();
        carefully_srl.setRefreshing(false);
    }

    public static class CarefullyAsyncTask extends AsyncTask {
        @Override
        protected void onPostExecute(Object result) {
            try {
                getData(result);
                //只是指一次adapter
                if (num == 1)
                    carefully_recycler.setAdapter(carefully_dapter);
            } catch (Exception e) {
                carefully_frg_line.setVisibility(View.GONE);
                carefully_error.setVisibility(View.VISIBLE);
            }
        }

        String httpUrl = "http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text";
        String httpArg = "page=" + num;

        @Override
        protected Object doInBackground(Object... params) {
            return SmileService.request(httpUrl, httpArg);
        }
    }

    public static void getData(Object jsonResult) {
        JSONObject result = JSON.parseObject((String) jsonResult);
        JSONObject contentlist = JSON.parseObject(result.get("showapi_res_body").toString());
        JSONArray listarray = JSONArray.parseArray(contentlist.get("contentlist").toString());
        for (int i = 0; i < 20; i++) {
            JSONObject joke = JSON.parseObject(listarray.get(i).toString());
            String str = joke.get("text").toString();
            str = str.replaceAll("<p>", "");
            str = str.replaceAll("</p>", "");
            str = str.replaceAll("\n\n", "");
            str = str.replaceAll("\n", "\n        ");
            str = str.replaceAll("\t", "");
            strings.add("        " + str);
        }
        //当加载出数据时，加载页面隐藏
        if (strings.size() != 0) {
            carefully_frg_line.setVisibility(View.GONE);
            carefully_error.setVisibility(View.GONE);
        }
        //当不是第一页时，隐藏上拉ProgressBar
        if (num != 1)
            carefully_dapter.carefullyholder.getCarefully_pro().setVisibility(View.GONE);
    }
}

package com.kokonote.irkitcontrol.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kokonote.irkitcontrol.R;
import com.kokonote.irkitcontrol.nw.GsonRequest;

/**
 * 赤外線通信情報追加画面フラグメント
 * Created by kuriyama on 2014/07/26.
 */
public class InfaredLightInputFragment extends Fragment{

    private RequestQueue mQueue;

    /**
     * インスタンス生成
     * @return インスタンス
     */
    public static InfaredLightInputFragment newInstance(){
        return new InfaredLightInputFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_infared_light_input,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRequest();

    }

    private void initRequest() {
        if(mQueue == null) {
            mQueue = Volley.newRequestQueue(getActivity());
        }


    }
}

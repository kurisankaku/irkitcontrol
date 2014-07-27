package com.kokonote.irkitcontrol.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.kokonote.irkitcontrol.R;
import com.kokonote.irkitcontrol.ds.dao.PostHistoryDao;
import com.kokonote.irkitcontrol.ds.entity.InfaredLightData;
import com.kokonote.irkitcontrol.ds.entity.PostHistory;
import com.kokonote.irkitcontrol.fragment.adapter.PostHistoryAdapter;
import com.kokonote.irkitcontrol.nw.GsonRequest;
import com.kokonote.irkitcontrol.util.DateTimeUtils;
import com.kokonote.irkitcontrol.view.MyProgressDialogFragment;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kuriyama on 2014/07/26.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private RequestQueue queue;

    /**
     * GridView
     */
    private GridView gridView;

    private MyProgressDialogFragment dialogFragment;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListAdapter adapter = createListAdapter();

        queue = Volley.newRequestQueue(getActivity());
        gridView.setAdapter(adapter);
    }

    private ListAdapter createListAdapter() {
        PostHistoryDao dao = new PostHistoryDao(getActivity());
        ListAdapter adapter = new PostHistoryAdapter(getActivity(),R.layout.item_post_history,dao.findAll());
        return adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        view.findViewById(R.id.fragment_main_aircon_off).setOnClickListener(this);
        view.findViewById(R.id.fragment_main_aircon_on).setOnClickListener(this);
        view.findViewById(R.id.fragment_main_fan_swing).setOnClickListener(this);
        view.findViewById(R.id.fragment_main_fan_switch).setOnClickListener(this);
        view.findViewById(R.id.fragment_main_fan_power_up).setOnClickListener(this);
        view.findViewById(R.id.fragment_main_reibou).setOnClickListener(this);

        gridView = (GridView) view.findViewById(R.id.fragment_main_grid);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onClick(View v) {
        dialogFragment = MyProgressDialogFragment.newInstance("","送信中");
        dialogFragment.show(getFragmentManager(),null);

        Message msg = Message.obtain();
        switch (v.getId()) {
            case R.id.fragment_main_aircon_on:
                msg.obj = AIRCON_ON;
                send(msg,1,"エアコンON");
                break;
            case R.id.fragment_main_aircon_off:
                msg.obj = AIRCON_OFF;
                send(msg,1,"エアコンOFF");

                break;
            case R.id.fragment_main_fan_switch:
                msg.obj = FUN_ON;
                send(msg,1,"扇風機スイッチ");

                break;
            case R.id.fragment_main_fan_swing:
                msg.obj = FUN_SWING;
                send(msg,1,"扇風機首振り");

                break;
            case R.id.fragment_main_fan_power_up:
                msg.obj = FUN_POWER_UP;
                send(msg,1,"扇風機風力UP");

                break;
            case R.id.fragment_main_reibou:
                Message msg1 = Message.obtain();
                msg1.what = 1;
                msg1.obj = AIRCON_ON;
                send(msg1,1,"エアコンON");

                Message msg2 = Message.obtain();
                msg2.what = 2;
                msg2.obj = FUN_ON;
                send(msg2,2,"扇風機スイッチ");

                Message msg3 = Message.obtain();
                msg3.what = 3;
                msg3.obj = FUN_SWING;
                send(msg3,3,"扇風機首振り");

                break;
        }
    }



    private void send(final Message msg, int count,String title){
        PostHistoryDao dao = new PostHistoryDao(getActivity());
        PostHistory entity = new PostHistory();
        entity.title = title;
        entity.datetime = DateTimeUtils.dateFormatNow();
        dao.save(entity);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendMessage(msg);
            }
        },count * 1000);
    }

    private void postMessageToIrkit(HashMap<String, String> params) {
        GsonRequest<InfaredLightData> request = new GsonRequest<InfaredLightData>(Request.Method.POST, "https://api.getirkit.com/1/messages", InfaredLightData.class,
                params, successListener, errorListener);

        queue.add(request);
    }

    private HashMap<String, String> createParams(String message) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("clientkey", "");
        map.put("deviceid", "");
        map.put("message", message);

        return map;
    }

    private Response.Listener<InfaredLightData> successListener = new Response.Listener<InfaredLightData>() {

        @Override
        public void onResponse(InfaredLightData response) {
           ArrayAdapter<PostHistory> adapter = (ArrayAdapter<PostHistory>) gridView.getAdapter();
            adapter.clear();
            adapter.addAll(new PostHistoryDao(getActivity()).findAll());
            dismissDialog();
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            dismissDialog();
            Toast.makeText(getActivity(),"送信に失敗しました",Toast.LENGTH_SHORT).show();
        }

    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("test", ""+msg.what);
            postMessageToIrkit(createParams((String) msg.obj));
        }
    };

    private void dismissDialog(){
        if(dialogFragment != null && dialogFragment.getDialog() != null){
            dialogFragment.getDialog().dismiss();
        }
    }

    private static final String FUN_ON = "{\"format\":\"raw\",\"freq\":38,\"data\":[18031,9061,1150,3458,1150,1037,1232,3341,1232,1232,1073,1073,1073,3458,1073,3458,1232,3341,1232,1037,1037,3579,1150,1150,1037,1150,1150,3341,1275,1002,1232,3341,1319,3341,1319,968,1150,1150,1150,968,1275,1037,1275,1037,1150,1150,1150,1150,1150,3458,1150,3458,1150,3458,1150,3458,1150,3458,1150,3458,1150,3458,1150,3458,1150,1150,1150,65535,0,6881,18031,4554,1111,65535,0,65535,0,60108,18031,4400,1002,65535,0,65535,0,60108,18031,4554,1111,65535,0,65535,0,60108,18031,4554,1150]}";
    private static final String FUN_SWING = "{\"format\":\"raw\",\"freq\":38,\"data\":[18031,9061,1232,3341,1232,1232,1111,3458,1111,1111,1319,1002,1319,3341,1232,3341,1111,3458,1111,1111,1232,3341,1232,1037,1232,1037,1232,3341,1190,1190,1190,3341,1190,3341,1190,1190,1190,1002,1514,1150,1002,1150,1150,1150,1150,1150,1150,3341,1232,3341,1232,3341,1111,3458,1232,3341,1111,3458,1111,3458,1232,3341,1232,1037,1232,1037,1366,65535,0,6424,18031,4554,1190]}";
    private static final String FUN_POWER_UP = "{\"format\":\"raw\",\"freq\":38,\"data\":[18031,9061,1232,3341,1111,1111,1111,3341,1366,1037,1150,1150,1150,3458,1150,3458,1150,3458,1150,1150,1150,3458,1150,1037,1275,1037,1275,3341,1111,1111,1111,3341,1111,3341,1111,1111,1111,1111,1366,1232,1232,1232,1111,1111,1111,1111,1002,3458,1232,1002,1232,3341,1190,3341,1190,3341,1190,3341,1073,3458,1073,3458,1275,1037,1275,3341,1366,65535,0,6424,18031,4554,1190]}";
    private static final String AIRCON_ON = "{\"format\":\"raw\",\"freq\":38,\"data\":[6881,3341,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,2537,904,2537,904,815,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,2537,904,815,968,815,968,815,968,815,968,815,968,19315,6881,3341,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,2537,904,2537,904,815,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,815,904,815,904,815,904,2537,904,2537,904,815,904,815,904,815,904,2537,904,2537,904,2537,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,2537,904,2537,904,2537,904,2537,904,815,904,2537,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,2537,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,815,904,2537,904,2537,904]}";
    private static final String AIRCON_OFF = "{\"format\":\"raw\",\"freq\":38,\"data\":[6881,3341,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,2537,904,2537,904,2537,904,787,904,787,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,2537,904,815,904,815,904,815,904,815,904,815,904,19315,6881,3341,904,815,904,2537,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,2537,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,2537,904,2537,904,2537,904,815,904,815,904,2537,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,787,904,2537,904,2537,904,787,904,787,904,787,904,2537,904,2537,904,2537,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,2537,904,2537,904,2537,904,2537,904,787,904,2537,904,787,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,815,904,2537,904,2537,904,815,904,2537,904,815,904,815,904,815,904,2537,904,2537,904,2537,904,2537,904,815,904,815,904,2537,904,2537,904]}";


}

package com.kokonote.irkitcontrol.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kokonote.irkitcontrol.R;

/**
 * 赤外線入力画面
 * Created by kuriyama on 2014/07/26.
 */
public class InfaredLightInputActivity extends Activity{

    /**
     * Intentを生成する
     * @param context コンテキスト
     * @return Intent
     */
    public static Intent createIntent(Context context){
        Intent intent = new Intent(context,InfaredLightInputActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infared_light_input);

        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.activity_infared_light_input_linearlayout,null);
        tran.commit();
    }
}

package com.kokonote.irkitcontrol.ds.entity;

import android.provider.BaseColumns;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 赤外線データテーブル
 * Created by kuriyama on 2014/07/26.
 */
@DatabaseTable(tableName = "infared_light_data")
public class InfaredLightData{

    /**
     * ID
     */
    @DatabaseField(generatedId = true)
    public int _id;

    /**
     * デバイスID
     */
    @DatabaseField(canBeNull = false)
    public String deviceId;

    /**
     * フォーマット<br/>
     * irkitからのデータ受信時の指定フォーマット値
     */
    @DatabaseField(canBeNull = false)
    public String format;

    /**
     * irkitからのデータ受信時の指定freq値
     */
    @DatabaseField(canBeNull = false)
    public int freq;

    /**
     * irkitからのデータ受信時の指定data値
     */
    @DatabaseField(canBeNull = false)
    public String data;

    /**
     * データ名
     */
    @DatabaseField(canBeNull = false)
    public String name;

    /**
     * 画像IconリソースID
     */
    @DatabaseField(canBeNull = false)
    public int imageId;
}

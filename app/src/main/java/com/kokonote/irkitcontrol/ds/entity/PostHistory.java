package com.kokonote.irkitcontrol.ds.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 送信履歴
 * Created by kuriyama on 2014/07/27.
 */
@DatabaseTable(tableName = "post_history")
public class PostHistory {

    /**
     * ID
     */
    @DatabaseField(generatedId = true)
    public int _id;
    /**
     * 送信タイトル
     */
    @DatabaseField(canBeNull = false)
    public String title;
    /**
     * 日付
     */
    @DatabaseField(canBeNull = false)
    public String datetime;

}

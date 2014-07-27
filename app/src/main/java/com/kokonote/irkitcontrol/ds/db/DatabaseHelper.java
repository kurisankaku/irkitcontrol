package com.kokonote.irkitcontrol.ds.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kokonote.irkitcontrol.ds.entity.InfaredLightData;
import com.kokonote.irkitcontrol.ds.entity.PostHistory;

import java.sql.SQLException;

/**
 * DatabaseHelper
 * Created by kuriyama on 2014/07/26.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "irkitcontrol.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper mDatabaseHelper;

    /**
     * インスタンスを取得する
     * @param context コンテキスト
     * @return インスタンス
     */
    public static synchronized DatabaseHelper getInstance(Context context){
        if(mDatabaseHelper == null){
            mDatabaseHelper = new DatabaseHelper(context);
        }
        return mDatabaseHelper;
    }

    /**
     * コンストラクタ
     * @param context コンテキスト
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, InfaredLightData.class);
            TableUtils.createTable(connectionSource, PostHistory.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getCanonicalName(), "データベースを作成できませんでした", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}

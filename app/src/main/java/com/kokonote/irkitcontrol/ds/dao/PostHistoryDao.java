package com.kokonote.irkitcontrol.ds.dao;

import android.content.Context;
import android.provider.BaseColumns;
import android.provider.SyncStateContract;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.kokonote.irkitcontrol.ds.db.DatabaseHelper;
import com.kokonote.irkitcontrol.ds.entity.InfaredLightData;
import com.kokonote.irkitcontrol.ds.entity.PostHistory;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by kuriyama on 2014/07/27.
 */
public class PostHistoryDao {

    private DatabaseHelper mDatabaseHelper;

    public PostHistoryDao(Context context){
        mDatabaseHelper = DatabaseHelper.getInstance(context);
    }

    /**
     * データを取得する
     * @return
     */
    public List<PostHistory> findAll(){
        try {
            Dao<PostHistory,Integer> dao = mDatabaseHelper.getDao(PostHistory.class);
            QueryBuilder<PostHistory,Integer> builder = dao.queryBuilder().orderBy(BaseColumns._ID,false);
            return builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * データを保存する
     * @param postHistory
     */
    public void save(PostHistory postHistory) {
        try {
            Dao<PostHistory, Integer> dao = mDatabaseHelper.getDao(PostHistory.class);
            dao.createOrUpdate(postHistory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * データを削除する
     * @param id キー
     */
    public void delete(int id){
        try {
            Dao<PostHistory,Integer> dao = mDatabaseHelper.getDao(PostHistory.class);
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

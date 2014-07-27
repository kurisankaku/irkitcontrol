package com.kokonote.irkitcontrol.ds.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.kokonote.irkitcontrol.ds.db.DatabaseHelper;
import com.kokonote.irkitcontrol.ds.entity.InfaredLightData;

import java.sql.SQLException;
import java.util.List;

/**
 * 赤外線データDao
 * Created by kuriyama on 2014/07/26.
 */
public class InfaredLightDataDao {

    private DatabaseHelper mDatabaseHelper;

    /**
     * コンストラクタ
     * @param context コンテクスト
     */
    public InfaredLightDataDao(Context context){
        mDatabaseHelper = DatabaseHelper.getInstance(context);
    }

    /**
     * 赤外線データを全取得する
     * @return
     */
    public List<InfaredLightData> findAll(){
        try {
            Dao<InfaredLightData,Integer> dao = mDatabaseHelper.getDao(InfaredLightData.class);
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * データを保存する
     * @param infaredLightData
     */
    public void save(InfaredLightData infaredLightData) {
        try {
            Dao<InfaredLightData, Integer> dao = mDatabaseHelper.getDao(InfaredLightData.class);
            dao.createOrUpdate(infaredLightData);
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
            Dao<InfaredLightData,Integer> dao = mDatabaseHelper.getDao(InfaredLightData.class);
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package adel.twitterclient.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import adel.twitterclient.R;
import adel.twitterclient.businessModel.DTO.FollowerInfo;

/**
 * Created by adelhegazy on 7/6/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "adeltwitterclient.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<FollowerInfo, Integer> followerInfoDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, FollowerInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, FollowerInfo.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<FollowerInfo, Integer> getFollowerInfoDao() throws SQLException {
        if (followerInfoDao == null) {
            followerInfoDao = getDao(FollowerInfo.class);
        }
        return followerInfoDao;
    }

    @Override
    public void close() {
        followerInfoDao = null;
        super.close();
    }
}

package endless.probidercilent;

import android.provider.BaseColumns;

/**
 * Created by z on 2016/8/30.
 */
public class TypeEntry implements BaseColumns {
    //表名
    public static final String TABLE_NAME = "PhoneType";
    //列名
    public static final String COLUMMNS_NAME_TYPE = "type";
    public static final String COLUMMNS_NAME_SUBTABLE = "subtable";
    //创建表格的SQL语句
    public static final String SQL_CREATE_TABLE =
            "create table " + TABLE_NAME + " (" +
                    _ID + " integer primary key," +
                    COLUMMNS_NAME_TYPE + " text," +
                    COLUMMNS_NAME_SUBTABLE + " text" + ")";
    //删除表格的SQL语句
    public static final String SQL_DELETE_TABLE =
            "drop table if exists " + TABLE_NAME;
}
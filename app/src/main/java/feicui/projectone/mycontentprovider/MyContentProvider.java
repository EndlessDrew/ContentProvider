package feicui.projectone.mycontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;



/**
 * 通讯录的内容提供者
 * Created by z on 2016/9/12.
 */
public class MyContentProvider extends ContentProvider {
    //访问PhoneType表类型的uri
    private static final int URI_CODE_PHONEYTPE = 0;
    //实例化一个urimatcher对象
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        //给内容提供者添加一条匹配MyContactsContract.PHONETYPE的uri,uri类型为URI_CODE_PHONEYTPE
        uriMatcher.addURI(MyContactsContract.AUTHORITY,
                MyContactsContract.PHONETYPE,
                URI_CODE_PHONEYTPE);
    }


    @Override
    public boolean onCreate() {

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //获取可读的database对象，通过打开固定路径的方式
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                MyContactsContract.DATABASE_PATH, null
        );

        //要返回给用户的游标
        Cursor mCursor = null;
        //使用urimatcher类匹配uri返回uri的类型
        switch (uriMatcher.match(uri)) {
            //匹配到PhonType表的uri
            case URI_CODE_PHONEYTPE:
                //查询数据库,返回一个游标
                mCursor = db.query(MyContactsContract.PHONETYPE, //表名
                        projection,//COLUMNS
                        selection, //WHERE
                        selectionArgs, //Where args
                        null, //GROUP BY
                        null, //HAVING
                        sortOrder //ORDER BY
                );
                break;
        }

        return mCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        //返回给用户的MIME类型
        String mType = null;
        //分析匹配的uri
        switch (uriMatcher.match(uri)) {
            //查询PhoneType表的uri
            case URI_CODE_PHONEYTPE:
                mType = "vnd.android.cursor.dir/" + MyContactsContract.AUTHORITY + "." + MyContactsContract.PHONETYPE;
                break;
        }
        return mType;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //获取可读的database对象，通过打开固定路径的方式
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                MyContactsContract.DATABASE_PATH, null
        );
        //新插入行的id
        long id = 0;
        //分析匹配的uri
        switch (uriMatcher.match(uri)) {
            //插入PhoneType表的uri
            case URI_CODE_PHONEYTPE:
                //插入一行
                id = db.insert(MyContactsContract.PHONETYPE, //表名
                        null,
                        values
                );
                //关闭数据库
                db.close();
                break;
        }
        //返回新插入的uri
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //获取可读的database对象，通过打开固定路径的方式
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                MyContactsContract.DATABASE_PATH, null
        );
        //成功匹配删除的条目
        int i_delete = 0;
        //分析匹配的uri
        switch (uriMatcher.match(uri)) {
            //从PhoneType表中删除内容
            case URI_CODE_PHONEYTPE:
                //删除某行
                i_delete = db.delete(MyContactsContract.PHONETYPE, //表名
                        selection,//选择条件
                        selectionArgs//选择参数
                );
                //关闭数据库
                db.close();
                break;
        }
        //返回删除的条数
        return i_delete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        //获取可读的database对象，通过打开固定路径的方式
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                MyContactsContract.DATABASE_PATH, null
        );
        //更新的条数
        int i_update = 0;
        //分析匹配的uri
        switch (uriMatcher.match(uri)) {
            //在PhoneType中更新的uri
            case URI_CODE_PHONEYTPE:
                //更新PhoneType表中某一行的值
                i_update = db.update(MyContactsContract.PHONETYPE, //表名
                        values,//更新的值
                        selection,//选择条件
                        selectionArgs//选择参数
                );
                //关闭数据库
                db.close();
                break;
        }
        //返回更新的条数
        return i_update;
    }
}


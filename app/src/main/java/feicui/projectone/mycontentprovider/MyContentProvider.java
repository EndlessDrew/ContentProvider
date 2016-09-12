package feicui.projectone.mycontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import feicui.projectone.sqlite.TypeEntry;


/**
 * 通讯录的内容提供者
 * Created by z on 2016/9/12.
 */
public class MyContentProvider extends ContentProvider {
    private static final UriMatcher urimatcher = new UriMatcher(UriMatcher.NO_MATCH );
    static {
        urimatcher.addURI("feicui.projectone.provider", TypeEntry.TABLE_NAME,1);
    }



    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {
        //获取可读的database的对象，通过打开固定的文件路径方式
        SQLiteDatabase db= SQLiteDatabase.openOrCreateDatabase(TypeEntry.DATABASE_PATH +"/phone.db",null);
        //要返回给用户的游标
        Cursor mcursor =null;
        //使用urimatcher类匹配返回uri类型
        switch (urimatcher.match(uri)){
            //匹配到PhoneType表的uri
            case 1:
                //查询数据库，返回一个游标
                mcursor=db.query(
                        TypeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                        );

        }
        return mcursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        //返回给用户的MIME类型
        String mType =null ;
        //分析匹配的uri
        switch (urimatcher.match(uri)){
            //查询phonetype表的uri
            case 1:
                mType = "vnd.android.cursor.dir/feicui.projectone.PhoneType";
                break;
        }


        return mType;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

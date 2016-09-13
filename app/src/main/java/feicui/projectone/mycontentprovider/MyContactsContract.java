package feicui.projectone.mycontentprovider;

import android.net.Uri;

/**
 * 我的联系人内容提供者协定类
 * Created by z on 2016/9/13.
 */
public class MyContactsContract {
    public static final String  AUTHORITY="feicui.projectone";
    public static final Uri     AUTHORITY_URI=Uri.parse("content://"+AUTHORITY);
    public static final String  PHONETYPE ="PhoneType";
    public static final String TYPENAME = "type";
    public static final String ID = "_id";
    public static final Uri  PHONETYPE_URI = Uri.withAppendedPath(AUTHORITY_URI,PHONETYPE);
    public static final String DATABASE_PATH = "/data/data/feicui.projectone/databases/phone.db";
}

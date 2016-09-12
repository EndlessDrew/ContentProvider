package endless.probidercilent;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView tv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_data=(TextView)findViewById(R.id.tv_date);
        queryProvider();

    }

    private void queryProvider(){
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://feicui.projectone.provider/PhoneType"),//自定义的uri地址
                new String[]{"*"},
                null,
                null,
                null
        );
        //临时保存数据的字符
        String temp ="";
        if(cursor!=null&& cursor.getCount()>0){
            cursor.moveToFirst();
            int i_typeNameindex = cursor.getColumnIndexOrThrow(TypeEntry.COLUMMNS_NAME_TYPE);
            do{
                //获取相应下标的类型名称
                String typeName = cursor.getString(i_typeNameindex);
                temp +="TypeName :"+typeName + "\n\n";
            }while(cursor.moveToNext());
            tv_data.setText(temp);
        }


    }
}

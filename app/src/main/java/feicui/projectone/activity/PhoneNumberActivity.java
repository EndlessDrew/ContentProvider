package feicui.projectone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import feicui.projectone.R;
import feicui.projectone.adapter.PhoneNumberAdapter;
import feicui.projectone.entity.PhoneNumberEntity;
import feicui.projectone.entity.PhoneTypeEntity;
import feicui.projectone.sqlite.NumberEntry;
import feicui.projectone.sqlite.TypeEntry;

public class PhoneNumberActivity extends BaseActivity {


    //显示电话号码的列表
    ListView lv_phoneNumber;
    //电话号码列表的适配器
    PhoneNumberAdapter pnAdapter;
    //loading界面
    LinearLayout ll_loading;

    @Override
    protected int setContent() {
        return R.layout.activity_phone_number;
    }

    @Override
    protected void initView() {
        lv_phoneNumber=(ListView)findViewById(R.id.lv_phoneNumber);
        ll_loading=(LinearLayout)findViewById(R.id.ll_loading);
        new InitTask().execute();

    }

    @Override
    protected void setListener() {

    }
    //异步初始化操作任务类

    class InitTask extends AsyncTask<Void ,Void, Void>{
        //任务启动后再异步线程中执行的代码，不可以操作UI
        @Override
        protected Void doInBackground(Void... params) {
            //装载ListView
            initList();
            return null;
        }
        //任务启动之前执行的代码，可以操作UI
        protected  void  onPreExecute(){
            //让loading界面显示
            ll_loading.setVisibility(View.VISIBLE);
        }
        //任务完成后执行的代码，可以操作UI
        protected  void onPostExecute(Void aVoid){
            //隐藏loading界面
            ll_loading.setVisibility(View.GONE);
            //给列表设置适配器
            lv_phoneNumber.setAdapter(pnAdapter);
        }
    }



    private  void initList(){
        //获取可读的database对象，通过打开固定的路径的方式
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(TypeEntry.DATABASE_PATH + "/phone.db",null);
        //获取当前应加载的数据库表名
        String subTable=getIntent().getStringExtra(TypeEntry.COLUMNS_NAME_SUBTABLE);

        //查询数据库，返回一个游标
        Cursor cursor=db.query(subTable,new String[]{"*"},null,null,null,null,null);
        //将游标移动到第一行
        cursor.moveToFirst();
        final  ArrayList<PhoneNumberEntity> entities=new ArrayList<PhoneNumberEntity>();
        do{
            //将游标中的数据取出
            //获取电话类型名字
            String phoneName=cursor.getString(cursor.getColumnIndexOrThrow(NumberEntry.COLUMNS_NAME_NAME));
            //获取电话号码
            String phoneNumber=cursor.getString(cursor.getColumnIndexOrThrow(NumberEntry.COLUMNS_NAME_NUMBER));
            PhoneNumberEntity entity=new PhoneNumberEntity();
            entity.setPhoneName(phoneName);
            entity.setPhoneNumber(phoneNumber);
            entities.add(entity);
        }while(cursor.moveToNext());
        //初始化适配器
        pnAdapter=new PhoneNumberAdapter(this,entities);
        lv_phoneNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                //创建询问是否拨打电话的对话框
                new AlertDialog.Builder(PhoneNumberActivity.this)
                        .setTitle("警告")//标题
                        .setMessage(
                                "是否开始拨打"
                                        + entities.get(position).getPhoneName()
                                        + "电话"
                                        + "\n"
                                        + "TEL:" + entities.get(position).getPhoneNumber())//对话框信息
                        .setPositiveButton(
                                "拨号",
                                //点击拨号后的监听
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //拨打当前点击的电话号码
                                        Intent intent = new Intent(
                                                "android.intent.action.CALL",
                                                Uri.parse("tel:" + entities.get(position).getPhoneNumber()));
                                        startActivity(intent);
                                    }
                                })//确认按钮
                        .setNegativeButton("取消", null)//取消按钮
                        .show();//显示警告框
            }
        });
    }
}

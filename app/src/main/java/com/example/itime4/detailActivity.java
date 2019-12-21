package com.example.itime4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class detailActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 901;
    public static final int REQUEST_CODE1 = 903;
    int  PictureResource,position,color_choose=0;
    Bundle bundle;
    AllData date,date2;
   CountDownTimer downTimer1,downTimer2;
   Time time1=new Time();
   Time time2=new Time();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // if(resultCode == RESULT_OK) {
        //Toast.makeText(detailActivity.this, "你是超级大傻瓜", Toast.LENGTH_SHORT).show();
        // switch (requestCode) {
        // case REQUEST_CODE:
        Intent data=getIntent();
        color_choose=data.getIntExtra("color", -1);
        if(data.getIntExtra("color", -1)!=0) {

            FloatingActionButton but1=findViewById(R.id.backBut_detail);
            but1.setBackgroundTintList(ColorStateList.valueOf(data.getIntExtra("color", -1)));
            FloatingActionButton but2=findViewById(R.id.deleteBut_detail);
            but2.setBackgroundTintList(ColorStateList.valueOf(data.getIntExtra("color", -1)));
            FloatingActionButton but3=findViewById(R.id.editBut_detail);
            but3.setBackgroundTintList(ColorStateList.valueOf(data.getIntExtra("color", -1)));
        }
       position=data.getIntExtra("position",-1);
        bundle = data.getExtras();
        date = (AllData) bundle.getSerializable("bb");
        date2=date;

        TextView title_detail=findViewById(R.id.title_detail);
        title_detail.setText(date.getTitleStr());
        TextView time_detail= findViewById(R.id.time_detail);
        time_detail.setText(date.getTimeStr());

        Context cet = getBaseContext();
        PictureResource = getResources().getIdentifier(date.getPicture_Str(),"drawable",cet.getPackageName());//字符换图片

        ImageView picture_detail=findViewById(R.id.picture_detail);
        picture_detail.setImageResource(PictureResource);
        final TextView countdown_detail = findViewById(R.id.countdown_detail);
        time1.init(date.getDate());
        time1.setTextViewshow1(countdown_detail);
        time1.start();
        /*
        downTimer1 = new CountDownTimer(transformTime(date.getDate()),1000) {

            @Override
            public void onTick(long millisUntilFinished) {


                countdown_detail.setText("还有"+formatTime(millisUntilFinished));
            }

            //倒计时结束后的操作
            @Override
            public void onFinish() {
                 countdown_detail.setText("");

            }


        };
        downTimer1.start();

         */

        //设置返回的点击事件
        FloatingActionButton back=findViewById(R.id.backBut_detail);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("position",position);
                Bundle bundle2=new Bundle();
                bundle2.putSerializable("dd",(Serializable) date2);
                intent.putExtras(bundle2);
                setResult(RESULT_FIRST_USER,intent);
                finish();
            }
        });
        //设置删除的点击事件
        FloatingActionButton delete=findViewById(R.id.deleteBut_detail);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连接两个不同的组件。
                // 在存放资源代码的文件夹下下，
                Intent i = new Intent();
                i.putExtra("deleteposition",position);
                Bundle bundle2=new Bundle();
                bundle2.putSerializable("ee",(Serializable) date);
               setResult(RESULT_CANCELED,i);
               finish();
            }
        });
        //设置编辑的点击事件
        FloatingActionButton edit=findViewById(R.id.editBut_detail);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连接两个不同的组件。
                // 在存放资源代码的文件夹下下，
                Intent i = new Intent(detailActivity.this,selection.class);
                i.putExtra("color",color_choose);
                i.putExtra("deleteposition",position);
                Bundle bundle1=new Bundle();
                bundle1.putSerializable("cc",(Serializable) date);
                i.putExtras(bundle1);
                i.putExtra("State",1);
                startActivityForResult(i,REQUEST_CODE1);

            }
        });

    }
    //将时间转换long数据

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            date2=(AllData)bundle.getSerializable("aa");

            TextView title=findViewById(R.id.title_detail);
            title.setText(date2.getTitleStr());
            TextView time=findViewById(R.id.time_detail);
            time.setText(date2.getTimeStr());

           // downTimer1.cancel();
            time1.end();
            final TextView countdown_detail = findViewById(R.id.countdown_detail);
            time2.init(date2.getDate());
            time2.setTextViewshow1(countdown_detail);
            time2.start();
            /*
            downTimer2 = new CountDownTimer(transformTime(date2.getDate()),1000) {

                @Override
                public void onTick(long millisUntilFinished) {


                    countdown_detail.setText("还有"+formatTime(millisUntilFinished));
                }

                //倒计时结束后的操作
                @Override
                public void onFinish() {
                    countdown_detail.setText("");

                }


            };
            downTimer2.start();

             */
        }
        if(resultCode==-2){
            date2=date;
        }
    }


    }


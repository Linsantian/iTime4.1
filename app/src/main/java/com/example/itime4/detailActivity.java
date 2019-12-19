package com.example.itime4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class detailActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 901;
    int  PictureResource;
    Bundle bundle;
    AllData date;
    CountDownTimer downTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // if(resultCode == RESULT_OK) {
        //Toast.makeText(detailActivity.this, "你是超级大傻瓜", Toast.LENGTH_SHORT).show();
        // switch (requestCode) {
        // case REQUEST_CODE:
        Intent data=getIntent();
        //Toast.makeText(detailActivity.this, "你是傻瓜", Toast.LENGTH_SHORT).show();
        //String gettitleStr = data.getStringExtra("标题名字");
        //String getDateStr = data.getStringExtra("时间");
        //String getPictureStr = data.getStringExtra("图片");
        bundle = data.getExtras();
        date = (AllData) bundle.getSerializable("aa");
        TextView title_detail=findViewById(R.id.title_detail);
        title_detail.setText(date.getTitleStr());
        TextView time_detail= findViewById(R.id.time_detail);
        time_detail.setText(date.getTimeStr());
        Context cet = getBaseContext();
        PictureResource = getResources().getIdentifier(date.getPicture_Str(),"drawable",cet.getPackageName());//字符换图片
        ImageView picture_detail=findViewById(R.id.picture_detail);
        picture_detail.setImageResource(PictureResource);
        final TextView countdown_detail = findViewById(R.id.countdown_detail);
        downTimer = new CountDownTimer(transformTime(date.getDate()),1000) {

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
        downTimer.start();
        //  break;
        //   }
        // }
    }
    //将时间转换long数据
    public long transformTime(java.util.Date chooseTime){
        //获取当前系统时间
        java.util.Date date = new java.util.Date((System.currentTimeMillis()));
        long result = chooseTime.getTime()-date.getTime();
        return result;
    }
    //返回格式化的日期和时间
    public String formatTime(long millisecond) {
        long day = (long) ((millisecond)/1000/60/60/24);
        long hour = (long) ((millisecond-(day*24*60*60*1000))/1000/60/60);
        long minute = (long) ((millisecond-(day*24*60*60*1000)-(hour*60*60*1000))/1000/60);
        long second = (long) (((millisecond-(day*24*60*60*1000)-(hour*60*60*1000)-(minute*1000*60))/1000)%60);

        if(day==0){
            if(hour==0){
                if (minute==0){
                    return second + "秒";
                }else {
                    return minute + "分" + second + "秒";
                }
            }else {
                return hour+"时" + minute + "分" + second + "秒";
            }
        }else {
            return day + "天" + hour + "时" + minute + "分" + second + "秒";
        }
    }
    }

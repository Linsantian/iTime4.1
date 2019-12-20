package com.example.itime4;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Time {
    private  long day;
    private  long hours;
    private  long minter;
    private  long second;
    private Timer myTimer;
    private Date date = new Date((System.currentTimeMillis()));
    private boolean state;
    TextView textViewshow1,textViewshow2;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                computeTime();
                if (second==0&&minter==0&&hours==0&&day==0){
                    state=false;
                }

                if(state == false) {
                    if (day==0&&hours==0&&minter==0) {

                            textViewshow1.setText("已经"+second+"秒");
                        if(textViewshow2!=null){
                        textViewshow2.setText("已经" + '\n' + second + "秒");
                        }
                    }
                    else if(day==0&&hours==0) {
                        textViewshow1.setText("已经"+minter+"分钟"+second+"秒");
                        if(textViewshow2!=null) {
                            textViewshow2.setText("已经" + '\n' + minter + "分钟");
                        }
                    }
                    else if(day==0) {
                        textViewshow1.setText("已经"+hours+"小时"+minter+"分钟"+second+"秒");
                        if(textViewshow2!=null) {
                            textViewshow2.setText("已经" + '\n' + hours + "小时");
                        }
                    }
                    else {
                        textViewshow1.setText("已经"+day+"天"+hours+"小时"+minter+"分钟"+second+"秒");
                        if(textViewshow2!=null) {
                            textViewshow2.setText("已经" + '\n' + day + "天");
                        }
                    }
                }
                else if(state==true){
                    if (day==0&&hours==0&&minter==0) {
                        textViewshow1.setText("只剩"+second+"秒");
                        if(textViewshow2!=null) {
                            textViewshow2.setText("只剩" + '\n' + second + "秒");
                        }
                    }
                    else if(day==0&&hours==0){
                        textViewshow1.setText("只剩"+minter+"分钟"+second+"秒");
                        if(textViewshow2!=null) {
                            textViewshow2.setText("只剩" + '\n' + minter + "分钟");
                        }
                    }
                    else if(day==0){
                        textViewshow1.setText("只剩"+hours+"小时"+minter+"分钟"+second+"秒");
                        if(textViewshow2!=null) {
                            textViewshow2.setText("只剩" + '\n' + hours + "小时");
                        }
                    }
                    else {
                        textViewshow1.setText("只剩"+day+"天"+hours+"小时"+minter+"分钟"+second+"秒");
                        if(textViewshow2!=null) {
                            textViewshow2.setText("只剩" + '\n' + day + "天");
                        }
                    }

                }

            }
        }
    };


    public Time(){}

    public  void  start(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what=1;
                handler.sendMessage(message);
            }
        };
        myTimer.schedule(timerTask,0,1000);
    }

    public  void end(){
        if(myTimer!=null)
            myTimer.cancel();
    }
//计算
    private  void computeTime(){
        if (state==true){
            second--;
            if(second<0){
                second=59;
                minter--;
                if(minter<0){
                    minter=59;
                    hours--;
                    if(hours<0){
                        hours=23;
                        day--;
                        if(day<0){
                            second=0;
                            minter=0;
                            hours=0;
                            day=0;
                        }
                    }
                }
            }
        }
        else if(state==false){
            second++;
            if(second>59){
                second=0;
                minter++;
                if(minter>59){
                    minter=0;
                    hours++;
                    if(hours>23){
                        hours=0;
                        day++;
                    }
                }
            }
        }
    }
//初始
    public void init(Date date1){
        long result;
        if(date1.getTime()>date.getTime()) {
            result = date1.getTime() - date.getTime();
            state=true;
        }
        else {
            result = date.getTime() - date1.getTime();
            state=false;
        }
        result/=1000;

        second=result%60;
        minter=(result/60)%60;
        hours=(result/3600)%24;
        day=(result/86400);

        myTimer = new Timer();
    }
//设置viewpager文本
    public void setTextViewshow1(TextView textViewshow) {
        this.textViewshow1 = textViewshow;
    }
//设置listview文本
    public void setTextViewshow2(TextView textViewshow) {
        this.textViewshow2 = textViewshow;
    }
}

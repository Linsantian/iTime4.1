package com.example.itime4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Date;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class selection extends AppCompatActivity {

    private FloatingActionButton backButton;
    private ArrayList<Item> selfItem;
    private ListView SelfListView;
    private String Date_ymd;
    Bitmap bitmap;
    Date date;
    AllData allData = new AllData();
    spq selfSpq;
    long number_before,number_after;
    Calendar calendarzdy,calendarlgq;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);



        selfItem = new ArrayList<>();
        selfItem.add(new Item(R.drawable.yqcr,"日期","长按使用日期计算器"));
        selfItem.add(new Item(R.drawable.yqcr2,"重复设置","无"));
        selfItem.add(new Item(R.drawable.yqcr3,"图片",""));
        selfItem.add(new Item(R.drawable.yqcr4,"添加标签",""));
        selfSpq = new spq(this,R.layout.layout,selfItem);
        SelfListView = findViewById(R.id.listview);
        SelfListView.setAdapter(selfSpq);


        Intent data=getIntent();
        if(data.getIntExtra("color", -1)!=0) {
            ImageView imageView = findViewById(R.id.layout_background);
            imageView.setBackgroundColor(data.getIntExtra("color", -1));
        }
            if(data.getIntExtra("State",-1) == 1){

                Bundle bundle = data.getExtras();
                AllData date1 = (AllData) bundle.getSerializable("cc");
                EditText title=findViewById(R.id.title_name);
                title.setText(date1.getTitleStr());
                EditText tip=findViewById(R.id.tipname);
                tip.setText(date1.getTipStr());
                Toast.makeText(selection.this,date1.getPicture_Str()+"333333333",Toast.LENGTH_SHORT).show();
                selfItem.get(0).setSmalltext(date1.getTimeStr());
                selfSpq.notifyDataSetChanged();
            }





//短按


        SelfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long ID) {
                switch (position){
                    case 0:
/*

                        //Toast.makeText(selection.this, "你是傻瓜", Toast.LENGTH_SHORT).show();
*/

                       // showTimePickDlg();
                        showDatePickDlg();
                        break;
                    case 1:
                        Toast.makeText(selection.this, "你是大傻瓜", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Intent intent = new Intent();

                        intent.setType("image/*");

                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(intent,1);

                       Toast.makeText(selection.this, "item2被点击", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(selection.this, "你是超级大大傻瓜", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
//长按
        SelfListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long ID) {
                switch(position) {
                    case 0:
                       Long();
                        break;

                }

                return true;
            }
        });

//返回按钮的点击事件
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连接两个不同的组件。
                // 在存放资源代码的文件夹下下，
                setResult(-2);
              finish();
            }
        });
//确认新添的点击事件
        FloatingActionButton yesButton=findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //数据传输
                EditText titlename = (EditText)findViewById(R.id.title_name);
                String titleStr = titlename.getText().toString();
                EditText tipname = (EditText) findViewById(R.id.tipname);
               String tipStr = tipname.getText().toString();

                TextView textview = (TextView)findViewById(R.id.datatip);
                Date_ymd=(String)textview.getText();
                //产生随机数
                Random random = new Random();
                int a_after_number=random.nextInt(10)+1;
                String picture_code = "a"+a_after_number;
                Intent i = new Intent();

                allData.setTitleStr(titleStr);
                allData.setTipStr(tipStr);

                allData.setPicture_Str(picture_code);
                if(date==null) {
                    Calendar cal = Calendar.getInstance();
                    date = new Date((cal.get(Calendar.YEAR) - 1900), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
                    Date_ymd=cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+cal.get(Calendar.DAY_OF_MONTH)+"日"+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.SECOND);
                }
                allData.setDate(date);
                allData.setTimeStr(Date_ymd);
                Bundle bundle = new Bundle();
                bundle.putSerializable("aa",(Serializable) allData);

                i.putExtras(bundle);
                //i.putExtra("标题名字",titleStr);
               //i.putExtra("备注",tipStr);
              // i.putExtra("时间",Date_ymd);
               //i.putExtra("图片",picture_code);
               //i.putExtra("选取的时间",date);
                //startActivity(i);
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }



    private class spq extends ArrayAdapter<Item> {
        private  int resourceId;

        public spq(@NonNull Context context, int resource, @NonNull List<Item> objects) {
            super(context, resource, objects);
            resourceId=resource;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater mInflater= LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(this.resourceId,null);

            ImageView img = (ImageView)item.findViewById(R.id.clock);
            TextView name = (TextView)item.findViewById(R.id.data);
            TextView price = (TextView)item.findViewById(R.id.datatip);

            Item self_item= this.getItem(position);

            img.setImageResource(self_item.getPictureSource());
            name.setText(self_item.getBigtext());
            price.setText(self_item.getSmalltext());

            return item;
        }
    }
//显示日期、时间选择器
    public void showDatePickDlg () {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(selection.this, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint({"ResourceType", "WrongViewCast"})
            @Override
            public void onDateSet(DatePicker view, final int year,final int monthOfYear, final int dayOfMonth) {
              //  Item.this.setSmalltext().setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                Item item1= selfItem.get(0);
                //final int yyy = monthOfYear++;
                item1.setSmalltext(year + "年" + (monthOfYear+1) + "月" + dayOfMonth+"日");
                selfSpq.notifyDataSetChanged();

                // Calendar calendar = Calendar.getInstance();
                final int finalMonthOfYear = monthOfYear;
                TimePickerDialog timePickerDialog = new TimePickerDialog(selection.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int Hours, int Minute) {
                        date = new Date((year-1900), monthOfYear,dayOfMonth,Hours,Minute,0);
                        Item item2= selfItem.get(0);
                        item2.appendSmalltext("  "+Hours + ":" + Minute);
                        //item2.setBigtext("  "+Hours + ":" + Minute );
                        selfSpq.notifyDataSetChanged();

                    }
                },calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),true);
                timePickerDialog.show();

            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
//显示时间选择器
public void showTimePickDlg(final Calendar calendar1){
    Item item1= selfItem.get(0);
    item1.setSmalltext(calendar1.get(Calendar.YEAR) + "年" + (calendar1.get(Calendar.MONTH)+1) + "月" + calendar1.get(Calendar.DAY_OF_MONTH)+"日");
    Calendar calendar = Calendar.getInstance();
    TimePickerDialog timePickerDialog = new TimePickerDialog(selection.this, new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int Hours, int Minute) {
            date = new Date((calendar1.get(Calendar.YEAR)-1900), calendar1.get(Calendar.MONTH),calendar1.get(Calendar.DAY_OF_MONTH),Hours,Minute,0);
            Item item2= selfItem.get(0);
            item2.appendSmalltext("  "+Hours + ":" + Minute);
            selfSpq.notifyDataSetChanged();
        }
    },calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),true);
    timePickerDialog.show();
}
//长按
public void Long() {
    final Dialog dia;
    LayoutInflater lay=LayoutInflater.from(this);
    View v1=lay.inflate(R.layout.longshow,null);
    AlertDialog.Builder bui=new AlertDialog.Builder(this);
    bui.setView(v1);
    dia= bui.create();
    dia.show();


    //设置对话框初始文本
    Calendar cal=Calendar.getInstance();
    final int y=cal.get(Calendar.YEAR);
    final int m=cal.get(Calendar.MONTH)+1;
    final int d=cal.get(Calendar.DAY_OF_MONTH);
    TextView textbefore=v1.findViewById(R.id.beforetextView);
    textbefore.setText(y+"年"+m+"月"+d+"日");
    TextView textafter=v1.findViewById(R.id.aftertextView);
    textafter.setText(y+"年"+m+"月"+d+"日");
    TextView textmid=v1.findViewById(R.id.midtextView);
    textmid.setText(y+"年"+m+"月"+d+"日");



//限制输入的位数
    final EditText editbefore=v1.findViewById(R.id.before);
    final TextView textbefore_change=v1.findViewById(R.id.beforetextView);
    editbefore.addTextChangedListener(new TextWatcher() {
        int before=0,after=0;
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!editbefore.getText().toString().trim().isEmpty())
                before = Integer.parseInt(editbefore.getText().toString().trim());
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(!editbefore.getText().toString().trim().isEmpty())
                after = Integer.parseInt(editbefore.getText().toString().trim());
            if (after<=0||after>9999)
                editbefore.setText(""+before);



            if(editbefore.getText().toString().equals("")){
                number_before=0;
            }
            else{
                number_before=Integer.parseInt(editbefore.getText().toString());

            }
            //Date date_b=new Date(y-1900,m,(d-number_before),0,0,0);
            //textbefore_change.setText(date_b.getYear()+"年"+date_b.getMonth()+"月"+date_b.getDay()+"日");
            calendarzdy = Calendar.getInstance();
            long oldTime = calendarzdy.getTimeInMillis();
            long newTime = oldTime - (number_before*1000*60*60*24);
            calendarzdy.setTimeInMillis(newTime);
            textbefore_change.setText(calendarzdy.get(Calendar.YEAR)+"年"+(calendarzdy.get(Calendar.MONTH)+1)+"月"+calendarzdy.get(Calendar.DAY_OF_MONTH)+"日");
        }




    });
    final EditText editafter=v1.findViewById(R.id.after);
    final TextView textafter_change=v1.findViewById(R.id.aftertextView);
    editafter.addTextChangedListener(new TextWatcher() {
        int before=0,after=0;
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!editafter.getText().toString().trim().isEmpty())
                before = Integer.parseInt(editafter.getText().toString().trim());
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(!editafter.getText().toString().trim().isEmpty())
                after = Integer.parseInt(editafter.getText().toString().trim());
            if (after<=0||after>9999)
                editafter.setText(""+before);
            if(editafter.getText().toString().equals("")){
                number_after=0;
            }
            else{
                number_after=Integer.parseInt(editafter.getText().toString());

            }
            //Date date_b=new Date(y-1900,m,(d-number_before),0,0,0);
            //textbefore_change.setText(date_b.getYear()+"年"+date_b.getMonth()+"月"+date_b.getDay()+"日");
            calendarlgq = Calendar.getInstance();
            long oldTime = calendarlgq.getTimeInMillis();
            long newTime = oldTime + (number_after*1000*60*60*24);
            calendarlgq.setTimeInMillis(newTime);
            textafter_change.setText(calendarlgq.get(Calendar.YEAR)+"年"+(calendarlgq.get(Calendar.MONTH)+1)+"月"+calendarlgq.get(Calendar.DAY_OF_MONTH)+"日");
        }
    });
    //设置选择时间点击事件
    Button choose_before=v1.findViewById(R.id.choose_before);
    choose_before.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showTimePickDlg(calendarzdy);
            dia.dismiss();
        }
    });
    //设置选择时间点击事件
    Button choose_after=v1.findViewById(R.id.choose_after);
    choose_after.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showTimePickDlg(calendarlgq);
            dia.dismiss();
        }
    });
    //设置取消按钮
    Button cancel=v1.findViewById(R.id.cancel);
    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
            Log.e("uri",uri.toString());
            ContentResolver cr = this.getContentResolver();
            Toast.makeText(this,"图片的路径为"+uri.toString(),Toast.LENGTH_LONG).show();
            try{
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                //Log.d("seletion","获得的图片"+(bitmap.getByteCount()/1024)+"KB");
                Toast.makeText(this,"获得的图片大小为："+(bitmap.getByteCount()/1024)+"KB",Toast.LENGTH_LONG).show();
                ImageView imageView =  findViewById(R.id.layout_background);

                imageView.setImageBitmap(bitmap);
                Toast.makeText(this,"图片被赋值",Toast.LENGTH_LONG).show();
            }catch(FileNotFoundException e){
                Log.e("Exception",e.getMessage(),e);
            }

        }


    }
    //将当时时间转换long数据
    public long transformTime(int number_day){
        //获取当前系统时间
        java.util.Date date = new java.util.Date((System.currentTimeMillis()));

        long result = date.getTime()+number_day*24*60*60*1000;

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

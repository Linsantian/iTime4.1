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
import android.content.res.ColorStateList;
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
import java.util.Objects;
import java.util.Random;

public class selection extends AppCompatActivity {

    private FloatingActionButton backButton;
    private ArrayList<Item> selfItem;
    private ArrayList<repetitionItem> repetitionItemArrayList;
    private ListView SelfListView,Repetition_listview;
    private String Date_ymd,picture_code;
    Bitmap bitmap;
    Date date;
    AllData allData = new AllData();
    spq selfSpq;
    repetitionAdapter selfreprtitionAdapter;
    long number_before,number_after;
    Calendar calendarzdy,calendarlgq;
    long repetitionDay=0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);



        selfItem = new ArrayList<>();
        selfItem.add(new Item(R.drawable.c1,"日期","长按使用日期计算器"));
        selfItem.add(new Item(R.drawable.c2,"重复设置","无"));
        selfItem.add(new Item(R.drawable.c3,"图片",""));
        selfItem.add(new Item(R.drawable.c4,"添加标签",""));
        selfSpq = new spq(this,R.layout.layout,selfItem);
        SelfListView = findViewById(R.id.listview);
        SelfListView.setAdapter(selfSpq);
        //产生随机数
        Random random = new Random();
        int a_after_number=random.nextInt(10)+1;
        picture_code = "a"+a_after_number;
        Context cet = getBaseContext();
        int PictureResource = getResources().getIdentifier(picture_code,"drawable",cet.getPackageName());//字符换图片
        ImageView imageView1 = findViewById(R.id.layout_background);
        imageView1.setImageResource(PictureResource);


        Intent data=getIntent();
        if(data.getIntExtra("color", -1)!=0) {
            ImageView imageView = findViewById(R.id.layout_background);
            imageView.setBackgroundColor(data.getIntExtra("color", -1));
            FloatingActionButton but1=findViewById(R.id.backButton);
            but1.setBackgroundTintList(ColorStateList.valueOf(data.getIntExtra("color", -1)));
            FloatingActionButton but2=findViewById(R.id.yesButton);
            but2.setBackgroundTintList(ColorStateList.valueOf(data.getIntExtra("color", -1)));
        }
            if(data.getIntExtra("State",-1) == 1){

                Bundle bundle = data.getExtras();
                AllData date1 = (AllData) bundle.getSerializable("cc");
                EditText title=findViewById(R.id.title_name);
                title.setText(date1.getTitleStr());
                EditText tip=findViewById(R.id.tipname);
                tip.setText(date1.getTipStr());
                selfItem.get(0).setSmalltext(date1.getTimeStr());
                selfSpq.notifyDataSetChanged();
            }





//短按


        SelfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long ID) {
                switch (position){
                    case 0:
                        showDatePickDlg();
                        break;
                    case 1:
                        Repetition();
                        break;
                    case 2:
                        Intent intent = new Intent();

                        intent.setType("image/*");

                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(intent,1);

                        break;
                    case 3:
                        Label();
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
                EditText title_edit = (EditText)findViewById(R.id.title_name);
                if(title_edit.getText().toString().isEmpty())
                    Toast.makeText(selection.this,"标题不能为空",Toast.LENGTH_SHORT).show();
                else{
                //数据传输
                EditText titlename = (EditText)findViewById(R.id.title_name);
                String titleStr = titlename.getText().toString();
                EditText tipname = (EditText) findViewById(R.id.tipname);
               String tipStr = tipname.getText().toString();

                TextView textview = (TextView)findViewById(R.id.datatip);
                Date_ymd=(String)textview.getText();


                Intent i = new Intent();
                allData.setRepetitionDay(repetitionDay);
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
                setResult(RESULT_OK,i);
                finish();}
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
    private class repetitionAdapter extends ArrayAdapter<repetitionItem> {
        private  int resourceId;

        public repetitionAdapter(@NonNull Context context, int resource, @NonNull List<repetitionItem> objects) {
            super(context, resource, objects);
            resourceId=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater mInflater= LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(this.resourceId,null);


            TextView price = (TextView)item.findViewById(R.id.repetition_textview);

            repetitionItem self_item= this.getItem(position);

            price.setText(self_item.getString());

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
            dia.dismiss();
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

                Toast.makeText(this,"获得的图片大小为："+(bitmap.getByteCount()/1024)+"KB",Toast.LENGTH_LONG).show();
                ImageView imageView =  findViewById(R.id.layout_background);

                imageView.setImageBitmap(bitmap);

            }catch(FileNotFoundException e){
                Log.e("Exception",e.getMessage(),e);
            }
        }
    }
    //设置重复设置
    public void Repetition(){
        final Dialog dia;
        LayoutInflater lay=LayoutInflater.from(this);
        View v1=lay.inflate(R.layout.repetition_layout,null);
        AlertDialog.Builder bui=new AlertDialog.Builder(this);
        bui.setView(v1);
        dia= bui.create();
        dia.show();

        repetitionItemArrayList = new ArrayList<>();
        repetitionItemArrayList.add(new repetitionItem("每年"));
        repetitionItemArrayList.add(new repetitionItem("每月"));
        repetitionItemArrayList.add(new repetitionItem("每周"));
        repetitionItemArrayList.add(new repetitionItem("自定义"));
        repetitionItemArrayList.add(new repetitionItem("无"));
        selfreprtitionAdapter = new repetitionAdapter(this,R.layout.repetition_layout_listview,repetitionItemArrayList);
        Repetition_listview = v1.findViewById(R.id.repetition_listview);
        Repetition_listview.setAdapter(selfreprtitionAdapter);
        Repetition_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long ID) {
                Item item1= selfItem.get(1);
                switch (position){
                    case 0:
                        item1.setSmalltext("每年");
                       repetitionDay=365;

                        break;
                    case 1:
                        item1.setSmalltext("每月");
                        repetitionDay=30;
                        break;
                    case 2:
                        item1.setSmalltext("每周");
                        repetitionDay=7;
                        break;
                    case 3:
                        dia.dismiss();
                        final Dialog dia1;
                        LayoutInflater lay=LayoutInflater.from(selection.this);
                        View v2=lay.inflate(R.layout.custom_layout,null);
                        AlertDialog.Builder bui=new AlertDialog.Builder(selection.this);
                        bui.setView(v2);
                        dia1= bui.create();
                        dia1.show();

                        //限制输入的位数
                        final EditText custom_editText=v2.findViewById(R.id.custom_editText);
                        custom_editText.addTextChangedListener(new TextWatcher() {
                            int before=0,after=0;
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                if(!custom_editText.getText().toString().trim().isEmpty())
                                    before = Integer.parseInt(custom_editText.getText().toString().trim());
                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                if(!custom_editText.getText().toString().trim().isEmpty())
                                    after = Integer.parseInt(custom_editText.getText().toString().trim());
                                if (after<=0||after>9999)
                                    custom_editText.setText(""+before);

                                if(custom_editText.getText().toString().equals("")){
                                    repetitionDay=0;
                                }
                                else{
                                    repetitionDay=Long.parseLong(custom_editText.getText().toString());
                                    selfSpq.notifyDataSetChanged();
                                }
                            }
                        });
                        //设置确定按钮
                        Button choose_sure=v2.findViewById(R.id.sure_button_custom);
                        choose_sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Item item2= selfItem.get(1);
                                item2.setSmalltext(repetitionDay+"天");
                                dia1.dismiss();
                                selfSpq.notifyDataSetChanged();
                            }
                        });
                        item1.setSmalltext(repetitionDay+"天");
                        break;
                    case 4:
                        item1.setSmalltext("无");
                        repetitionDay=0;
                        break;
                }
                selfSpq.notifyDataSetChanged();
                dia.dismiss();
            }
        });


    }
    //设置标签设置
    public  void Label(){
        final Dialog dia;
        LayoutInflater lay=LayoutInflater.from(this);
        View v1=lay.inflate(R.layout.label_layout,null);
        AlertDialog.Builder bui=new AlertDialog.Builder(this);
        bui.setView(v1);
        dia= bui.create();
        dia.show();
//设置确定按钮
        Button choose_sure=v1.findViewById(R.id.button3);
        choose_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dia.dismiss();
            }
        });
        //设置取消按钮
        Button choose_cancel=v1.findViewById(R.id.button2);
        choose_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dia.dismiss();
            }
        });
    }
}

package com.example.itime4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 901;
    public static final int REQUEST_CODE1 = 902;
    private AppBarConfiguration mAppBarConfiguration;
    private ArrayList<View> pager_arraylist = new ArrayList<>();
    private ArrayList<mainitem> selfItem = new ArrayList<>();
    private viewpagerAdapter self_pagerAdapter = new viewpagerAdapter(pager_arraylist);
    ArrayList<AllData> theDatas=new ArrayList<>();
    Bundle bundle;
    private ListView SelfListView;
    private ViewPager Selfviewpager;
    MainActivity.mainAdapter selfSpq;
    int PictureResource=0,color_choose=0;
    private  ArrayList<Time> timeArrayList=new ArrayList<>();
    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    SaveData saveData;

    //Time time_viewpager=new Time();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitData();

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        selfSpq = new MainActivity.mainAdapter(this,R.layout.mainlayout,theDatas);
        SelfListView = findViewById(R.id.mainlistview);
        SelfListView.setAdapter(selfSpq);
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        //设置新添键的点击事件
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连接两个不同的组件。
                // 在存放资源代码的文件夹下下，
                Intent i = new Intent(MainActivity.this, selection.class);
                i.putExtra("color",color_choose);
                i.putExtra("State",0);
                // 启动
                startActivityForResult(i,REQUEST_CODE);
            }
        });
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.getMenu().getItem(3).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final Dialog dia;
                LayoutInflater lay=LayoutInflater.from(MainActivity.this);
                View v1=lay.inflate(R.layout.color_layout,null);
                AlertDialog.Builder bui=new AlertDialog.Builder(MainActivity.this);
                bui.setView(v1);
                dia= bui.create();
                dia.show();
                final ColorView colorView = v1.findViewById(R.id.color_bar);
                colorView.setToolbar(toolbar);
                colorView.setFloatingActionButton(fab);
                Button button_sure=v1.findViewById(R.id.sure_color);
                button_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_choose = colorView.getCurrentColor();
                        dia.dismiss();
                    }
                });
                Button button_cancel=v1.findViewById(R.id.cancel_color);
                button_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dia.dismiss();
                    }
                });
                //主动收回抽屉
                drawer.closeDrawers();
                return false;
            }
        });





/*        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = new NavController(this);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Init();
        Start();
        self_pagerAdapter = new MainActivity.viewpagerAdapter(pager_arraylist);
        Selfviewpager = findViewById(R.id.mainviewPager);
        Selfviewpager.setAdapter(self_pagerAdapter);
        self_pagerAdapter.notifyDataSetChanged();
        SelfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long ID) {
                onCreatDetail(position);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {      //新添

            switch (requestCode) {
               case REQUEST_CODE:


                    Bundle bundle = data.getExtras();
                    AllData date = (AllData) bundle.getSerializable("aa");

                    ADD(date);
                    break;
           }
            SelfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long ID) {
                onCreatDetail(position);
                }
            });

        }
        if(resultCode == RESULT_CANCELED){   //删除
            int position=data.getIntExtra("position",-1);
            Bundle bundle2;
            bundle2=data.getExtras();
            AllData date=(AllData)bundle2.getSerializable("ee");
            DELETE(date,data.getIntExtra("deleteposition",-1));
        }
        if(resultCode == RESULT_FIRST_USER){    //编辑重来
            int position=data.getIntExtra("position",-1);
            Bundle bundle2;
            bundle2=data.getExtras();
            AllData date3=(AllData)bundle2.getSerializable("dd");
            Toast.makeText(this, date3.getTitleStr()+"5555555", Toast.LENGTH_SHORT).show();

            DELETE(date3,position);
            ADD(date3);

            selfSpq.notifyDataSetChanged();
            self_pagerAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = new NavController(this);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
//重写主页面的适配器
    private class mainAdapter extends ArrayAdapter<AllData> {
        private int resourceId;

        public mainAdapter(@NonNull Context context, int resource, @NonNull List<AllData> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater mInflater = LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(this.resourceId, null);

            ImageView img = (ImageView) item.findViewById(R.id.listview_picture);
            TextView name = (TextView) item.findViewById(R.id.nametextView);
            TextView tip = (TextView) item.findViewById(R.id.tiptextView);
            TextView time = (TextView) item.findViewById(R.id.timetextView);
            TextView counttime = (TextView) item.findViewById(R.id.picturetextView);
            AllData self_item = this.getItem(position);

            Context cet = getBaseContext();
            int PictureResource2 = getResources().getIdentifier(self_item.getPicture_Str(),"drawable",cet.getPackageName());//字符换图片
            selfItem.add(new mainitem(PictureResource2, " ",self_item.getTitleStr(),self_item.getTimeStr(),self_item.getTipStr()));
            img.setImageResource(PictureResource2);
            name.setText(self_item.getTitleStr());
             tip.setText(self_item.getTipStr());
             time.setText(self_item.getTimeStr());
             counttime.setText(self_item.getPicture_Str());
            //设置倒计时
            //timeArrayList.get(position).init(theDatas.get(position).getDate());
            timeArrayList.get(position).setTextViewshow2(counttime);

            return item;
        }

    }
    //重写viewpager的适配器
    private class viewpagerAdapter extends PagerAdapter{
        private ArrayList<View> list;
        public viewpagerAdapter(ArrayList<View> list){
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view ==(View)object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if(list.size()!=0){
                View imageView=list.get(position%list.size());
                container.removeView(imageView);
            }
            else{
                container.removeViewAt(0);
            }
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
            ((ViewPager)container).removeView(list.get(position));
        }
    }
    //初始化viewpager
    private void Init(){
        pager_arraylist.clear();
        int i =0;

        if(theDatas.size()!=0)
        while(i<theDatas.size())
        {
            LayoutInflater mInflater = LayoutInflater.from(this);
            View item = mInflater.inflate(R.layout.viewpager_layout, null);
            ImageView img = item.findViewById(R.id.viewpager_picture);
            TextView name = item.findViewById(R.id.viewpager_title_name);
            TextView time = item.findViewById(R.id.viewpager_time);
            final TextView countdown = item.findViewById(R.id.viewpager_countdown);
            Context cet = getBaseContext();
            int PictureResource = getResources().getIdentifier(theDatas.get(i).getPicture_Str(),"drawable",cet.getPackageName());//字符换图片
            img.setImageResource(PictureResource);
            name.setText(theDatas.get(i).getTitleStr());
            time.setText(theDatas.get(i).getTimeStr());
                timeArrayList.get(i).init(theDatas.get(i).getDate());
                timeArrayList.get(i).setTextViewshow1(countdown);
                //time_viewpager.start();
                item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCreatDetail(pager_arraylist.indexOf(view));
                }
            });
            pager_arraylist.add(item);
            selfSpq.notifyDataSetChanged();
            i++;
        }
        self_pagerAdapter.notifyDataSetChanged();
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
    //设置点击listview和viewpager跳转到信息页面
    public  void onCreatDetail(int position){
        Intent i = new Intent(MainActivity.this, detailActivity.class);
        i.putExtra("position",position);
        i.putExtra("color",color_choose);
        Bundle bundle2 =new Bundle();
        AllData allData3 = new AllData();
        allData3.setTimeStr(theDatas.get(position).getTimeStr());
        allData3.setDate(theDatas.get(position).getDate());
        allData3.setTitleStr(theDatas.get(position).getTitleStr());
        allData3.setTipStr(theDatas.get(position).getTipStr());
        allData3.setPicture_Str(theDatas.get(position).getPicture_Str());
        bundle2.putSerializable("bb",allData3);
        i.putExtras(bundle2);
        //i.putExtra("标题名字",gettitleStr);
        //i.putExtra("时间",getDateStr);
        //i.putExtra("图片",getPictureStr);
        //setResult(RESULT_OK,i);
        // 启动
        startActivityForResult(i,REQUEST_CODE1);

    }
    //删除函数
    public void DELETE(AllData date,int delete){

        selfItem.remove(delete);
        theDatas.remove(delete);
        timeArrayList.remove(delete);
        Init();
        self_pagerAdapter = new MainActivity.viewpagerAdapter(pager_arraylist);
        Selfviewpager = findViewById(R.id.mainviewPager);
        Selfviewpager.setAdapter(self_pagerAdapter);
        self_pagerAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this,pager_arraylist.size()+"    bilibalabilibala",Toast.LENGTH_SHORT).show();
        self_pagerAdapter.notifyDataSetChanged();
        selfSpq.notifyDataSetChanged();
    }
    //增加函数
    public  void ADD (AllData date){
        theDatas.add(date);
        Time time=new Time();
        time.init(date.getDate());
        timeArrayList.add(time);
        Context cet = getBaseContext();
        int PictureResource1 = getResources().getIdentifier(date.getPicture_Str(),"drawable",cet.getPackageName());//字符换图片
        selfItem.add(new mainitem(PictureResource1, formatTime(transformTime(date.getDate())),date.getTitleStr(),date.getTimeStr(),date.getTipStr()));
        //selfSpq = new MainActivity.mainAdapter(this,R.layout.mainlayout,selfItem);
        //SelfListView = findViewById(R.id.mainlistview);
        //SelfListView.setAdapter(selfSpq);
/*
        final mainitem new1 = selfItem.get(selfItem.size()-1);


        downTimer = new CountDownTimer(transformTime(date.getDate()),1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                //TextView countdown_main = findViewById(R.id.picturetextView);
                //countdown_main.setText(countdownTip+formatTime(millisUntilFinished));
                new1.setPicturetext("还有"+formatTime(millisUntilFinished));
                selfSpq.notifyDataSetChanged();
            }

            //倒计时结束后的操作
            @Override
            public void onFinish() {
                TextView countdown_main = findViewById(R.id.picturetextView);
                countdown_main.setText("");

            }


        };
        downTimer.start();

 */
        time.start();
        selfSpq.notifyDataSetChanged();
        Init();
        self_pagerAdapter = new MainActivity.viewpagerAdapter(pager_arraylist);
        Selfviewpager = findViewById(R.id.mainviewPager);
        Selfviewpager.setAdapter(self_pagerAdapter);
        self_pagerAdapter.notifyDataSetChanged();

    }


//保存数据
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveData.saveAllDate();
    }
    //保存数据
    @Override
    protected void onPause() {
        saveData.saveAllDate();
        super.onPause();
    }



    //打开app后获取之前的数据
    private void InitData() {
        saveData=new SaveData(this);
        theDatas=saveData.loadAllData();
        int i=0;
        while (i<theDatas.size()) {
            timeArrayList.add(new Time());
            i++;
        }
    }

    private void Start(){
        int i = 0;
        while(i<timeArrayList.size()){
            timeArrayList.get(i).start();
            i++;
        }
    }
}

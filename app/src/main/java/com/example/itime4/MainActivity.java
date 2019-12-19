package com.example.itime4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

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
    ArrayList<AllData> theDatas = new ArrayList<>();
    AllData date;
    Bundle bundle;
    CountDownTimer downTimer,downTimer2;
    private ListView SelfListView;
    private ViewPager Selfviewpager;
    String countdownTip="还有";
    MainActivity.mainAdapter selfSpq;
    int PictureResource=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        selfSpq = new MainActivity.mainAdapter(this,R.layout.mainlayout,selfItem);
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
        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连接两个不同的组件。
                // 在存放资源代码的文件夹下下，
                Intent i = new Intent(MainActivity.this, selection.class);
                i.putExtra("State",0);
                // 启动
                startActivityForResult(i,REQUEST_CODE);
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



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {

            switch (requestCode) {
               case REQUEST_CODE:
                   // gettitleStr = data.getStringExtra("标题名字");
                   // String gettipStr = data.getStringExtra("备注");
                    //getDateStr = data.getStringExtra("时间");
                    //getPictureStr = data.getStringExtra("图片");
                    //date = data.getStringArrayExtra("选取的时间");
                    //date = (AllData)data.getSerializableExtra("aa");
                    bundle = data.getExtras();
                    date = (AllData) bundle.getSerializable("aa");
                   Toast.makeText(this, date.getPicture_Str()+"11111111", Toast.LENGTH_SHORT).show();
                    ADD(date);
                    /*
                    Context cet = getBaseContext();
                    PictureResource = getResources().getIdentifier(date.getPicture_Str(),"drawable",cet.getPackageName());//字符换图片


                    //Toast.makeText(this, "  成功了吗？   "+gettitleStr, Toast.LENGTH_SHORT).show();
                    selfItem.add(new mainitem(PictureResource, countdownTip+formatTime(transformTime(date.getDate())),date.getTitleStr(),date.getTimeStr(),date.getTipStr()));
                    //selfSpq = new MainActivity.mainAdapter(this,R.layout.mainlayout,selfItem);
                    //SelfListView = findViewById(R.id.mainlistview);
                    //SelfListView.setAdapter(selfSpq);

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


                    selfSpq.notifyDataSetChanged();

                    Init();
                    self_pagerAdapter = new MainActivity.viewpagerAdapter(pager_arraylist);
                    Selfviewpager = findViewById(R.id.mainviewPager);
                    Selfviewpager.setAdapter(self_pagerAdapter);
                    self_pagerAdapter.notifyDataSetChanged();

                     */



                    break;
           }
            SelfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long ID) {
                onCreatDetail(position);
                }
            });

        }
        if(resultCode == RESULT_CANCELED){

            DELETE(data.getIntExtra("deleteposition",-1));
        }
        if(resultCode == RESULT_FIRST_USER){
            int position=data.getIntExtra("position",-1);
            Bundle bundle2;
            bundle2=data.getExtras();
            AllData date3=(AllData)bundle2.getSerializable("dd");
            Toast.makeText(this, date3.getTitleStr()+"5555555", Toast.LENGTH_SHORT).show();

            DELETE(position);
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
    private class mainAdapter extends ArrayAdapter<mainitem> {
        private int resourceId;

        public mainAdapter(@NonNull Context context, int resource, @NonNull List<mainitem> objects) {
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
            mainitem self_item = this.getItem(position);

            img.setImageResource(self_item.getPictureSourse());
            name.setText(self_item.getNametext());
             tip.setText(self_item.getTiptext());
             time.setText(self_item.getTimetext());
             counttime.setText(self_item.getPicturetext());

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

        if(selfItem.size()!=0)
        while(i<selfItem.size())
        {
            LayoutInflater mInflater = LayoutInflater.from(this);
            View item = mInflater.inflate(R.layout.viewpager_layout, null);
            ImageView img = item.findViewById(R.id.viewpager_picture);
            TextView name = item.findViewById(R.id.viewpager_title_name);
            TextView time = item.findViewById(R.id.viewpager_time);
            final TextView countdown = item.findViewById(R.id.viewpager_countdown);
            img.setImageResource(selfItem.get(i).getPictureSourse());
            name.setText(selfItem.get(i).getNametext());
            time.setText(selfItem.get(i).getTimetext());
            //countdown.setText(selfItem.get(i).getPicturetext());
            downTimer2 = new CountDownTimer(transformTime(date.getDate()),1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    // TextView countdown_main = findViewById(R.id.picturetextView);
                    // countdown_main.setText(countdownTip+formatTime(millisUntilFinished));
                    //selfItem.get(j).setPicturetext(formatTime(millisUntilFinished));
                    countdown.setText(formatTime(millisUntilFinished));
                    selfSpq.notifyDataSetChanged();
                }

                //倒计时结束后的操作
                @Override
                public void onFinish() {
                    TextView countdown_main = findViewById(R.id.picturetextView);
                    countdown_main.setText("");

                }


            };
            downTimer2.start();
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
        if (result>0)
            countdownTip="还有";
        else
            countdownTip="已经";
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
    public void DELETE(int delete){

        selfItem.remove(delete);
        theDatas.remove(delete);
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
        Context cet = getBaseContext();
       PictureResource = getResources().getIdentifier(date.getPicture_Str(),"drawable",cet.getPackageName());//字符换图片


        //Toast.makeText(this, "  成功了吗？   "+gettitleStr, Toast.LENGTH_SHORT).show();
        selfItem.add(new mainitem(PictureResource, countdownTip+formatTime(transformTime(date.getDate())),date.getTitleStr(),date.getTimeStr(),date.getTipStr()));
        //selfSpq = new MainActivity.mainAdapter(this,R.layout.mainlayout,selfItem);
        //SelfListView = findViewById(R.id.mainlistview);
        //SelfListView.setAdapter(selfSpq);

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


        selfSpq.notifyDataSetChanged();

        Init();
        self_pagerAdapter = new MainActivity.viewpagerAdapter(pager_arraylist);
        Selfviewpager = findViewById(R.id.mainviewPager);
        Selfviewpager.setAdapter(self_pagerAdapter);
        self_pagerAdapter.notifyDataSetChanged();

    }
}

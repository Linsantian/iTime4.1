package com.example.itime4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class selection extends AppCompatActivity {

    private FloatingActionButton backButton;
    private ArrayList<Item> selfItem;
    private ListView SelfListView;
    spq selfSpq;

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
                        Toast.makeText(selection.this, "你是超级大傻瓜", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(selection.this, "zdy牛逼", Toast.LENGTH_SHORT).show();
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
              finish();           }           });


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

    public void showDatePickDlg () {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(selection.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
              //  Item.this.setSmalltext().setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                Item item1= selfItem.get(0);
                monthOfYear++;
                item1.setSmalltext(year + "-" + monthOfYear + "-" + dayOfMonth);
                selfSpq.notifyDataSetChanged();
                // Calendar calendar = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(selection.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int Hours, int Minute) {
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

/*public void showTimePickDlg(){
    Calendar calendar = Calendar.getInstance();
    TimePickerDialog timePickerDialog = new TimePickerDialog(selection.this, new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int Hours, int Minute) {
            Item item2= selfItem.get(0);
            item2.appendSmalltext("  "+Hours + ":" + Minute);
            //item2.setBigtext("  "+Hours + ":" + Minute );
            selfSpq.notifyDataSetChanged();
        }
    },calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),true);
    timePickerDialog.show();
}*/


}

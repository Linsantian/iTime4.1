package com.example.itime4;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveData {
        private Context context;
        private ArrayList<AllData> allData = new ArrayList<>();


        public SaveData(Context context) {
            this.context = context;
        }

        public ArrayList<AllData> getAllData(){
            return allData;
        }


        public void  saveAllDate(){
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(
                        context.openFileOutput("Serializable.txt",Context.MODE_PRIVATE)
                );
                outputStream.writeObject(allData);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public ArrayList<AllData> loadAllData(){
            try{
                ObjectInputStream inputStream = new ObjectInputStream(
                        context.openFileInput("Serializable.txt")
                );
                allData = (ArrayList<AllData>) inputStream.readObject();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return allData;
        }


    }


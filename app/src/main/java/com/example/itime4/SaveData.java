package com.example.itime4;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveData {

        private Context context;
        private int color = 0;
        private ArrayList<Time> allTime = new ArrayList<Time>();
        private ArrayList<AllData> allData = new ArrayList<>();

        public ArrayList<Time> getAllTime() {
            return allTime;
        }

        public void setAllTime(ArrayList<Time> allTime) {
            this.allTime = allTime;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public SaveData(Context context) {
            this.context = context;
        }

        public ArrayList<AllData> getTimes(){
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

        public void saveColor(){
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(
                        context.openFileOutput("Color.txt",Context.MODE_PRIVATE)
                );
                outputStream.writeObject(color);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public int loadColor() {
            try{
                ObjectInputStream inputStream = new ObjectInputStream(
                        context.openFileInput("Color.txt")
                );
                color = (int) inputStream.readObject();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return color;
        }


        public ArrayList<Time> loadTime() {
            try{
                ObjectInputStream inputStream = new ObjectInputStream(
                        context.openFileInput("Labels.txt")
                );
                allTime = (ArrayList<Time>) inputStream.readObject();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return allTime;
        }

        public void saveTime(){
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(
                        context.openFileOutput("Labels.txt",Context.MODE_PRIVATE)
                );
                outputStream.writeObject(allTime);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


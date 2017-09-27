package jankhan.healthkeeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by arifu on 6/22/2017.
 */

public class UserData implements Serializable{
    String name="Arifullah Jan";
    int age = 19;
    String sex = "male";
    int pin = 1111;
    String info = "---";
    List<Medicine> medicines;
    List<Doctor> doctors;
    List<TestReport> tests;
    List<String> filesAttached;
    List<DataVsDate<Integer>> heartRateLog;
    List<DataVsDate<Integer>> weightLog;


    public UserData() {
    }

    public UserData(String name, int age, String sex, int pin, String all) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.pin = pin;
        this.info=all;

        medicines = new ArrayList<Medicine>();
        tests = new ArrayList<TestReport>();
        doctors = new ArrayList<Doctor>();
        heartRateLog = new ArrayList<DataVsDate<Integer>>();
        filesAttached = new ArrayList<String>();
        List<DataVsDate<Integer>> weightLog = new ArrayList<DataVsDate<Integer>>();

    }

    public int getNumberOfDoctors() {
        return this.doctors.size();
    }



    public static class DataVsDate<s> implements Serializable{
        Date date;
        s value;

        public DataVsDate(Date date,s value) {
            Calendar calendar = Calendar.getInstance();
            //this.date = calendar.getTime();
            this.date = date;
            this.value = value;
        }
    }
}

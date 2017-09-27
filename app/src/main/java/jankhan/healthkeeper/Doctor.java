package jankhan.healthkeeper;

import java.io.Serializable;

/**
 * Created by arifu on 6/22/2017.
 */

public class Doctor implements Serializable {
    private String hospital;
    int id;
    String name;
    String info;
    int pin;

    public Doctor(String name, String hospital,String info, int pin ,UserData userData) {
        this.name = name;
        this.hospital = hospital;
        this.pin = pin;
        this.info = info;
        this.id= userData.doctors.size();//icrements id
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}

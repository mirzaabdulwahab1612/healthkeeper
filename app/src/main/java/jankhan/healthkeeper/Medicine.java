package jankhan.healthkeeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by arifu on 6/22/2017.
 */

public class Medicine implements Serializable {

    public final static int MEDIINE_SPOON = 0;
    public final static int MEDIINE_TABLET = 1;






    public int duration;//--
    public String name;//--
    public String comment;//
    public int quatitityPerTime;//--
    public int type;//--
    public boolean isForbidden;
    public boolean started;
    public Doctor suggestedBy;
    public Date startDate;
    public Date endDate;
    public MedicineTime medicineTime;
    public boolean alert = true;


    public Medicine(String name, int durationDays, String comment, Doctor suggestedBy, int quatitityPerTime,int type,MedicineTime medicineTime) {
        this.name = name;
        this.duration = durationDays;
        this.comment = comment;
        this.quatitityPerTime =  quatitityPerTime;
        this.suggestedBy = suggestedBy;
        this.type = type;

        this.started = false;
        this.isForbidden = false;
        this.startDate=null;
        this.endDate = null;
        this.medicineTime = medicineTime;





    }


    public static class MedicineTime implements Serializable {

        public static final int BREAKFAST = 1;
        public static final int LUNCH = 2;
        public static final int DINNER = 3;

        boolean after;
        boolean meals[];
        int everyXDays;

        public MedicineTime(int everyXDays,boolean[] meals, boolean after){
            this.meals=meals;
            this.after=after;
            this.everyXDays=everyXDays;
        }
    }






    public int daysLeft(){
        if(this.started){
            return duration-Utitlity.daysBetween(this.startDate,new Date());
        }
        return duration;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public int getQuatitityPerTime() {
        return quatitityPerTime;
    }

    public int getType() {
        return type;
    }

    public boolean isForbidden() {

        /// to do ...  check with respect to duration tooo
        return isForbidden;
    }

    public boolean isStarted() {
        return started;
    }

    public Doctor getSuggestedBy() {
        return suggestedBy;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public MedicineTime getMedicineTime() {
        return medicineTime;
    }

    public boolean isAlert() {
        return alert;
    }
}

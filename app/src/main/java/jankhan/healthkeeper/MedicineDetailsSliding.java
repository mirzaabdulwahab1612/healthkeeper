package jankhan.healthkeeper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pavlospt.roundedletterview.RoundedLetterView;
import com.klinker.android.sliding.SlidingActivity;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Arrays;
import java.util.List;

/**
 * Created by arifu on 6/30/2017.
 */

public class MedicineDetailsSliding extends SlidingActivity {

    public final static  String MEDICINE_INDEX="medicine_index";

    UserData userData;
    String s;
    int intt;
    boolean a;
    boolean b;
    boolean c;

    RotatingTextWrapper rotatingTextWrapper;
    Rotatable rotatable;


    @Override
    public void init(Bundle savedInstanceState) {


        Intent recievedIntent = getIntent();
        int tempI = recievedIntent.getIntExtra(MEDICINE_INDEX,0);
        userData = MainActivity.userData;
        Medicine focusedMedicine = userData.medicines.get(tempI);
        //disableHeader();
        //setTitle("Medicine: "+focusedMedicine.name);
        //enableFullscreen();


        setImage(R.drawable.diff_medicines);

        setPrimaryColors(
                getResources().getColor(R.color.bpLight_gray),
                getResources().getColor(R.color.bpblack)
        );

        setContent(R.layout.sliding_activity_med_details);


        ((TextView)findViewById(R.id.med_details_nameofmed)).setText(focusedMedicine.name);


        //Days left....................

        if(focusedMedicine.started){
//            DateTime datenow = new DateTime();
//            DateTime dateStarted  = new DateTime(focusedMedicine.startDate);
//            int a= focusedMedicine.duration-Days.daysBetween(dateStarted.toLocalDate(), datenow.toLocalDate()).getDays();
//            ((TextView)findViewById(R.id.tv_days_left)).setText(a);
            ((TextView)findViewById(R.id.tv_days_left_extra)).setText("Started on: "+ focusedMedicine.startDate.toString());
        }
        else{
            ((TextView)findViewById(R.id.tv_days_left)).setText(focusedMedicine.duration+"");
            ((TextView)findViewById(R.id.tv_days_left_extra)).setText("Not started yet");
        }



        ////////meals...............




        a = focusedMedicine.medicineTime.meals[0];
        b = focusedMedicine.medicineTime.meals[1];
        c = focusedMedicine.medicineTime.meals[2];





        intt=0;
        s="";

        if(focusedMedicine.medicineTime.meals[0]){
            s+=" Breakfast";
            intt++;
        }
        if(focusedMedicine.medicineTime.meals[1]){
            s+=" Lunch";
            intt++;
        }
        if(focusedMedicine.medicineTime.meals[2]){
            s+=" Dinner";
            intt++;
        }
        ((TextView)findViewById(R.id.tv_times_per_day)).setText(intt+"");


        /////////////spoooooooooons tablets.....
        String tempS=""+focusedMedicine.quatitityPerTime;
        if(focusedMedicine.type==Medicine.MEDIINE_TABLET){
            tempS+=" tablet";
        }
        else {
            tempS+=" spoon";
        }
        if(focusedMedicine.quatitityPerTime>1){
            tempS+="s";
        }
        ((TextView)findViewById(R.id.tv_quantiy_per_time)).setText(tempS);



        ////commmmmmenttttttt.....
        ((TextView)findViewById(R.id.tv_comment)).setText(focusedMedicine.comment);












    }


    private void displayRotatable(final boolean a,final boolean b, final boolean c){

        runOnUiThread(new Runnable() {
            public void run() {
                rotatable=new Rotatable(Color.parseColor("#FFA036"),1000,"error");

                rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.tv_times_per_day_extra);
                //rotatingTextWrapper.setSize(22);
                rotatable.setAnimationDuration(0);

                if(a && !b && !c){
                    rotatingTextWrapper.setContent("Breakfast only");

                }
                else if(!a && b && !c){
                    rotatingTextWrapper.setContent("Lunch only");

                }
                else if(!a && !b && c){

                    rotatingTextWrapper.setContent("Dinner only");

                }
                else if(a && b&& !c){
                    rotatable = new Rotatable(Color.parseColor("#FFA036"),1000,"breakfast","    lunch");
                    rotatable.setAnimationDuration(500);
                    rotatable.setInterpolator(new BounceInterpolator());
                    rotatingTextWrapper.setContent("", rotatable);

                }
                else if(a && !b && c){
                    rotatable = new Rotatable(Color.parseColor("#FFA036"),1000,"breakfast","   dinner");
                    rotatable.setAnimationDuration(500);
                    rotatable.setInterpolator(new BounceInterpolator());
                    rotatingTextWrapper.setContent("", rotatable);

                }
                else if(!a && b && c){
                    rotatable = new Rotatable(Color.parseColor("#FFA036"),1000,"lunch","dinner");
                    rotatable.setAnimationDuration(500);
                    rotatable.setInterpolator(new BounceInterpolator());
                    rotatingTextWrapper.setContent("", rotatable);

                }
                else if(a && b && c){
                    rotatable = new Rotatable(Color.parseColor("#FFA036"),1000,"breakfast","    lunch","   dinner");
                    rotatable.setAnimationDuration(500);
                    rotatable.setInterpolator(new BounceInterpolator());
                    rotatingTextWrapper.setContent("", rotatable);


                }
            }
        });
    }
    @Override
    public void onPause(){
        super.onPause();
        rotatingTextWrapper=null;

    }
    @Override
    public  void onResume(){
        super.onResume();

        displayRotatable(a,b,c);

    }
}

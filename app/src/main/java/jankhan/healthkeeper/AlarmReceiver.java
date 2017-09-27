package jankhan.healthkeeper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by arifu on 7/14/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    UserData userData;
    @Override
    public void onReceive(Context context, Intent intent) {

        userData = new Sync(context).getData();
//        if(anyMedicine()){
//            //notification.....
//        }

    }

//    private boolean anyMedicine() {
//
//        for(int i=0; i< userData.medicines.size();i++){
//            if(!userData.medicines.get(i).isForbidden()){
//                //if(userData.medicines.get(i).medicineTime);
//            }
//        }
//    }
}

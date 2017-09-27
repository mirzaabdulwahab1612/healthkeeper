package jankhan.healthkeeper;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GeneralHealth.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GeneralHealth#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneralHealth extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "GENERAL HEALTH" ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GraphView graphHR;
    private GraphView graphWT;
    TextView textViewName;
    TextView textViewAge;
    TextView textViewSex;
    TextView textViewInfo;



    Calendar calendar = Calendar.getInstance();
    LineGraphSeries<DataPoint> seriesWT;
    LineGraphSeries<DataPoint> seriesHR;
    UserData userData;




    private Context context;

    private OnFragmentInteractionListener mListener;

    public GeneralHealth() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GeneralHealth.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneralHealth newInstance(Context context,String param1, String param2) {
        GeneralHealth fragment = new GeneralHealth();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        fragment.context=context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_health, container, false);

        userData = MainActivity.userData;

        //FABs:

        FloatingActionButton fabHR= (FloatingActionButton) view.findViewById(R.id.general_fab_hr);
        fabHR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getActivity())
                                .setSmallIcon(R.drawable.ic_attach)
                                .setContentTitle("My notification")
                                .setContentText("Hello World!");
                // Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(getActivity(), MedicineAlarmActivity.class);

                // The stack builder object will contain an artificial back stack for the
                // started Activity.
                // This ensures that navigating backward from the Activity leads out of
                // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(MainActivity.class);
                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

                // mNotificationId is a unique integer your app uses to identify the
                // notification. For example, to cancel the notification, you can pass its ID
                // number to NotificationManager.cancel().
                mNotificationManager.notify(1, mBuilder.build());
            }
        });
        FloatingActionButton fabWT= (FloatingActionButton) view.findViewById(R.id.general_fab_wt);
        fabWT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWT();
            }
        });



        this.graphHR = (GraphView) view.findViewById(R.id.general_graph_hr);
        graphHR.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        //graphHR.setTitle("Resting Heartrate");
        this.graphWT = (GraphView) view.findViewById(R.id.general_graph_wt);
        graphWT.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        //graphWT.setTitle("Weight");



        /////PopulateTextfields
        textViewName = (TextView) view.findViewById(R.id.general_name);
        textViewAge = (TextView) view.findViewById(R.id.general_age);
        textViewSex = (TextView) view.findViewById(R.id.general_sex);
        textViewInfo = (TextView) view.findViewById(R.id.general_info);
        textViewName.setText(userData.name);
        textViewAge.setText(userData.age + " years old");
        textViewSex.setText(userData.sex);
        textViewInfo.append(userData.info);




//        calendar.add(Calendar.DATE, 1);
//        userData.heartRateLog.add(new UserData.DataVsDate<Integer>(calendar.getTime(),41));
//        calendar.add(Calendar.DATE, 1);
//        userData.heartRateLog.add(new UserData.DataVsDate<Integer>(calendar.getTime(),42));
//        calendar.add(Calendar.DATE, 1);
//        userData.heartRateLog.add(new UserData.DataVsDate<Integer>(calendar.getTime(),40));
//        calendar.add(Calendar.DATE, 1);
//        userData.heartRateLog.add(new UserData.DataVsDate<Integer>(calendar.getTime(),39));
//        calendar.add(Calendar.DATE, 1);
//        userData.heartRateLog.add(new UserData.DataVsDate<Integer>(calendar.getTime(),40));
        //drawGraphs();


        // Inflate the layout for this fragment
        return view;

    }
    private void getHR(){
        NumberPickerBuilder npb = new NumberPickerBuilder().setFragmentManager(getActivity().getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setLabelText("HeartRate ");
        npb.setDecimalVisibility(NumberPicker.INVISIBLE);
        npb.setMaxNumber(new BigDecimal(500));
        npb.setMinNumber(new BigDecimal(0));
        npb.setPlusMinusVisibility(NumberPicker.INVISIBLE);
        npb.setStyleResId(R.style.MyCustomBetterPickerTheme);
        npb.show();
        npb.addNumberPickerDialogHandler(new NumberPickerDialogFragment.NumberPickerDialogHandlerV2() {
            @Override
            public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {
                int numberPicked = number.intValue();

                userData.heartRateLog.add(new UserData.DataVsDate<Integer>(Calendar.getInstance().getTime(),numberPicked));
                calendar.add(Calendar.DATE, 1);
                if(userData.heartRateLog.get(userData.heartRateLog.size()-1).date.getDay()==calendar.getTime().getDay()){
                    int avg = (userData.heartRateLog.get(userData.heartRateLog.size()-1).value + numberPicked)/2;
                    userData.heartRateLog.get(userData.heartRateLog.size()-1).value = avg;
                }
                else {
                    DataPoint dp = new DataPoint(calendar.getTime(),numberPicked);
                    double lowest = seriesHR.getLowestValueX();
                    double highi = seriesHR.getHighestValueX();
                    seriesHR.appendData(dp,false,30);
                    double highf = seriesHR.getHighestValueX();
                    double diff = highf - highi;
                    graphHR.getViewport().setMaxX(seriesHR.getHighestValueX());
                    //graphHR.getViewport().setMinX(lowest+diff);
                }
                refreshGraph(graphHR);


            }
        });
    }
    private void getWT(){

        NumberPickerBuilder npb = new NumberPickerBuilder().setFragmentManager(getActivity().getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setLabelText("Weight ");
        npb.setDecimalVisibility(NumberPicker.INVISIBLE);
        npb.setMaxNumber(new BigDecimal(500));
        npb.setMinNumber(new BigDecimal(0));
        npb.setPlusMinusVisibility(NumberPicker.INVISIBLE);
        npb.setStyleResId(R.style.MyCustomBetterPickerTheme);
        npb.show();
        npb.addNumberPickerDialogHandler(new NumberPickerDialogFragment.NumberPickerDialogHandlerV2() {
            @Override
            public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {
                int numberPicked = number.intValue();
                userData.weightLog.add(new UserData.DataVsDate<Integer>(Calendar.getInstance().getTime(),numberPicked));
                calendar.add(Calendar.DATE, 1);
                if(userData.weightLog.get(userData.weightLog.size()-1).date.getDay()==calendar.getTime().getDay()){
                    int avg = (userData.weightLog.get(userData.weightLog.size()-1).value + numberPicked)/2;
                    userData.weightLog.get(userData.weightLog.size()-1).value = avg;
                }
                else{
                    DataPoint dp = new DataPoint(calendar.getTime(),numberPicked);
                    seriesWT.appendData(dp,false,30);
                    graphWT.getViewport().setMaxX(seriesWT.getHighestValueX());
                }
                refreshGraph(graphWT);
            }
        });
    }


    public void drawGraphs(){
        DataPoint datapointsHR[] = new DataPoint[userData.heartRateLog.size()];
        String a = "";
        for(int i=0; i<datapointsHR.length;i++){
            datapointsHR[i]= new DataPoint(userData.heartRateLog.get(i).date,userData.heartRateLog.get(i).value);
            a+=datapointsHR[i].getY()+" for date:"+datapointsHR[i].getX()+"\n";
        }
        Toast.makeText(getActivity(), a,
                Toast.LENGTH_LONG).show();
        seriesHR = new LineGraphSeries<>(datapointsHR);
        graphHR.addSeries(seriesHR);
        graphHR.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graphHR.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
        graphWT.getGridLabelRenderer().setHumanRounding(false);
        graphHR.getViewport().setScrollable(true);
        graphHR.getViewport().setXAxisBoundsManual(true);

        ////////////////////
        DataPoint datapointsWT[] = new DataPoint[userData.weightLog.size()];
        for(int i=0; i<datapointsWT.length;i++){
            datapointsWT[i]= new DataPoint(userData.weightLog.get(i).date,userData.weightLog.get(i).value);
        }
        seriesWT = new LineGraphSeries<>(datapointsWT);
        graphWT.addSeries(seriesWT);
        graphWT.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graphWT.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
        graphWT.getGridLabelRenderer().setHumanRounding(false);
        graphWT.getViewport().setScrollable(true);
        graphWT.getViewport().setXAxisBoundsManual(true);
//        calendar.add(Calendar.DATE, 1);
//        UserData.heartRateLog.add(new UserData.DataVsDate<Integer>(calendar.getTime(),41));
//        calendar.add(Calendar.DATE, 1);
//        UserData.heartRateLog.add(new UserData.DataVsDate<Integer>(calendar.getTime(),42));
//        calendar.add(Calendar.DATE, 1);
//        UserData.heartRateLog.add(new UserData.DataVsDate<Integer>(calendar.getTime(),40));
//        calendar.add(Calendar.DATE, 1);
//        UserData.heartRateLog.add(new UserData.DataVsDate<Integer>(calendar.getTime(),39));
//        calendar.add(Calendar.DATE, 1);
//        UserData.heartRateLog.add(new UserData.DataVsDate<Integer>(calendar.getTime(),40));
//
////        UserData.heartRateLog.add(new UserData.DataVsDate<Integer>(41));
////        UserData.heartRateLog.add(new UserData.DataVsDate<Integer>(42));
////        UserData.heartRateLog.add(new UserData.DataVsDate<Integer>(40));
////        UserData.heartRateLog.add(new UserData.DataVsDate<Integer>(42));
//
//
//        DataPoint dataPoint[] = new DataPoint[UserData.heartRateLog.size()];
//
//
//        for(int i=0;i<dataPoint.length;i++){
//            //UserData.heartRateLog.get(i).date.setDate(i);
//            dataPoint[i] = new DataPoint(UserData.heartRateLog.get(i).date, UserData.heartRateLog.get(i).value);
//        }
//
//        // you can directly pass Date objects to DataPoint-Constructor
//// this will convert the Date to double via Date#getTime()
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoint);
//
//        graphHR.addSeries(series);
//
//        // set date label formatter
//        graphHR.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
//        graphHR.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
//
//
//        // as we use dates as labels, the human rounding to nice readable numbers
//        // is not necessary
//        graphHR.getGridLabelRenderer().setHumanRounding(false);
    }

    private  void refreshGraph(GraphView graph){
        graphWT.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graphWT.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }




    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}

package jankhan.healthkeeper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.applidium.headerlistview.HeaderListView;
import com.applidium.headerlistview.SectionAdapter;
import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.riddhimanadib.formmaster.helper.FormBuildHelper;
import me.riddhimanadib.formmaster.model.FormElement;
import me.riddhimanadib.formmaster.model.FormHeader;
import me.riddhimanadib.formmaster.model.FormObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Medicines.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Medicines#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Medicines extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "MEDICINE FRGMNT";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Medicines.OnFragmentInteractionListener mListener;
    LayoutInflater inflater;



    private HeaderListView headerListView;



    List<Medicine> sections[];
    static UserData userData;

    Doctor a;
    Doctor b;
    Doctor c;

    public Medicines() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Medicines.
     */
    // TODO: Rename and change types and number of parameters
    public static Medicines newInstance(String param1, String param2) {
        Medicines fragment = new Medicines();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.fragment_medicines, container, false);
        headerListView =(HeaderListView) view.findViewById(R.id.medicines_list);
        userData = MainActivity.userData;
        if(headerListView!=null)
        Toast.makeText(getActivity(), "here", Toast.LENGTH_SHORT).show();
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.medecines_fab_new);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMedicine();
            }
        });

        if(userData.doctors.size()==0){

            a = new Doctor("Arifullah Jan","SEECS","info",1111,userData);
            userData.doctors.add(a);
            b = new Doctor("Fatima Khan","SEECS","info",1111,userData);
            userData.doctors.add(b);
            c = new Doctor("Ali","SEECS","info",1111,userData);
            userData.doctors.add(c);
        }
        else{
            a=userData.doctors.get(0);
            b=userData.doctors.get(1);
            c=userData.doctors.get(2);
        }

//        Medicine aaa= new Medicine("a", 20, "no comment", a, 2,Medicine.MEDIINE_SPOON,new Medicine.MedicineTime(new int[]{8,21},new int[]{1,4}));
//        Medicine bbb =new Medicine("b", 20, "no comment", b, 2,Medicine.MEDIINE_SPOON,new Medicine.MedicineTime(new int[]{8,21},new int[]{1,4}));
//        Medicine ccc =new Medicine("c", 20, "no comment", c, 2,Medicine.MEDIINE_SPOON,new Medicine.MedicineTime(new int[]{8,21},new int[]{1,4}));
//        userData.medicines.add(aaa);
//        userData.medicines.add(aaa);
//        userData.medicines.add(bbb);
//        userData.medicines.add(ccc);
//        userData.medicines.add(aaa);
//        userData.medicines.add(bbb);
//        userData.medicines.add(bbb);
//        userData.medicines.add(bbb);
//        userData.medicines.add(ccc);
//        userData.medicines.add(ccc);
//        userData.medicines.add(ccc);
//        userData.medicines.add(ccc);
//        userData.medicines.add(ccc);

        refresh();


        return view;
    }




    private void refresh() {
        int numberOfDoctors = userData.getNumberOfDoctors();
        Toast.makeText(getActivity(),numberOfDoctors+" "+ userData.medicines.size() , Toast.LENGTH_SHORT).show();
        sections = new ArrayList[numberOfDoctors];
        for(int i =0;i<numberOfDoctors;i++){
            sections[i]= new ArrayList<Medicine>();
        }
        for(int i =0; i<userData.medicines.size();i++){
            sections[userData.medicines.get(i).suggestedBy.id].add(userData.medicines.get(i));
        }
        makeList();


    }

    private void makeList() {
        headerListView.setAdapter(new SectionAdapter() {
            @Override
            public int numberOfSections() {
                return userData.doctors.size();
            }

            @Override
            public int numberOfRows(int section) {
                int temp=0;
                for(int i=0; i<userData.medicines.size();i++){
                    if(userData.medicines.get(i).suggestedBy.id == section){
                        temp++;
                    }
                }
                return temp;
            }

            @Override
            public Object getRowItem(int section, int row) {
                return null;
            }
            @Override
            public boolean hasSectionHeaderView(int section) {
                return true;
            }

            @Override
            public View getRowView(int section, int row, View convertView, ViewGroup parent) {
                if(convertView==null){
                    convertView = inflater.inflate(R.layout.list_item, null);
                }
                ((TextView)convertView.findViewById(R.id.list_item_text)).setText(sections[section].get(row).name);


                return convertView;
            }

            @Override
            public int getSectionHeaderViewTypeCount() {
                return 1;
            }

//            @Override
//            public int getSectionHeaderItemViewType(int section) {
//                return (section % 2);
//            }

            @Override
            public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.section_header,null);
                }
                convertView.setBackgroundColor(getResources().getColor(R.color.bpLight_gray));
                ((TextView)convertView.findViewById(R.id.section_header_text)).setText(userData.doctors.get(section).getName());
                return convertView;
            }

            @Override
            public void onRowItemClick(AdapterView<?> parent, View view, int section, int row, long id) {
                super.onRowItemClick(parent, view, section, row, id);
                //showMedicine(sections[section].get(row));

                int temp=0;
                for(int i=0; i<userData.medicines.size();i++){
                    if(userData.medicines.get(i)==sections[section].get(row)){
                        temp=i;
                    }
                }



                Intent intent = new Intent(getActivity(),MedicineDetailsSliding.class);
                intent.putExtra(MedicineDetailsSliding.MEDICINE_INDEX,temp);
                startActivity(intent);
                Toast.makeText(getActivity(), "Section " + section + " Row " + row, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showMedicine(Medicine medicine) {


    }








    private void newMedicine() {




        final View view =getActivity().getLayoutInflater().inflate(R.layout.dialog_new_medicine,null);









        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
//        view.setMinimumWidth((int)(width * 0.9f));



        final DialogPlus dialog = DialogPlus.newDialog(getActivity())
                .setContentHolder(new ViewHolder(view))
                //.setFooter(R.layout.footer)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.TOP)
                .setExpanded(true,(int)(height * 0.5f))
                .setCancelable(true)
                .create();
        dialog.show();




        //////BEFORAFTER
        final Spinner spinnerBeforAfter = (Spinner) view.findViewById(R.id.new_medicine_beforafter);
        ArrayAdapter<CharSequence> adapterBeforAfter = ArrayAdapter.createFromResource(getActivity(),
                R.array.menu_array_meals, android.R.layout.simple_spinner_item);
        adapterBeforAfter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBeforAfter.setAdapter(adapterBeforAfter);



        //////TYPE
        final Spinner spinnerType = (Spinner) view.findViewById(R.id.new_medicine_type);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(getActivity(),
                R.array.menu_array_type, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);



//




        ImageButton buttonDone = (ImageButton) view.findViewById(R.id.new_medicine_done);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        String name = ((EditText)view.findViewById(R.id.new_medicine_name)).getText().toString();
                        String comment = ((EditText)view.findViewById(R.id.new_medicine_comment)).getText().toString();
                        int duration = Integer.parseInt(((EditText)view.findViewById(R.id.new_med_duration)).getText().toString());
                        int quantity = Integer.parseInt(((EditText)view.findViewById(R.id.new_med_quantity)).getText().toString());
                        int everyXDays = Integer.parseInt(((EditText)view.findViewById(R.id.new_med_every_x_days)).getText().toString());
                        int type = spinnerType.getSelectedItemPosition();
                        boolean after=true;
                        if(spinnerBeforAfter.getSelectedItemPosition()==0){
                            after=false;
                        }




                        boolean meals[] = new boolean[3];
                        meals[0]= false;
                        meals[1]= false;
                        meals[2]= false;

                        if(((CheckBox)view.findViewById(R.id.new_med_chk1)).isChecked()){
                            meals[0]= true;
                        }
                        if(((CheckBox)view.findViewById(R.id.new_med_chk2)).isChecked()){
                            meals[1]= true;
                        }
                        if(((CheckBox)view.findViewById(R.id.new_med_chk3)).isChecked()){
                            meals[2]= true;
                        }

                        Medicine temp = new Medicine(name,duration,comment,a,quantity,type,new Medicine.MedicineTime(everyXDays,meals,after));
                        userData.medicines.add(temp);

                        Toast toast= Toast.makeText(getActivity(),
                                ""+userData.medicines.get(userData.medicines.size()-1).duration, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.NO_GRAVITY|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();

                        refresh();
                        dialog.dismiss();
                    }
                    catch (Exception e){

                        Toast toast= Toast.makeText(getActivity(),
                                "Invalid!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.NO_GRAVITY|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
            }
        });



//        builder.setTitle("Prescribe Medicine: ");
//
//        builder.setView(view);
//
//
//
//        final AlertDialog alertDialog = builder.show();
//
//        //keyboard didn't pop up
//        alertDialog.getWindow().clearFlags(
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                        |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//
//
//        Window window = alertDialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//
//        wlp.gravity = Gravity.TOP;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        window.setAttributes(wlp);
//
////        buttonForbid.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                userData.tests.remove(testReport);
////                alertDialog.dismiss();
////                refresh();
////            }
////        });
////        buttonAttach.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
////        buttonDone.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if(testReport.isdue){
////                    testReport.setResult(editText.getText().toString());
////                }
////
////
////                alertDialog.dismiss();
////                refresh();
////            }
////        });



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

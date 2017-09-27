package jankhan.healthkeeper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applidium.headerlistview.HeaderListView;
import com.applidium.headerlistview.SectionAdapter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import me.grantland.widget.AutofitTextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestReports.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestReports#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestReports extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Test Reports: ";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    LayoutInflater inflater;



    private HeaderListView headerListView;
    DialogPlus alertDialog;


    List<TestReport> sections[] = new List[2];
    TestReport focusedTestReport=null;
    static UserData userData;






    public TestReports() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestReports.
     */
    // TODO: Rename and change types and number of parameters
    public static TestReports newInstance(String param1, String param2) {
        TestReports fragment = new TestReports();
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
        View view = inflater.inflate(R.layout.fragment_test_reports, container, false);
        headerListView =(HeaderListView) view.findViewById(R.id.testreports_list);
        userData = MainActivity.userData;

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.testreports_fab_new);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Suggest a test:");

                // Set up the input
                final EditText input = new EditText(getActivity());
                input.setMaxLines(1);
                builder.setView(input);




                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userData.tests.add(new TestReport(input.getText().toString()));
                        refresh();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        refresh();


        return view;
    }



    public void refresh() {

//        {
//
//            //test
//            userData.tests.add(new TestReport("Test A"));
//            userData.tests.add(new TestReport("Test B"));
//            userData.tests.add(new TestReport("Test E"));
//            userData.tests.add(new TestReport("Test H"));
//            userData.tests.add(new TestReport("Test C"));
//            userData.tests.add(new TestReport("Test G"));
//            userData.tests.add(new TestReport("Test D"));
//            userData.tests.add(new TestReport("Test F"));
//            userData.tests.add(new TestReport("Test A"));
//            userData.tests.add(new TestReport("Test B"));
//            userData.tests.add(new TestReport("Test E"));
//            userData.tests.add(new TestReport("Test H"));
//            userData.tests.add(new TestReport("Test C"));
//            userData.tests.add(new TestReport("Test G"));
//            userData.tests.add(new TestReport("Test D"));
//            userData.tests.add(new TestReport("Test F"));
//            userData.tests.add(new TestReport("Test A"));
//            userData.tests.add(new TestReport("Test B"));
//            userData.tests.add(new TestReport("Test E"));
//            userData.tests.add(new TestReport("Test H"));
//            userData.tests.add(new TestReport("Test C"));
//            userData.tests.add(new TestReport("Test G"));
//            userData.tests.add(new TestReport("Test D"));
//            userData.tests.add(new TestReport("Test F"));
//            userData.tests.add(new TestReport("Test A"));
//            userData.tests.add(new TestReport("Test B"));
//            userData.tests.add(new TestReport("Test E"));
//            userData.tests.add(new TestReport("Test H"));
//            userData.tests.add(new TestReport("Test C"));
//            userData.tests.add(new TestReport("Test G"));
//            userData.tests.add(new TestReport("Test D"));
//            userData.tests.add(new TestReport("Test F"));
//            userData.tests.add(new TestReport("Test A"));
//            userData.tests.add(new TestReport("Test B"));
//            userData.tests.add(new TestReport("Test E"));
//            userData.tests.add(new TestReport("Test H"));
//            userData.tests.add(new TestReport("Test C"));
//            userData.tests.add(new TestReport("Test G"));
//            userData.tests.add(new TestReport("Test D"));
//            userData.tests.add(new TestReport("Test F"));
//            userData.tests.add(new TestReport("Test A"));
//            userData.tests.add(new TestReport("Test B"));
//            userData.tests.add(new TestReport("Test E"));
//            userData.tests.add(new TestReport("Test H"));
//            userData.tests.add(new TestReport("Test C"));
//            userData.tests.add(new TestReport("Test G"));
//            userData.tests.add(new TestReport("Test D"));
//            userData.tests.add(new TestReport("Test F"));
//            userData.tests.add(new TestReport("Test A"));
//            userData.tests.add(new TestReport("Test B"));
//            userData.tests.add(new TestReport("Test E"));
//            userData.tests.add(new TestReport("Test H"));
//            userData.tests.add(new TestReport("Test C"));
//            userData.tests.add(new TestReport("Test G"));
//            userData.tests.add(new TestReport("Test D"));
//            userData.tests.add(new TestReport("Test F"));
//
//            userData.tests.get(0).isdue = false;
//            userData.tests.get(1).isdue = false;
//            userData.tests.get(2).isdue = false;
//            userData.tests.get(3).isdue = false;
//            userData.tests.get(4).isdue = false;
//            userData.tests.get(5).isdue = false;
//            userData.tests.get(6).isdue = false;
//            userData.tests.get(7).isdue = false;
//            userData.tests.get(8).isdue = false;
//            userData.tests.get(9).isdue = false;
//            userData.tests.get(10).isdue = false;
//            userData.tests.get(11).isdue = false;
//            userData.tests.get(12).isdue = false;
//            userData.tests.get(13).isdue = false;
//            userData.tests.get(14).isdue = false;
//            userData.tests.get(15).isdue = false;
//            userData.tests.get(16).isdue = false;
//            userData.tests.get(17).isdue = false;
//            userData.tests.get(18).isdue = false;
//            userData.tests.get(19).isdue = false;
//            userData.tests.get(20).isdue = false;
//            userData.tests.get(21).isdue = false;
//            userData.tests.get(22).isdue = false;
//            userData.tests.get(23).isdue = false;
//
//        }



        sections[0] = new ArrayList<TestReport>();//todos
        sections[1]  = new ArrayList<TestReport>();//dones

        for (int i = 0; i < userData.tests.size(); i++) {
            TestReport temp = userData.tests.get(i);
            if (temp.isdue) {
                sections[0].add(temp);
            } else {
                sections[1].add(temp);
            }
        }

        makeList();

    }

    private void makeList() {
        headerListView.setAdapter(new SectionAdapter() {
            @Override
            public int numberOfSections() {
                return 2;
            }

            @Override
            public int numberOfRows(int section) {
                if(section == 0){//means todos..
                    return sections[0].size();
                }
                else if(section == 1){//means dones...
                    return sections[1].size();
                }

                return 0;
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
                ((TextView)convertView.findViewById(R.id.list_item_text)).setText(sections[section].get(row).nameoftest);


                return convertView;
            }

            @Override
            public int getSectionHeaderViewTypeCount() {
                return 2;
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




                switch (section) {
                    case 0:
                        ((TextView)convertView.findViewById(R.id.section_header_text)).setText("Tests Reports due:");
                        convertView.setBackgroundColor(getResources().getColor(R.color.bpLight_gray));
                        break;
                    case 1:
                        ((TextView)convertView.findViewById(R.id.section_header_text)).setText("Previous test reports:");
                        convertView.setBackgroundColor(getResources().getColor(R.color.bpBlue));
                        break;
                }
                return convertView;
            }

            @Override
            public void onRowItemClick(AdapterView<?> parent, View view, int section, int row, long id) {
                super.onRowItemClick(parent, view, section, row, id);
                focusedTestReport=sections[section].get(row);
                if(focusedTestReport.isdue){
                    getTestReport(focusedTestReport);
                }
                else {

                    int temp=0;
                    for(int i=0; i<userData.tests.size();i++){
                        if(userData.tests.get(i)==sections[section].get(row)){
                            temp=i;
                        }
                    }
                    try{
                        Intent intent = new Intent(getActivity(),TestReportDetailsSliding.class);
                        intent.putExtra(TestReportDetailsSliding.TESTREPORT_INDEX,temp);
                        startActivity(intent);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
//                getTestReport(sections[section].get(row));
                Toast.makeText(getActivity(), "Section " + section + " Row " + row, Toast.LENGTH_SHORT).show();
            }
        });
    }




    Uri uriToBeAttached = null;
    AutofitTextView tvFileAttached=null;
    void getTestReport(final TestReport testReport){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle("Test Report");


        View view =getActivity().getLayoutInflater().inflate(R.layout.dialog_testreport_result,null);


        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        view.setMinimumWidth((int)(width * 0.9f));
        //view.setMinimumHeight((int)(height * 0.9f));

        alertDialog = DialogPlus.newDialog(getActivity())
                .setContentHolder(new ViewHolder(view))
                //.setFooter(R.layout.footer)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.TOP)
                .setExpanded(true,(int)(height * 0.55f))
                .setCancelable(true)
                .create();
        alertDialog.show();






        final EditText editText = (EditText) view.findViewById(R.id.testreport_result_edittxt);

        ImageButton buttonDelete = (ImageButton) view.findViewById(R.id.testreport_result_delete);
        ImageButton buttonAttach =(ImageButton) view.findViewById(R.id.testreport_result_attach);
        ImageButton buttonDone = (ImageButton) view.findViewById(R.id.testreport_result_done);
        TextView titleTextView =(TextView) view.findViewById(R.id.testreportResultTitle);
        tvFileAttached = (AutofitTextView)view.findViewById(R.id.testreport_result_filename);
        titleTextView.setText("Test result: "+testReport.nameoftest);


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData.tests.remove(testReport);
                alertDialog.dismiss();
                refresh();
            }
        });
        buttonAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("application/pdf");
                    try {
                        startActivityForResult(intent,1111);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), "Please install a File Manager.",
                                Toast.LENGTH_SHORT).show();
                    }
            }
        });
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    focusedTestReport.attachFile(uriToBeAttached,userData,getActivity().getApplicationContext());
                    Toast.makeText(getActivity(),"file saved as: "+focusedTestReport.fileAttached.name,Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getActivity(),"file not found",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                testReport.setResult(editText.getText().toString());
                refresh();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == 1111 && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().

            if (resultData != null) {
                uriToBeAttached = resultData.getData();
                File temp = new File(uriToBeAttached.getPath());
                tvFileAttached.setText("File choosen: "+uriToBeAttached.toString());
                Log.i(TAG, "Uri: " + uriToBeAttached.toString());
            }
        }
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

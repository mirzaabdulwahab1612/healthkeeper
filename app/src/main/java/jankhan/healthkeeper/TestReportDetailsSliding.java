package jankhan.healthkeeper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.klinker.android.sliding.SlidingActivity;

import java.io.File;

import me.grantland.widget.AutofitTextView;

/**
 * Created by arifu on 7/7/2017.
 */

public class TestReportDetailsSliding extends SlidingActivity {


    public static final String TESTREPORT_INDEX = "index_testreport";
    UserData userData;
    String s;
    int intt;

    @Override
    public void init(Bundle savedInstanceState) {
//
        Intent recievedIntent = getIntent();
        int tempI = recievedIntent.getIntExtra(TESTREPORT_INDEX,0);
        userData = MainActivity.userData;
        final TestReport focusedTest = userData.tests.get(tempI);
        //disableHeader();
        //setTitle("Medical Test: "+focusedTest.nameoftest);
        //enableFullscreen();

//        setPrimaryColors(
//                getResources().getColor(R.color.bpLight_gray),
//                getResources().getColor(R.color.bpblack)
//        );

        setContent(R.layout.sliding_activity_testreport_details);



        ((AutofitTextView)findViewById(R.id.tr_details_nameoftest)).setText(focusedTest.nameoftest);
        ((TextView)findViewById(R.id.tr_details_date)).setText("Dated: "+ focusedTest.date.toString());
        ((TextView)findViewById(R.id.tr_details_testsummary)).setText(focusedTest.result);
        if(focusedTest.fileAttached.evenAttached){
            ((TextView)findViewById(R.id.tr_details_filename)).setText("Click to open "+focusedTest.fileAttached.name+".pdf");
        }
        else {
            ((TextView)findViewById(R.id.tr_details_filename)).setText("No file attached...");
        }

        ((LinearLayout)findViewById(R.id.tr_details_file)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File temp_file = new File(focusedTest.fileAttached.name);
                    String filePath = temp_file.getPath();
                    Uri data = Uri.parse("content://healthkeeper.provider/" + filePath);
                    String type = Utitlity.getMimeType(data.toString());
                    //Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(data, "application/pdf");
                    startActivity(intent);

                } catch (Exception e) {
                        Log.d("Internal Storage POC ", "No Supported Application found to open this file");
                    e.printStackTrace();
                    //Toast.makeText(this,"no file..."+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });



        ((ImageButton)findViewById(R.id.tr_details_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData.tests.remove(focusedTest);
                finish();
            }
        });

        ((ImageButton)findViewById(R.id.tr_details_done)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((ImageButton)findViewById(R.id.tr_details_edit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
